package com.example.jfaust.vizzydizzle.optimized;

import android.app.Activity;
import android.os.Bundle;

import com.example.jfaust.vizzydizzle.R;

public class RecordingActivity extends Activity {
    private MyMediaRecorder myMediaRecorder;
//    private MyAudioRecorder myAudioRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        go();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
    }

    private void go() {
        myMediaRecorder = new MyMediaRecorder((MaxAmplitudeView) findViewById(R.id.visualizer));
//        myAudioRecorder = new MyAudioRecorder((MaxAmplitudeView) findViewById(R.id.visualizer));
    }

    private void stop() {
        myMediaRecorder.destroy();
//        myAudioRecorder.stopRecording();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            myMediaRecorder.start();
//            myAudioRecorder.startRecording();
        }
    }
}