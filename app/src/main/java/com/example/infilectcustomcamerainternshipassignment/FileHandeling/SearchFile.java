package com.example.infilectcustomcamerainternshipassignment.FileHandeling;
import com.example.infilectcustomcamerainternshipassignment.UserModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchFile {

    private List<UserModel> list = new ArrayList<>();
    private List<String> listString=new ArrayList<>();
    private String[] strings = new String[24];
    private File file;
    private File[] filesList;
    private String[] splited;
    private String[] time;
    private ArrayList<String> arrayListTimeHour = new ArrayList<>();

    public List<UserModel> getImagesFromFile(File file) {
        this.file = new File(String.valueOf(file));
        filesList = this.file.listFiles();
        for (int i = 0; i < filesList.length; i++) {
            String path = filesList[i].getAbsolutePath();
            Date lastModifiedDate = new Date(filesList[i].lastModified());

            splited = String.valueOf(lastModifiedDate).split("\\s+");

            time = splited[3].split(":");
            chechImageCaptureTime(time);
            list.add(new UserModel(path, time[0], splited[3]));
        }
        return list;
    }

    public ArrayList<String> getImageClickTime() {
        return arrayListTimeHour;
    }

    private void chechImageCaptureTime(String[] time) {

        if (arrayListTimeHour.size() == 0) {
            arrayListTimeHour.add(time[0]);
        } else {
            if (!arrayListTimeHour.contains(time[0])) {
                arrayListTimeHour.add(time[0]);
            }
        }
    }

    public List<String> getImagesFromFileForBackup(File file) {
        this.file = new File(String.valueOf(file));
        filesList = this.file.listFiles();
        for (int i = 0; i < filesList.length; i++) {
            String path = filesList[i].getAbsolutePath();
            listString.add(path);
        }
        return listString;
    }
}
