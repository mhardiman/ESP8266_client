package mark.esp8266_client;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Mark on 6/29/2016.
 */
public class Alarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("broadcast received");
        Toast toast = Toast.makeText(context, "Alarm", Toast.LENGTH_SHORT);
        toast.show();
        ClientActivity clientAct = ((ClientActivity)context.getApplicationContext());
        clientAct.myClient = new ClientTask(context);
        clientAct.myClient.execute(~clientAct.state);
    }

    public void setAlarm (Context appContext, long millis){
        AlarmManager alarmer = (AlarmManager)appContext.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(appContext, Alarm.class);
        PendingIntent pend = PendingIntent.getBroadcast(appContext,0,alarmIntent,0);
        Intent tapIntent = new Intent(appContext, AlarmTapActivity.class);
        PendingIntent pend2 = PendingIntent.getActivity(appContext, 0, tapIntent, 0);
        AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(millis, pend2);
        alarmer.setAlarmClock(info, pend);
    }

    public void cancelAlarm (Context appContext) {
        Intent intent = new Intent(appContext, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(appContext, 0, intent, 0);
        AlarmManager alarmer = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
        alarmer.cancel(sender);
    }
}
