package edu.skku.cs.pa2;

import java.util.ArrayList;

public class DataHint {
    private Integer state;
    private ArrayList<Integer> path;

    public DataHint(Integer state, ArrayList<Integer> path){
        this.state = state;
        this.path = path;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }
}
