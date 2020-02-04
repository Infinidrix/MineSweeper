/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minesweeper;

import java.util.ArrayList;

/**
 *
 * @author Biruk Solomon
 */
public class GridClass {
    private static final int DEFAULTSIZE = 12;
    private static final int DEFAULTPOP = 25;
    private Cell[][] CellGrid;
    private final int Size;
    private final int Population;
    private boolean GameOver;
    private boolean Lost;
    
    
    GridClass(int aSize, int aPopulation){
        GameOver = false;
        Lost = false;
        CellGrid = new Cell[aSize][aSize];
        Size = aSize;
        Population = aPopulation;
        System.out.println("Got to the start");
        for(int i =0; i<Size;i++){
            for(int j = 0; j<Size; j++){
                CellGrid[i][j] = new Cell(i, j);
            }
        }
        System.out.println("created cell grid");
        int i = 0;
        while(i<Population){
            int xCord = (int)(Math.random()*Size);
            int yCord = (int)(Math.random()*Size);
            if (!CellGrid[xCord][yCord].isMine()){
                CellGrid[xCord][yCord].setMine(true);
                i++;
            }
        }
        System.out.println("populated cell grid");
        compute();
        System.out.println("assigned ratings to cells");
    }
    GridClass(){
        this(DEFAULTSIZE, DEFAULTPOP);
    }
        public int getSize() {
        return Size;
    }

    public int getPopulation() {
        return Population;
    }
    private void compute(){
        int x;
        int y;
        int rating;
        for(Cell[] row:CellGrid){
            for (Cell cell:row){
                x = cell.getX();
                y = cell.getY();
                rating = 0;
                
                for(int i = -1; i<2; i++){
                    for(int j = -1; j<2; j++){
                        if (!(x+i>=Size || x+i<0 || y+j>=Size || y+j<0)){
                            if (CellGrid[x+i][y+j].isMine()){ 
                                rating++;
                            }
                        }
                    }
                }
                cell.setRating(rating);
            }
        }
    }
    public void selectCell(Cell cell){
        if(cell.isMine()){
            //TODO game over
            GameOver = true;
            Lost = true;
            return;
        }
        else if(cell.getRating() > 0){
            cell.setVisible(true);
            endChecker();
            return;
        }
        cell.setVisible(true);
        ArrayList<Cell> selected = new ArrayList<>();
        ArrayList<Cell> checked = new ArrayList<>();
        selected.add(cell);
        int xCord;
        int yCord;
        do{
            xCord = selected.get(0).getX();
            yCord = selected.get(0).getY();
            for(int i = -1; i<2; i++){
                    for(int j = -1; j<2; j++){
                        if(i == 0 && j == 0){
                            continue;
                        }
                        if (!(xCord + i >= Size || xCord + i < 0 || yCord + j >= Size ||  yCord + j < 0)){
                            if (CellGrid[xCord+i][yCord+j].getRating() > 0){
                                CellGrid[xCord+i][yCord+j].setVisible(true);
                            }
                            else if(!(checked.contains(CellGrid[xCord+i][yCord+j]) || selected.contains(CellGrid[xCord+i][yCord+j]))){
                                CellGrid[xCord+i][yCord+j].setVisible(true);
                                selected.add(CellGrid[xCord+i][yCord+j]);
                            }
                        }
                    }
                }
            checked.add(selected.get(0));
            selected.remove(selected.get(0));
        }while(selected.size() > 0);
        endChecker();
    }

    public void selectCell(int x, int y){
        selectCell(CellGrid[x][y]);
    }

        
    private void endChecker(){
        int unchecked = 0;
        for (Cell[] row:CellGrid){
            for (Cell cell:row){
                unchecked += (cell.isVisible())? 0:1;
            }
        }
        GameOver = (unchecked == Population);
    }

    public boolean isGameOver(){
        return GameOver;
    }
    
    public boolean isLost(){
        return Lost;
    }
    
    public Cell getCell(int x, int y){
        return CellGrid[x][y];
    }
}
