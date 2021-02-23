package com.milkywaylhy.ex92videoviewandexoplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.Gson;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VH> {

    Context context;
    ArrayList<VideoItem> items;

    public VideoListAdapter(Context context, ArrayList<VideoItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        VideoItem item= items.get(position);
        holder.tvTitle.setText(item.title);
        holder.tvSubTitle.setText(item.subtitle);
        holder.tvDesc.setText(item.desc);

        //플레이어가 실행할 미디어 아이템 객체 생성
        MediaItem mediaItem= MediaItem.fromUri(item.sources);
        //플레이어에 미디어 설정 [플레이어뷰가 아님!!]
        holder.player.setMediaItem(mediaItem);
        holder.player.prepare();
        //holder.player.play();//곧바로 실행하면 여러동영상이 동시에 실해되서 정신없음.
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvSubTitle;
        PlayerView pv;
        TextView tvDesc;

        //플레이어뷰에 동영상을 실제로 실행시킬 객체참조변수
        SimpleExoPlayer player;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvTitle= itemView.findViewById(R.id.tv_title);
            tvSubTitle= itemView.findViewById(R.id.tv_subtitle);
            tvDesc= itemView.findViewById(R.id.tv_desc);

            pv= itemView.findViewById(R.id.pv);
            player= new SimpleExoPlayer.Builder(context).build();
            pv.setPlayer(player);

            //항목뷰를 클릭하면 새로운 액티비티로 이동하면서 선태고딘
            //Item 의 값 전달
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //클릭한 번째의 아이템 객체 얻어오기
                    VideoItem videoItem = items.get(getLayoutPosition());
                    Intent intent= new Intent(context, DetailActivity.class);
                    intent.putExtra("item", videoItem);//객체를 통으로 전달 불가 - 어차피 intent를 통하여 객체 전달이 불가능하기 때문에 객체를 json문자열로 변환
                    //Gson 라이브러리가 이런변환을 아주 쉽게해줌
                    Gson gson = new Gson();
                    String jsonstr = gson.toJson(videoItem);
                    context.startActivity(intent);
                }
            });

        }
    }


}
