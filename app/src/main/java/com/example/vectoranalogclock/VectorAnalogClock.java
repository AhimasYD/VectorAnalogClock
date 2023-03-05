package com.example.vectoranalogclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import java.util.Calendar;

public class VectorAnalogClock extends RelativeLayout {

    public VectorAnalogClock(Context context) {
        super(context);
        init(context);
    }

    public VectorAnalogClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VectorAnalogClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public VectorAnalogClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }


    private void init(Context context) {
        inflate(context, R.layout.vector_analog_clock,this);

        // Receiver of system time updates
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                runAnimations();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        context.registerReceiver(receiver, filter);

        runAnimations();
    }

    public void runAnimations() {
        AppCompatImageView hourView = findViewById(R.id.hour);
        AppCompatImageView minuteView = findViewById(R.id.minute);
        AppCompatImageView secondView = findViewById(R.id.second);

        hourView.clearAnimation();
        minuteView.clearAnimation();
        secondView.clearAnimation();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        hourView.setRotation((hour + minute / 60f) * 30f);
        minuteView.setRotation((minute + second / 60f) * 6f);
        secondView.setRotation((second + millisecond / 1000f) * 6f);

        Animation rotator;

        rotator = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotator.setRepeatMode(Animation.RESTART);
        rotator.setRepeatCount(Animation.INFINITE);
        rotator.setInterpolator(new LinearInterpolator());
        rotator.setDuration(12 * 60 * 60 * 1000);
        hourView.startAnimation(rotator);

        rotator = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotator.setRepeatMode(Animation.RESTART);
        rotator.setRepeatCount(Animation.INFINITE);
        rotator.setInterpolator(new LinearInterpolator());
        rotator.setDuration(60 * 60 * 1000);
        minuteView.startAnimation(rotator);

        rotator = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotator.setRepeatMode(Animation.RESTART);
        rotator.setRepeatCount(Animation.INFINITE);
        rotator.setInterpolator(new LinearInterpolator());
        rotator.setDuration(60 * 1000);
        secondView.startAnimation(rotator);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        runAnimations();
    }
}