package pulkit.demo_recording.com.demorecording;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn_record_voice, btn_call_record, btn_video_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        init();
    }

    private void findIds() {

        btn_record_voice = (Button) findViewById(R.id.btn_record_voice);
        btn_call_record = (Button) findViewById(R.id.btn_call_record);
        btn_video_record = (Button) findViewById(R.id.btn_video_record);
    }

    private void init() {

        btn_record_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartVoiceRecordingActivity.class);
                startActivity(intent);
            }
        });

        btn_call_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartVoiceRecordingActivity.class);
                startActivity(intent);
            }
        });


        btn_video_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartVoiceRecordingActivity.class);
                startActivity(intent);
            }
        });

    }


}



