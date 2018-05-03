package com.sona.myapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static TextView tV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final StopWatch s=(StopWatch)findViewById(R.id.watch);
        tV=(TextView)findViewById(R.id.digits);
        tV.setText(s.minutes, TextView.BufferType.SPANNABLE);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s.status==0) {

                    s.status = 1;
                    s.milis=System.currentTimeMillis();
                }
                else if(s.status==1){
                    s.status=2;
                }
                else {
                    s.status = 0;
                }

            }
        });
        final TextView textView=(TextView)findViewById(R.id.textView);
        SpannableString sstring=new SpannableString("Stop Watch");
        sstring.setSpan(new RelativeSizeSpan(2f),0,sstring.length(),0);
        sstring.setSpan(new ForegroundColorSpan(Color.WHITE),5,sstring.length(),0);
        sstring.setSpan(new ForegroundColorSpan(Color.RED),0,4,0);
        sstring.setSpan(new StyleSpan(Typeface.BOLD),0,sstring.length(),0);
        sstring.setSpan(new TypefaceSpan("monospace"),0,sstring.length(),0);
        SpannedString ss=new SpannedString(sstring);
        textView.setText(ss);


    }

}