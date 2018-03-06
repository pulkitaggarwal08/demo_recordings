package pulkit.demo_recording.com.demorecording.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;

/**
 * Created by pulkit on 18/11/17.
 */

public class VoiceRecordService extends Service {

    String AudioSavePathInDevice = null;
    public static final int RequestPermissionCode = 1;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    public VoiceRecordService() {
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();

        AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + CreateRandomAudioFileName() + ".mp3";

        startRecording();

//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private void startRecording() {

        MediaRecorderReady();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Alert! application not working", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, "Recording started", Toast.LENGTH_LONG).show();
    }

    private String CreateRandomAudioFileName() {

        /*get Date and time*/
        Date date = new Date();
        CharSequence sequence = DateFormat.format("yyMMdd_HHmmss", date.getTime());
        String dateFormat = sequence.toString();

        return dateFormat.toString();
    }

    private void MediaRecorderReady() {

        if (mediaPlayer != null) {

            mediaPlayer.stop();
            mediaPlayer.release();

        }

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {

            mediaPlayer.stop();
            mediaPlayer.release();

        }

        Toast.makeText(this, "Service Stopped!!", Toast.LENGTH_SHORT).show();
    }

}
