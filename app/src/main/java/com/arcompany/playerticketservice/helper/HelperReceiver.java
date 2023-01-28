package com.arcompany.playerticketservice.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.arcompany.playerticketservice.MainActivity;
import com.arcompany.playerticketservice.constant.Constants;
import com.arcompany.playerticketservice.service.TicketService;

public class HelperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startIntent = new Intent(context.getApplicationContext(), TicketService.class);
        startIntent.setAction(Constants.ACTION.START_ACTION);
        context.getApplicationContext().startService(startIntent);


        Toast.makeText(context, "Start up!", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
