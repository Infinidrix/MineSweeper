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
public class SweeperFrame extends JFrame{
    private static final int DEFAULTSIZE = 10;
    private static final int DEFAULTPOP = 5;
    public static final int BGCOLOR = 0;
    public static final int DISCOLOR = 1;
    private int Size;
    private int Pop;
    private JPanel Board;
    private JPanel Header;
    private JButton Info;
    private JMenuBar MenuBar;
    private SettingsDialog SettingsMenu;
    private PrefDialog PrefMenu;
    private int Marked;
    private JLabel Content;
    private Color[] Colorings = new Color[2];
    private Color[] TempColorings = new Color[2];
    protected GridClass Grid;
    SweeperFrame(){       
        this(DEFAULTSIZE, DEFAULTPOP);
        JFrame hii = new JFrame();
        //setSize(200,400);
    }
    SweeperFrame(int aSize, int aPop){
        setTitle("MineSweeper");
        Size = aSize;
        Pop = aPop;
        
        Board = new JPanel();
        Header = new JPanel();
        Content = new JLabel();
        Info = new JButton();
        Info.setEnabled(false);
        Header.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Header.add(Info);
        Header.add(Content);
        add(Header, BorderLayout.NORTH);
        
        setLocationByPlatform(true);
        setSize(700, 700);
        
        createBoard();
        add(Board);

        Colorings[0] = Board.getComponent(0).getBackground();
        Colorings[1] = Info.getBackground();
        
        clone(Colorings, TempColorings);
        
        
        MenuBar = new JMenuBar();
        
        JMenu GameMenu = new JMenu("Game");
        JMenuItem RestartItem = new JMenuItem(new RestartAction("Restart"));
        JMenuItem CloseItem = new JMenuItem(new AbstractAction("Close"){
            @Override
            public void actionPerformed(ActionEvent event){
                System.exit(0);
            }
        });
        GameMenu.add(RestartItem);
        GameMenu.add(CloseItem);
        MenuBar.add(GameMenu);
        
        JMenu EditMenu = new JMenu("Edit");
        JMenuItem SettingsItem = new JMenuItem(new SettingAction("Settings", this, SettingsMenu));
        JMenuItem PrefItem = new JMenuItem(new SettingAction("Preferences", this, PrefMenu));
        EditMenu.add(SettingsItem);
        EditMenu.add(PrefItem);
        MenuBar.add(EditMenu);
        
        setJMenuBar(MenuBar);
    }
    
    private void clone(Color[] main, Color[] target){
        int i = 0;
        for (Color color:main)
            target[i++] = new Color(color.getRGB());
    }
    
    protected void setBoard(int aSize, int percent){
        Size = aSize;
        Pop = (int) ((double) percent * (double) Size*Size / 100.0);
        Board.removeAll();
        createBoard();
        revalidate();
    }
    
    protected void setColoring(Color color, int index){
        
        TempColorings[index] = color;
        setPref(TempColorings);
        
    }
    
    protected void resetColorings(){
        setPref(Colorings);
        clone(Colorings, TempColorings);
    }
    
    protected void setColorings(){
        clone(TempColorings, Colorings);
    }
    
    protected void setPref(Color[] Coloring){
        Component[] all  = Board.getComponents();
        JButton temp;
        for (Component comp:all){
            temp = (JButton)comp;
            if (temp.isEnabled()){
                temp.setBackground(Coloring[0]);
            }
            else{
                temp.setBackground(Coloring[1]);
            }
        }
    }
    
    private void createBoard(){
        Marked = 0;
        Info.setText("" + Pop);
        Content.setText("mines left.");
        Board.setLayout(new GridLayout(Size, Size));
        Grid = new GridClass(Size, Pop);
        for(int i = 0; i < Size; i++){
            for(int j = 0; j < Size; j++){
                Board.add(new MineButton(new MineAction(i, j)));
            }
        }
        GridTest.printGridall(Grid);
    }
    
    public void redraw(){
        for (Component button:Board.getComponents()){
            MineButton minebutton = (MineButton) button;
            MineAction mineaction = (MineAction) minebutton.getAction();
            mineaction.evaluate(minebutton);
            setPref(Colorings);
            Info.setText("" + (Pop - Marked));
        }
        if (Grid.isGameOver()){
            String FinalMsg;
            if(Grid.isLost()){
                FinalMsg = "OHHH NOOOO! You have clicked on a mine. Would you like to restart the game?";
            }
            else{
                FinalMsg = "YAYYYYYY!!! You have won!! Would you like to restart the game?"; 
            }
            
            int selection;
            selection = JOptionPane.showConfirmDialog(this, FinalMsg, "Game over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(selection == JOptionPane.YES_OPTION){
                Board.removeAll();
                createBoard();
                revalidate();
            }
            else{
                System.exit(0);
            }
        }
    }
 
    class MineAction extends AbstractAction{
        private final int XCord;
        private final int YCord;
        private boolean Flagged;
        MineAction(int aX, int aY){
            XCord = aX;
            YCord = aY;
            Flagged = false;
            putValue("Name", "");
        }
        public void setFlagged(Boolean flag){
            Flagged = flag;
            Marked += (flag) ? 1:-1;
        }
        public boolean getFlagged(){
            return Flagged;
        }
        @Override
        public void actionPerformed(ActionEvent event){
            JButton button = (JButton) event.getSource();
            Grid.selectCell(XCord, YCord);
            redraw();
        }
        public void evaluate(JButton target){
            Cell Source = Grid.getCell(XCord, YCord);
            if (Source.isVisible()){
                if (Source.isMine()){
                    if (Grid.isGameOver()){
                        target.setText("*");
                    }
                }
                else{
                    if (Flagged){
                        setFlagged(false);
                    }
                    if (Source.getRating() == 0){
                        target.setText("");
                    }
                    else{
                        target.setText("" + Source.getRating());
                    }
                    target.setEnabled(false);
                }
            }
            else if(Flagged){
                target.setText("X");
            }
            else{
                target.setText("");
            }
        }
    }

    protected class RightClickHandler extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent event){
            if (event.getButton() == MouseEvent.BUTTON3){
                MineButton source = (MineButton) event.getSource();
                MineAction action = (MineAction) source.getAction();
                boolean flag = action.getFlagged();
                action.setFlagged(!flag);
                action.evaluate(source);
                Info.setText("" + (Pop-Marked));
                revalidate();
            }
        }
    }
    class MineButton extends JButton{
        MineButton(Action action){
            super(action);
            addMouseListener(new RightClickHandler());
        }
        public void redraw(){
            
        }
    }
    
    class RestartAction extends AbstractAction{
        RestartAction(String Name){
            putValue(Action.NAME, Name);
            putValue(Action.SHORT_DESCRIPTION, "Restarts the minefield with current settings");
        }
        @Override
        public void actionPerformed(ActionEvent event){
            Board.removeAll();
            createBoard();
            revalidate();
        }
    }
    
    class SettingAction extends AbstractAction{
        JFrame Parent;
        JDialog Dialog;
        String ClassType;
        SettingAction(String Name, JFrame Frame, JDialog dialog){
            super(Name);
            ClassType = Name;
            Parent = Frame;
            Dialog = dialog;
        }
        @Override
        public void actionPerformed(ActionEvent event){
            if (Dialog == null){
                if(ClassType.equals("Settings")) {
                    Dialog = new SettingsDialog(Parent, true);
                }
                else if(ClassType.equals("Preferences")){
                    Dialog = new PrefDialog(Parent, true);
                }
                else{
                    System.err.println("Dialog Type Unrecognized");
                }
            }
            Dialog.setVisible(true);
        }
    }
}






