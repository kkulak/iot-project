package pl.calendartir;

import android.content.Context;
import android.util.Log;

import pl.calendartir.mqtt.MqttPublisher;

/**
 * Created by dominikmajda on 03.05.15.
 */
public class CoffeHelper {

    /**
     * Sends info to Gallileo to make coffee.
     * @param context Context, required for network connections
     */
    public static void makeCoffe(Context context) {
        Log.d("Coffee", "Making coffee");
        new MqttPublisher().startCoffee();
    }

}
