package com.aosp.bookstore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class BookDetailActivity extends AppCompatActivity {

    static final int REQUEST_PERMISSION_CODE = 102;
    ShapeableImageView bookCoverPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        bookCoverPhoto = findViewById(R.id.img_book);
        FloatingActionButton editPhotoButton = findViewById(R.id.fab_edit_photo);
        editPhotoButton.setOnClickListener(v -> selectImage());

    }
    // dialog to choose camera or gallery
    private void selectImage(){
        if(hasPermission()) {
            final String[] options = {"Select from gallery", "take photo", "cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.book_alert_title);
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (options[which].equals("Select from gallery")) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        photoResultLauncher.launch(pickPhoto);
                    } else if (options[which].equals("take photo")) {
                        Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraResultLauncher.launch(takePhoto);
                    } else {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }else{
            requestPermissions();
        }
    }
    //check permissions
    private boolean hasPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    // request permission
    private void requestPermissions(){
        String[] listPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, listPermissions, REQUEST_PERMISSION_CODE);
    }

    //Activity result launcher
    ActivityResultLauncher<Intent> cameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getData() != null && result.getResultCode() == RESULT_OK){
                        Bitmap selectedImage = (Bitmap) result.getData().getExtras().get("data");
                        bookCoverPhoto.setImageBitmap(selectedImage);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> photoResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getData() != null && result.getResultCode() == RESULT_OK) {
                        Uri selectedImage = result.getData().getData();
                        bookCoverPhoto.setImageURI(selectedImage);
                    }
                }
            }
    );

    // PERMISSION results


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean cameraFlag = false;
        boolean writeStorageflag = false;
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals(Manifest.permission.CAMERA)){
                cameraFlag = checkPermissionGranted(grantResults, i);
                if(!cameraFlag && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                    showRationalMessage("camera");
                }
            }
            else if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                writeStorageflag = checkPermissionGranted(grantResults, i);
                if(!writeStorageflag && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    showRationalMessage("write to external storage");
                }
            }
        }
//        if( cameraFlag || writeStorageflag)
//            selectImage();
    }

    private void showRationalMessage(String message) {
        message = "Requires " +  message + " permission for the app to get bookcover photo.";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showdDenialMessage();
                    }
                });
        builder.show();
    }

    private void showdDenialMessage() {
        Toast.makeText(this , "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
    }

    private boolean checkPermissionGranted(int[] grantResults, int i) {
        return grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED;
    }
}