package com.example.jfaust.vizzydizzle.optimized;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;

public class MyAudioRecorder {
    private static final String tag = MyAudioRecorder.class.getSimpleName();

    private MaxAmplitudeView maxAmplitudeView;


    private static final int sampleRate = 44100; // guaranteed
    private static final int channels = AudioFormat.CHANNEL_IN_MONO;
    private static final int encoding = AudioFormat.ENCODING_PCM_16BIT; // guaranteed
    private static final int source = MediaRecorder.AudioSource.MIC;
    private boolean isRecording = false;
    int bufferElementsToRec = 1024;
    int bytesPerElement = 2;
    private AudioRecord recorder;

    private Thread recordingThread = new Thread(new Runnable() {
        public void run() {
            String filePath = "/dev/null";
            short sData[] = new short[bufferElementsToRec];
            byte bData[] = new byte[bufferElementsToRec * 2];

            try (FileOutputStream os = new FileOutputStream(filePath)) {
                while (isRecording) {
                    // gets the voice output from microphone to byte format
                    recorder.read(sData, 0, bufferElementsToRec);
                    byte max = 0;
                    for (int i = 0; i < sData.length; i++) {
                        int idx = i * 2;
                        bData[idx++] = (byte) (sData[i] & 0x00FF);
                        bData[idx] = (byte) (sData[i] >> 8);
                        sData[i] = 0;
                        max = (byte) Math.max(max, Math.max(bData[idx], bData[--idx]));
                    }
                    maxAmplitudeView.addAmplitude(max);
                    os.write(bData, 0, bufferElementsToRec * bytesPerElement);
                }
            } catch (IOException ignored) {
            }
        }
    }, "AudioRecorder Thread");

    public MyAudioRecorder(MaxAmplitudeView maxAmplitudeView) {
        this.maxAmplitudeView = maxAmplitudeView;
        try {
            recorder = new AudioRecord(source, sampleRate, channels, encoding,
                    AudioRecord.getMinBufferSize(sampleRate, channels, encoding));
        } catch (IllegalArgumentException e) {
            Log.e(tag, "init failed");
        }

    }

    public void startRecording() {
        recorder.startRecording();
        isRecording = true;
        recordingThread.start();
    }

    public void stopRecording() {
        // stops the recording activity
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
        }
    }
}
