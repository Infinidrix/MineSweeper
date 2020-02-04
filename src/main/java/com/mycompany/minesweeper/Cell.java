/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minesweeper;

/**
 *
 * @author Biruk Solomon
 */
public class Cell {
    private boolean Visible;
    private boolean Mine;
    private int Rating;
    private int X;
    private int Y;
    
    Cell(int xCord, int yCord){
        Visible = false;
        Mine = false;
        X = xCord;
        Y = yCord;
    }

    public boolean isMine() {
        return Mine;
    }
    protected void setMine(boolean flag){
        Mine = flag;
    }
    public boolean isVisible(){
        return Visible;
    }
    protected void setVisible(boolean flag){
        Visible = flag;
    }
    public int getRating(){
        return Rating;
    }
    protected void setRating(int number){
        Rating = number;
    }
    public int getX(){
        return X;
    }
    public int getY(){
        return Y;
    }
    
}
