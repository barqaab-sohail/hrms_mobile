package com.example.hrms_android_3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class PdfViewActivity extends AppCompatActivity {

   private String pdfurl;
            //"http://192.168.1.10/hrms/public/storage/hr/documentation/3-sohail_afzal/3-HR Form-1588017649.pdf"

    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        setTitle(getIntent().getStringExtra("Title"));
        pdfurl = getIntent().getStringExtra("url");
        pdfView = findViewById(R.id.idPDFView);
        new RetrivePDFfromUrl().execute(pdfurl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.share_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_pdf:
                Toast.makeText(getApplicationContext(), "PDf Click", Toast.LENGTH_SHORT).show();


                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // create an async task class for loading pdf file from URL.
    private class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                //FOR HTTPS CONNTECTION PLESE USE
                //  HttpURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
               if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
               }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            //Toast.makeText(getApplicationContext(), inputStream.toString(), Toast.LENGTH_SHORT).show();

//            InputStream pdf = inputStream;
//            try (FileOutputStream outputStream =6 new FileOutputStream(new File("/Users/kirkbrown/documents/my.pdf"))) {
//                int read;
//                byte[] bytes = new byte[1024];
//                while ((read = pdf.read(bytes)) != -1) {
//                    outputStream.write(bytes, 0, read);
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            pdfView.fromStream(inputStream).load();
        }
    }
}