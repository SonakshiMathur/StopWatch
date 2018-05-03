package com.sona.myapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

public class StopWatch extends View {

    private int height, width, padding, numericSpace = 0, fontSize, radius, handTrunc, minuteHandTrunc;
    private Paint paint;
    private boolean isInit;
    private int[] numbers = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60};
    private Rect rect = new Rect();
    public int status=-1;
    public long milis;
    public long difference;
    public SpannableStringBuilder minutes=new SpannableStringBuilder();

    public StopWatch(Context context) {
        super(context);
    }

    public StopWatch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StopWatch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initWatch() {

        height = getHeight();
        width = getWidth();
        padding = numericSpace + 50;
        fontSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 13, getResources().getDisplayMetrics());
        int min = Math.min(height, width);
        radius = (min / 2) - padding;
        handTrunc = min / 3;
        minuteHandTrunc = min / 4;

        paint = new Paint();
        isInit = true;
        status=0;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (!isInit) {
            initWatch();
        }
        canvas.drawColor(getResources().getColor(R.color.colorPrimaryDark));
        drawCircle(canvas);
        drawCentre(canvas);
        drawNumeral(canvas);
        drawHand(canvas);
        updateTimer();
        postInvalidateDelayed(500);
        invalidate();
        if(MainActivity.tV!=null){
            MainActivity.tV.invalidate();
        }
    }

    private void drawCircle(Canvas canvas) {

        paint.reset();
        paint.setColor(getResources().getColor(R.color.colorAccent));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, height / 2, radius + padding - 10, paint);

    }

    private void drawCentre(Canvas canvas) {

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(width / 2, height / 2, 10, paint);

    }

    private void drawNumeral(Canvas canvas) {

        int x;
        int y;
        paint.setTextSize(fontSize);
        for (int num : numbers) {
            String s = String.valueOf(num);
            paint.getTextBounds(s, 0, s.length(), rect);
            double angle = Math.PI / 6 * (num / 5 - 3);
            x = (int) (width / 2 + Math.cos(angle) * radius - rect.width() / 2);
            y = (int) (height / 2 + Math.sin(angle) * radius - rect.height() / 2);
            canvas.drawText(s, x, y, paint);
        }

    }

    private void drawHand(Canvas canvas) {
        double angle, angle2;

        if(status==0){
            canvas.drawLine(width/2,height/2,(float)(width/2),(float)((height/2)-handTrunc),paint);
        }
        else if(status==1){
              long currtt=System.currentTimeMillis();
            difference=currtt-milis;
            difference=difference/1000;
            angle=Math.PI*difference/30-Math.PI/2;
            canvas.drawLine(width/2,height/2,(float)(width/2+Math.cos(angle)*handTrunc),(float)((height/2)+Math.sin(angle)*handTrunc),paint);
            angle2=Math.PI*difference/1800-Math.PI/2;
            canvas.drawLine(width/2,height/2,(float)(width/2+Math.cos(angle2)*minuteHandTrunc),(float)((height/2)+Math.sin(angle2)*minuteHandTrunc),paint);
        }
        else if(status==2){

            angle=Math.PI*difference/30-Math.PI/2;
            canvas.drawLine(width/2,height/2,(float)(width/2+Math.cos(angle)*handTrunc),(float)((height/2)+Math.sin(angle)*handTrunc),paint);
            angle2=Math.PI*difference/1800-Math.PI/2;
            canvas.drawLine(width/2,height/2,(float)(width/2+Math.cos(angle2)*minuteHandTrunc),(float)((height/2)+Math.sin(angle2)*minuteHandTrunc),paint);
        }
    }

    private void updateTimer(){

        int secs=(int)difference%60;
        int mins=(int)difference/60;
        minutes.clearSpans();
        minutes.clear();
        minutes.append(Integer.toString(mins)+" : "+Integer.toString(secs));
        minutes.setSpan(new RelativeSizeSpan(2f),0,minutes.length(),0);
        minutes.setSpan(new ForegroundColorSpan(Color.rgb((int)(255*secs/60),(int)(255*(secs-60)/60),0)),0,minutes.length(),0);
        if(MainActivity.tV!=null){
            MainActivity.tV.setText(minutes, TextView.BufferType.SPANNABLE);
        }
    }

}