package com.ashrafunahid.asynctaskbackground.Classes;

import android.app.Activity;

public abstract class AsynchronousRequest {

    Activity activity;
    public AsynchronousRequest(Activity activity) {
        this.activity = activity;
    }

    public void startAsyncRequest(){
        // on pre execute
        onPreExecute();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do in background
                doInBackground();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // do post task
                        onPostExecute();
                    }
                });

            }
        }).start();
    }

    public void execute(){
        startAsyncRequest();
    }

    public abstract void onPreExecute();
    public abstract void doInBackground();
    public abstract void onPostExecute();

}
