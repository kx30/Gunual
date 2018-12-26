package com.example.nikolay.gunual;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeaponAdapter extends RecyclerView.Adapter<WeaponAdapter.ViewHolder> {

    private static final String TAG = "WeaponAdapter";

    private Context mContext;
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();

    public WeaponAdapter(Context context, ArrayList<String> titles, ArrayList<String> descriptions, ArrayList<Integer> images) {
        mContext = context;
        mTitles = titles;
        mDescriptions = descriptions;
        mImages = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weapon_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: created.");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mTitle.setText(mTitles.get(i));
        viewHolder.mDescription.setText(mDescriptions.get(i));
        viewHolder.mImage.setImageResource(R.drawable.image);

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InformationActivity.class);
                mContext.startActivity(intent);
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

        private TextView mTitle;
        private TextView mDescription;
        private ImageView mImage;
        private LinearLayout mParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);
            mImage = itemView.findViewById(R.id.image);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
