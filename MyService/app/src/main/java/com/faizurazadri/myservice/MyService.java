package com.faizurazadri.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

import static android.content.ContentValues.TAG;

public class MyService extends Service implements DummyAsyncCallback  {

    private static final String TAG = MyService.class.getSimpleName();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Service dijalankan");

        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();
        return START_STICKY;
    }

    @Override
    public void preAsync() {
        Log.d(TAG , "preAsync: Mulai...");
    }

    @Override
    public void postAsync() {
        Log.d(TAG, "postAsync: Selesai.....");
    }

    private static class DummyAsync extends AsyncTask<Void, Void, Void>{
        private final WeakReference<DummyAsyncCallback> callback;

        DummyAsync(DummyAsyncCallback callback){
            this.callback = new WeakReference<DummyAsyncCallback>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute: ");
            callback.get().preAsync();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG,"doInBackground: ");
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d(TAG, "onPostExecute");
            callback.get().postAsync();
        }
    }
}
interface DummyAsyncCallback {
    void preAsync();
    void postAsync();
}