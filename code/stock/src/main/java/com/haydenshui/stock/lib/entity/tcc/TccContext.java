package com.haydenshui.stock.lib.entity.tcc;

import java.util.HashMap;
import java.util.Map;

public class TccContext {
    
    private final String xid;
    private final Map<String, Object> context = new HashMap<>();

    public TccContext(String xid) {
        this.xid = xid;
    }

    public String getXid() {
        return xid;
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        return clazz.cast(context.get(key));
    }

    public Map<String, Object> getContext() {
        return context;
    }
}
