package com.example.ovix.learnasynctask;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ovix on 12/27/17.
 */

public class DownloadFile extends AsyncTask<String,String,String> {

    private MainActivity mainActivity;

    String fileName = "downloaded.jpg";

    public DownloadFile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    @Override
    public void onPreExecute(){
        super.onPreExecute();
        mainActivity.setStateImage(1);
    }


    @Override
    protected String doInBackground(String... fetch_url) {

        int count;

        try{
            URL url = new URL(fetch_url[0]);
            URLConnection conn = url.openConnection();
            conn.connect();

            int lengthOfFile = conn.getContentLength();

            InputStream inputStream = new BufferedInputStream(url.openStream(),8192);

            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath()+"/download");
            dir.mkdirs();
            File fileDownloaded = new File(dir,fileName);

            Log.d("Url Image Path Simpan ",String.valueOf(fileDownloaded));

            OutputStream outputStream = new FileOutputStream(fileDownloaded);



            byte data[] = new byte[1024];

            long total = 0;

            while((count = inputStream.read(data)) != -1){
                 total += count;

                 publishProgress(""+(int)((total*100/lengthOfFile)));


                 outputStream.write(data,0,count);
            }



        }
        catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        super.onProgressUpdate(progress);
        mainActivity.setShowProgress(Integer.parseInt(progress[0]));
        Log.d("Isi ", progress[0]);
        mainActivity.setStateImage(2);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String imagePath = Environment.getExternalStorageDirectory().getPath()+"/download/"+fileName;
        mainActivity.setImageDownloaded(imagePath);
        mainActivity.setStateImage(3);
        Log.d("Url Image Path Buka ",imagePath);

    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

}
