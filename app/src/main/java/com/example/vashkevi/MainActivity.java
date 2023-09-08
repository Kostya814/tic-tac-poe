package com.example.vashkevi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.example.vashkevi.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private LinearLayout board;
    private Timer time;
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
                int [] comb = findWinner(GameInfo.firstSymbol);
                if(comb!= null)
                {
                    Toast.makeText(this, "Победил "+GameInfo.firstSymbol+ "!",Toast.LENGTH_LONG).show();
                    win(comb);



                }
            }
            else {
                button.setText(GameInfo.secondSymbol);
                int [] comb = findWinner(GameInfo.secondSymbol);
                if(comb!=null)
                {
                    Toast.makeText(this, "Победил "+GameInfo.secondSymbol+ "!",Toast.LENGTH_LONG).show();
                    win(comb);



                }
            }
            GameInfo.whoIsNext = !GameInfo.whoIsNext;
        };
        setListenerToSquares(clickListener);
        //initClearBordBtn();
    }
    public void initClearBordBtn()
    {
        binding.clearBoardValue.setOnClickListener(view -> {
            GameInfo.whoIsNext= true;
            Toast.makeText(this,"New Game",Toast.LENGTH_LONG).show();
            for (Button square:squares) {
                square.setText("");
                square.setBackgroundTintList(
                        ContextCompat.getColorStateList(
                                getApplicationContext(),
                                R.color.gray));

            }
        });

    }
    public void win(int[]winCombo)
    {
        for(int i =0; i<winCombo.length;i++)
        {
            squares.get(winCombo[i]).setBackgroundTintList(
                    ContextCompat.getColorStateList(
                            getApplicationContext(),
                            R.color.teal_200));;
        }


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
                    button.setHeight(convertToDp(90));
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
    private int[] findWinner(String symbol)
    {

        for (int i =0; i<GameInfo.winCombination.length;i++)
        {
            boolean findComb = true;
            for(int j =0;j<GameInfo.winCombination[1].length;j++)
            {
               int idBtn = GameInfo.winCombination[i][j];
               if(!squares.get(idBtn).getText().equals(symbol))
               {
                   findComb = false;
                   break;


               }
            }
            if(findComb) return GameInfo.winCombination[i];

        }
        return null;



    }
}