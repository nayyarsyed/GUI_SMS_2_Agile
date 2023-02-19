package com.example.gui_sms_2_agile;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ToastService extends Service {
    private Handler mHandler = new Handler();
    private static final int NOTIFICATION_ID = 123;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create a notification channel for the foreground service (required for Android 8.0 and above)
        createNotificationChannel();

        // Build the notification that will be displayed when the service is running in the foreground
        Notification notification = new NotificationCompat.Builder(this, "default")
                .setContentTitle("My App is Running")
                .setContentText("Click to open")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
                .build();

        // Start the service as a foreground service with the notification
        startForeground(NOTIFICATION_ID, notification);

        mHandler.postDelayed(mShowToastRunnable, 5000); // Show first toast after 5 seconds
        return START_STICKY; // Service will be restarted if it's killed by the system
    }

    private Runnable mShowToastRunnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(getApplicationContext(), "Hello from the service!", Toast.LENGTH_SHORT).show();
            Log.d("ToastService", "Toast shown at " + System.currentTimeMillis());
            mHandler.postDelayed(this, 5000); // Show next toast after 5 seconds
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel("default", "My App Service", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}


//public class ToastService extends Service {
//    private Handler mHandler = new Handler();
//    private static final int NOTIFICATION_ID = 123;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        // Create a notification channel for the foreground service (required for Android 8.0 and above)
//        createNotificationChannel();
//
//        // Build the notification that will be displayed when the service is running in the foreground
//        Notification notification = new NotificationCompat.Builder(this, "default")
//                .setContentTitle("My App is Running")
//                .setContentText("Click to open")
//                .setSmallIcon(R.drawable.ic_notification)
//                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0))
//                .build();
//
//        // Start the service as a foreground service with the notification
//        startForeground(NOTIFICATION_ID, notification);
//
//        mHandler.postDelayed(mShowToastRunnable, 5000); // Show first toast after 5 seconds
//        return START_STICKY; // Service will be restarted if it's killed by the system
//    }
//
//    private Runnable mShowToastRunnable = new Runnable() {
//        @Override
//        public void run() {
//            Toast.makeText(getApplicationContext(), "Hello from the service!", Toast.LENGTH_SHORT).show();
//            Log.d("ToastService", "Toast shown at " + System.currentTimeMillis());
//            mHandler.postDelayed(this, 5000); // Show next toast after 5 seconds
//        }
//    };
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        stopForeground(true);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("default", "My App Service", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//}
