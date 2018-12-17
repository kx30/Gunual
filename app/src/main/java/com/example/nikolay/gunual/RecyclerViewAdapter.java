package com.example.nikolay.gunual;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private Context mContext;
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mSubtitles = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();


    public RecyclerViewAdapter(Context context, ArrayList<String> titles, ArrayList<String> subtitles, ArrayList<Integer> images) {
        mContext = context;
        mTitles = titles;
        mSubtitles = subtitles;
        mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: created.");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mImage.setImageResource(R.drawable.image);
        viewHolder.mTitle.setText(mTitles.get(i));
        viewHolder.mSubtitle.setText(mSubtitles.get(i));

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mTitles.get(i));

                Toast.makeText(mContext, mTitles.get(i), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d(TAG, "onBindViewHolder: called.");
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mTitle;
        private TextView mSubtitle;
        private LinearLayout mParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_category);
            mTitle = itemView.findViewById(R.id.text_title_category);
            mSubtitle = itemView.findViewById(R.id.text_subtitle_category);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
