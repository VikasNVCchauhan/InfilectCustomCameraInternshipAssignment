package com.example.infilectcustomcamerainternshipassignment.GoogleDriveHelper;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GoogleDriveHelper {

    private final Executor mExecutor= Executors.newSingleThreadExecutor();
    private Drive mDriveServices;

    public GoogleDriveHelper(Drive mDriveServices){
        this.mDriveServices=mDriveServices;
    }

    public Task<String> createFile(String filePath){
        return Tasks.call(mExecutor,()->{

            File fileMetadata =new File();
            fileMetadata.setName("MyImageFile");
            java.io.File file=new java.io.File(filePath);
            File myFileID=null;
            try{
                myFileID=mDriveServices.files().create(fileMetadata).execute();

            }catch(Exception e){
                e.printStackTrace();
            }
            if(myFileID==null){
                throw new IOException("Null return from drive when file uploading");
            }

            return myFileID.getId();
        });
    }
}
