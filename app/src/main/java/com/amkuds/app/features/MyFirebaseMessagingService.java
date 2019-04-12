package com.amkuds.app.features;

import com.amkuds.app.features.main.MainActivity;
import com.amkuds.app.utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            NotificationUtils.sendFirebaseNotification(this,
                    MainActivity.class,
                    remoteMessage.getData(),
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }
}
