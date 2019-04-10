package com.example.nikolay.gunual.preview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikolay.gunual.R;

public class DemoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo, container, false);
        TextView previewInformationText = view.findViewById(R.id.preview_information_text);
        ImageView previewImage = view.findViewById(R.id.preview_image);
        previewInformationText.setText(getArguments().getString("message"));
        previewImage.setImageResource(getArguments().getInt("image"));
        return view;
    }
}
