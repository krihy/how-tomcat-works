package cn.dream.chapter8.container;

import cn.dream.chapter8.life.LifeCycleBase;

public abstract class ValveBase extends LifeCycleBase implements Valve, Contained {

    private Container container;

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }


}
