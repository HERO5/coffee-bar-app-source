package com.example.apple.bakebar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.apple.bakebar.Entity.Point;

import java.util.List;

/**
 * Created by apple on 2017/10/27.
 */

public class CloseRadomWareRectWithHorizontalTop extends View {

    private int screenHeight;
    private int screenWidth;
    private List<Point> points;
    private int color = Color.RED;
    private int shapeHeight = 300;
    private boolean withBorder = false;

    public CloseRadomWareRectWithHorizontalTop(Context context) {
        super(context);
    }
    public CloseRadomWareRectWithHorizontalTop(Context context, List<Point> points, int color, int shapeHeight, boolean withBorder){
        super(context);
        this.points = points;
        this.color = color;
        this.shapeHeight = shapeHeight;
        this.withBorder = withBorder;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getScreenSize();

        Point mLeft1 = new Point(0,70);
        Point mControlLeft1 = new Point(screenWidth/16,0);
        Point mLeft2 = new Point(screenWidth/8,70);
        Point mControlLeft2 = new Point(3*screenWidth/16,270);
        Point mFirst = new Point(screenWidth/4,70);
        Point mControlFirst = new Point(5*screenWidth/16,-50);
        Point mSecond = new Point(3*screenWidth/8,70);
        Point mControlSecond = new Point(7*screenWidth/16,190);
        Point mLeft12 = new Point(screenWidth/2,70);
        Point mControlLeft12 = new Point(9*screenWidth/16,-30);
        Point mLeft22 = new Point(5*screenWidth/8,70);
        Point mControlLeft22 = new Point(11*screenWidth/16,140);
        Point mFirst2 = new Point(3*screenWidth/4,70);
        Point mControlFirst2 = new Point(13*screenWidth/16,-100);
        Point mSecond2 = new Point(7*screenWidth/8,70);
        Point mControlSecond2 = new Point(15*screenWidth/16,340);
        Point mRight2 = new Point(screenWidth,70);
        Paint mPaint = new Paint();
        mPaint.setColor(color);// 设置灰色
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mPaint.setAntiAlias(true);//锯齿效果
        mPaint.clearShadowLayer();
        Path mPath = new Path();
        mPath.reset();
        mPath.moveTo(mLeft1.x, mLeft1.y);
        if (withBorder){
            Point borderControlLeft = new Point(0,20);
            Point borderLeft = new Point(50,20);
            Point borderRight = new Point(screenWidth-50,20);
            Point borderControlRight = new Point(screenWidth,20);

            mPath.quadTo(borderControlLeft.x,borderControlLeft.y,borderLeft.x,borderLeft.y);
            mPath.lineTo(borderRight.x,borderRight.y);
            mPath.quadTo(borderControlRight.x,borderControlRight.y,mRight2.x,mRight2.y);

        }else{
            mPath.lineTo(mRight2.x, mRight2.y);
        }
        mPath.lineTo(mRight2.x, (mRight2.y+shapeHeight));
        mPath.quadTo(mControlSecond2.x, (mControlSecond2.y+shapeHeight), mSecond2.x, (mSecond2.y+shapeHeight));
        mPath.quadTo(mControlFirst2.x, (mControlFirst2.y+shapeHeight), mFirst2.x, (mFirst2.y+shapeHeight));
        mPath.quadTo(mControlLeft22.x, (mControlLeft22.y+shapeHeight), mLeft22.x, (mLeft22.y+shapeHeight));
        mPath.quadTo(mControlLeft12.x, (mControlLeft12.y+shapeHeight), mLeft12.x, (mLeft12.y+shapeHeight));

        mPath.quadTo(mControlSecond.x, (mControlSecond.y+shapeHeight), mSecond.x, (mSecond.y+shapeHeight));
        mPath.quadTo(mControlFirst.x, (mControlFirst.y+shapeHeight), mFirst.x, (mFirst.y+shapeHeight));
        mPath.quadTo(mControlLeft2.x, (mControlLeft2.y+shapeHeight), mLeft2.x, (mLeft2.y+shapeHeight));
        mPath.quadTo(mControlLeft1.x, (mControlLeft1.y+shapeHeight), mLeft1.x, (mLeft1.y+shapeHeight));
        mPath.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(1*screenWidth/15, 70, 35, mPaint);
        canvas.drawCircle(screenWidth/14, 200, 20, mPaint);
        canvas.drawCircle(screenWidth/5, 100, 30, mPaint);
        canvas.drawCircle(3*screenWidth/7, 40, 10, mPaint);
        canvas.drawCircle(3*screenWidth/7, 150, 25, mPaint);
        canvas.drawCircle(5*screenWidth/9, 140, 30, mPaint);
        canvas.drawCircle(3*screenWidth/4, 180, 40, mPaint);
        canvas.drawCircle(5*screenWidth/6, 70, 20, mPaint);
        canvas.drawCircle(12*screenWidth/13, 200, 20, mPaint);

    }

    private void getScreenSize(){
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        screenHeight = dm2.heightPixels;

        screenWidth = dm2.widthPixels;
    }

}


