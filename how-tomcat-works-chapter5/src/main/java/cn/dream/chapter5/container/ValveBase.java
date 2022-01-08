package cn.dream.chapter5.container;

public abstract class ValveBase implements Valve, Contained{

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
