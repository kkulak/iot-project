package pl.calendartir;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dominikmajda on 03.05.15.
 */
public class CoffeeBroadcast extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        CoffeHelper.makeCoffe(context);
    }
}
