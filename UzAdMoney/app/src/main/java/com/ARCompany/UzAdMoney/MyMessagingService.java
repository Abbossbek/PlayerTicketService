package com.ARCompany.UzAdMoney;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // ...
        super.onMessageReceived(remoteMessage);

        ShowNotification(remoteMessage.getNotification());
    }


    private void ShowNotification(RemoteMessage.Notification notification) {
        NotificationCompat.Builder builder =new NotificationCompat.Builder(getApplicationContext(), "My")
                .setContentTitle(notification.getTitle())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setContentText(notification.getBody())
                .setContentIntent(PendingIntent.getActivity(this,1,new Intent(this, ProfileActivity.class),PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(0,builder.build());

    }

}
