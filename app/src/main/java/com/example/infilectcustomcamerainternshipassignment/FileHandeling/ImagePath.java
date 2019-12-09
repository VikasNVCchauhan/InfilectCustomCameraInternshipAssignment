package com.example.infilectcustomcamerainternshipassignment.FileHandeling;

import android.os.Environment;

public class ImagePath {

    public String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();
    public String NVCImages = ROOT_DIR + "/NVCImages";
}
