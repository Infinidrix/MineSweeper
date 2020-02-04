/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 *
 * @author Biruk Solomon
 */
public class MainFrameBasic{
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                SweeperFrame sf;
                sf = new SweeperFrame();
                sf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sf.setVisible(true);
                
            }
        });
    }
}
