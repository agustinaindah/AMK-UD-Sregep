package com.amkuds.app.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.amkuds.app.R;

import java.util.List;
import java.util.Map;

/**
 * Created by agustinaindah on 02 Januari 2019
 */

public class NotificationUtils {

    public static void sendFirebaseNotification(Context context, Class mainActivity,
                                                Map<String, String> data, String title, String
                                                        content) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentTitle(title)
                .setTicker(title)
                .setContentText(content)
                .setAutoCancel(true)
                .setOngoing(false);

        Intent intent = new Intent();
        int flag = 0;

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code
                 */, intent,
                flag);
        notificationBuilder.setContentIntent(pendingIntent);

        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Resources resources = context.getResources(), systemResources = Resources.getSystem();

        notificationBuilder.setSound(ringtoneUri);
        notificationBuilder.setVibrate(new long[]{500, 500});

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = notificationBuilder.build();

        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledOnMS = resources.getInteger(systemResources.getIdentifier(
                "config_defaultNotificationLedOn", "integer", "android"));
        notification.ledOffMS = resources.getInteger(systemResources.getIdentifier(
                "config_defaultNotificationLedOff", "integer", "android"));

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }


    public static boolean hasNotificationExtraKey(Context context, Intent intent, String key,
                                                  Class service) {
        return context != null
                && isServiceAvailable(context, service)
                && intent != null && intent.getStringExtra(key) != null;
    }

    private static boolean isServiceAvailable(Context context, Class service) {
        if (context == null) return false;
        try {
            final PackageManager packageManager = context.getPackageManager();
            final Intent intent = new Intent(context, service);
            List resolveInfo =
                    packageManager.queryIntentServices(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return resolveInfo.size() > 0;
        } catch (Exception ex) {
            return false;
        }
    }

}
