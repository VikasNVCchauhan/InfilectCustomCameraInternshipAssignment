package com.example.infilectcustomcamerainternshipassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.infilectcustomcamerainternshipassignment.CustomCamera.CustomCameraClass;
import com.example.infilectcustomcamerainternshipassignment.CustomGallery.GalleryRecyclerAdaper;
import com.example.infilectcustomcamerainternshipassignment.FileHandeling.ImagePath;
import com.example.infilectcustomcamerainternshipassignment.FileHandeling.SearchFile;
import com.example.infilectcustomcamerainternshipassignment.GoogleDriveHelper.GoogleDriveHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Widgets
    private RecyclerView recyclerView;
    private RecyclerView horizontalRecycler;
    private LinearLayout linearLayoutCamera, linearLayoutSynk;
    //Some other Predefined classes
    private File file;
    private List<String> listStringBackupImageUrl;
    private List<UserModel> listUserModel;
    private ArrayList<String> arrayListImageClickHour;
    //User Defined Classes
    private ImagePath imagePath;
    private SearchFile searchFile;
    private GalleryRecyclerAdaper galleryRecyclerAdaper;
    private GoogleDriveHelper helper;
    //Constant Variables
    private final static int REQUEST_CAMERA_PERMISSION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custome_gallery_layout);

        imagePath = new ImagePath();
        listUserModel = new ArrayList<>();
        searchFile = new SearchFile();
        listStringBackupImageUrl = new ArrayList<>();

        setIdForAllWidgets();

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        setPathToFile();

        galleryRecyclerAdaper = new GalleryRecyclerAdaper(listUserModel, arrayListImageClickHour, this);
        recyclerView.setAdapter(galleryRecyclerAdaper);
        galleryRecyclerAdaper.notifyDataSetChanged();

        linearLayoutSynk.setOnClickListener(this);
        linearLayoutCamera.setOnClickListener(this);
    }

    private void setPathToFile() {
        file = new File(imagePath.NVCImages);
        if (file.exists()) {
            listUserModel = searchFile.getImagesFromFile(file);
            arrayListImageClickHour = searchFile.getImageClickTime();
            Collections.sort(arrayListImageClickHour);
            Toast.makeText(this, "" + arrayListImageClickHour.size(), Toast.LENGTH_SHORT).show();
            for (int i = 0; i < arrayListImageClickHour.size(); i++) {
                System.out.println("Hours Are Here : " + arrayListImageClickHour.get(i));

            }

        } else {
            Toast.makeText(this, "No Images In Gallery Go To Camera Activity & Click Some Sample Shots", Toast.LENGTH_SHORT).show();
            //Show any message i.e your camera doesn't having any images
        }
    }

    private void setIdForAllWidgets() {
        recyclerView = findViewById(R.id.recycler_view_gallery_main);
        linearLayoutCamera = findViewById(R.id.linear_layout_camera_image_custom_gallery);
        linearLayoutSynk = findViewById(R.id.linear_layout_synk_image_custom_gallery);
    }


    @Override
    public void onClick(View view) {
        if (view == linearLayoutCamera) {
            Intent intent = new Intent(MainActivity.this, CustomCameraClass.class);
            intent.putExtra("CURRENT_CAMERA", 0);
            startActivity(intent);
        } else if (view == linearLayoutSynk) {
            BackupData();

        }
    }

    private void BackupData() {
        checkPermissions();
        requestSignIn();

        CountDownTimer countDownTimer=new CountDownTimer(1000,100) {
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                showDialog();
            }
        };
        countDownTimer.start();
    }

    private void showDialog() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Do you want to sync Data");
        alertDialog.setTitle("Please select ok if you want to sync data to google drive");
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadDataToDrive();
            }
        });
        alertDialog.show();

    }

    private void requestSignIn() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_METADATA))
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        startActivityForResult(googleSignInClient.getSignInIntent(), 400);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 400:
                if (resultCode == RESULT_OK) {
                    handelLoginIntent(data);
                }
                break;
        }
    }

    private void checkPermissions() {
        //checking realtime permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS
            }, REQUEST_CAMERA_PERMISSION);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "You can't sync Data without permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void handelLoginIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        GoogleAccountCredential googleAccountCredential = GoogleAccountCredential.usingOAuth2(MainActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
                        googleAccountCredential.setSelectedAccount(googleSignInAccount.getAccount());

                        Drive googleDriveServices = new Drive.Builder(
                                AndroidHttp.newCompatibleTransport(),
                                new GsonFactory(),
                                googleAccountCredential)
                                .setApplicationName("InfilectCustomGallery ")
                                .build();

                        helper = new GoogleDriveHelper(googleDriveServices);
                    }
                }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Problem In SigIn to google drive ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadDataToDrive() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading to google drive");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        ImagePath path = new ImagePath();
        SearchFile searchFile = new SearchFile();

        String filePath = path.NVCImages;
        File file = new File(filePath);
        final int[] counter = {0};

        listStringBackupImageUrl = searchFile.getImagesFromFileForBackup(file);

        for(String sPath:listStringBackupImageUrl) {

            if (helper != null) {
                helper.createFile(listStringBackupImageUrl.get(0)).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        counter[0]++;
                        Toast.makeText(MainActivity.this, "Uploaded SucessFully", Toast.LENGTH_SHORT).show();
                        progressDialog.setMessage(counter[0] + " Image Uploaded Sucessfully");
                        if (counter[0] == (listStringBackupImageUrl.size() - 1)) {
                            progressDialog.dismiss();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Check your Drive permissions", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(this, "Class Null", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
