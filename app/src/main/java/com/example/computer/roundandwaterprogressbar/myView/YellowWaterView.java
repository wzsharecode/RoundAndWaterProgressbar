/*--------------------------------------------------
 * Copyright (C) 2016 The Android SanDao Project
 *               http://www.sandaogroup.com
 * 创建时间：2016/6/24
 * 内容说明：
 *
 * 变更时间：
 * 变更说明：
 * -------------------------------------------------- */
package com.example.computer.roundandwaterprogressbar.myView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.computer.roundandwaterprogressbar.R;

/**
 * Created by wds on 16/7/7.
 */
public class YellowWaterView extends View {
    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 900;
    private int dx, dy;
private ValueAnimator mAnimator;
    public YellowWaterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();

        mPaint.setColor(context.getResources().getColor(R.color.wate_light_red));
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        dy = 10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int originY = 60;
        int halfWaveLen = mItemWaveLength / 2;
        // 博客中代码,不向下移动
        mPath.moveTo(-mItemWaveLength + dx, originY);

        // 实现向下移动动画
        // mPath.moveTo(-mItemWaveLength+dx,originY+dy);
        // dy += 1;

        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -40, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 40, halfWaveLen, 0);
        }
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    public void startAnim() {
        mAnimator = ValueAnimator.ofInt(0, mItemWaveLength);
        mAnimator.setDuration(1800);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = Integer.valueOf(animation.getAnimatedValue().toString());
                postInvalidate();
            }
        });
        mAnimator.start();

    }
    public void stopAnim(){
        if (mAnimator == null)
            return;
        mAnimator.cancel();
    }
}
