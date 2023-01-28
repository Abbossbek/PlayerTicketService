package com.arcompany.playerticketservice.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arcompany.playerticketservice.MainActivity;
import com.arcompany.playerticketservice.R;
import com.arcompany.playerticketservice.constant.Constants;

public class TicketService extends Service {
    private final static String FOREGROUND_CHANNEL_ID = "foreground_channel_id";
    private static final String TAG = TicketService.class.getSimpleName();

    private NotificationManager mNotificationManager;
    static private int sStateService = Constants.STATE_SERVICE.START;
    private final int interval = 3000;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable(){
        public void run() {
// ...

// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://www.google.com";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    textView.setText("That didn't work!");
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
            Toast.makeText(getApplicationContext(), "C'Mom no hands!", Toast.LENGTH_SHORT).show();
            if(sStateService == Constants.STATE_SERVICE.START)
                handler.postDelayed(this, interval);
        }
    };
    public TicketService() {
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sStateService = Constants.STATE_SERVICE.START;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getAction()) {
            case Constants.ACTION.START_ACTION:
                Log.i(TAG, "Received start Intent ");
                sStateService = Constants.STATE_SERVICE.START;
                handler.postDelayed(runnable, interval);
                startForeground(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
                break;

            case Constants.ACTION.PAUSE_ACTION:
                sStateService = Constants.STATE_SERVICE.PAUSE;
                mNotificationManager.notify(Constants.NOTIFICATION_ID_FOREGROUND_SERVICE, prepareNotification());
                Log.i(TAG, "Clicked Pause");
                break;

            case Constants.ACTION.STOP_ACTION:
                Log.i(TAG, "Received Stop Intent");
                sStateService = Constants.STATE_SERVICE.STOP;
                stopForeground(true);
                stopSelf();
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }
    private Notification prepareNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O &&
                mNotificationManager.getNotificationChannel(FOREGROUND_CHANNEL_ID) == null) {
            // The user-visible name of the channel.
            CharSequence name = getString(R.string.service_running);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(FOREGROUND_CHANNEL_ID, name, importance);
            mChannel.setSound(null, null);
            mChannel.enableVibration(false);
            mNotificationManager.createNotificationChannel(mChannel);
        }
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setAction(Constants.ACTION.START_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent lPauseIntent = new Intent(this, TicketService.class);
        lPauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
        PendingIntent lPendingPauseIntent = PendingIntent.getService(this, 0, lPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(this, TicketService.class);
        playIntent.setAction(Constants.ACTION.START_ACTION);
        PendingIntent lPendingPlayIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent lStopIntent = new Intent(this, TicketService.class);
        lStopIntent.setAction(Constants.ACTION.STOP_ACTION);
        PendingIntent lPendingStopIntent = PendingIntent.getService(this, 0, lStopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews lRemoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        lRemoteViews.setOnClickPendingIntent(R.id.ui_notification_close_button, lPendingStopIntent);

        switch (sStateService) {

            case Constants.STATE_SERVICE.PAUSE:
                lRemoteViews.setTextViewText(R.id.tv_status, getText(R.string.service_paused));
                lRemoteViews.setViewVisibility(R.id.ui_notification_progress_bar, View.INVISIBLE);
                lRemoteViews.setOnClickPendingIntent(R.id.ui_notification_player_button, lPendingPlayIntent);
                lRemoteViews.setImageViewResource(R.id.ui_notification_player_button, R.drawable.ic_play_arrow_white);
                break;

            case Constants.STATE_SERVICE.START:
                lRemoteViews.setTextViewText(R.id.tv_status, getText(R.string.service_running));
                lRemoteViews.setViewVisibility(R.id.ui_notification_progress_bar, View.INVISIBLE);
                lRemoteViews.setOnClickPendingIntent(R.id.ui_notification_player_button, lPendingPauseIntent);
                lRemoteViews.setImageViewResource(R.id.ui_notification_player_button, R.drawable.ic_pause_white);
                break;

        }

        NotificationCompat.Builder lNotificationBuilder;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            lNotificationBuilder = new NotificationCompat.Builder(this, FOREGROUND_CHANNEL_ID);
        } else {
            lNotificationBuilder = new NotificationCompat.Builder(this);
        }
        lNotificationBuilder
                .setContent(lRemoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setCategory(NotificationCompat.CATEGORY_SERVICE)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            lNotificationBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
        return lNotificationBuilder.build();

    }
}