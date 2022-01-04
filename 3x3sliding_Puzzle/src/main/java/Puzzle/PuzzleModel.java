/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Puzzle;
import java.util.Random;
/**
 *
 * @author satvi
 */
public class PuzzleModel {
    private int nbTiles;
    private int row;
    private int[] Tiles;
    private int blankpos;
    private static final Random RANDOM = new Random();

    
    public PuzzleModel() {
        constants c=new constants();
        row=c.Grid_size;
        nbTiles=c.Tile_Count;
        blankpos=nbTiles-1;
        Tiles=new int[nbTiles];
        initialize();
    }

    public void initialize()
    {
        for(int i=0;i<nbTiles;i++)
        {
            Tiles[i]=i+1;
        }
        Tiles[blankpos]=0;
        //Tiles[8]=Tiles[7];
        //Tiles[7]=0;
    }

    public void shuffle() {
     int n = nbTiles-1;
     while (n > 0) {
       int r = RANDOM.nextInt(n--);
       int tmp = Tiles[r];
       Tiles[r] = Tiles[n];
       Tiles[n] = tmp;
     }
   }

    public int tile(int i)
    {
        return Tiles[i];
    }
    public boolean isSolvable() {
     int countInversions = 0;

     for (int i = 0; i < nbTiles-1; i++) {
       for (int j = 0; j < i; j++) {
         if (Tiles[j] > Tiles[i])
           countInversions++;
       }
     }
     return countInversions % 2 == 0;
   }
    
    public boolean isSolved() {
      if (Tiles[Tiles.length - 1] != 0)
        return false;

      for (int i=0;i<nbTiles-1;i++) {
        if (Tiles[i]!=i+1)
          return false;      
      }

      return true;
    }
  
    public boolean checkEmpty(int i) {
        int c=-1;
        if(i+row<nbTiles && Tiles[i+row]==0){
            c=i+row;
        }
        else if(i-row>=0 && Tiles[i-row]==0){
            c=i-row;
        }
        else if(i%row!=0 && Tiles[i-1]==0){
            c=i-1;
        }
        else if((i+1)%row!=0 && Tiles[i+1]==0){
            c=i+1;
        }
        if(c==-1)return false;
        else{
            Tiles[c]=Tiles[i];
            Tiles[i]=0;
            return true;
        }
    }
}
