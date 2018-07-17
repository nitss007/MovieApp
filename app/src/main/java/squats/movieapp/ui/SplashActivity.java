package squats.movieapp.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

import squats.movieapp.HomeActivity;
import squats.movieapp.R;

public class SplashActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory{

    private HTextView hTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {

            hTextView = (HTextView) findViewById(R.id.cooltext);
            hTextView.setAnimateType(HTextViewType.LINE);
            hTextView.animateText("Movie App".toUpperCase());
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        finish();


                }
            }, secondsDelayed * 2000);
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }
    }
    @Override
    public View makeView() {
        TextView t = new TextView(this);
        t.setGravity(Gravity.CENTER);
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        return t;
    }
}
