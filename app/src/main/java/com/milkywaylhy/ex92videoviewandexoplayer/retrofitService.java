package com.milkywaylhy.ex92videoviewandexoplayer;

import retrofit2.Call;
import retrofit2.http.GET;

public interface retrofitService {

    @GET("/videos/videoData.json")
    Call<String> getVideoDatas();
}
