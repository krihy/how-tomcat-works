package cn.dream.chapter2;

public class StaticResourceProcessor {

    public void process(Request request, Response response) {

        try {
            response.sendStaticResource();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
