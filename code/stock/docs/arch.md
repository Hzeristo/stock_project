## 2. 服务器端设计

### 2.1 服务器架构

本部分将简述服务器端整体架构设计。本项目服务器端采用微服务架构，分为`Capital`，`Securities`，`Position`，`Trade`微服务，微服务间通过RocketMQ进行通信，使用TCC模式和redis进行分布式事务。每个微服务都可以分为4个层级：Controller层，Service层，Mapper层，Model层。

#### 2.1.1 层次结构

项目采用标准的Spring Boot框架，层次结构是Spring Boot框架的默认结构。需要注意各个层次结构组件都是**STATELESS**的，即**无状态**，组件只负责传入、处理并传出数据，不存储中间状态。

**1. Controller层：**

在服务器端整体架构中，Controller层负责处理传入前端的HTTP请求，根据请求中的数据执行必要的操作并返回适当的响应；同时负责用于对用户输入执行验证以及处理请求处理过程中可能发生的异常。Controller层是MVC架构模式的关键组件之一。

本项目中，每个微服务的Controller层都包含一个`@RestController`类，该类负责处理传入前端的HTTP请求，通过`HttpServletRequest`直接提取请求数据或直接在函数参数中声明所需的请求数据，并调用Service层中的方法来执行业务逻辑。

本项目中绝大部分异常在Controller层进行捕获并处理，以返回适当的HTTP响应。本项目服务器端可以将较低层次的异常逐层传递，最后都作为Controller层异常抛出，并使用`@ControllerAdvice`注解下的`GlobalExceptionHandler`统一处理。

同时，本项目中对用户权限的检测主要集中在Controller层。HTTP请求传入时首先通过`SecuritiesFilterChain`筛选并提取jwt标签，随后根据存入本地会话中，在`Controller`中通过`@PreAuthorize`注解来控制用户权限。

**2. Service层：**

在服务器端整体架构中，Service层负责执行业务逻辑操作，使用数据访问层与数据库进行交互，并将结果返回给控制器。

本项目中，每个微服务的Service层包括以下模块：

- `@Service`注解下的`Service`类：调用业务逻辑的第一端口，负责接收Controller请求，自行调用方法处理或交由服务层中的其他模块进行处理，并返回结果。Controller层调用Service层中对象时只会调用此类对象和Listener。

- `@RocketMQMessageListener`注解下的Listener类：用于接收RocketMQ消息，并调用Service层中对象进行处理。用于手动维护分布式数据库外键一致性和TCC事务分布一致性。Controller层调用Service层中对象时只会调用此类对象和`Service`类。

- `Component`注解下的处理模块：负责业务逻辑，但是只会被其他服务调用。此类模块包括诸如策略模式下的实际策略类、方便代码复用的抽象业务类、直接与数据库交互的子业务类等。

Service层中各个模块的交互是本项目的核心逻辑。


**3. Mapper层：**

Mapper层用于在应用程序的不同层之间进行数据转换。

本项目中直接把`Mapper`层抽离层级结构，实现为全局可用的静态工具类。工具类的职责包括DTO与`Entity`对象的转换、`org.springframework.beans.BeanUtils`功能的封装等。

**4. Model层：**

Model层用于定义应用程序的数据模型。

本项目直接使用标准的JPA框架来实现数据模型。实体类直接用`@Entity`标注；存储库直接使用JPA实现并用`@Repository`标注。考虑到本项目作为微服务架构项目数据库外键无法使用，数据库的一致性维护在Service层通过RocketMQ通信实现。

#### 2.1.2 微服务结构

本项目服务器端采用微服务架构，分为`Capital`，`Securities`，`Position`，`Trade`微服务，每个微服务独立运行，业务上相互依赖。

**1. `Securities`微服务**

证券账户微服务负责处理#证券账户模块职责和安全职责，包括：

**2. `Capital`微服务**

资金账户微服务负责处理#维护用户账户资金变更逻辑的职责，包括：

**3. `Position`微服务**

持仓账户微服务负责处理#维护用户持仓逻辑的职责，包括：

**4. `Trade`微服务**

交易微服务负责处理#交易生成，记录和#中交易撮合职责，包括：

### 2.2 服务端功能设计

#### 2.2.1 事件驱动架构

为实现服务间的解耦、提升系统扩展性与可靠性，本项目后端采用事件驱动架构。通过RocketMQ作为事件总线，各微服务通过发布和订阅消息事件的方式进行通信，替代了传统的同步调用。事件驱动架构避免了微服务间的同步阻塞问题，提高了系统在高并发和异步处理场景下的响应能力，同时也降低了服务之间的耦合度，便于服务的独立扩展与演进。

本项目的运行环境中部署了RocketMQ服务器，直接连接RocketMQ的`DefaultMQProducer`和`DefaultMQPushConsumer`实现消息发送和接收。

本项目封装了`RocketMQUtils`类，用于使用不同的producer进行消息发送。producer如下：

```
producers:
    trade: trade_producer_group
    capital: capital_producer_group
    position: position_producer_group
    securities: securities_producer_group
```

在项目初始化后，相关的`Config`和`Properties`类会自动读取配置中的静态producer和consumer，并使用`@PostConstruct`完成各个`RocketMQTemplate`的初始化，存入Map中供`RocketMQUtils`使用工厂模式调用。封装通用消息工具类、统一管理多个Producer是为了支持不同业务类型的消息发送，并提升可维护性。

本项目在各个微服务的Service层中通过`@RocketMQMessageListener`注解下的Listener类来接收RocketMQ消息，并调用Service层中对象进行处理。listener如下:

```
consumers:
    trade: trade_consumer_group
    capital: capital_consumer_group
    position: position_consumer_group
    securities: securities_consumer_group
```

RocketMQ 默认不阻止 Producer 所在服务的 Consumer 接收自己发送的消息。在本项目设计中，TCC事务取消阶段利用自消费特性调用本服务 cancel 对应函数，函数内部会默认检查自消费情况，阻止消息循环产生。

项目中消息按业务领域划分 Topic，并通过 Tag 表示不同操作类型（如 TRY/CONFIRM/CANCEL），各微服务 listener 根据 tag 分发不同的业务逻辑处理方法。

项目中，所有消息统一封装为`TransactionMessage<T>`类。消息结构如下：

```java
public class TransactionMessage<T> {   
    private Long xid;
    private String businessAction;   
    private TccContext context;
    private T payload;      
    private String errorCode;
    private String errorMessage;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();  
}
```
消息中的`xid`是项目程序现场生成的雪花ID，用于保证消息的幂等性。在执行需要事务一致性的操作时，`payload`会携带transaction DTO，DTO携带用于事务本身的`xid`。

为提升系统的健壮性，本项目中投递失败的消息会进行重试，重试次数默认为3次，重试间隔为1秒。重试次数过多会直接将消息标记为失败，进入死信队列，记录错误日志并通知管理员。管理员可手动重放消息或标记为失败单处理，避免异常消息影响业务流程。

#### 2.2.2 TCC事务一致性

本项目手动完成简单的TCC框架，通过RocketMQ事件驱动来串联TCC各阶段操作，相较于传统RPC调用方式，避免了服务间强耦合与同步阻塞问题，提升了系统容错能力与可伸缩性。

TCC（Try-Confirm-Cancel）的核心思想是先尝试改变数据库状态为中间态，如果没有问题才进行真正的修改，如果有问题则返回之前的状态。三个部分的拆解如下：

- Try：尝试将结果修改为中间态；
- Confirm：如果 Try 成功，就真正将状态修改为已处理；
- Cancel：如果 Try 失败，将中间态回滚至最初状态；

项目无法使用Seata TCC框架维护一致性，考虑到各方面因素本项目最终确定手动完成一个简单的TCC框架。

本项目中TCC事务的触发是完全事件驱动的，由各个微服务中Service层的`listener`控制。`listener`收到消息后根据消息的tag确定触发哪种事务方法，并根据事务方法执行结果发出控制消息。在RocketMQ服务高可用的前提下，这种控制方式可以保证TCC阶段能正常执行，同时保证事务方法的无状态特性，进而保证不同用户的并发访问能力。在这种情况下需要根据TCC上下文确定当前事务进行到了什么状态，并确定当前事务所涉及各个数据的当前值。

`TccContext`实现如下，用于记录当前事务的状态与数据，在微服务之间传递：

```java
public class TccContext {
    private final String xid;
    private final Map<String, Object> context = new HashMap<>();
    //and methods
}
```
各个微服务可以获取当前事务上下文，并在操作后更新上下文。`TccContext`通过RocketMQ消息体在各微服务之间传递，无需借助 HTTP header 或全局上下文容器，降低了实现复杂度并增强了系统解耦性。

本项目中并发控制使用了redission框架。redission框架实现了分布式锁，保证同一时刻只有一个用户能执行TCC事务。项目将redission配置集成为`@Bean`，将加锁/去锁的逻辑封装在`@Lock`切面中，使用Spring AOP控制。`@Lock`的结构：

```java
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface Lock {
    String lockKey();  // 锁的 key
    long waitTime() default 10L;  // 最大等待时间，单位秒
    long leaseTime() default 30L;  // 锁的自动释放时间，单位秒
}
```
在实际使用中，规定`lockKey`的结构是形如`"LOCK:TCC:<name>:{<dtoName>.id}"`的结构，且DTO必须是函数中的参数`arg[1]`。这样切面就可以自动提取函数的第二个参数生成锁的key，方便直接在函数上使用。

本项目中TCC事务幂等性控制使用了redis缓存。redis会使用当前处理阶段的事务信息和传入的消息信息生成key，将处理进度作为value缓存，形如：

```java
String <opsKey> = "Msg:" + <msg.xid> + "TCC:" + <ctx.xid> + ":position:" + <dto.getid()> + ":<ops>";
```
这样，不同阶段的方法都可以写入本阶段完成情况，供其他阶段查询。结合 Redisson 分布式锁与 Redis 缓存幂等机制，本项目在多用户并发下仍能安全执行 TCC 流程，避免了重复提交、乱序执行等问题，确保了操作的幂等性与原子性。

为保证在服务异常宕机或网络抖动下的恢复能力，项目结合 RocketMQ 的消息重试机制与幂等判断逻辑，实现了TCC各阶段方法的重入安全性。若 Confirm 或 Cancel 执行失败，消息会自动重试，确保最终一致性。

### 2.3 服务器处理流程

### 2.4 数据库设计

微服务架构的数据库中基本不会出现外键约束，所有一致性约束都是由不同微服务的Service层利用专用的 TOPIC 通信手动实现的。实际数据库中表结构如下：



