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

public class CloseWareRectWithHorizontalBottom extends View {
    private int screenHeight;
    private int screenWidth;
    private List<Point> points;
    private int color = Color.RED;
    private int shapeHeight = 300;
    private boolean withBorder = false;

//    /** 声明位图渲染对象 */
//    private Shader[] shaders = new Shader[5];
//    /** 声明颜色数组 */
//    private int[] colors;

    public CloseWareRectWithHorizontalBottom(Context context) {
        super(context);
    }

    public CloseWareRectWithHorizontalBottom(Context context,List<Point> points,int color,int shapeHeight,boolean withBorder){
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

//        // 获取bitmap实例
//        Bitmap bm = BitmapFactory.decodeResource(getResources(),
//                R.mipmap.a);
//        // 设置渐变颜色组,也就是按红,绿,蓝的方式渐变
//        colors = new int[] { Color.RED, Color.GREEN, Color.BLUE };
//        // 实例化BitmapShader,x坐标方向重复图形,y坐标方向镜像图形
//        shaders[0] = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
//        // 实例化LinearGradient
//        shaders[1] = new LinearGradient(0, 0, 100, 100, colors, null,
//                Shader.TileMode.REPEAT);
//        // 实例化RadialGradient
//        shaders[2] = new RadialGradient(100, 100, 80, colors, null,
//                Shader.TileMode.REPEAT);
//        // 实例化SweepGradient
//        shaders[3] = new SweepGradient(160, 160, colors, null);
//        // 实例化ComposeShader
//        shaders[4] = new ComposeShader(shaders[1], shaders[2],
//                PorterDuff.Mode.DARKEN);

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
        if (withBorder){
            Point borderControlLeft = new Point(0,mLeft1.y+shapeHeight+50);
            Point borderLeft = new Point(50,mLeft1.y+shapeHeight+50);
            Point borderRight = new Point(screenWidth-50,mRight.y+shapeHeight+50);
            Point borderControlRight = new Point(screenWidth,mRight.y+shapeHeight+50);

            mPath.quadTo(borderControlRight.x,borderControlRight.y,borderRight.x,borderRight.y);
            mPath.lineTo(borderLeft.x,borderLeft.y);
            mPath.quadTo(borderControlLeft.x,borderControlLeft.y,mLeft1.x,mLeft1.y+shapeHeight);

        }else{
            mPath.lineTo(mLeft1.x,(mLeft1.y+shapeHeight));
        }
        mPath.lineTo(mLeft1.x, mLeft1.y);
        canvas.drawPath(mPath, mPaint);
    }

    private void getScreenSize(){
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        screenHeight = dm2.heightPixels;

        screenWidth = dm2.widthPixels;
    }
}
