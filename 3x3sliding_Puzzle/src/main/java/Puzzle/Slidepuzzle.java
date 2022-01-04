/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Puzzle;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author satvi
 */
public class Slidepuzzle extends javax.swing.JFrame {

    private String player_a,player_b;
    private char turn;
    private static JFrame frame;
    private int scoreA,scoreB;
    private boolean gameover;
    private boolean end;
    private PuzzleModel model;
    private constants cons;
    
    public Slidepuzzle(String pa,String pb) {
        player_a=pa;
        player_b=pb;
        turn='a';
        scoreA=0;scoreB=0;
        gameover = false;
        end=false;
        
        frame=new JFrame();
        model=new PuzzleModel();
        cons=new constants();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("3x3 Sliding Puzzle");
        frame.setPreferredSize(new Dimension(cons.Board_size+14,cons.Board_size+200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
              if(gameover && turn=='b')
              {
                  if(end)
                  {
                    String winner;
                    if(scoreA==scoreB)winner="draw";
                    else
                    winner=(scoreA<scoreB)?player_a:player_b; 
                    
                    String filepath="C:\\Users\\satvi\\OneDrive\\Desktop\\Netbeans projects\\3x3sliding_Puzzle\\saved_scores.txt";
                    File file= new File(filepath);
                 
                      try {
                          FileWriter fw=new FileWriter(file,true);
                          BufferedWriter bw = new BufferedWriter(fw);
                          bw.write(player_a+" "+String.valueOf(scoreA)+" ");
                          bw.write(player_b+" "+String.valueOf(scoreB)+" ");
                          bw.write(winner+'\n');
                          
                          bw.close();
                          fw.close();
                      } catch (IOException ex) {
                          Logger.getLogger(Slidepuzzle.class.getName()).log(Level.SEVERE, null, ex);
                      }
             
                    
                    
                    Start_screen start=new Start_screen();
                    start.setVisible(true);
                    start.setLocationRelativeTo(null);
                    frame.dispose();
                  }
                  else
                  {
                    endgame();
                    return;
                  }
              }
              else if (gameover && turn=='a'){
                turn='b';
                newgame();
                return;
               } 
              else {
                int ex = e.getX();
                int ey = e.getY();
                if (ex < 0 || ex > cons.Board_size  || ey < 0  || ey > cons.Board_size)
                  return;
                
                int c1 = ex / cons.Tiles_size;
                int r1 = ey / cons.Tiles_size;
                int clickPos = r1*cons.Grid_size + c1;

                if(!model.checkEmpty(clickPos))return;
                if(turn=='a')scoreA++;
                else
                scoreB++;
                gameover=model.isSolved();
            }
            paint();
          }
        });
        newgame();
        initComponents();
    }
    
    public void endgame()
    {
        frame.getContentPane().removeAll();
        frame.repaint();
        if(scoreA!=scoreB)
        {
            String s=(scoreA>scoreB)?player_b:player_a;
            int x=Math.abs(scoreA-scoreB);
            String s1=String.valueOf(x);
            JLabel label=new JLabel(s + " won by " + s1 + " moves");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setBounds(cons.Board_size/2-100,cons.Board_size/2-10,200,20);
            frame.add(label);
        }
        else
        {
            JLabel label2=new JLabel("Looke like it's a draw. You both suck!");
            label2.setHorizontalAlignment(JLabel.CENTER);
            label2.setVerticalAlignment(JLabel.CENTER);
            label2.setBounds(cons.Board_size/2-100,cons.Board_size/2-10,200,20);
            frame.add(label2);           
        }
        JLabel label1=new JLabel("Click for a rematch.");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setVerticalAlignment(JLabel.CENTER);
        label1.setBounds(cons.Board_size/2-100,cons.Board_size/2+10,200,20);
        frame.add(label1);
        
        end=true;
    }
    
    public void newgame()
    {
        do{
        model.initialize();
        model.shuffle();
        }while(!model.isSolvable());
        gameover=false;
        
        paint();
    }
    public void paint()
    {
        frame.getContentPane().removeAll();
        frame.repaint();
        if(gameover)
        {
            if(turn=='a')
            {
                JLabel label=new JLabel("Well Done " + player_a + "!");
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setBounds(cons.Board_size/2-100,cons.Board_size/2-10,200,20);
                frame.add(label);
                
                String s=String.valueOf(scoreA);
                JLabel label1=new JLabel("Your score: " + s);
                label1.setHorizontalAlignment(JLabel.CENTER);
                label1.setVerticalAlignment(JLabel.CENTER);
                label1.setBounds(cons.Board_size/2-100,cons.Board_size/2+10,200,20);
                frame.add(label1);
                
                JLabel label2=new JLabel("Click to start for player B");
                label2.setHorizontalAlignment(JLabel.CENTER);
                label2.setVerticalAlignment(JLabel.CENTER);
                label2.setBounds(cons.Board_size/2-100,cons.Board_size/2+30,200,20);
                frame.add(label2);
            }
            else
            {
                JLabel label=new JLabel("Well Done " + player_b + "!");
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setBounds(cons.Board_size/2-100,cons.Board_size/2-10,200,20);
                frame.add(label);
                
                String s=String.valueOf(scoreB);
                JLabel label1=new JLabel("Your score: " + s);
                label1.setHorizontalAlignment(JLabel.CENTER);
                label1.setVerticalAlignment(JLabel.CENTER);
                label1.setBounds(cons.Board_size/2-100,cons.Board_size/2+10,200,20);
                frame.add(label1);
                
                JLabel label2=new JLabel("Click to see who won");
                label2.setHorizontalAlignment(JLabel.CENTER);
                label2.setVerticalAlignment(JLabel.CENTER);
                label2.setBounds(cons.Board_size/2-100,cons.Board_size/2+30,200,20);
                frame.add(label2);
            }
        }
        else
        {
            for(int i=0;i<cons.Tile_Count;i++)
            {
                String s=String.valueOf(model.tile(i));
                if(s.equals("0"))s="";
                JLabel label=new JLabel(s);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                Border border = BorderFactory.createLineBorder(Color.RED,1);
                label.setBorder(border);
                int c=i/cons.Grid_size;
                int r=i%cons.Grid_size;
                label.setBounds(r*cons.Tiles_size,c*cons.Tiles_size,cons.Tiles_size,cons.Tiles_size);
                frame.add(label);
            }
            String s=(turn=='a')?player_a:player_b;
            JLabel label=new JLabel("Player Name: " + s);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setBounds(cons.Grid_size*cons.Tiles_size/2-100,cons.Grid_size*cons.Tiles_size+50,200,20);
            frame.add(label);
            int x=(turn=='a')?scoreA:scoreB;
            s=String.valueOf(x);
            JLabel label1=new JLabel("moves: " + s);
            label1.setHorizontalAlignment(JLabel.CENTER);
            label1.setVerticalAlignment(JLabel.CENTER);
            label1.setBounds(cons.Grid_size*cons.Tiles_size/2-40,cons.Grid_size*cons.Tiles_size+100,80,20);
            frame.add(label1);
        }

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Slidepuzzle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> {
            Start_screen start=new Start_screen();
            start.setVisible(true);
            start.setLocationRelativeTo(null);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
