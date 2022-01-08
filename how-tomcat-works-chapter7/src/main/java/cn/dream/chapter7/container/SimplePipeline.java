package cn.dream.chapter7.container;

import cn.dream.chapter7.connect.parameter.Request;
import cn.dream.chapter7.connect.parameter.Response;
import cn.dream.chapter7.life.LifeCycle;
import cn.dream.chapter7.life.LifeCycleBase;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

public class SimplePipeline extends LifeCycleBase implements Pipeline, Contained {

    private Container container;
    private List<Valve> valves = new ArrayList<>();
    private Valve basic;


    public SimplePipeline(Container container) {
        setContainer(container);
    }

    @Override
    public void addValve(Valve valve) {
        if (valve instanceof Contained) {
            ((Contained) valve).setContainer(getContainer());
        }
        valves.add(valve);
    }

    @Override
    public boolean removeValve(Valve valve) {
        return valves.remove(valve);
    }

    @Override
    public void setBasic(Valve valve) {
        if (valve instanceof Contained) {
            ((Contained) valve).setContainer(this.container);
        }
        this.basic = valve;
    }

    @Override
    public void invoke(Request request, Response response) throws ServletException {
        ValveContext valveContext = new SimplePipelineValveContext();
        valveContext.invokeNext(request, response);

    }

    public class SimplePipelineValveContext implements ValveContext {
        private int stage = 0;

        @Override
        public void invokeNext(Request request, Response response) throws ServletException {
            int subscript = stage;
            stage++;
            if (subscript < valves.size()) {
                valves.get(subscript).invoke(request, response, this);
            } else if (subscript == valves.size() && basic != null) {
                basic.invoke(request, response, this);
            } else {
                throw new ServletException("no value in pipeline");
            }
        }
    }

    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {

        this.container = container;
    }

    @Override
    protected void startInternal() {

        //start all valves
        for (Valve valve : valves) {
            if (valve instanceof LifeCycle) {
                ((LifeCycle) valve).start();
            }
        }
        //start basic valve
        if (basic instanceof LifeCycle) {
            ((LifeCycle) basic).start();
        }
    }

    @Override
    protected void stopInternal() {
        //start all valves
        for (Valve valve : valves) {
            if (valve instanceof LifeCycle) {
                ((LifeCycle) valve).stop();
                System.out.println(valve.getInfo() + " " + LifeCycle.STOP_EVENT);
            }
        }
        //start basic valve
        if (basic instanceof LifeCycle) {
            ((LifeCycle) basic).stop();
            System.out.println(basic.getInfo() + " " + LifeCycle.STOP_EVENT);
        }
    }
}
