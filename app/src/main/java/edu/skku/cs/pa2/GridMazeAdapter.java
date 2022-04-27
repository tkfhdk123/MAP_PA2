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

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class GridMazeAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> num_array;
    private ArrayList<Cell> cell_array;
    private ArrayList<Integer> map_size;

    public GridMazeAdapter(Context mContext, ArrayList<Integer> num_array, ArrayList<Cell> cell_array, ArrayList<Integer> map_size){
        this.mContext = mContext;
        this.num_array = num_array;
        this.cell_array = cell_array;
        this.map_size = map_size;
    }

    @Override
    public int getCount() {
        return num_array.size();
    }

    @Override
    public Object getItem(int i) {
        return num_array.get(i);
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
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);

        TextView textView = view.findViewById(R.id.tv);
        textView.setText(String.valueOf(num_array.get(i)));

        ImageView imageView = view.findViewById(R.id.imageView);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();

        int margin = 3 * metrics.densityDpi / 160;

        layoutParams.width = (350 * metrics.densityDpi) / (160 * map_size.get(i)) - 2 * margin;
        layoutParams.height = (350 * metrics.densityDpi) / (160 * map_size.get(i)) - 2 * margin;

        layoutParams.topMargin = margin;
        layoutParams.bottomMargin = margin;
        layoutParams.leftMargin = margin;
        layoutParams.rightMargin = margin;

        imageView.setLayoutParams(layoutParams);

        return view;
    }
}
