package util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *OKhttp请求
 */
public class OkhttpRequest {
    public static void sendOkhttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
