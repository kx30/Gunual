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
import java.util.List;

public class WeaponAdapter extends RecyclerView.Adapter<WeaponAdapter.ViewHolder> {

    private static final String TAG = "WeaponAdapter";

    private Context mContext;
    private ArrayList<Weapon> mWeapons;
    private String mExtra;

    public WeaponAdapter(Context context, ArrayList<Weapon> weapons, String extra) {
        mContext = context;
        mWeapons = weapons;
        mExtra = extra;
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
        viewHolder.mDescription.setText(mWeapons.get(i).getDescription());
        viewHolder.mImage.setImageResource(R.drawable.image);

        viewHolder.mParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, InformationActivity.class);
                intent.putExtra("weapon", mExtra);
                intent.putExtra("country", mWeapons.get(i).getCountry());
                intent.putExtra("yearOfProduction", mWeapons.get(i).getYearOfProduction());
                intent.putExtra("typeOfBullet", mWeapons.get(i).getTypeOfBullet());
                intent.putExtra("effectiveRange", mWeapons.get(i).getEffectiveRange());
                intent.putExtra("muzzleVelocity", mWeapons.get(i).getMuzzleVelocity());
                intent.putExtra("feedSystem", mWeapons.get(i).getFeedSystem());
                intent.putExtra("length", mWeapons.get(i).getLength());
                intent.putExtra("barrelLength", mWeapons.get(i).getBarrelLength());
                intent.putExtra("loadedWeight", mWeapons.get(i).getLoadedWeight());
                intent.putExtra("unloadedWeight", mWeapons.get(i).getUnloadedWeight());
                intent.putExtra("rapidFire", mWeapons.get(i).getRapidFire());
                intent.putExtra("cost", mWeapons.get(i).getCost());
                mContext.startActivity(intent);
            }
        });
        Log.d(TAG, "onBindViewHolder: " + mExtra);

        Log.d(TAG, "onBindViewHolder: called.");
    }

    @Override
    public int getItemCount() {
        return mWeapons.size();
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
