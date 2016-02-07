package com.example.android.requests.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.requests.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUploading extends AppCompatActivity {
    ImageView ivImage;
    int REQUEST_CAMERA=1;
    int SELECT_FILE =2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_uploading);
        Button btn = (Button)findViewById(R.id.btnSelectPhoto);
        ivImage = (ImageView)findViewById(R.id.ivImage);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

//    private void captureImage() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//        // start the image capture Intent
//        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ImageUploading.this);
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
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("TAG", String.valueOf(requestCode));
        Log.i("TAG", String.valueOf(resultCode));
        Log.i("TAG", String.valueOf(RESULT_OK));
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bundle extras = data.getExtras();
                Bitmap thumbnail = (Bitmap) extras.get("data");
                saveToInternalStorage(thumbnail);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//                File destination = new File(Environment.getExternalStorageDirectory(),
//                        System.currentTimeMillis() + ".jpg");
//
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                ivImage.setImageBitmap(thumbnail);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);
                Log.i("TAG",selectedImagePath);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                ivImage.setImageBitmap(bm);
            }
        }

    }
    private String saveToInternalStorage(Bitmap bitmapImage){
        //ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/Alternative Images
        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "Alternative Images");
        // Create imageDir
        FileOutputStream fos = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        try {
            File mypath = File.createTempFile("Profile-" + timeStamp, ".jpg", imagesFolder);
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream

            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            Log.i("TAG", mypath.getAbsolutePath());
            //ContentResolver cr = getContentResolver();
            //ContentValues values = new ContentValues();
            // ÷ISPLAY_NAME, file.getName().toLowerCase(Locale.US));
            //values.put("_data", mypath.getAbsolutePath());
            //cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            return mypath.getAbsolutePath();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            //fos.close();
        }
        return "";

    }


    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    private class AsynRunner extends AsyncTask<String, Void, String> {

        @Override
            protected void onPreExecute() {

            };

        public AsynRunner() {

        }

        @Override
        protected String doInBackground(String... params) {
            BitmapFactory.Options options = null;
            options = new BitmapFactory.Options();
            options.inSampleSize = 3;
            Bitmap bitmap = BitmapFactory.decodeFile("imgPath", options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // Must compress the Image to reduce image size to make upload easy
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byte[] byte_arr = stream.toByteArray();
            // Encode Image to String
             String encodedString = Base64.encodeToString(byte_arr, 0);
            return "";
        }




            @Override
            protected void onPostExecute(String msg) {
//                prgDialog.setMessage("Calling Upload");
//                // Put converted Image string into Async Http Post param
//                params.put("image", encodedString);
//                // Trigger Image upload
//                triggerImageUpload();
            }
    }

    public void hey (){
//        OutputStream fOut = null;
//        File file = new File( imagePath,"GE_"+ System.currentTimeMillis() +".jpg");
//
//        try {
//            fOut = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//        try {
//            fOut.flush();
//            fOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.TITLE, this.getString(R.string.picture_title));
//        values.put(MediaStore.Images.Media.DESCRIPTION, this.getString(R.string.picture_description));
//        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
//        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, file.toString().toLowerCase(Locale.US).hashCode());
//        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, file.getName().toLowerCase(Locale.US));
//        values.put("_data", file.getAbsolutePath());
//
//        ContentResolver cr = getContentResolver();
//        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

}

