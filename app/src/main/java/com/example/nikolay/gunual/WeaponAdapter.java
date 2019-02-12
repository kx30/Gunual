package com.example.nikolay.gunual;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WeaponAdapter extends RecyclerView.Adapter<WeaponAdapter.ViewHolder> {

    private static final String TAG = "WeaponAdapter";

    private Context mContext;
    private ArrayList<Weapon> mWeapons;
    private String mExtra;

    private boolean ifThereIsExtra;

    private ArrayList<String> mUrls = new ArrayList<>();

    public WeaponAdapter(Context context, ArrayList<Weapon> weapons, String extra) {
        mContext = context;
        mWeapons = weapons;
        mExtra = extra;
        ifThereIsExtra = true;
    }

    public WeaponAdapter(Context context, ArrayList<Weapon> weapons) {
        mContext = context;
        mWeapons = weapons;
        ifThereIsExtra = false;
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
        viewHolder.mTitle.setText(mWeapons.get(i).getTitle());

        Glide.with(mContext)
                .load("https:" + mWeapons.get(i).getImageUrl())
                .into(viewHolder.mImage);

        if (viewHolder.mImage.getDrawable() == null) {
            Glide.with(mContext)
                    .load("http:" + mWeapons.get(i).getImageUrl())
                    .into(viewHolder.mImage);
        }

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InformationActivity.class);
                if (ifThereIsExtra) {
                    intent.putExtra("weapon", mExtra);
                }
                intent.putExtra("title", mWeapons.get(i).getTitle());
                intent.putExtra("country", mWeapons.get(i).getCountry());
                intent.putExtra("year_of_production", mWeapons.get(i).getYearOfProduction());
                intent.putExtra("type_of_bullet", mWeapons.get(i).getTypeOfBullet());
                intent.putExtra("effective_range", mWeapons.get(i).getEffectiveRange());
                intent.putExtra("muzzle_velocity", mWeapons.get(i).getMuzzleVelocity());
                intent.putExtra("feed_system", mWeapons.get(i).getFeedSystem());
                intent.putExtra("length", mWeapons.get(i).getLength());
                intent.putExtra("barrel_length", mWeapons.get(i).getBarrelLength());
                intent.putExtra("weight", mWeapons.get(i).getWeight());
                intent.putExtra("description", mWeapons.get(i).getDescription());
                intent.putExtra("image_url", mWeapons.get(i).getImageUrl());
                if (ifThereIsExtra) {
                    ((WeaponActivity) mContext).startActivityForResult(intent, 1);
                } else {
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWeapons.size();
    }

    public void updateList(ArrayList<Weapon> weapons) {
        mWeapons = new ArrayList<>();
        mWeapons.addAll(weapons);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private ImageView mImage;
        private ImageView mStar;
        private LinearLayout mParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mImage = itemView.findViewById(R.id.image);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
            mStar = itemView.findViewById(R.id.star);

        }
    }

}