/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minesweeper;

import java.util.Scanner;

/**
 *
 * @author Biruk Solomon
 */
public class GridTest {
    private static int size = 12;
    private static int pop = 25;
    private static GridClass Grid;
    public static void main(String[] args){
        System.out.println("Started Test...");
        Grid = new GridClass(size, pop);
        printGridall();
        Scanner in = new Scanner(System.in);
        int x = in.nextInt();
        in.nextLine();
        int y = in.nextInt();
        in.nextLine();
        Grid.selectCell(x, y);
        printView();
    }
    public static void printGridall(){
        StringBuilder printable = new StringBuilder();
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                if(Grid.getCell(i, j).isMine()) printable.append(" X");
                        else printable.append(" " + Grid.getCell(i, j).getRating());
            }
            printable.append('\n');
        }
        System.out.println(printable.toString());
    }
    public static void printGridall(GridClass grid){
        int gridSize = grid.getSize();
        StringBuilder printable = new StringBuilder();
        for(int i = 0; i<gridSize; i++){
            for(int j = 0; j<gridSize; j++){
                if(grid.getCell(i, j).isMine()) printable.append(" X");
                        else printable.append(" " + grid.getCell(i, j).getRating());
            }
            printable.append('\n');
        }
        System.out.println(printable.toString());
    }
    public static void printView(){
        StringBuilder printable = new StringBuilder();
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                if(!(Grid.getCell(i, j).isVisible())) printable.append(" X");
                        else printable.append(" " + Grid.getCell(i, j).getRating());
            }
            printable.append('\n');
        }
        System.out.println(printable.toString());
    }
}
