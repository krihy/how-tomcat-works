package cn.dream.chapter3;

public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response) {

        try {
            response.sendStaticResource();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
