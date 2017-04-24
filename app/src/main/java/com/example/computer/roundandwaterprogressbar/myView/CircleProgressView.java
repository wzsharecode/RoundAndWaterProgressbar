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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.computer.roundandwaterprogressbar.R;
import com.example.computer.roundandwaterprogressbar.interfac.OnProgressListener;

/**
 * 首页自定义圆形进度条
 * Created by wds on 16/7/24.
 */
public class CircleProgressView extends View {
    private Paint mPaintBackground; // 绘制背景圆环的画笔
    private Paint mPaintProgress;   // 绘制背景进度的画笔
    private Paint mPaintProgressBitmap; // 绘制背景进度的画笔

    private Paint mPaintBackgroundRoundDot;  // 绘制背景进度的画笔
    private Paint mPaintProgressRoundDot;    // 绘制背景进度的画笔

    private Paint mPaintText; // 绘制背景字体的画笔
    private Paint mPaintTextIntro; // 绘制背景字体的画笔
    private Paint mPaintTextPercent;

    private int bgColor = Color.WHITE; // 背景圆环的颜色
    private int textColor = Color.WHITE; // 字体的颜色
    private int progressColor = Color.WHITE; // 进度条的颜色
    private float mStrokeWidth = 10;// 背景圆环的宽度
    private float mRadius = 40; // 背景圆环的半径
    private RectF rectPro;// 进度条的绘制外接矩形
    private int mProgress = 0; // 当前进度
    private int mStartProgress = 0; // 当前进度
    private int mMaxProgress = 100; // 最大进度
    private int mWidth, mHeight;
    private OnProgressListener mOnProgressListener;

    private Drawable thumb_double;
    private int thumbSize_int = 45;
    private float mNumPi = 0.033f;
    private String mText = "90";

    public void setOnProgressListener(OnProgressListener mOnProgressListener) {
        this.mOnProgressListener = mOnProgressListener;
    }

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++) {
                int attr = ta.getIndex(i);
                switch (attr) {
                    case R.styleable.CircleProgress_radius:
                        mRadius = ta.getDimension(R.styleable.CircleProgress_radius, mRadius);
                        break;
                    case R.styleable.CircleProgress_strokeWidth:
                        mStrokeWidth = ta.getDimension(R.styleable.CircleProgress_strokeWidth, mStrokeWidth);
                        break;
                    case R.styleable.CircleProgress_bgColor:
                        bgColor = ta.getColor(R.styleable.CircleProgress_bgColor, bgColor);
                        break;
                    case R.styleable.CircleProgress_progressColor:
                        progressColor = ta.getColor(R.styleable.CircleProgress_progressColor, progressColor);
                        break;
                    case R.styleable.CircleProgress_android_textColor:
                        textColor = ta.getColor(R.styleable.CircleProgress_android_textColor, textColor);
                        break;
                }
            }
            ta.recycle();
        }

        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaintBackground = new Paint();
        mPaintBackground.setColor(Color.parseColor("#A0e7e7e7"));
        // 设置抗锯齿
        mPaintBackground.setAntiAlias(true);
        // 设置防抖动
        mPaintBackground.setDither(true);
        // 设置样式为环形
        mPaintBackground.setStyle(Paint.Style.STROKE);
        // 设置环形的宽度
        mPaintBackground.setStrokeWidth(mStrokeWidth);

        mPaintBackgroundRoundDot = new Paint();
        mPaintBackgroundRoundDot.setColor(Color.parseColor("#A0e7e7e7"));
        // 设置抗锯齿
        mPaintBackgroundRoundDot.setAntiAlias(true);
        // 设置防抖动
        mPaintBackgroundRoundDot.setDither(true);
        // 设置样式为环形
        mPaintBackgroundRoundDot.setStyle(Paint.Style.FILL);

        mPaintProgressRoundDot = new Paint();
        mPaintProgressRoundDot.setColor(Color.WHITE);
        mPaintProgressRoundDot.setAlpha(50);
        // 设置抗锯齿
        mPaintProgressRoundDot.setAntiAlias(true);
        // 设置防抖动
        mPaintProgressRoundDot.setDither(true);
        // 设置样式为环形
        mPaintProgressRoundDot.setStyle(Paint.Style.FILL);

        mPaintProgress = new Paint();
        mPaintProgress.setColor(Color.WHITE);
        // 设置抗锯齿
        mPaintProgress.setAntiAlias(true);
        // 设置防抖动
        mPaintProgress.setDither(true);
        // 设置样式为环形
        mPaintProgress.setStyle(Paint.Style.STROKE);
        // 设置环形的宽度
        mPaintProgress.setStrokeWidth(mStrokeWidth);

        mPaintProgressBitmap = new Paint();
        mPaintProgressBitmap.setColor(Color.WHITE);

        mPaintProgressBitmap.setStyle(Paint.Style.FILL);
        // 设置抗锯齿
        mPaintProgressBitmap.setAntiAlias(true);
        // 设置防抖动
        mPaintProgressBitmap.setDither(true);
        // 设置样式为环形
        mPaintProgressBitmap.setStyle(Paint.Style.STROKE);
        // 设置环形的宽度
        mPaintProgressBitmap.setStrokeWidth(mStrokeWidth);

        mPaintTextPercent = new Paint();
        mPaintTextPercent.setColor(Color.WHITE);
        // 设置抗锯齿
        mPaintTextPercent.setAntiAlias(true);
        mPaintTextPercent.setTextAlign(Paint.Align.CENTER);
        mPaintTextPercent.setTextSize(70);
        mPaintTextPercent.setStrokeWidth(40);
        mPaintTextPercent.setFakeBoldText(true);

        mPaintText = new Paint();
        mPaintText.setColor(Color.WHITE);
        // 设置抗锯齿
        mPaintText.setAntiAlias(true);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(150);
        mPaintText.setStrokeWidth(40);
        mPaintText.setFakeBoldText(true);

        mPaintTextIntro = new Paint();
        mPaintTextIntro.setColor(Color.WHITE);

        // 设置抗锯齿
        mPaintTextIntro.setAntiAlias(true);
        mPaintTextIntro.setTextAlign(Paint.Align.CENTER);
        mPaintTextIntro.setTextSize(45);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }

    private void initRect() {
        if (rectPro == null) {
            rectPro = new RectF();
            int viewSize = (int) (mRadius * 2) - 90;
            int left = (mWidth - viewSize) / 2;
            int top = (mHeight - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;
            rectPro.set(left, top, right, bottom);
        }
    }

    private int getRealSize(int measureSpec) {
        int result = -1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) { // 这两种模式需要自己计算
            result = (int) (mRadius * 2 + mStrokeWidth * 2);
        } else {
            result = size;
        }
        return result;
    }

    /**
     * 绘制画布
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initRect();
        drawBackground(canvas);
        drawBackgroundDot(canvas);
        drawProgressRoundDot(canvas);

        drawText(canvas);
        drawTextPercent(canvas);
        drawTextIntro(canvas);

        drawProgress(canvas);
        drawProgressBitmap(canvas);
    }

    /**
     * 绘制百分比字体
     * @param canvas
     */
    private void drawTextPercent(Canvas canvas) {
        //绘制简介字体
        int with = 0;
        if (mText.length()>1){
            with = 100;
        }else {
            with = 70;
        }
        canvas.drawText("%", mWidth / 2 + with, mHeight / 2 + 40, mPaintTextPercent);
    }

    /**
     * 绘制背景灰色的圆环
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        //绘制背景圆环
        canvas.drawArc(rectPro, -90, 360, false, mPaintBackground);
        canvas.save();
    }

    /**
     * 绘制中间简介字体
     * @param canvas
     */
    private void drawTextIntro(Canvas canvas) {
        //绘制简介字体
        canvas.drawText("预期年化收益", mWidth / 2, mHeight / 2 + 150, mPaintTextIntro);
    }

    private void drawText(Canvas canvas) {
        //绘制字体
        canvas.drawText(mText, mWidth / 2 - 20, mHeight / 2 + 40, mPaintText);
    }

    /**
     * 绘制圆形进度条
     * @param canvas
     */
    private void drawProgress(Canvas canvas) {
        float angle = mStartProgress / (mMaxProgress * 1.0f) * 360; // 圆弧角度
        //绘制进度条
        canvas.drawArc(rectPro, -90, -angle, false, mPaintProgress);
        if (mStartProgress < mProgress) {
            mStartProgress += 1;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();
        }
        //当进度到达最大值时  调用此函数
        if (mOnProgressListener != null) {
            if (mStartProgress == mProgress) {
                mOnProgressListener.onEnd();
            }
        }
    }

    /**
     * 绘制圆形背景
     * @param canvas
     */
    private void drawBackgroundDot(Canvas canvas) {
        float radius = 7.0f;
        float yuan = getWidth() / 2 - 10;
        for (int i = 1; i < 13; i++) {
            double x = yuan + yuan * Math.cos(30 * i * 3.14 / 180);
            double y = yuan + yuan * Math.sin(30 * i * 3.14 / 180);
            canvas.drawCircle((float) x + 10, (float) y + 10, radius, mPaintBackgroundRoundDot);
        }
    }

    /**
     * 绘制圆点背景 进度条
     * @param canvas
     */
    private void drawProgressRoundDot(Canvas canvas) {
        float angle = mStartProgress / (mMaxProgress * 1.0f) * 360; // 圆弧角度
        float radius = 9.0f;
        mPaintProgressRoundDot.setStyle(Paint.Style.FILL);
        mPaintProgressRoundDot.setColor(Color.WHITE);
        float yuan = getWidth() / 2 - 10;
        for (int i = 0; i < angle * mNumPi; i++) {
            double x = yuan + yuan * Math.cos(30 * i * 3.14 / 180);
            double y = yuan + yuan * Math.sin(30 * i * 3.14 / 180);

//            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.faguang), yuan * 2 - (float) y - 3, yuan * 2 - (float) x - 3, mPaintBackgroundRoundDot);
            canvas.drawCircle(yuan * 2 - (float) y + 10, yuan * 2 - (float) x + 10, radius, mPaintProgressRoundDot);
        }
    }

    /**
     * 绘制进度条
     * @param canvas
     */
    private void drawProgressBitmap(Canvas canvas) {
        if (thumb_double == null) {
            mPaintProgressBitmap.setStyle(Paint.Style.FILL);
            Bitmap bitmap = Bitmap.createBitmap(thumbSize_int, thumbSize_int, Bitmap.Config.ARGB_8888);
            // 图片画片
            Canvas cas = new Canvas(bitmap);
            int center = thumbSize_int / 2;
            int radius = center - thumbSize_int / 2 - 10;
            cas.drawCircle(center, center, radius, mPaintProgressBitmap);
            // 图片画片
            Canvas cas2 = new Canvas(bitmap);
            int center2 = thumbSize_int / 2;
            int radius2 = center;
            cas2.drawCircle(center2, center2, radius2, mPaintProgressBitmap);
            thumb_double = new BitmapDrawable(getResources(), bitmap);
        }
        int center = getWidth() / 2;
        int radius = center - thumbSize_int / 2 - 40;
        float angle = mStartProgress / (mMaxProgress * 1.0f) * 360; // 圆弧角度
        double cycle_round = (angle + 90 * 2) * Math.PI / 180;
        float x = (float) (Math.cos(cycle_round) * (radius) + center - thumbSize_int / 2);
        float y = (float) (Math.sin(cycle_round) * (radius) + center - thumbSize_int / 2);
        thumb_double.setBounds(0, 0, thumbSize_int, thumbSize_int);
        canvas.translate(y, x);
        thumb_double.draw(canvas);
        canvas.restore();
    }

    /**
     * 设置进度 刻度
     * @param progress
     */
    public void setProgress(int progress) {
        this.mProgress = progress;
        mStartProgress = 0;
        invalidate();
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getProgress() {
        return mProgress;
    }
}
