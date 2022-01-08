package cn.dream.chapter5.container.processor;

import cn.dream.chapter5.connect.parameter.HttpRequestBase;
import cn.dream.chapter5.connect.parameter.HttpResponseBase;

public class StaticResourceProcessor {

    public void process(HttpRequestBase request, HttpResponseBase response) {

        try {
            response.sendStaticResource();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
