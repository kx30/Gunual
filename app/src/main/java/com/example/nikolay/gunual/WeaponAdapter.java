package com.example.nikolay.gunual;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class WeaponAdapter extends RecyclerView.Adapter<WeaponAdapter.ViewHolder> {

    private static final String TAG = "WeaponAdapter";

    private Context mContext;
    private ArrayList<Weapon> mWeapons;
    private String mExtra;

    private boolean mWeaponActivity;

    public WeaponAdapter(Context context, ArrayList<Weapon> weapons, String extra) {
        mContext = context;
        mWeapons = weapons;
        mExtra = extra;
        mWeaponActivity = true;
    }

    public WeaponAdapter(Context context, ArrayList<Weapon> weapons) {
        mContext = context;
        mWeapons = weapons;
        mWeaponActivity = false;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weapon_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
//        Log.d(TAG, "onCreateViewHolder: created.");

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.mTitle.setText(mWeapons.get(i).getTitle());

        Glide.with(mContext)
                .load("https:" + mWeapons.get(i).getImageUrl())
                .into(viewHolder.mImage);

        if (viewHolder.mImage.getDrawable() == null) {
            Glide.with(mContext)
                    .load("http:" + mWeapons.get(i).getImageUrl())
                    .into(viewHolder.mImage);
        }

        viewHolder.mStar.setImageResource(mWeapons.get(i).getDrawable());


        viewHolder.mStar.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("value", Context.MODE_PRIVATE);
            String sharedValue = sharedPreferences.getString("favorites", "");

            @Override
            public void onClick(View view) {
                if (mWeapons.get(i).getDrawable() == R.drawable.favorite_star) {

                    Gson gson = new Gson();
                    String weaponPosition = gson.toJson(mWeapons.get(i));

                    if (sharedValue.contains(weaponPosition)) {
                        // If first object in string
                        if (sharedValue.indexOf(weaponPosition) - 1 == 0) {
                            sharedValue = sharedValue.replace(weaponPosition, "");
                            if (sharedValue.charAt(1) == ',') {
                                sharedValue = sharedValue.substring(2);
                                sharedValue = "[" + sharedValue;
                            } else {
                                sharedValue = "";
                            }
                        } else if (sharedValue.charAt(sharedValue.indexOf(weaponPosition) + weaponPosition.length()) == ']') {
                            // If last object in string
                            sharedValue = sharedValue.replace(weaponPosition, "");
                            sharedValue = sharedValue.substring(0, sharedValue.length() - 2);
                            sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                        } else {
                            sharedValue = sharedValue.substring(0, sharedValue.indexOf(weaponPosition)) +
                                    sharedValue.substring(
                                            sharedValue.indexOf(weaponPosition) + weaponPosition.length() + 1,
                                            sharedValue.length());
                        }
                        mWeapons.get(i).setDrawable(R.drawable.unfavorite_star);
                    }
                } else {
                    Gson gson = new Gson();
                    mWeapons.get(i).setDrawable(R.drawable.favorite_star);
                    if (sharedValue.equals("")) {
                        sharedValue += "[" + gson.toJson(mWeapons.get(i));
                        sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                    } else {
                        sharedValue = sharedValue.substring(0, sharedValue.length() - 1);
                        sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), ",").toString();
                        sharedValue += gson.toJson(mWeapons.get(i));
                        sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                    }
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("favorites", sharedValue);
                editor.apply();
                notifyDataSetChanged();
            }
        });

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InformationActivity.class);
                if (mWeaponActivity) {
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

                mContext.startActivity(intent);
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