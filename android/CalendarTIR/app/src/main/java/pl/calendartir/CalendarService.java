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

        // Get all coffee events
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(java.util.Calendar.DATE, 7);
        Date future = calendar.getTime();

        Calendar[] calendarList = CalendarUtility.getGoogleCalendars(this, "ddmajda@gmail.com");

        for (Calendar cal : calendarList) {
            ArrayList<Event> events = EventUtility.getEvents(this, cal.id);
            Event event = events.get(0);
            if (event.title.equals("[Kawa]")) {
                Log.d("[KAWA]", event.toString());
            }
        }

//        List<CalendarInfo> calendars = CalendarInfo.getAllCalendars(this);
//        List<EventInfo> list= EventInfo.getEvents(this, now, future, cal)
//        mEventsCoffeeList.clear();
//
//
//        for (EventInfo event : list) {
//            long diff = event.getStartDate().getTime() - now.getTime();
//            long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//            if (event.getTitle().equals("[Kawa]")) {
//                mEventsCoffeeList.add(event);
//            }
//        }
//
//        // Get alarm manager
//        String alarm = Context.ALARM_SERVICE;
//        AlarmManager am = (AlarmManager) getSystemService( alarm );
//
//        Intent coffeeIntent = new Intent(getString(R.string.coffe_broadcast));
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//        int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
//        long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
//        long triggerTime = SystemClock.elapsedRealtime() + interval;
//
//        for (EventInfo event : mEventsCoffeeList) {
////            am.setExact(AlarmManager.RTC_WAKEUP, );
//        }

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
