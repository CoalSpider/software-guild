/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.mod2.lesson34;

/**
 *
 * @author Ben Norman
 */
public class ShapeExcercise {
    
}
abstract class Shape{
    private final int hexColor;
    
    Shape(int hexColor){
        this.hexColor = hexColor;
    }
    
    protected abstract float getArea();
    protected abstract float getPerimeter();
    int getHexColor(){
        return hexColor;
    }
}

class Square extends Shape{
    private float sideLength;
    public Square(int hexColor, float sideLength) {
        super(hexColor);
        this.sideLength = sideLength;
    }
    
    @Override
    protected float getArea() {
        return sideLength*2;
    }

    @Override
    protected float getPerimeter() {
        return sideLength*4;
    }
    
}

class Rectangle extends Shape{
    private float width,height;
    public Rectangle(int hexColor, float width, float height) {
        super(hexColor);
        this.width = width;
        this.height = height;
    }

    @Override
    protected float getArea() {
        return width*height;
    }

    @Override
    protected float getPerimeter() {
        return width*2 + height*2;
    }
    
}

// coulda done 3 vec2s but I didnt want to do the work
class IsosoleseTriangle extends Shape{
    private float base, height;
    
    public IsosoleseTriangle(int hexColor, float base, float height) {
        super(hexColor);
        this.base = base;
        this.height = height;
    }

    @Override
    protected float getArea() {
        return (base* height)/2;
    }

    @Override
    protected float getPerimeter() {
        // assuming a issosolete
        float sideLength = (float)Math.sqrt((base/2)*(base/2) + (height*height));
        return sideLength+sideLength+base;
    }
}

class Circle extends Shape{
    private float radius;

    public Circle(int hexColor, int radius) {
        super(hexColor);
        this.radius = radius;
    }

    @Override
    protected float getArea() {
        return (float)(Math.PI*radius*radius);
    }

    @Override
    protected float getPerimeter() {
        return (float)(2*Math.PI*radius);
    }
}
