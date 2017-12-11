package com.example.admins.freemusic.ultis;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admins.freemusic.Databases.TopSongModel;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.ThinDownloadManager;

/**
 * Created by Admins on 12/9/2017.
 */

public class DownloadHandler {
    private static final String TAG = DownloadHandler.class.toString() ;

    public static void DownloadSong(final Context context, final TopSongModel topSongModel) {
        Uri downloadUri = Uri.parse(topSongModel.url);
        final Uri destinationUri = Uri.parse(context.getExternalCacheDir().toString() + "/" + topSongModel.song +"-"+ topSongModel.signer +".mp3");
        DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
                .setRetryPolicy(new DefaultRetryPolicy())
                .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
                .addCustomHeader("Auth-Token", "YourTokenApiKey")
                .setDownloadContext(context)
                .setDownloadListener(new DownloadStatusListener() {
                    @Override
                    public void onDownloadComplete(int id) {
                        Toast.makeText(context, " COMPLETE", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onDownloadComplete: "+destinationUri.toString());

                    }

                    @Override
                    public void onDownloadFailed(int id, int errorCode, String errorMessage) {
                        Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onProgress(int id, long totalBytes, long downloadedBytes, int progress) {
//                        Toast.makeText(context, "Progressing", Toast.LENGTH_SHORT).show();

                    }
                });

        ThinDownloadManager thinDownloadManager = new ThinDownloadManager();
        thinDownloadManager.add(downloadRequest);


    }
}
