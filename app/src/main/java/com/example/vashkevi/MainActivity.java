package com.example.vashkevi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.vashkevi.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private LinearLayout board;
    private ArrayList<Button>squares = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        board = binding.board;
        generateBoard(3,3,board);
        View.OnClickListener clickListener = (view) -> {
            Button button = (Button) view;
            if(!button.getText().toString().equals("")) return;

            if(GameInfo.whoIsNext) {
                button.setText(GameInfo.firstSymbol);
            }
            else {
                button.setText(GameInfo.secondSymbol);
            }
            GameInfo.whoIsNext = !GameInfo.whoIsNext;
        };
        setListenerToSquares(clickListener);
    }
    private void setListenerToSquares(View.OnClickListener listener){
        for (int i =0; i< squares.size();i++)
        {
            squares.get(i).setOnClickListener(listener);
        }
    }

    public void generateBoard(int rowCount, int columnCount, LinearLayout board)
    {
        for (int row=0 ; row<rowCount;row++) {
            LinearLayout rowСontainer = generateRow(columnCount);
            board.addView(rowСontainer);
        }
    }
    private LinearLayout generateRow(int squaresCount)
    {
        LinearLayout rowContainer = new LinearLayout(getApplicationContext());
        rowContainer.setOrientation(LinearLayout.HORIZONTAL);
        rowContainer.setLayoutParams(
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

        for(int square = 0;square<squaresCount ;square++)
        {
            Button button = new Button(getApplicationContext());
            button.setBackgroundTintList(
                    ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.gray));

                    button.setWidth(convertToDp(50));
                    button.setHeight(convertToDp(50));
                    rowContainer.addView(button);
                    squares.add(button);
        }
        return rowContainer;

    }
    public  int convertToDp(int digit)
    {
        float density = getApplicationContext()
                .getResources().getDisplayMetrics().density;
        return (int)(digit*density+0/5);
    }
}