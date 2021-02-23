package com.milkywaylhy.ex92videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    VideoView vv;

    //Video Uri [ 용량문제로 별도의 서버에 위치하는 경우가 대부분 ] - 인터넷 퍼미션 필요
    //즉, 비디오파일을 서버에 놓고 이를 읽어들여 실행하도록 코딩 -- 구글에 "sample video uri"로 검색
    Uri videoUri= Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vv= findViewById(R.id.vv);

        //비디오뷰의 재생,일시정지 등을 할 수 있는 '컨트롤바' 붙이기
        vv.setMediaController(new MediaController(this));

        //비디오뷰에 동영상의 uri 설정하기
        vv.setVideoURI(videoUri);

        //동영상을 로딩하는데 시간이 걸리므로 곧바로 실행 못하고
        // 비디오의 로딩준비(prepare)가 완료되었을때 실행하도록...
        // 리스너 이용
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //비디어 시작
                vv.start();
            }
        });
    }

    //액비비티가 화면에서 보이지 않을 때 비디오를 일시정지 시킬필요 있음
    //비디오뷰는 별도 Thread로 동영상을 재생하기에 ...
    @Override
    protected void onPause() {
        super.onPause();

        //비디오 일시정지
        if(vv != null && vv.isPlaying() ) vv.pause();
    }


    //VideoView는 성능이 좋지않고 무거움. 또한 컨트롤러 위치 지정도 다소 불편
    //실무에서는 안드로이드의 공식 VideoView Library 인 ExoPlayer를 많이 사용함
    public void clickBtn(View view) {
        startActivity( new Intent(this, SecondActivity.class) );
        finish();
    }

    public void clickBtn2(View view) {
        startActivity( new Intent(this, thirdActivity.class) );
        finish();
    }
}