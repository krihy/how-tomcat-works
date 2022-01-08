package cn.dream.chapter4;

import cn.dream.chapter4.connect.HttpRequestBase;
import cn.dream.chapter4.connect.HttpResponseBase;

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
