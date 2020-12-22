package com.messed.ircmbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class BroadcastTest extends BroadcastReceiver
{
    public BroadcastTest() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"test",Toast.LENGTH_SHORT).show();
    }
}
