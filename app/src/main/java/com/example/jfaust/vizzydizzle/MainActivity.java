package com.example.jfaust.vizzydizzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jfaust.vizzydizzle.optimized.RecordingActivity;
import com.example.jfaust.vizzydizzle.surface.LunarLander;
import com.example.jfaust.vizzydizzle.visualizer.VisualizerActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewGroup activityContainer = (ViewGroup) findViewById(R.id.activity_main_container);

        addButton(RecordingActivity.class, "Recording", activityContainer);
        addButton(LunarLander.class, "Moon Landing", activityContainer);
        addButton(VisualizerActivity.class, "Visualizer", activityContainer);
    }

    private void addButton(final Class destination, String description, ViewGroup parent) {
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent problemIntent = new Intent(MainActivity.this, destination);
                startActivity(problemIntent);
            }
        });
        button.setText(description);
        parent.addView(button);
    }
}
