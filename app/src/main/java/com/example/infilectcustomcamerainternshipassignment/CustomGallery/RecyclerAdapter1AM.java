package com.example.infilectcustomcamerainternshipassignment.CustomGallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.infilectcustomcamerainternshipassignment.ClickedImage.SelectedImageFromRecycler;
import com.example.infilectcustomcamerainternshipassignment.R;
import com.example.infilectcustomcamerainternshipassignment.UserModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter1AM extends RecyclerView.Adapter<RecyclerAdapter1AM.TimeViewHolder>{
    private List<UserModel> userModelList;
    private Context context;
    private String stringHour;
    private ArrayList<String> stringsNoOfRows = new ArrayList<>();
    private ArrayList<String> arrayListStringImageUrl = new ArrayList<>();
    private ArrayList<String> getArrayListStringImageTime = new ArrayList<>();

    public RecyclerAdapter1AM(List<UserModel> userModelList, Context context, String stringHour) {
        this.userModelList = userModelList;
        this.context = context;
        this.stringHour = stringHour;
//        this.stringsNoOfRows = stringsNoOfRows;
//        Toast.makeText(context, ""+userModelList.size(), Toast.LENGTH_SHORT).show();
        setDataToRow(stringHour);
    }

    private void setDataToRow(String hr) {
        for (int i = 0; i < userModelList.size(); i++) {
            if (userModelList.get(i).getStringHour().equals(hr)) {
                arrayListStringImageUrl.add(userModelList.get(i).getStringImageUrl());
                getArrayListStringImageTime.add(userModelList.get(i).getStringTime());
            }
        }
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_image_item_recycler_gallery, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        holder.setData(arrayListStringImageUrl.get(position), getArrayListStringImageTime.get(position));
    }

    @Override
    public int getItemCount() {

        System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFGGGGGGGGGGGGGGGGGGGGGGGGGG  " + stringHour + "   " + arrayListStringImageUrl.size());
        return arrayListStringImageUrl.size();
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);

            setIdForAllWidgets(itemView);
            imageView.setOnClickListener(this);
        }

        private void setIdForAllWidgets(View itemView) {
            textView = itemView.findViewById(R.id.text_view_time_item_recycler);
            imageView = itemView.findViewById(R.id.image_view_recycler_item_recycler_gallery);
        }

        private void setData(String imageUrl, String time) {
            Glide.with(context).load(imageUrl)
                    .into(imageView);
            textView.setText(time);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, SelectedImageFromRecycler.class);
                intent.putExtra("STRING_IMG_URL", arrayListStringImageUrl.get(position));
                context.startActivity(intent);
            }
        }
    }
}
