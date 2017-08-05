package com.savantech.seekarclibrarydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.savantech.seekarc.SeekArc;


public class MainActivity extends AppCompatActivity implements SeekArc.OnSeekArcChangeListener{

    SeekArc seekArc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekArc = (SeekArc)findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(this);
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
        Log.d("Started ","Tracking");
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
        Toast.makeText(this,"Current Position "+seekArc.getProgress(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, float progress) {
        Log.d("Moving Position ",""+progress);
    }
}
