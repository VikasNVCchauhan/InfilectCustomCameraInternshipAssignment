package com.example.infilectcustomcamerainternshipassignment.CustomGallery;


import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infilectcustomcamerainternshipassignment.R;
import com.example.infilectcustomcamerainternshipassignment.UserModel;

import java.util.ArrayList;
import java.util.List;

public class GalleryRecyclerAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserModel> list;
    private Context context;
    private String viewTypeReturn;
    private ArrayList<String> arrayListImageClickedHour;

    private View view;
    private LayoutInflater layoutInflater;

    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private TextView textView;

    public GalleryRecyclerAdaper(List<UserModel> list, ArrayList<String> arrayListImageClickedHour, Context context) {
        this.list = list;
        this.context = context;
        this.arrayListImageClickedHour = arrayListImageClickedHour;
    }


    @Override
    public int getItemViewType(int position) {
        return switchReturn(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_recycler_gallery, null);
        switch (viewType) {
            case 1:
                return new CustomHolder1AM(view);
            case 2:
                return new CustomHolder2AM(view);
            case 3:
                return new CustomHolder3AM(view);
            case 4:
                return new CustomHolder4AM(view);
            case 5:
                return new CustomHolder5AM(view);
            case 6:
                return new CustomHolder6AM(view);
            case 7:
                return new CustomHolder7AM(view);
            case 8:
                return new CustomHolder8AM(view);
            case 9:
                return new CustomHolder9AM(view);
            case 10:

                return new CustomHolder10AM(view);
            case 11:
                return new CustomHolder11AM(view);
            case 12:
                return new CustomHolder12PM(view);
            case 13:
                return new CustomHolder1PM(view);
            case 14:
                return new CustomHolder2PM(view);
            case 15:
                return new CustomHolder3PM(view);
            case 16:
                return new CustomHolder4PM(view);
            case 17:
                return new CustomHolder5PM(view);
            case 18:
                return new CustomHolder6PM(view);
            case 19:
                return new CustomHolder7PM(view);
            case 20:
                return new CustomHolder8PM(view);
            case 21:
                return new CustomHolder9PM(view);
            case 22:
                return new CustomHolder10PM(view);
            case 23:
                return new CustomHolder11PM(view);
            case 00:
                return new CustomHolder00AM(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (arrayListImageClickedHour.get(position)) {
            case "01":
                ((CustomHolder1AM) holder).setData(arrayListImageClickedHour.get(position));
                break;
            case "02":
                ((CustomHolder2AM) holder).setData(arrayListImageClickedHour.get(position));
                break;
            case "03":
                ((CustomHolder3AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "04":
                ((CustomHolder4AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "05":
                ((CustomHolder5AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "06":
                ((CustomHolder6AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "07":
                ((CustomHolder7AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "08":
                ((CustomHolder8AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "09":
                ((CustomHolder9AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "10":
                ((CustomHolder10AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "11":
                ((CustomHolder11AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "12":
                ((CustomHolder12PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "13":
                ((CustomHolder1PM) holder).setData(arrayListImageClickedHour.get(position));
                break;
            case "14":
                ((CustomHolder2PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "15":
                ((CustomHolder3PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "16":
                ((CustomHolder4PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "17":
                ((CustomHolder5PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "18":
                ((CustomHolder6PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "19":
                ((CustomHolder7PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "20":
                ((CustomHolder8PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "21":
                ((CustomHolder9PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "22":
                ((CustomHolder10PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "23":
                ((CustomHolder11PM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            case "00":
                ((CustomHolder00AM) holder).setData(arrayListImageClickedHour.get(position));
                break;

            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return arrayListImageClickedHour.size();
    }

    private void setLayoutManager() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void setRecyclerViewSize() {
        linearLayout = new LinearLayout(context);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = 375;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                width, height);
        recyclerView.setLayoutParams(params);
    }

    private void setIdForAllWidgets(View itemView) {
        linearLayout = itemView.findViewById(R.id.linear_layout_recycle_item_gallery);
        recyclerView = itemView.findViewById(R.id.recycler_view_item_recycler_gallery);
        textView = itemView.findViewById(R.id.text_view_time_image_click);
    }

    private void setIt(String time) {
        System.out.println("Time is : " + time + " IS IT Correct Or Not ");
        if (Integer.valueOf(time) == 00) {
            textView.setText("12 AM");
        } else if (Integer.valueOf(time) > 11) {
            textView.setText((Integer.valueOf(time) - 12) + " PM");
        } else {
            textView.setText(time + " AM");
        }
    }

//    private void getNewList(String timeHr) {
//
//        newList.clear();
//        ArrayList<String> newArrayListHr = new ArrayList<>();
//        ArrayList<String> newArrayListImageUrl = new ArrayList<>();
//        ArrayList<String> newArrayListTime = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getStringHour().equals(timeHr)) {
//                newArrayListHr.add(list.get(i).getStringHour());
//                newArrayListImageUrl.add(list.get(i).getStringImageUrl());
//                newArrayListTime.add(list.get(i).getStringTime());
//            }
//        }
//
//        for (int i = 0; i < newArrayListHr.size(); i++) {
//
//            System.out.println("DDDDDDDDDDDDDDDDDDUUUUUUUUUUUUUUUUUUUUUUUUUJJJJJJJJJJJJJJJJJJJJ  " + i + "      " + newList.size());
//
//            Toast.makeText(context, "" + newArrayListHr.size(), Toast.LENGTH_LONG).show();
//            UserModel userModel = new UserModel();
//            userModel.setStringTime(newArrayListTime.get(i));
//            userModel.setStringImageUrl(newArrayListImageUrl.get(i));
//            userModel.setStringHour(newArrayListHr.get(i));
//            newList.add(userModel);
//        }
////        System.out.println("DDDDDDDDDDDDDDDDDDUUUUUUUUUUUUUUUUUUUUUUUUUJJJJJJJJJJJJJJJJJJJJ  " + count + "      " + newList11.size());
//
////        Toast.makeText(context, "" + newList.size(), Toast.LENGTH_SHORT).show();
//    }

    private class CustomHolder00AM extends RecyclerView.ViewHolder {

        public CustomHolder00AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter00AM holderClass;
            holderClass = new RecyclerAdapter00AM(list, context, "00");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder1AM extends RecyclerView.ViewHolder {

        public CustomHolder1AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter1AM holderClass;
            holderClass = new RecyclerAdapter1AM(list, context, "01");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder2AM extends RecyclerView.ViewHolder {

        public CustomHolder2AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter2AM holderClass;
            holderClass = new RecyclerAdapter2AM(list, context, "02");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder3AM extends RecyclerView.ViewHolder {

        public CustomHolder3AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {

            RecyclerAdapter3AM holderClass;
            holderClass = new RecyclerAdapter3AM(list, context, "03");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder4AM extends RecyclerView.ViewHolder {

        public CustomHolder4AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {

            RecyclerAdapter4AM holderClass;
            holderClass = new RecyclerAdapter4AM(list, context, "04");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder5AM extends RecyclerView.ViewHolder {

        public CustomHolder5AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter6AM holderClass;
            holderClass = new RecyclerAdapter6AM(list, context, "05");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder6AM extends RecyclerView.ViewHolder {

        public CustomHolder6AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter6AM holderClass;
            holderClass = new RecyclerAdapter6AM(list, context, "06");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder7AM extends RecyclerView.ViewHolder {

        public CustomHolder7AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter7AM holderClass;
            holderClass = new RecyclerAdapter7AM(list, context, "07");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder8AM extends RecyclerView.ViewHolder {

        public CustomHolder8AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter8AM holderClass;
            holderClass = new RecyclerAdapter8AM(list, context, "08");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder9AM extends RecyclerView.ViewHolder {

        public CustomHolder9AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter9AM holderClass;
            holderClass = new RecyclerAdapter9AM(list, context, "09");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }

    }

    private class CustomHolder10AM extends RecyclerView.ViewHolder {

        public CustomHolder10AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter10AM holderClass;
            holderClass = new RecyclerAdapter10AM(list, context, "10");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder11AM extends RecyclerView.ViewHolder {

        public CustomHolder11AM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter11AM holderClass;
            holderClass = new RecyclerAdapter11AM(list, context, "11");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder12PM extends RecyclerView.ViewHolder {

        public CustomHolder12PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }


        private void setDataToRecycler() {
            RecyclerAdapter12PM holderClass;
            holderClass = new RecyclerAdapter12PM(list, context, "12");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder1PM extends RecyclerView.ViewHolder {

        public CustomHolder1PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);


            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter1PM holderClass;
            holderClass = new RecyclerAdapter1PM(list, context, "13");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder2PM extends RecyclerView.ViewHolder {

        public CustomHolder2PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter2PM holderClass;
            holderClass = new RecyclerAdapter2PM(list, context, "14");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder3PM extends RecyclerView.ViewHolder {

        public CustomHolder3PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter3PM holderClass;
            holderClass = new RecyclerAdapter3PM(list, context, "15");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder4PM extends RecyclerView.ViewHolder {

        public CustomHolder4PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter4PM holderClass;
            holderClass = new RecyclerAdapter4PM(list, context, "16");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder5PM extends RecyclerView.ViewHolder {

        public CustomHolder5PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter5PM holderClass;
            holderClass = new RecyclerAdapter5PM(list, context, "17");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder6PM extends RecyclerView.ViewHolder {

        public CustomHolder6PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter6PM holderClass;
            holderClass = new RecyclerAdapter6PM(list, context, "18");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder7PM extends RecyclerView.ViewHolder {

        public CustomHolder7PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter7PM holderClass;
            holderClass = new RecyclerAdapter7PM(list, context, "19");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder8PM extends RecyclerView.ViewHolder {

        public CustomHolder8PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter8PM holderClass;
            holderClass = new RecyclerAdapter8PM(list, context, "20");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder9PM extends RecyclerView.ViewHolder {

        public CustomHolder9PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter9PM holderClass;
            holderClass = new RecyclerAdapter9PM(list, context, "21");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder10PM extends RecyclerView.ViewHolder {

        public CustomHolder10PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter10PM holderClass;
            holderClass = new RecyclerAdapter10PM(list, context, "22");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private class CustomHolder11PM extends RecyclerView.ViewHolder {

        List<UserModel> newList = new ArrayList<>();

        public CustomHolder11PM(@NonNull View itemView) {
            super(itemView);
            setIdForAllWidgets(itemView);

            setIdForAllWidgets(itemView);
            setRecyclerViewSize();
            setLayoutManager();
            setDataToRecycler();
        }

        private void setDataToRecycler() {
            RecyclerAdapter11PM holderClass;
            holderClass = new RecyclerAdapter11PM(list, context, "23");
            recyclerView.setAdapter(holderClass);
            holderClass.notifyDataSetChanged();
        }

        public void setData(String time) {
            setIt(time);
        }
    }

    private int switchReturn(int position) {
        viewTypeReturn = arrayListImageClickedHour.get(position);

        switch (viewTypeReturn) {
            case "01":
                return 1;
            case "02":
                return 2;
            case "03":
                return 3;
            case "04":
                return 4;
            case "05":
                return 5;
            case "06":
                return 6;
            case "07":
                return 7;
            case "08":
                return 8;
            case "09":
                return 9;
            case "10":
                return 10;
            case "11":
                return 11;
            case "12":
                return 12;
            case "13":
                return 13;
            case "14":
                return 14;
            case "15":
                return 15;
            case "16":
                return 16;
            case "17":
                return 17;
            case "18":
                return 18;
            case "19":
                return 19;
            case "20":
                return 20;
            case "21":
                return 21;
            case "22":
                return 22;
            case "23":
                return 23;
            case "00":
                return 00;
            default:
                return -1;

        }
    }
}
