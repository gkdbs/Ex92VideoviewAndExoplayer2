package com.milkywaylhy.ex92videoviewandexoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class thirdActivity extends AppCompatActivity {

    ArrayList<VideoItem> items= new ArrayList<>();
    RecyclerView recyclerView;
    VideoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recyclerView= findViewById(R.id.recycler);
        adapter= new VideoListAdapter(this, items);
        recyclerView.setAdapter(adapter);

        //서버에 있는 json문서를 읽어와서 파싱(분석)하여 리스트로 보여주는 작업
        loadData();
    }

    void loadData(){
        //Retrofit을 이용하여 서버에있는 json문서 읽어와서 파싱(우선 json을 그냥 문자열로 읽어와서 직접 파싱해보기)
        Retrofit.Builder builder= new Retrofit.Builder();
        builder.baseUrl("http://gkdbs514.dothome.co.kr/");//서버 기본 url
        builder.addConverterFactory(ScalarsConverterFactory.create());
        Retrofit retrofit= builder.build();

        retrofitService retrofitService= retrofit.create(retrofitService.class);
        Call<String> call= retrofitService.getVideoDatas();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonStr= response.body();
                Log.i("tag", jsonStr);

                //이 jsonStr을 VideoItem으로 분서하여 리스트뷰에 추가하기
                try {
                    JSONObject jsonObject=new JSONObject(jsonStr);
                    JSONArray categories = jsonObject.getJSONArray("categories");
                    JSONObject object =categories.getJSONObject(0);
                    JSONArray videoArray = object.getJSONArray("videos");

                    for (int i=0; i<videoArray.length(); i++){
                        JSONObject video= videoArray.getJSONObject(i);

                        String title = video.getString("title");
                        String subtitle = video.getString("subtitle");
                        String thumb = video.getString("thumb");
                        String desc = video.getString("desc");
                        String sources = video.getString("sources");
                        sources= sources.replace("\\/","/");
                        sources= sources.replace("[\"","");
                        sources= sources.replace("\"","");

                        VideoItem videoItem = new VideoItem(title,subtitle,thumb,desc,sources);
                        items.add(videoItem);
                        //리사이클러화면 갱신
                        adapter.notifyItemInserted(items.size()-1);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(thirdActivity.this, "error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }
}