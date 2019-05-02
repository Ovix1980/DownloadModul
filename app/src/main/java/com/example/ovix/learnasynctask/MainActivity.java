package com.example.ovix.learnasynctask;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button bttnDownload;

    String imageHasDownloaded;

    private ProgressBar progressBar;

    private ImageView imageView;

    private TextView textProgress;

    private static String URL = "http://www.astrodynamics.net/wp-content/uploads/2010/04/Scorpio-full-moon.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageView = (ImageView)findViewById(R.id.stateImage);
        bttnDownload = (Button)findViewById(R.id.bttnDownload);
        textProgress = (TextView)findViewById(R.id.textProgress);


        imageView.setVisibility(View.VISIBLE);

        bttnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               downloadData();
            }
        });


    }

    public void downloadData(){
        DownloadFile downloadFile = new DownloadFile(MainActivity.this);
        downloadFile.execute(URL);
    }

    public void setImageDownloaded(String imageDownloaded){
        if(imageDownloaded != null) {
            this.imageHasDownloaded = imageDownloaded;
        }else{
            Log.d("Url Image di Activity :","NULL");
        }
    }

    public void setStateImage(int stateImage){
        switch(stateImage){
            case 1:
                imageView.setImageResource(R.drawable.ic_file_download_black_24dp);
                break;
            case 2:
                imageView.setImageResource(R.drawable.ic_save_black_24dp);
                break;
            default:
                imageView.setImageDrawable(Drawable.createFromPath(imageHasDownloaded));
                break;
        }
    }

    public void setShowProgress(int inProgress){
        progressBar.setProgress(inProgress);
        textProgress.setText(String.valueOf(inProgress));
    }
}
