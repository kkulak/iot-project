package pl.calendartir;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import it.macisamuele.calendarprovider.EventInfo;

/**
 * Created by dominikmajda on 03.05.15.
 */
public class EventsAdapter extends BaseAdapter{

    private LayoutInflater mInflater;

    private List<EventInfo> mEventList;

    private SimpleDateFormat mDateFormat, mHourFormat;

    public EventsAdapter(Activity context, List<EventInfo> eventInfo) {
        mInflater = context.getLayoutInflater();
        mEventList = eventInfo;
        mDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        mHourFormat = new SimpleDateFormat("HH:mm:ss");
    }

    @Override
    public int getCount() {
        return mEventList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        EventInfo eventInfo = (EventInfo)getItem(position);

        if (v==null) {
            v = mInflater.inflate(R.layout.event_layout, parent, false);
        }

        TextView dateTV = (TextView)v.findViewById(R.id.date_tv);
        TextView hourTV = (TextView)v.findViewById(R.id.hour_tv);

        dateTV.setText("Kawka dnia " + mDateFormat.format(eventInfo.getStartDate()));
        hourTV.setText("Godzina " + mHourFormat.format(eventInfo.getStartDate()));


        return v;
    }
}
