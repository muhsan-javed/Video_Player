package com.muhsantech.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<VideoHolder> {

    Context context;
    ArrayList<File> videoArrayList;
//    ArrayList<File> videoArrayListFiltered; //-- > Search Function Filter

    public MyAdapter(Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
//        this.videoArrayListFiltered = videoArrayList; //-- > Search Function Filter
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        holder.txtFileName.setText(MainActivity.fileArrayList.get(position).getName());
        Bitmap bitmapThumbnail = ThumbnailUtils.createVideoThumbnail(videoArrayList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.imageThumbnail.setImageBitmap(bitmapThumbnail);
        holder.mCardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, VideoPlayerActivity.class);
            intent.putExtra("position", holder.getAdapterPosition());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
//        return videoArrayListFiltered.size(); // -- > Search Function Filter
        if (videoArrayList.size() > 0){
            return videoArrayList.size();
        }else {
            return 1;
        }
    }

    // -- > Search Function Filter
  /*@Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    filterResults.count = videoArrayList.size();
                    filterResults.values = videoArrayList;
                } else {
                    List<File> resultsModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();

                    for (File itemsModel : videoArrayList) {
                        if (itemsModel.getName().toLowerCase().contains(searchStr)) {
                            resultsModel.add(itemsModel);
                        }

                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;

                    }

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                videoArrayListFiltered = (ArrayList<File>) filterResults.values;

                MainActivity.fileArrayList = (ArrayList<File>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
*/
}

class VideoHolder extends RecyclerView.ViewHolder{

    TextView txtFileName;
    ImageView imageThumbnail;
    CardView mCardView;
    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        txtFileName = itemView.findViewById(R.id.txt_videoFileName);
        imageThumbnail = itemView.findViewById(R.id.iv_thumbnail);
        mCardView = itemView.findViewById(R.id.myCardView);
    }
}
