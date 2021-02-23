package com.milkywaylhy.ex92videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;


public class DetailActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv= findViewById(R.id.tv_detail);

        String jsonstr= getIntent().getStringExtra("item");
        //json문자열을 --> VideoItem객체로 변환
        VideoItem videoItem= new Gson().fromJson(jsonstr,VideoItem.class);

        tv.setText(videoItem.title+"\n"+videoItem.subtitle+"\n"+videoItem.sources);

    }
}