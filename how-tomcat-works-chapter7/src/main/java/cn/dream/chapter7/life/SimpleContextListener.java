package cn.dream.chapter7.life;

import cn.dream.chapter7.container.context.Context;

import java.util.Objects;

public class SimpleContextListener implements LifeCycleListener {
    @Override
    public void lifeCycleEvent(LifeCycleEvent event) {
        LifeCycle lifeCycle = event.getLifeCycle();
        if (lifeCycle instanceof Context) {
            if (Objects.equals(event.getType(), LifeCycle.START_EVENT)) {
                System.out.println("listen context " + lifeCycle.START_EVENT );
            }

            if (Objects.equals(event.getType(), LifeCycle.STOP_EVENT)) {
                System.out.println("listen context " + lifeCycle.STOP_EVENT );
            }
        }
    }
}
