package com.example.hrms_android_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

public class ImageViewActivity extends AppCompatActivity {
    String title;  String url;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        title = getIntent().getStringExtra("Title");
        setTitle(title);
        url = getIntent().getStringExtra("url");
        imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.get().load(url).into(imageView);
        imageView.setOnTouchListener(new ImageMatrixTouchHandler(getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_img, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.share_img:
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                shareImageandText(bitmap);

//                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
//                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,"title",null);
//                //Uri uri = Uri.parse(bitmapPath);
//                Intent intent = new Intent(Intent.ACTION_SEND);
//               intent.setType("text/plain");
//               // intent.putExtra(Intent.EXTRA_STREAM, uri);
//                intent.putExtra(Intent.EXTRA_TEXT, title);
//                startActivity(Intent.createChooser(intent, "Share"));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getmageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, title);

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // setting type to image
        intent.setType("image/jpg");

        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
    private Uri getmageToShare(Bitmap bitmap) {
        File imagefolder = new File(getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
            File file = new File(imagefolder, "shared_image.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.anni.shareimage.fileprovider", file);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}