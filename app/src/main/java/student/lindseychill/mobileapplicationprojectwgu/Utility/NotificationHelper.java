package student.lindseychill.mobileapplicationprojectwgu.Utility;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import student.lindseychill.mobileapplicationprojectwgu.R;

/**
 * Reference used for understanding:
 * Schedule alarms  :   android developers. Android Developers. (n.d.).
 * Retrieved May 17, 2022, from https://developer.android.com/training/scheduling/alarms
 */
public class NotificationHelper extends ContextWrapper {
    public static final String CHANNEL_ID = "FHU";
    public static final String CHANNEL_NAME = "FHU Alert Channel";
    public static final String NOTIFICATION_GROUP = "FHU Notifications";
    static String title;
    static String contentText;

    private NotificationManager mManager;

    public NotificationHelper(Context ctx, String title, String text) {
        super(ctx);
        NotificationHelper.title = title;
        NotificationHelper.contentText = text;
        createChannel();
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.ic_notifications)
                .setGroup(NOTIFICATION_GROUP);
    }
}
