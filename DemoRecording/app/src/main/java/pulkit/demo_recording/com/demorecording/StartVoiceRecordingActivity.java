package pulkit.demo_recording.com.demorecording;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import pulkit.demo_recording.com.demorecording.service.VoiceRecordService;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by pulkit on 18/11/17.
 */

public class StartVoiceRecordingActivity extends AppCompatActivity {

    Button buttonStart, buttonStop, buttonPlayLastRecordAudio, buttonStopPlayingRecording;

    String AudioSavePathInDevice = null;
    MediaRecorder mediaRecorder;
    Random random;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_voice);

        findIds();
        init();
    }

    private void findIds() {

        buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStop = (Button) findViewById(R.id.buttonStop);
        buttonPlayLastRecordAudio = (Button) findViewById(R.id.button3);
        buttonStopPlayingRecording = (Button) findViewById(R.id.button4);
    }

    private void init() {

        /*Disable All Buttons first*/
        buttonStop.setEnabled(false);
        buttonPlayLastRecordAudio.setEnabled(false);
        buttonStopPlayingRecording.setEnabled(false);

        /*Check Permisssions*/
        checkPermission();

    }

    private void setListeners() {

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                        + CreateRandomAudioFileName() + ".mp3";

                MediaRecorderReady();

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);

                Toast.makeText(StartVoiceRecordingActivity.this, "Recording started", Toast.LENGTH_LONG).show();*/

//                MediaRecorderReady();
                buttonStart.setEnabled(false);
                buttonStop.setEnabled(true);

                Intent intent = new Intent(getApplicationContext(), VoiceRecordService.class);
                startService(intent);

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                mediaRecorder.stop();

                buttonStop.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);

//                Toast.makeText(StartVoiceRecordingActivity.this, "Recording Completed", Toast.LENGTH_LONG).show();

                stopService(new Intent(getApplicationContext(), VoiceRecordService.class));
            }
        });

        buttonPlayLastRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlayingRecording.setEnabled(true);

                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();

                Toast.makeText(StartVoiceRecordingActivity.this, "Recording Playing", Toast.LENGTH_LONG).show();
            }
        });

        buttonStopPlayingRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlayingRecording.setEnabled(false);
                buttonPlayLastRecordAudio.setEnabled(true);

                if (mediaPlayer != null) {

                    mediaPlayer.stop();
                    mediaPlayer.release();

                    MediaRecorderReady();
                }
            }
        });
    }

    private String CreateRandomAudioFileName() {

        /*get Date and time*/
        Date date = new Date();
        CharSequence sequence = DateFormat.format("yyMMdd_HHmmss", date.getTime());
        String dateFormat = sequence.toString();

        return dateFormat.toString();

        /*get random charaters*/
//        StringBuilder stringBuilder = new StringBuilder(numberOfDigits);
//        int i = 0;
//        while (i < numberOfDigits) {
//
////            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));
//
//            i++;
//        }
//        return stringBuilder.toString();
    }

    private void MediaRecorderReady() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    private void checkPermission() {
        /*check permissions*/
        int writeExternalStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int recordAudio = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {

                setListeners();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, RECORD_AUDIO},
                    RequestPermissionCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                checkPermission();
            }
        } else {
            checkPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

//        startService(new Intent(getApplicationContext(), VoiceRecordService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.v("Tag" , "onPause");
//        stopService(new Intent(getApplicationContext(), VoiceRecordService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.v("Tag" , "onPause");
        stopService(new Intent(getApplicationContext(), VoiceRecordService.class));
    }
//
}
