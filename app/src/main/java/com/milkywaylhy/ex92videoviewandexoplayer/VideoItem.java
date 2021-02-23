package com.milkywaylhy.ex92videoviewandexoplayer;

import com.google.gson.annotations.SerializedName;

public class VideoItem {

    String title;
    String subtitle;
    String thumb;
    String sources;

    //만약 자동 파싱되는 json의 키값과 다른 멤버변수명을 사용하고 싶다면
    @SerializedName("description")
    String desc;

    public VideoItem() {
    }

    public VideoItem(String title, String subtitle, String thumb, String sources, String desc) {
        this.title = title;
        this.subtitle = subtitle;
        this.thumb = thumb;
        this.sources = sources;
        this.desc = desc;
    }
}
