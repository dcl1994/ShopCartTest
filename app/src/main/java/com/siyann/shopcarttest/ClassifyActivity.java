package com.siyann.shopcarttest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import util.OkhttpRequest;
import adapter.ClassIfyAdapter;
import widget.VGoodsId;

/**
 * 商品分类的activity
 * 从接口中获取商品数据填充到activity中
 */
public class ClassifyActivity extends Activity {

    private Context mContext;

    private RecyclerView recyclerView;

    private List<VGoodsId> shopList=new ArrayList<>();

    private ClassIfyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);

        mContext=this;

        init();

        recyclerView= (RecyclerView) findViewById(R.id.my_recycler);

        LinearLayoutManager manager=new LinearLayoutManager(mContext);

        recyclerView.setLayoutManager(manager);
    }
    private void init() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                request();
            }
        });
    }

    private void request(){
        OkhttpRequest.sendOkhttpRequest("http://192.168.1.150:8080/TendaEHome/app/goods/getGoodsList", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("e",e+"");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String result = response.body().string();
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    jsonObject = new JSONObject(result);
                    Log.e("jsonObject", jsonObject + "");
                    jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
                    Log.e("jsonarry", jsonArray + "");
                    shopList = gson.fromJson(jsonArray.toString(), new TypeToken<List<VGoodsId>>() {
                    }.getType());
                    adapter = new ClassIfyAdapter(mContext, shopList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

}
