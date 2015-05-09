package pl.calendartir;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.darkrockstudios.libs.calendar.Calendar;
import com.darkrockstudios.libs.calendar.CalendarUtility;
import com.darkrockstudios.libs.calendar.Event;
import com.darkrockstudios.libs.calendar.EventUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import it.macisamuele.calendarprovider.CalendarInfo;
import it.macisamuele.calendarprovider.EventInfo;

/**
 * Created by dominikmajda on 03.05.15.
 */
public class CalendarService extends Service {

    private IBinder mBinder = new LocalBinder();

    private List<EventInfo> mEventsCoffeeList = new ArrayList<>();


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public CalendarService getServiceInstance() {
            return CalendarService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = super.onStartCommand(intent, flags, startId);

        // Prepare dates
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(java.util.Calendar.DATE, 7);
        Date future = calendar.getTime();

        // Get alarm manager
        String alarm = Context.ALARM_SERVICE;
        AlarmManager alarmManager = (AlarmManager) getSystemService(alarm);

        // Cancel old intents
        for (int i = 0; i<mEventsCoffeeList.size(); i++) {
            EventInfo event = mEventsCoffeeList.get(i);
            Intent coffeeIntent = new Intent(this, CoffeeBroadcast.class);
            PendingIntent pi = PendingIntent.getBroadcast(this, i, coffeeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            pi.cancel();
            alarmManager.cancel(pi);
        }

        // Construct new event list
        List<CalendarInfo> calendars = CalendarInfo.getAllCalendars(this);
        List<Integer> calendarsId = new ArrayList<>();
        for (CalendarInfo cal : calendars) {
            calendarsId.add(cal.getId().intValue());
        }
        List<EventInfo> list = EventInfo.getEvents(this, now, future, calendarsId, null);
        mEventsCoffeeList.clear();

        for (EventInfo event : list) {
            long diff = event.getStartDate().getTime() - now.getTime();
            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if (event.getTitle().equals("[Kawa]")) {
                mEventsCoffeeList.add(event);
            }
        }

        // Create intents and start them
        Intent coffeeIntent = new Intent(this, CoffeeBroadcast.class);

        for (int i = 0; i<mEventsCoffeeList.size(); i++) {
            EventInfo event = mEventsCoffeeList.get(i);

            PendingIntent piNew = PendingIntent.getBroadcast(this, i, coffeeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, event.getStartDate().getTime(), piNew);
        }





        return command;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public List<EventInfo> getmEventsCoffeeList() {
        return mEventsCoffeeList;
    }
}
