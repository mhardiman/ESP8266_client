package mark.esp8266_client;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

public class getTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_time);
    }

    public void confirmTime(View view){
        TimePicker clock = (TimePicker)findViewById(R.id.timePicker);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("hr", clock.getCurrentHour());
        resultIntent.putExtra("min", clock.getCurrentMinute());
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    public void cancelTime(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}
