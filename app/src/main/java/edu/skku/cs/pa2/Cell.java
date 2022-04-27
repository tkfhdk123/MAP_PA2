package edu.skku.cs.pa2;

public class Cell {
    boolean top = true;
    boolean bottom = true;
    boolean left = true;
    boolean right = true;

    public Cell(int num){
        int div;

        if((num / 8) == 1){
            this.top = true;
        }
        else{
            this.top=false;
        }
        div = num % 8;

        if((num / 4) == 1){
            this.left = true;
        }
        else{
            this.left=false;
        }
        div = div % 4;

        if((num / 2) == 1){
            this.bottom = true;
        }
        else{
            this.bottom=false;
        }
        div = div % 2;

        if(div == 1){
            this.right = true;
        }
        else{
            this.right = false;
        }
    }


}
