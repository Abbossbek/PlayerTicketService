package com.ARCompany.KarmonAdmin;

import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // ...
        //super.onMessageReceived(remoteMessage);
        Values.notification_title=remoteMessage.getNotification().getTitle();
        Values.notification_url=remoteMessage.getData().get("url");
        Values.notification_body=remoteMessage.getNotification().getBody();
        Values.notification_image=remoteMessage.getNotification().getImageUrl();
        ShowNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }


    private void ShowNotification(String title, String message) {
        NotificationCompat.Builder builder =new NotificationCompat.Builder(getApplicationContext(), "Admin")
                .setContentTitle(title)
                .setAutoCancel(true)
                .setContentText(message)
               .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(100,builder.build());

    }
}
