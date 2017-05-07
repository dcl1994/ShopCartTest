package com.siyann.shopcarttest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.tablemanager.Connector;

/**
 * 创建数据库和数据表
 */
public class MainActivity extends Activity {
    private Button button;
    private Button add;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        button = (Button) findViewById(R.id.request);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase(); //创建一个数据库
                Toast.makeText(mContext,"创建数据库成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
