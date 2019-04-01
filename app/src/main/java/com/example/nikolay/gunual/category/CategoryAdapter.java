package com.example.nikolay.gunual.category;

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

import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.weapon.WeaponActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private static final String TAG = "CategoryAdapter";

    private Context mContext;
    private ArrayList<String> mTitles;
    private ArrayList<Integer> mImages;
    private String[] mCategoryOfWeapons = {"Pistol", "Submachine gun", "Rifle", "Carbine", "Sniper rifle", "Machine gun", "Shotgun"};

    public CategoryAdapter(Context context, ArrayList<String> titles, ArrayList<Integer> images) {
        mContext = context;
        mTitles = titles;
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
        viewHolder.mTitle.setText(mTitles.get(i));
        viewHolder.mImage.setImageResource(mImages.get(i));

        viewHolder.mParentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WeaponActivity.class);
            Log.d(TAG, "onClick: " + mCategoryOfWeapons[i]);
            intent.putExtra("Weapon", mCategoryOfWeapons[i]);
            mContext.startActivity(intent);
        });
        Log.d(TAG, "onBindViewHolder: called.");
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mImage;
        private LinearLayout mParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.text_view_category_item);
            mImage = itemView.findViewById(R.id.image_view_category_item);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
