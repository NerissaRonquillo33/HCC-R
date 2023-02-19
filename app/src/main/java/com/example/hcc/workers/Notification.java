package com.example.hcc.workers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.hcc.Login;
import com.example.hcc.R;
import com.example.hcc.abstracts.Database;
import com.example.hcc.http_request.HttpRequest;
import com.example.hcc.interfaces.RequestCallback;
import com.example.hcc.models.Announcements;
import com.example.hcc.models.Bills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Notification extends Worker {

    Context context;
    Database database;

    public Notification(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        database = Database.getInstance(context);
        String studentid = getInputData().getString("studentid");
        checkAnnouncement(studentid);
        return Result.success();
    }

    private void displayNotification(String title, String task) {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notifier", "notifier", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Intent myIntent = new Intent(context, Login.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,myIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "notifier")
                .setContentTitle(title)
                .setContentText(task)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        notificationManager.notify(1, notification.build());
    }

    public void checkAnnouncement(String studentid) {
        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("secret_key", "secret_key");
            jsonParams.put("username", studentid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpRequest().doPost(context, "https://sisholycrosscollege.info/api/notifications.php", jsonParams, new RequestCallback() {
            @Override
            public void success(String response, JSONObject jsonObject) {
                if (response.equals("success")) {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("announcement");
                        JSONObject jsonObject2 = jsonObject.getJSONObject("billing");
                        //jsonArray.length()
                        List<Announcements> announcements = database.announcementsDao().all();
                        Bills bills = database.billsDao().find(studentid);
                        String message = "";
                        if (announcements.size() != jsonArray.length()) {
                            message = "New Announcement Arrived!";
                        }
                        if (bills != null && bills.getDatecreated() != jsonObject2.getInt("convertedTS")) {
                            message = message.length() > 0 ? message + "\nNew Billing Arrived!" : "New Billing Arrived!";
                        }
                        if (message.length() > 0) {
                            displayNotification("HCC", message);
                        }
                    } catch (JSONException e) {
                        //todo
                    }
                }
            }
        });
    }
}
