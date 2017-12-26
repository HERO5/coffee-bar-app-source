package com.example.apple.bakebar.Entity;

/**
 * Created by apple on 2017/10/26.
 */

public class Point {

    public int x;
    public int y;
    private int radius;
    private int color;

    public Point(){
    }

    public Point(int x,int y){
        this.x = x;
        this.y = y;
    }

    public Point(int x,int y,int color,int radius){
        this.x = x;
        this.y = y;
        this.color = color;
        this.radius = radius;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
