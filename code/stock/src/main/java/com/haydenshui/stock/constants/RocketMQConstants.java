package com.haydenshui.stock.constants;

public class RocketMQConstants {

    public static final String PRODUCER_TRADE = "${rocketmq-config.producers.trade}";
    public static final String PRODUCER_CAPITAL = "${rocketmq-config.producers.capital}";
    public static final String PRODUCER_POSITION = "${rocketmq-config.producers.position}";
    public static final String PRODUCER_SECURITIES = "${rocketmq-config.producers.securities}";

    public static final String CONSUMER_TRADE = "${rocketmq-config.consumers.trade}";
    public static final String CONSUMER_CAPITAL = "${rocketmq-config.consumers.capital}";
    public static final String CONSUMER_POSITION = "${rocketmq-config.consumers.position}";
    public static final String CONSUMER_SECURITIES = "${rocketmq-config.consumers.securities}";

    public static final String TOPIC_TRADE = "${rocketmq-config.topics.trade}";
    public static final String TOPIC_MATCH = "${rocketmq-config.topics.match}";
    public static final String TOPIC_POSITION = "${rocketmq-config.topics.position}";
    public static final String TOPIC_CAPITAL = "${rocketmq-config.topics.capital}";
    public static final String TOPIC_SECURITIES = "${rocketmq-config.topics.securities}";

    public static final String TAG_TRADE_CREATE = "${rocketmq-config.tags.trade-create}";
    public static final String TAG_TRADE_CONFIRM = "${rocketmq-config.tags.trade-confirm}";
    public static final String TAG_TRADE_CANCEL = "${rocketmq-config.tags.trade-cancel}";
    public static final String TAG_TRADE_VALIDITY_CHECK = "${rocketmq-config.tags.trade-validity-check}";
    public static final String TAG_CAPITAL_CHECK = "${rocketmq-config.tags.capital-check}";
    public static final String TAG_CAPITAL_VALIDITY_CHECK = "${rocketmq-config.tags.capital_validity-check}";
    public static final String TAG_CAPITAL_VALIDITY_CONFIRM = "${rocketmq-config.tags.capital_validity-confirm}";
    public static final String TAG_SECURITIES_CHECK = "${rocketmq-config.tags.securities-check}";

    private RocketMQConstants() {} // No instantiation
}