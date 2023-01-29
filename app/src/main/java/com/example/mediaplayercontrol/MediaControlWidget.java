package com.example.mediaplayercontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Implementation of App Widget functionality.
 */
public class MediaControlWidget extends AppWidgetProvider
{
    private static final String MPCHCWEBURL = "http://cosmos:13579/command.html?wm_command=";
    private static final String CMD_PAUSE = "888";
    private static final String CMD_PLAY = "887";
    private static final String CMD_FW = "900";
    private static final String CMD_BW = "899";
    private static final String CMD_PLAY_NEXT = "922";
    private static final String CMD_AUDIO_PREV = "953";
    private static final String CMD_AUDIO_NEXT = "952";
    private static final String CMD_SUB_TOGGLE = "956";
    private static final String CMD_SUB_PREV = "955";
    private static final String CMD_SUB_NEXT = "954";
    private static final String ACTION_PLAY   = "com.controlwidget.action.ACTION_PLAY";
    private static final String ACTION_PAUSE   = "com.controlwidget.action.ACTION_PAUSE";
    private static final String ACTION_FW   = "com.controlwidget.action.ACTION_FW";
    private static final String ACTION_BW   = "com.controlwidget.action.ACTION_BW";
    private static final String ACTION_PLAY_NEXT   = "com.controlwidget.action.ACTION_PLAY_NEXT";
    private static final String ACTION_AUDIO_PREV   = "com.controlwidget.action.ACTION_AUDIO_PREV";
    private static final String ACTION_AUDIO_NEXT = "com.controlwidget.action.ACTION_AUDIO_NEXT";
    private static final String ACTION_SUB_TOGGLE   = "com.controlwidget.action.ACTION_SUB_TOGGLE";
    private static final String ACTION_SUB_NEXT   = "com.controlwidget.action.ACTION_SUB_NEXT";
    private static final String ACTION_SUB_PREV   = "com.controlwidget.action.ACTION_SUB_PREV";

    private final OkHttpClient httpClient = new OkHttpClient();
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.media_control_widget);


        views.setOnClickPendingIntent(R.id.btn_play,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_PLAY),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_pause,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_PAUSE),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_fw,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_FW),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_bw,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_BW),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_play_next,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_PLAY_NEXT),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_sub_toggle,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_SUB_TOGGLE),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_sub_prev,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_SUB_PREV),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_sub_next,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_SUB_NEXT),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_audio_prev,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_AUDIO_PREV),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        views.setOnClickPendingIntent(R.id.btn_audio_next,
                PendingIntent.getBroadcast(context, 0,
                        new Intent(context, MediaControlWidget.class).setAction(ACTION_AUDIO_NEXT),
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // Chain up to the super class so the onEnabled, etc callbacks get dispatched
        super.onReceive(context, intent);
        // Handle a different Intent

        String action = intent.getAction();

        Request rq = null;

        if (action.equals(ACTION_PAUSE))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_PAUSE).build();
        }
        else if (action.equals(ACTION_PLAY))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_PLAY).build();
        }
        else if (action.equals(ACTION_FW))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_FW).build();
        }
        else if (action.equals(ACTION_BW))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_BW).build();
        }
        else if (action.equals(ACTION_PLAY_NEXT))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_PLAY_NEXT).build();
        }
        else if (action.equals(ACTION_SUB_TOGGLE))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_SUB_TOGGLE).build();
        }
        else if (action.equals(ACTION_SUB_PREV))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_SUB_PREV).build();
        }
        else if (action.equals(ACTION_SUB_NEXT))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_SUB_NEXT).build();
        }
        else if (action.equals(ACTION_AUDIO_PREV))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_AUDIO_PREV).build();
        }
        else if (action.equals(ACTION_AUDIO_NEXT))
        {
            rq = new Request.Builder().url(MPCHCWEBURL + CMD_AUDIO_NEXT).build();
        }


        if(rq != null)
        {
            httpClient.newCall(rq).enqueue(new Callback()
            {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e)
                {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException
                {
                }
            });
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}