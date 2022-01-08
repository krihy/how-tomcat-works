package cn.dream.chapter8.life;

import java.util.EventObject;

/**
 * Component LifeCycle event
 */
public class LifeCycleEvent extends EventObject {

    private LifeCycle lifeCycle;

    private String type;

    private Object data;

    public LifeCycleEvent(LifeCycle lifeCycle, String type) {
        this(lifeCycle, type, (Object) null);
    }

    public LifeCycleEvent(LifeCycle lifeCycle, String type, Object data) {
        super(lifeCycle);
        this.lifeCycle = lifeCycle;
        this.type = type;
        this.data = data;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public String getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
