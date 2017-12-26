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
 * Created by apple on 2017/10/26.
 */

public class CloseWareRect extends View {

    private int screenHeight;
    private int screenWidth;
    private List<Point> points;
    private int color = Color.RED;
    private int shapeHeight = 300;

    public CloseWareRect(Context context) {
        super(context);
    }

    public CloseWareRect(Context context,List<Point> points,int color,int shapeHeight){
        super(context);
        this.points = points;
        this.color = color;
        this.shapeHeight = shapeHeight;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getScreenSize();
        Point mLeft1 = new Point(0,70);
        Point mControlLeft1 = new Point(screenWidth/8,0);
        Point mLeft2 = new Point(screenWidth/4,70);
        Point mControlLeft2 = new Point(3*screenWidth/8,140);
        Point mFirst = new Point(screenWidth/2,70);
        Point mControlFirst = new Point(5*screenWidth/8,0);
        Point mSecond = new Point(3*screenWidth/4,70);
        Point mControlSecond = new Point(7*screenWidth/8,140);
        Point mRight = new Point(screenWidth,70);
        Paint mPaint = new Paint();
        mPaint.setColor(color);// 设置灰色
        mPaint.setStyle(Paint.Style.FILL);//设置填满
        mPaint.setAntiAlias(true);//锯齿效果
        Path mPath = new Path();
        mPath.reset();
        mPath.moveTo(mLeft1.x, mLeft1.y);
        mPath.quadTo(mControlLeft1.x, mControlLeft1.y, mLeft2.x, mLeft2.y);
        mPath.quadTo(mControlLeft2.x, mControlLeft2.y, mFirst.x, mFirst.y);
        mPath.quadTo(mControlFirst.x, mControlFirst.y, mSecond.x, mSecond.y);
        mPath.quadTo(mControlSecond.x, mControlSecond.y, mRight.x, mRight.y);
        mPath.lineTo(mRight.x, (mRight.y+shapeHeight));
//        mPath.lineTo(mLeft1.x, screenHeight);
        mPath.quadTo(mControlSecond.x, (mControlSecond.y+shapeHeight), mSecond.x, (mSecond.y+shapeHeight));
        mPath.quadTo(mControlFirst.x, (mControlFirst.y+shapeHeight), mFirst.x, (mFirst.y+shapeHeight));
        mPath.quadTo(mControlLeft2.x, (mControlLeft2.y+shapeHeight), mLeft2.x, (mLeft2.y+shapeHeight));
        mPath.quadTo(mControlLeft1.x, (mControlLeft1.y+shapeHeight), mLeft1.x, (mLeft1.y+shapeHeight));
        mPath.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void getScreenSize(){
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        screenHeight = dm2.heightPixels;

        screenWidth = dm2.widthPixels;
    }
}
