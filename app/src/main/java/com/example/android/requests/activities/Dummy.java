package com.example.android.requests.activities;

import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.android.requests.R;
import com.example.android.requests.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Dummy extends AppCompatActivity {
    int REQUEST_CAMERA=1;
    int SELECT_FILE =2;
    CognitoCachingCredentialsProvider credentialsProvider;
    AmazonS3 s3;
    TransferUtility transferUtility;
    TransferObserver observer;
    ObjectMetadata metadata;
    BasicAWSCredentials awsCreds;


    TransferListener transferListener = new TransferListener(){

        @Override
        public void onStateChanged(int id, TransferState state) {

        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            int percentage = (int) (bytesCurrent/bytesTotal * 100);
            Log.i("TAG", String.valueOf(percentage));

        }

        @Override
        public void onError(int id, Exception ex) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_uploading);
//        credentialsProvider = new CognitoCachingCredentialsProvider(
//                getApplicationContext(),
//                "ap-northeast-1:cf2e3a1f-4d77-4adc-9fe0-fef2630c6d5f", // Identity Pool ID
//                Regions.AP_NORTHEAST_1
//
//        );

        awsCreds = new BasicAWSCredentials("AKIAIAKCPCCLQ7KZ7NPQ", "ZkVtFY9o98gdJfSkvA+5QwbyTq5tEzS1B8tX9l9y");

        metadata = new ObjectMetadata();
        metadata.setContentEncoding("UTF-8");
        s3= new AmazonS3Client(awsCreds);


//        TransferListener listener = new UploadListener();

        transferUtility = new TransferUtility(s3, getApplicationContext());

        Button btn = (Button)findViewById(R.id.btnSelectPhoto);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Dummy.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[item].equals("Choose from Library")) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            // if you multiple pics to be selected
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            //intent.setType("image/* video/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("TAG", String.valueOf(requestCode));
        Log.i("TAG", String.valueOf(RESULT_OK));
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap thumbnail = (Bitmap) extras.get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
                String farjy =  getRealPathFromURI(tempUri);
                File file = new java.io.File(farjy);
                observer = transferUtility.upload("tulsi45676gcvbn", file.getName(), file, metadata);


            } else if (requestCode == SELECT_FILE) {
                if(data != null) {
                    String imagePath;
                    Uri selectedImageUri = data.getData();

                    if (selectedImageUri != null) {
                        imagePath = getRealPathFromURI(selectedImageUri);

                    }
                    else{
                        //bitmap = (Bitmap)data.getExtras().get("data");
                        String[] projection = {MediaStore.MediaColumns.DATA};
                        CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                        Cursor cursor = cursorLoader.loadInBackground();
                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                        cursor.moveToFirst();
                         imagePath = cursor.getString(column_index);

                    }

                    File file = new java.io.File(imagePath);
                    SharedPreferences sharepref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
                    String user_id = sharepref.getString(Constant.EMAIL_ID, "");
                    if (!user_id.equals("")) {

                        observer = transferUtility.upload("tulsi45676gcvbn", "users/profile_pic/" + md5(user_id), file, metadata);
                    }
                }


//                String[] projection = {MediaStore.MediaColumns.DATA};
//                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
//                Cursor cursor = cursorLoader.loadInBackground();
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//                cursor.moveToFirst();
//
//                String selectedImagePath = cursor.getString(column_index);
//                Log.i("TAG",selectedImagePath);


//                Bitmap bm;
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(selectedImagePath, options);
//                final int REQUIRED_SIZE = 200;
//                int scale = 1;
//                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//                    scale *= 2;
//                options.inSampleSize = scale;
//                options.inJustDecodeBounds = false;
//                bm = BitmapFactory.decodeFile(selectedImagePath, options);
            }
        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private class UploadListener implements TransferListener {

        // Simply updates the UI list when notified.
        @Override
        public void onError(int id, Exception e) {
            Log.i("TAG", "Error during upload: " + id, e);
        }

        @Override
        public void onStateChanged(int id, TransferState state) {
            Log.i("TAG", "Error during uploarerd: " + id);
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
            Log.i("TAG", "Error during uploarerd: " + id);
        }
    }


        public String md5(String s) {
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                for (int i=0; i<messageDigest.length; i++)
                    hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }

    public String getRealPathFromURI(Uri contentURI) {


        if( contentURI == null ) {
                // TODO perform some logging or show user feedback
                return null;
            }
            // try to retrieve the image from the media store first
            // this will only work for images selected from gallery
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(contentURI, projection, null, null, null);
            if( cursor != null ){
                int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
            // this is our fallback here
            return contentURI.getPath();
    }


}
