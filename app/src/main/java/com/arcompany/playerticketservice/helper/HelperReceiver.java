package com.arcompany.playerticketservice.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.arcompany.playerticketservice.MainActivity;
import com.arcompany.playerticketservice.constant.Constants;
import com.arcompany.playerticketservice.service.TicketService;

public class HelperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntent = new Intent(context.getApplicationContext(), TicketService.class);
        startIntent.setAction(Constants.ACTION.START_ACTION);
        if (Build.VERSION.SDK_INT >= 26)
            context.startForegroundService(startIntent);
        else
            context.startService(startIntent);
    }
}
