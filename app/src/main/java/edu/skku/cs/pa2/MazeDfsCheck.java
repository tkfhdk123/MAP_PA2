package edu.skku.cs.pa2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MazeDfsCheck {
    private ArrayList<Cell> cells;
    private Integer state;
    private Integer maze_size;

    public MazeDfsCheck(ArrayList<Cell> cells, Integer state, Integer maze_size){
        this.cells = cells;
        this.state = state;
        this.maze_size = maze_size;
    }

    public Integer bfs(){
        int curstate=0;
        ArrayList<Integer> finalpath = new ArrayList<>();
        finalpath.add(state);
        LinkedList<DataHint> queue = new LinkedList<DataHint>();
        boolean[] visited = new boolean[maze_size*maze_size];
        visited[state]=true;
        queue.add(new DataHint(state, finalpath));

        while(!queue.isEmpty()){
            DataHint data = queue.poll();
            curstate = data.getState();
            finalpath = data.getPath();

            if(curstate==(maze_size*maze_size - 1)){
                break;
            }

            //위로 움직일 수 있을 때
            if((curstate - maze_size)>=0 && (curstate - maze_size)<maze_size*maze_size
                    && !cells.get(curstate - maze_size).bottom && !visited[curstate - maze_size]){
                visited[curstate - maze_size]=true;
                Integer plus = curstate - maze_size;
                ArrayList<Integer> curpath = new ArrayList<>();
                curpath.addAll(finalpath);
                curpath.add(plus);
                queue.add(new DataHint(plus, curpath));
            }

            //왼쪽으로 움직일 수 있을 때
            if((curstate - 1)>=0 && (curstate - 1)<maze_size*maze_size
                    && !cells.get(curstate - 1).right && !visited[curstate - 1]){
                visited[curstate - 1]=true;
                Integer plus = curstate - 1;
                ArrayList<Integer> curpath = new ArrayList<>();
                curpath.addAll(finalpath);
                curpath.add(plus);
                queue.add(new DataHint(plus, curpath));
            }

            //밑으로 움직일 수 있을 때
            if((curstate + maze_size)>=0 && (curstate + maze_size)<maze_size*maze_size
                    && !cells.get(curstate + maze_size).top && !visited[curstate + maze_size]){
                visited[curstate + maze_size]=true;
                Integer plus = curstate + maze_size;
                ArrayList<Integer> curpath = new ArrayList<>();
                curpath.addAll(finalpath);
                curpath.add(plus);
                queue.add(new DataHint(plus, curpath));
            }

            //오른쪽으로 움직일 수 있을 때
            if((curstate + 1)>=0 && (curstate + 1)<maze_size*maze_size
                    && !cells.get(curstate + 1).left && !visited[curstate + 1]){
                visited[curstate + 1]=true;
                Integer plus = curstate + 1;
                ArrayList<Integer> curpath = new ArrayList<>();
                curpath.addAll(finalpath);
                curpath.add(plus);
                queue.add(new DataHint(plus, curpath));
            }
        }

        return finalpath.get(1);
    }
}
