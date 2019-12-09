package com.example.infilectcustomcamerainternshipassignment.ClickedImage;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.infilectcustomcamerainternshipassignment.R;

public class SelectedImageFromRecycler extends AppCompatActivity {

    private ImageView imageView;
    private String stringUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_image_view);

        setIdForAllWidgets();
        stringUrl = getIntent().getStringExtra("STRING_IMG_URL");
        setData();
    }

    private void setData() {
        Glide.with(this).load(stringUrl)
                .into(imageView);
    }

    private void setIdForAllWidgets() {
        imageView = findViewById(R.id.image_view_full_screen_image);
    }
}
