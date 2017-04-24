package com.example.computer.roundandwaterprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.computer.roundandwaterprogressbar.interfac.OnProgressListener;
import com.example.computer.roundandwaterprogressbar.myView.CircleProgressView;
import com.example.computer.roundandwaterprogressbar.myView.WhiteWaterView;
import com.example.computer.roundandwaterprogressbar.myView.YellowWaterView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private YellowWaterView yellow_water_view;
    private WhiteWaterView white_water_view;
    private CircleProgressView circleProgressbar;
    private FrameLayout water_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startAnim();
    }

    private void initView() {
        yellow_water_view = (YellowWaterView) findViewById(R.id.yellow_water_view);
        white_water_view = (WhiteWaterView) findViewById(R.id.white_water_view);
        circleProgressbar = (CircleProgressView) findViewById(R.id.circleProgressbar);
        circleProgressbar.setOnClickListener(this);
        water_view = (FrameLayout) findViewById(R.id.water_view);
        water_view.setVisibility(View.VISIBLE);
    }

    /**
     * 结束动画
     */
    private void stopAnim() {

        yellow_water_view.setVisibility(View.GONE);
        white_water_view.setVisibility(View.GONE);
        yellow_water_view.stopAnim();
        white_water_view.stopAnim();

    }

    /**
     * 开始动画
     */
    private void startAnim() {
        circleProgressbar.setProgress(90);
        circleProgressbar.setOnProgressListener(new OnProgressListener() {
            @Override
            public void onEnd() {
                stopAnim();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    yellow_water_view.startAnim();
                    yellow_water_view.setVisibility(View.VISIBLE);
                    white_water_view.setVisibility(View.VISIBLE);
                    white_water_view.startAnim();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }).run();

    }

    @Override
    public void onClick(View v) {
        startAnim();
    }
}
