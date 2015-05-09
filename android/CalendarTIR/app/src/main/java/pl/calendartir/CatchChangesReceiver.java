package pl.calendartir;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by dominikmajda on 09.05.15.
 */
public class CatchChangesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, CalendarService.class);
        context.startService(intentService);
    }
}
