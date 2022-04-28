package edu.skku.cs.pa2;

import java.util.ArrayList;

public class MazeBfsCheck {
    private ArrayList<Cell> cells;
    private Integer state;
    private Integer maze_size;

    public MazeBfsCheck(ArrayList<Cell> cells, Integer state, Integer maze_size){
        this.cells = cells;
        this.state = state;
        this.maze_size = maze_size;
    }

}
