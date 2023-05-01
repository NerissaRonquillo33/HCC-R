package com.example.hcc.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.hcc.Login;
import com.example.hcc.R;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Students;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Notifier extends FirebaseMessagingService {
    private static final String TAG = "NotifierLogger";

    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.d(TAG, "Token: " + token);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("tttt " + token);
        sendToken(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getNotification() != null) {
            PopNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void sendToken(String token) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("token", token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(Notifier.this, getResources().getString(R.string.server_path) + "token-saver.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    //nothing
                    Toast.makeText(Notifier.this, "Token: " + response, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Token: not success " + response);
                } else {
                    Toast.makeText(Notifier.this, "Token: " + response, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "Token: not success " + response);
                }
            }
        });
    }

    public void PopNotification(String title, String message)
    {
        Intent intent = new Intent(this, Login.class);
        String channel_id = "notification_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder  = new NotificationCompat
                .Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.log)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        builder = builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.log);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT  >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, builder.build());
    }
}
