package com.example.apple.bakebar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.apple.bakebar.Entity.Point;
import com.example.apple.bakebar.R;

import java.util.List;

/**
 * Created by apple on 2017/10/27.
 */

public class CloseCakeWareRectWithHorizontalBottom extends View {

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

    public CloseCakeWareRectWithHorizontalBottom(Context context) {
        super(context);
    }

    public CloseCakeWareRectWithHorizontalBottom(Context context, List<Point> points, int color, int shapeHeight, boolean withBorder){
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

        Point mLeft1 = new Point(0,150);
        Point mControlLeft1 = new Point(screenWidth/8,80);
        Point mLeft2 = new Point(screenWidth/4,150);
        Point mControlLeft2 = new Point(3*screenWidth/8,220);
        Point mFirst = new Point(screenWidth/2,150);
        Point mControlFirst = new Point(5*screenWidth/8,80);
        Point mSecond = new Point(3*screenWidth/4,150);
        Point mControlSecond = new Point(7*screenWidth/8,220);
        Point mRight = new Point(screenWidth,150);
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



//        // 从资源文件中生成位图bitmap
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ice_cream);
//        //Bitmap：图片对象，left:偏移左边的位置，top： 偏移顶部的位置
//        //rawBitmap(Bitmap bitmap, float left, float top, Paint paint)
//        int bit_width = bitmap.getWidth();
//        int bit_height = bitmap.getHeight();
//        //计算高宽比
//        float p = (float) bit_height/bit_width;
//        Bitmap newBmp = Bitmap.createScaledBitmap(bitmap, 70,(int)(70*p), true);

        Bitmap bitIceCream = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ice_cream),180,180,true);
        Bitmap bitTea = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.tea),200,200,true);
        Bitmap bitPizz = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pizz),200,200,true);
        Bitmap bitPie = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.pie),200,200,true);
        canvas.drawBitmap(bitIceCream, screenWidth/16, 0, mPaint); //在10,60处开始绘制图片
        canvas.drawBitmap(bitTea, 3*screenWidth/11, 30, mPaint);
        canvas.drawBitmap(bitPizz, 5*screenWidth/10, 0, mPaint);
        canvas.drawBitmap(bitPie, 7*screenWidth/9, 30, mPaint);

        canvas.drawPath(mPath, mPaint);

        mPaint.setColor(Color.YELLOW);
        canvas.drawCircle(screenWidth/15, 150, 25, mPaint);
        canvas.drawCircle(screenWidth/14, 260, 10, mPaint);
        canvas.drawCircle(screenWidth/5, 160, 20, mPaint);
        canvas.drawCircle(screenWidth/3,280, 5, mPaint);
        canvas.drawCircle(3*screenWidth/7, 210, 15, mPaint);
        canvas.drawCircle(5*screenWidth/9, 200, 20, mPaint);
        canvas.drawCircle(5*screenWidth/8, 270, 15, mPaint);
        canvas.drawCircle(3*screenWidth/4, 240, 25, mPaint);
        canvas.drawCircle(5*screenWidth/6, 230, 10, mPaint);
        canvas.drawCircle(12*screenWidth/13, 260, 10, mPaint);
    }

    private void getScreenSize(){
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        screenHeight = dm2.heightPixels;

        screenWidth = dm2.widthPixels;
    }

}
