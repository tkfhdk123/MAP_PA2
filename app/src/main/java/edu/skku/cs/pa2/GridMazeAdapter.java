package edu.skku.cs.pa2;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class GridMazeAdapter extends BaseAdapter {
    private Context mContext;
    private Integer image;
    private ArrayList<Cell> cell_array;
    private ArrayList<Integer> map_size;
    private ArrayList<Integer> state;
    private Integer hint;
    private boolean check;

    public GridMazeAdapter(Context mContext, Integer image,
                           ArrayList<Cell> cell_array, ArrayList<Integer> map_size,
                           ArrayList<Integer> state, Integer hint, boolean check){
        this.mContext = mContext;
        this.image = image;
        this.cell_array = cell_array;
        this.map_size = map_size;
        this.state = state;
        this.hint = hint;
        this.check = check;
    }

    @Override
    public int getCount() {
        return cell_array.size();
    }

    @Override
    public Object getItem(int i) {
        return cell_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.activity_grid_maze, viewGroup, false);
        }

        ImageView other_image = view.findViewById(R.id.other_imageView);

        if(check){
            if (i == hint){
                other_image.setImageResource(R.drawable.hint);
            }

            if (i == (map_size.get(i) * map_size.get(i)) - 1) {
                other_image.setImageResource(R.drawable.goal);
            }

            if (i == state.get(i)) {
                other_image.setImageResource(image);
            }
        }
        else {
            if (i == (map_size.get(i) * map_size.get(i)) - 1) {
                other_image.setImageResource(R.drawable.goal);
            }

            if (i == state.get(i)) {
                other_image.setImageResource(image);
            }
        }

        //각각의 디바이스의 dpi를 가져오고 maze 사이즈에 맞는 미로 생성
        ImageView imageView = view.findViewById(R.id.maze_imageView);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        int margin = 3 * metrics.densityDpi / 160;
        int hori=0;
        int vert=0;

        if(cell_array.get(i).top){
            layoutParams.topMargin = margin;
            vert++;
        }
        else {
            layoutParams.topMargin = 0;
        }
        if(cell_array.get(i).bottom){
            layoutParams.bottomMargin = margin;
            vert++;
        }
        else {
            layoutParams.bottomMargin = 0;
        }
        if(cell_array.get(i).left){
            layoutParams.leftMargin = margin;
            hori++;
        }
        else{
            layoutParams.leftMargin = 0;
        }
        if(cell_array.get(i).right){
            layoutParams.rightMargin = margin;
            hori++;
        }
        else{
            layoutParams.rightMargin = 0;
        }

        layoutParams.width = (350 * metrics.densityDpi) / (160 * map_size.get(i)) - hori * margin;
        layoutParams.height = (350 * metrics.densityDpi) / (160 * map_size.get(i)) - vert * margin;

        imageView.setLayoutParams(layoutParams);

        return view;
    }
}
