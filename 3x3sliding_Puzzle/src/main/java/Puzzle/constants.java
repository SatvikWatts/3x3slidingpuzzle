package Puzzle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author satvi
 */
public class constants {
   int Tile_Count,Grid_size,Board_size,Tiles_size;

    public constants() {
        this.Grid_size=10;
        this.Tile_Count=this.Grid_size*this.Grid_size;
        this.Tiles_size=100;
        this.Board_size=this.Grid_size*this.Tiles_size;
    }
}
