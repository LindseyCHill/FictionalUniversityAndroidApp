package student.lindseychill.mobileapplicationprojectwgu.Utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    static int uniqueID;
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        NotificationHelper notificationHelper = new NotificationHelper(context,title,text);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(uniqueID, nb.build());
    }
    public static void setUniqueID(int uniqueID){
        AlertReceiver.uniqueID = uniqueID;
    }
}