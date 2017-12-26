package com.example.apple.bakebar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.apple.bakebar.Entity.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by apple on 2017/10/29.
 */

public class CriclesView extends View{

    private int screenHeight;
    private int screenWidth;
    private int shapeHeight = 300;
    private List<Point> points;

//    /** 声明位图渲染对象 */
//    private Shader[] shaders = new Shader[5];
//    /** 声明颜色数组 */
//    private int[] colors;

    public CriclesView(Context context) {
        super(context);
    }

    public CriclesView(Context context,int shapeHeight){
        super(context);
        points = new ArrayList<Point>();
        this.shapeHeight = shapeHeight;
        getScreenSize();
        Random random=new Random();
        int x;
        int y;
        int color;
        int radius;
        int[] colors = new int[5];
        colors[0] = Color.YELLOW;
        colors[1] = Color.LTGRAY;
        colors[2] = Color.RED;
        colors[3] = Color.GREEN;
        colors[4] = Color.BLUE;
        for (int i = 0; i <36; i++) {
            x=random.nextInt(screenWidth-30)%(screenWidth-30-30+1) + 30;
            y=random.nextInt(shapeHeight-30)%(shapeHeight-30-30+1) + 30;
            radius = random.nextInt(30);
            color = random.nextInt(5);
            Point point = new Point(x,y,colors[color],radius);
            points.add(point);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getScreenSize();

//        Point point1 = new Point(11*screenWidth/20,20);
//        Point point2 = new Point(19*screenWidth/20,50);
//        Point point3 = new Point(17*screenWidth/20,210);
//        Point point4 = new Point(10*screenWidth/20,400);
//        Point point5 = new Point(11*screenWidth/20,640);
//        Point point6 = new Point(15*screenWidth/20,700);
//        Point point7 = new Point(18*screenWidth/20,800);
//        Point point8 = new Point(13*screenWidth/20,930);
//        Point point9 = new Point(19*screenWidth/20,1000);
//
//        Point point12 = new Point(1*screenWidth/20,20);
//        Point point22 = new Point(9*screenWidth/20,50);
//        Point point32 = new Point(7*screenWidth/20,210);
//        Point point42 = new Point(0,400);
//        Point point52 = new Point(1*screenWidth/20,640);
//        Point point62 = new Point(5*screenWidth/20,700);
//        Point point72 = new Point(8*screenWidth/20,800);
//        Point point82 = new Point(3*screenWidth/20,930);
//        Point point92 = new Point(9*screenWidth/20,1000);
        Paint mPaint = new Paint();
//        mPaint.setStyle(Paint.Style.FILL);//设置填满
//        mPaint.setAntiAlias(true);//锯齿效果
//
//        mPaint.setColor(Color.RED);// 设置颜色
//        canvas.drawCircle(point12.x, point12.y, 15, mPaint);
//        canvas.drawCircle(point42.x, point42.y, 25, mPaint);
//        canvas.drawCircle(point72.x, point72.y, 10, mPaint);
//        canvas.drawCircle(point22.x, point22.y, 20, mPaint);
//        mPaint.setColor(Color.YELLOW);// 设置颜色
//        canvas.drawCircle(point92.x, point92.y, 30, mPaint);
//        canvas.drawCircle(point32.x, point32.y, 25, mPaint);
//        canvas.drawCircle(point82.x, point82.y, 10, mPaint);
//        mPaint.setColor(Color.LTGRAY);// 设置颜色
//        canvas.drawCircle(point62.x, point62.y, 20, mPaint);
//        canvas.drawCircle(point52.x, point52.y, 15, mPaint);
//
//        mPaint.setColor(Color.RED);// 设置颜色
//        canvas.drawCircle(point1.x, point1.y, 15, mPaint);
//        canvas.drawCircle(point4.x, point4.y, 25, mPaint);
//        canvas.drawCircle(point7.x, point7.y, 10, mPaint);
//        canvas.drawCircle(point2.x, point2.y, 20, mPaint);
//        mPaint.setColor(Color.YELLOW);// 设置颜色
//        canvas.drawCircle(point9.x, point9.y, 30, mPaint);
//        canvas.drawCircle(point3.x, point3.y, 25, mPaint);
//        canvas.drawCircle(point8.x, point8.y, 10, mPaint);
//        mPaint.setColor(Color.LTGRAY);// 设置颜色
//        canvas.drawCircle(point6.x, point6.y, 20, mPaint);
//        canvas.drawCircle(point5.x, point5.y, 15, mPaint);
//
//        mPaint.setColor(Color.RED);// 设置颜色
//        canvas.drawCircle(point22.x, point22.y+1000, 15, mPaint);
//        canvas.drawCircle(point52.x, point52.y+1000, 25, mPaint);
//        canvas.drawCircle(point72.x, point72.y+1000, 10, mPaint);
//        canvas.drawCircle(point12.x, point12.y+1000, 20, mPaint);
//        mPaint.setColor(Color.YELLOW);// 设置颜色
//        canvas.drawCircle(point62.x, point62.y+1000, 30, mPaint);
//        canvas.drawCircle(point32.x, point32.y+1000, 25, mPaint);
//        canvas.drawCircle(point82.x, point82.y+1000, 10, mPaint);
//        mPaint.setColor(Color.LTGRAY);// 设置颜色
//        canvas.drawCircle(point92.x, point92.y+1000, 20, mPaint);
//        canvas.drawCircle(point42.x, point42.y+1000, 15, mPaint);
//
//        mPaint.setColor(Color.RED);// 设置颜色
//        canvas.drawCircle(point2.x, point2.y+1000, 15, mPaint);
//        canvas.drawCircle(point5.x, point5.y+1000, 25, mPaint);
//        canvas.drawCircle(point7.x, point7.y+1000, 10, mPaint);
//        canvas.drawCircle(point1.x, point1.y+1000, 20, mPaint);
//        mPaint.setColor(Color.YELLOW);// 设置颜色
//        canvas.drawCircle(point6.x, point6.y+1000, 30, mPaint);
//        canvas.drawCircle(point3.x, point3.y+1000, 25, mPaint);
//        canvas.drawCircle(point8.x, point8.y+1000, 10, mPaint);
//        mPaint.setColor(Color.LTGRAY);// 设置颜色
//        canvas.drawCircle(point9.x, point9.y+1000, 20, mPaint);
//        canvas.drawCircle(point4.x, point4.y+1000, 15, mPaint);
//
//        mPaint.setColor(Color.RED);// 设置颜色
//        canvas.drawCircle(point52.x, point52.y+2000, 15, mPaint);
//        canvas.drawCircle(point22.x, point22.y+2000, 25, mPaint);
//        canvas.drawCircle(point92.x, point92.y+2000, 10, mPaint);
//        canvas.drawCircle(point12.x, point12.y+2000, 20, mPaint);
//        mPaint.setColor(Color.YELLOW);// 设置颜色
//        canvas.drawCircle(point62.x, point62.y+2000, 30, mPaint);
//        canvas.drawCircle(point32.x, point32.y+2000, 25, mPaint);
//        canvas.drawCircle(point82.x, point82.y+2000, 10, mPaint);
//        mPaint.setColor(Color.LTGRAY);// 设置颜色
//        canvas.drawCircle(point72.x, point72.y+2000, 20, mPaint);
//        canvas.drawCircle(point42.x, point42.y+2000, 15, mPaint);
//
//        mPaint.setColor(Color.RED);// 设置颜色
//        canvas.drawCircle(point4.x, point4.y+2000, 15, mPaint);
//        canvas.drawCircle(point5.x, point5.y+2000, 25, mPaint);
//        canvas.drawCircle(point7.x, point7.y+2000, 10, mPaint);
//        canvas.drawCircle(point8.x, point8.y+2000, 20, mPaint);
//        mPaint.setColor(Color.YELLOW);// 设置颜色
//        canvas.drawCircle(point6.x, point6.y+2000, 30, mPaint);
//        canvas.drawCircle(point3.x, point3.y+2000, 25, mPaint);
//        canvas.drawCircle(point1.x, point1.y+2000, 10, mPaint);
//        mPaint.setColor(Color.LTGRAY);// 设置颜色
//        canvas.drawCircle(point9.x, point9.y+2000, 20, mPaint);
//        canvas.drawCircle(point2.x, point2.y+2000, 15, mPaint);

//        List<Point> points = getPoints();

        for (Point point : points){
            mPaint.setColor(point.getColor());
            canvas.drawCircle(point.x,point.y,point.getRadius(),mPaint);
        }

    }

    private void getScreenSize(){
        DisplayMetrics dm2 = getResources().getDisplayMetrics();

        screenHeight = dm2.heightPixels;

        screenWidth = dm2.widthPixels;
    }

    //在一定范围内生成不重复的随机
//    private List<Point> getPoints(){
//        getScreenSize();
//        points = new ArrayList<Point>();
//        Random random=new Random();
//        int x;
//        int y;
//        int color;
//        int radius;
//        int[] colors = new int[5];
//        colors[0] = Color.YELLOW;
//        colors[1] = Color.LTGRAY;
//        colors[2] = Color.RED;
//        colors[3] = Color.GREEN;
//        colors[4] = Color.BLUE;
//        for (int i = 0; i <36; i++) {
//            x=random.nextInt(screenWidth-30)%(screenWidth-30-30+1) + 30;
//            y=random.nextInt(shapeHeight-30)%(shapeHeight-30-30+1) + 30;
//            radius = random.nextInt(30);
//            color = random.nextInt(5);
//            Point point = new Point(x,y,colors[color],radius);
//            points.add(point);
//        }
//        return points;
//    }
}
