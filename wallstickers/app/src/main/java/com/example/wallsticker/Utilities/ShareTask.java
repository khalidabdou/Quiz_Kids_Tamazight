package com.example.wallsticker.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.example.wallsticker.R;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShareTask extends AsyncTask<String, String, String> {
    private final Context context;
    URL myFileUrl;
    Bitmap bmImg = null;
    File file;
    InputStream is;
    boolean isGif = false;
    private ProgressDialog pDialog;
    private String ToPackage;

    public ShareTask(Context context, String ToPackage) {
        this.context = context;
        this.ToPackage = ToPackage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.msg_please_wait));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected String doInBackground(String... args) {


        try {


            myFileUrl = new URL(args[0]);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bmImg = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            String path = myFileUrl.getPath();
            if (path.contains(".gif")) {
                isGif = true;
                path = args[0];
                String idStr = path.substring(path.lastIndexOf('/') + 1);
                File filepath = context.getExternalFilesDir(null);
                //context.getExternalFilesDir(null);
                File dir = new File(filepath.getAbsolutePath() + "/Download/");
                dir.mkdirs();
                String fileName = idStr;
                file = new File(dir, fileName);
                Toast.makeText(context,"is Gif",Toast.LENGTH_LONG).show();
                Ion.with(context.getApplicationContext()).load(path)
                        .write(file);
            } else {
                isGif = false;
                String idStr = path.substring(path.lastIndexOf('/') + 1);
                File filepath = context.getExternalFilesDir(null);
                File dir = new File(filepath.getAbsolutePath() + "/" + context.getResources().getString(R.string.saved_folder_name) + "/");
                dir.mkdirs();
                String fileName = idStr;
                file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bmImg.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                fos.flush();
                fos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String args) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                Intent share = new Intent(Intent.ACTION_SEND);
                if (ToPackage != null)
                    share.setPackage(ToPackage);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));

                // SHARE URL
                if (Const.enable_share_with_package)
                    share.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.share_text) + "\n" + context.getString(R.string.store_prefix) + context.getPackageName());
                context.startActivity(Intent.createChooser(share, "Share the photo"));

                pDialog.dismiss();
            }
        }, Const.Companion.getDELAY_SET_WALLPAPER());

    }
}
