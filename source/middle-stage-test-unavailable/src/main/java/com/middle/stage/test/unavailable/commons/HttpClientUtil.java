package com.middle.stage.test.unavailable.commons;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author zyq
 */
public class HttpClientUtil {

    public String sendHttpPost(String url, String JSONBody) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(JSONBody));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        response.close();
        httpClient.close();
        return responseContent;
    }

}
