package mark.esp8266_client;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

//figure out how to sleep and re-awake if checkbox is enabled at certain time
//save time to file and read back at beginning of app and write to whenever time is updated

public class ClientActivity extends AppCompatActivity {
    int hr = 0;
    int min = 0;
    int state = 0;
    TextView txtTime;
    CheckBox chkEnabled;//chkEnabled.isChecked();
    ClientTask myClient = null;
    Intent alarmIntent = null;
    AlarmManager alarmer = null;
    Calendar calendar = null;
    PendingIntent pend = null;
    Alarm alarm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("OnCreating");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        final Context appContext = getApplicationContext();

        txtTime = (TextView)findViewById(R.id.textView);
        chkEnabled = (CheckBox)findViewById(R.id.checkBox);
        Button btnClose = (Button)findViewById(R.id.button);
        assert btnClose != null;
        btnClose.setOnClickListener(btnCloseOnClickListener);
        Button btnOpen = (Button)findViewById(R.id.button2);
        assert btnOpen != null;
        btnOpen.setOnClickListener((btnOpenOnClickListener));
        LocalBroadcastManager localmanager = LocalBroadcastManager.getInstance(appContext);
        /*
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("broadcast received!");
                //myClient = new ClientTask(appContext);
                //myClient.execute(~state);
            }
        };

        IntentFilter inFilter = new IntentFilter(Intent.ACTION_MAIN);
        localmanager.registerReceiver(receiver, inFilter);
        alarmer = (AlarmManager)appContext.getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(appContext, ExecuteAlarm.class);
        pend = PendingIntent.getBroadcast(getApplicationContext(),0,alarmIntent,0);*/
    }

    View.OnClickListener btnCloseOnClickListener =
            new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    myClient = new ClientTask(getApplicationContext());
                    myClient.execute(0);
                }};
    View.OnClickListener btnOpenOnClickListener =
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClient = new ClientTask(getApplicationContext());
                    myClient.execute(1);
                 }};
    public void setTime(View view) {
        Intent timeIntent = new Intent(this, getTimeActivity.class);
        startActivityForResult(timeIntent, 1);
    }

    public void toggleTimer(View view) {
        if (chkEnabled.isChecked() & calendar != null) {
            System.out.println("Creating timer");
            alarm = new Alarm();
            alarm.setAlarm(getApplicationContext(), calendar.getTimeInMillis());
            Toast toast = Toast.makeText(getApplicationContext(),"Alarm set.",Toast.LENGTH_SHORT);
            //Format formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            //txtTime.setText(formatter.format(calendar.getTime()));
        }
        else if (!chkEnabled.isChecked() & alarm != null){
            alarm.cancelAlarm(getApplicationContext());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {

        super.onActivityResult(requestCode, resultCode, resultIntent);
        if (resultCode == Activity.RESULT_OK) {
            hr = resultIntent.getIntExtra("hr", 0);
            min = resultIntent.getIntExtra("min", 0);
            Format formatter;
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hr);
            calendar.set(Calendar.MINUTE, min);
            calendar.clear(Calendar.SECOND); //reset seconds to zero
            formatter = new SimpleDateFormat("hh:mm a");
            txtTime.setText(formatter.format(calendar.getTime()));
            if (chkEnabled.isChecked()){
                alarm = new Alarm();
                alarm.setAlarm(getApplicationContext(), calendar.getTimeInMillis());
                Toast toast = Toast.makeText(getApplicationContext(),"Alarm set.",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}

