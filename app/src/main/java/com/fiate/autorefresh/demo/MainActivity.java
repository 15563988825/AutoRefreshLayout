package com.fiate.autorefresh.demo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fiate.autorefresh.AutoRefreshLayout;
import com.fiate.autorefresh.OnAotoRefreshListener;

public class MainActivity extends AppCompatActivity implements OnAotoRefreshListener {

    private AutoRefreshLayout autoRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoRefreshLayout=(AutoRefreshLayout)findViewById(R.id.autoRefreshLayout);
        autoRefreshLayout.setOnAotoRefreshListener(this);

        ((ImageView)findViewById(R.id.imageView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefreshLayout.setRefreshFaild();
            }
        });
    }

    @Override
    public void OnRefreshStart(final AutoRefreshLayout autoRefreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                autoRefreshLayout.setRefreshSucceed();
            }
        },4000);
    }

    @Override
    public void OnRefreshSucceed(AutoRefreshLayout autoRefreshLayout) {

    }

    @Override
    public void OnRefreshFaild(AutoRefreshLayout autoRefreshLayout) {

    }
}
