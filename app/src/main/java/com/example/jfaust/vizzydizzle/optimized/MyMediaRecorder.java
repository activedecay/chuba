package com.example.jfaust.vizzydizzle.optimized;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;

import java.io.IOException;

public class MyMediaRecorder {
    private MediaRecorder recorder = new MediaRecorder();
    private Handler handler = new Handler();
    final Runnable updater = new Runnable() {
        public void run() {
            handler.postDelayed(this, 1);
            int maxAmplitude = recorder.getMaxAmplitude();
            if (maxAmplitude != 0) {
                maxAmplitudeView.addAmplitude(maxAmplitude);
            }
        }
    };
    private MaxAmplitudeView maxAmplitudeView;

    public MyMediaRecorder(MaxAmplitudeView maxAmplitudeView) {
        this.maxAmplitudeView = maxAmplitudeView;
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/dev/null");
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException | IOException ignored) {
        }
    }

    public void destroy() {
        handler.removeCallbacks(updater);
        recorder.stop();
        recorder.reset();
        recorder.release();
    }

    public void start() {
        handler.post(updater);
    }

    public MediaRecorder getRecorder() {
        return recorder;
    }
}
