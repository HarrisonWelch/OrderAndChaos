/*
* Name: Harrison Welch
* Class: CSCI 4010 Mobile Development
* Date: 11-6-2017 8:43 PM
* */
package com.harrisonwelch.orderandchaos;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = "GAME_ACTIVITY";

    private boolean isEndOfGame = false;
    private int numberOfPiecesPlaced = 0;
    public int currentGamePiece = R.drawable.letter_x;
    public int currentPlayer = 0; // 0 = Order, 1 = Chaos

    public int[] gameBoardIds = {
            R.id.imageButton00, R.id.imageButton01, R.id.imageButton02, R.id.imageButton03,
            R.id.imageButton04, R.id.imageButton05, R.id.imageButton10, R.id.imageButton11,
            R.id.imageButton12, R.id.imageButton13, R.id.imageButton14, R.id.imageButton15,
            R.id.imageButton20, R.id.imageButton21, R.id.imageButton22, R.id.imageButton23,
            R.id.imageButton24, R.id.imageButton25, R.id.imageButton30, R.id.imageButton31,
            R.id.imageButton32, R.id.imageButton33, R.id.imageButton34, R.id.imageButton35,
            R.id.imageButton40, R.id.imageButton41, R.id.imageButton42, R.id.imageButton43,
            R.id.imageButton44, R.id.imageButton45, R.id.imageButton50, R.id.imageButton51,
            R.id.imageButton52, R.id.imageButton53, R.id.imageButton54, R.id.imageButton55
    };

    public int[][] gameBoardIds2D = {
            {R.id.imageButton00, R.id.imageButton01, R.id.imageButton02, R.id.imageButton03, R.id.imageButton04, R.id.imageButton05},
            {R.id.imageButton10, R.id.imageButton11, R.id.imageButton12, R.id.imageButton13, R.id.imageButton14, R.id.imageButton15},
            {R.id.imageButton20, R.id.imageButton21, R.id.imageButton22, R.id.imageButton23, R.id.imageButton24, R.id.imageButton25},
            {R.id.imageButton30, R.id.imageButton31, R.id.imageButton32, R.id.imageButton33, R.id.imageButton34, R.id.imageButton35},
            {R.id.imageButton40, R.id.imageButton41, R.id.imageButton42, R.id.imageButton43, R.id.imageButton44, R.id.imageButton45},
            {R.id.imageButton50, R.id.imageButton51, R.id.imageButton52, R.id.imageButton53, R.id.imageButton54, R.id.imageButton55}
    };
    public boolean[] gameBoardFlags = new boolean[36];
    public int[][] gameBoardImages = new int[6][6];

    private int index2DvarCol = -1;
    private int index2DvarRow = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        setImageButtonListeners();

        for(int i = 0; i < gameBoardFlags.length; i++){
            gameBoardFlags[i] = false;
        }

        for(int i = 0; i < gameBoardIds2D.length; i++){ // col
            for(int j = 0; j < gameBoardIds2D[i].length; j++){ //row
                gameBoardImages[i][j] = R.drawable.white_square;
            }
        }

        ((TextView) findViewById(R.id.text_chaos_left_arrow)).setText("");
        ((TextView) findViewById(R.id.text_chaos_right_arrow)).setText("");
        ((TextView) findViewById(R.id.using_arrow_right)).setText("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_game:
                Toast.makeText(getApplicationContext(),"Reseting",Toast.LENGTH_LONG).show();
                reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Log.i("GAME ACTIVITY","btn = " + view.getId());
        switch (view.getId()){
            case R.id.btn_x:
                currentGamePiece = R.drawable.letter_x;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.grey_button_black_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.grey_button_no_border);
                ((TextView) findViewById(R.id.using_arrow_left)).setText("\u25C0");
                ((TextView) findViewById(R.id.using_arrow_right)).setText("");
                break;
            case R.id.btn_o:
                currentGamePiece = R.drawable.letter_o;
                ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.grey_button_no_border);
                ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.grey_button_black_border);
                ((TextView) findViewById(R.id.using_arrow_right)).setText("\u25B6");
                ((TextView) findViewById(R.id.using_arrow_left)).setText("");
                break;
            case R.id.imageButton00: case R.id.imageButton01: case R.id.imageButton02:
            case R.id.imageButton03: case R.id.imageButton04: case R.id.imageButton05:
            case R.id.imageButton10: case R.id.imageButton11: case R.id.imageButton12:
            case R.id.imageButton13: case R.id.imageButton14: case R.id.imageButton15:
            case R.id.imageButton20: case R.id.imageButton21: case R.id.imageButton22:
            case R.id.imageButton23: case R.id.imageButton24: case R.id.imageButton25:
            case R.id.imageButton30: case R.id.imageButton31: case R.id.imageButton32:
            case R.id.imageButton33: case R.id.imageButton34: case R.id.imageButton35:
            case R.id.imageButton40: case R.id.imageButton41: case R.id.imageButton42:
            case R.id.imageButton43: case R.id.imageButton44: case R.id.imageButton45:
            case R.id.imageButton50: case R.id.imageButton51: case R.id.imageButton52:
            case R.id.imageButton53: case R.id.imageButton54: case R.id.imageButton55:
                placeGamePiece(view.getId());
                break;
            default:
                Log.i("GAME ACTIVITY","Not a case in the switch statement.");
        }
    }

    public void setImageButtonListeners(){
        int[] ids = {
                R.id.btn_x,
                R.id.btn_o,
                R.id.imageButton00, R.id.imageButton01, R.id.imageButton02, R.id.imageButton03,
                R.id.imageButton04, R.id.imageButton05, R.id.imageButton10, R.id.imageButton11,
                R.id.imageButton12, R.id.imageButton13, R.id.imageButton14, R.id.imageButton15,
                R.id.imageButton20, R.id.imageButton21, R.id.imageButton22, R.id.imageButton23,
                R.id.imageButton24, R.id.imageButton25, R.id.imageButton30, R.id.imageButton31,
                R.id.imageButton32, R.id.imageButton33, R.id.imageButton34, R.id.imageButton35,
                R.id.imageButton40, R.id.imageButton41, R.id.imageButton42, R.id.imageButton43,
                R.id.imageButton44, R.id.imageButton45, R.id.imageButton50, R.id.imageButton51,
                R.id.imageButton52, R.id.imageButton53, R.id.imageButton54, R.id.imageButton55
        };
        for(int i = 0; i < ids.length; i++){
            ((ImageButton) findViewById(ids[i])).setOnClickListener(this);
        }
    }

    public void placeGamePiece(int id){
//        Log.i(TAG,"id = " + id);
        int indexOfImageButtonId = findIndexOfId(id);
//        Log.i(TAG,"indexOfImageButtonId = " + indexOfImageButtonId);
        if(indexOfImageButtonId != -1){
            gameBoardFlags[indexOfImageButtonId] = true;
        }

        //test for Order player victory

        // find the col and row that the peice was placed on

        find2DIndexOfId(id);

        if(gameBoardImages[index2DvarCol][index2DvarRow] != R.drawable.white_square) return;

        if(isEndOfGame){
            Toast.makeText(getApplicationContext(),"Press the reset button in the top right",Toast.LENGTH_SHORT).show();
            return;
        }

        ((ImageButton) findViewById(id)).setImageResource(currentGamePiece);

        // these were stored in variables index2DvarCol and index2DvarRow
        // check the row, col, and both diagonals

        // gameBoardIds2Das [ col ] [ row ]
        gameBoardImages[index2DvarCol][index2DvarRow] = currentGamePiece;

        int consecutiveSum = 1;
        int oldImage = -1;

        // check the each row in the constant column
        for(int i = 0; i < gameBoardImages.length; i++){
            if (gameBoardImages[index2DvarCol][i] == oldImage && gameBoardImages[index2DvarCol][i] == currentGamePiece){
                consecutiveSum++;
            } else {
                consecutiveSum = 1;
            }

            oldImage = gameBoardImages[index2DvarCol][i];

            if (consecutiveSum == 5){
                // we have a winner
                endGame("Order");
            }
        }

        consecutiveSum = 1;
        oldImage = -1;

        // check the each column in the constant row
        for(int i = 0; i < gameBoardImages.length; i++){
            if (gameBoardImages[i][index2DvarRow] == oldImage && gameBoardImages[i][index2DvarRow] == currentGamePiece){
                consecutiveSum++;
            } else {
                consecutiveSum = 1;
            }

            oldImage = gameBoardImages[i][index2DvarRow];

            if (consecutiveSum == 5){
                // we have a winner
                endGame("Order");
            }
        }
        // now for the diagonal, there can only be 8 possible 5-in-row collections, with 6 diagonal tracks
        // for each directions

        consecutiveSum = 1;
        oldImage = -1;

        // let's start with the top-left-to-bottom-right diagonal
        if ( (index2DvarCol - index2DvarRow) == 1){
            // Top
            Log.i(TAG, "Top");
            for (int i = 0; i < gameBoardIds2D.length-1; i++){
                if (gameBoardImages[i+1][i] == oldImage && gameBoardImages[i+1][i] == currentGamePiece){
                    consecutiveSum++;
                } else {
                    consecutiveSum = 1;
                }
                oldImage = gameBoardImages[i+1][i];
                if (consecutiveSum == 5){
                    // we have a winner
                    endGame("Order");
                }
            }
        } else if( (index2DvarCol - index2DvarRow) == 0){
            // Middle
            Log.i(TAG, "Middle");
            for (int i = 0; i < gameBoardIds2D.length; i++){
                if (gameBoardImages[i][i] == oldImage && gameBoardImages[i][i] == currentGamePiece){
                    consecutiveSum++;
                } else {
                    consecutiveSum = 1;
                }
                oldImage = gameBoardImages[i][i];
                if (consecutiveSum == 5){
                    // we have a winner
                    endGame("Order");
                }
            }
        } else if ( (index2DvarCol - index2DvarRow) == -1){
            // Bottom
            Log.i(TAG, "Bottom");
            for (int i = 0; i < gameBoardIds2D.length-1; i++){
                if (gameBoardImages[i][i+1] == oldImage && gameBoardImages[i][i+1] == currentGamePiece){
                    consecutiveSum++;
                } else {
                    consecutiveSum = 1;
                }
                oldImage = gameBoardImages[i][i+1];
                if (consecutiveSum == 5){
                    // we have a winner
                    endGame("Order");
                }
            }
        }

        consecutiveSum = 1;
        oldImage = -1;

        // finally the top-right-to-bottom-left diagonal
        if( (index2DvarCol + index2DvarRow) == 4){
            // Top
            Log.i(TAG, "Top 2");
            for (int i = 0,k = gameBoardIds2D.length-2; i < gameBoardIds2D.length-1; i++,k--){
                if(gameBoardImages[k][i] != currentGamePiece){
                    Log.i(TAG,"BREAK HERE");
                    break;
                }
                if(i == gameBoardIds2D.length-2){
                    endGame("Order");
                }
            }
        } else if ( (index2DvarCol + index2DvarRow) == 5){
            // Middle
            Log.i(TAG, "Middle 2");
            for (int i = 0,k = gameBoardIds2D.length-1; i < gameBoardIds2D.length; i++,k--){
                if (gameBoardImages[k][i] == oldImage && gameBoardImages[k][i] == currentGamePiece){
                    consecutiveSum++;
                } else {
                    consecutiveSum = 1;
                }
                oldImage = gameBoardImages[k][i];
                if (consecutiveSum == 5){
                    // we have a winner
                    endGame("Order");
                }
            }
        } else if ( (index2DvarCol + index2DvarRow) == 6){
            // Bottom
            Log.i(TAG, "Bottom 2");
            for (int i = 0,k = gameBoardIds2D.length-1; i < gameBoardIds2D.length-1; i++,k--){
                if(gameBoardImages[k][i+1] != currentGamePiece){
                    Log.i(TAG,"BREAK HERE");
                    break;
                }
                if(i == gameBoardIds2D.length-2){
                    endGame("Order");
                }
            }
        }
        numberOfPiecesPlaced++;
        swapPlayer();
        if(isFull()) endGame("Chaos");
    }

    public void swapPlayer(){
        // swap the current player

        // if currentPlayer = 0 make it 1, else make it 0
        if( currentPlayer == 0 ){
            currentPlayer = 1;
            ((TextView) findViewById(R.id.text_order_left_arrow)).setText("");
            ((TextView) findViewById(R.id.text_order_right_arrow)).setText("");
            ((TextView) findViewById(R.id.text_chaos_left_arrow)).setText("\u25B6");
            ((TextView) findViewById(R.id.text_chaos_right_arrow)).setText("\u25C0");
            ((TextView) findViewById(R.id.player_chaos)).setTypeface(Typeface.DEFAULT_BOLD);
            ((TextView) findViewById(R.id.player_order)).setTypeface(Typeface.DEFAULT);
        } else {
            currentPlayer = 0;
            ((TextView) findViewById(R.id.text_order_left_arrow)).setText("\u25B6");
            ((TextView) findViewById(R.id.text_order_right_arrow)).setText("\u25C0");
            ((TextView) findViewById(R.id.text_chaos_left_arrow)).setText("");
            ((TextView) findViewById(R.id.text_chaos_right_arrow)).setText("");
            ((TextView) findViewById(R.id.player_order)).setTypeface(Typeface.DEFAULT_BOLD);
            ((TextView) findViewById(R.id.player_chaos)).setTypeface(Typeface.DEFAULT);
        }

    }

    public boolean isFull(){
        return numberOfPiecesPlaced >= 36;
    }

    public int findIndexOfId(int id){
        for(int i = 0; i < gameBoardIds.length; i++){
            if(gameBoardIds[i] == id){
                return i;
            }
        }
        return -1;
    }

    public void find2DIndexOfId(int id){
        for(int i = 0; i < gameBoardIds2D.length; i++){ // col
            for(int j = 0; j < gameBoardIds2D[i].length; j++){ //row
                if(gameBoardIds2D[i][j] == id){
                    index2DvarCol = i;
                    index2DvarRow = j;
                    return;
                }
            }
        }
    }

    public void endGame(String winner){
        isEndOfGame = true;
        Toast.makeText(getApplicationContext(),winner + " Wins!!!",Toast.LENGTH_SHORT).show();
    }

    public void reset(){
        isEndOfGame = false;
        numberOfPiecesPlaced = 0;
        for(int i = 0; i < gameBoardIds2D.length; i++){ // col
            for(int j = 0; j < gameBoardIds2D[i].length; j++){ //row
                gameBoardImages[i][j] = R.drawable.white_square;
                ((ImageButton) findViewById(gameBoardIds2D[i][j])).setImageResource(R.drawable.white_square);
            }
        }
        // reset the active game piece to x
        if(currentGamePiece == R.drawable.letter_o) {
            currentGamePiece = R.drawable.letter_x;
            ((ImageButton) findViewById(R.id.btn_x)).setBackgroundResource(R.drawable.grey_button_black_border);
            ((ImageButton) findViewById(R.id.btn_o)).setBackgroundResource(R.drawable.grey_button_no_border);
            ((TextView) findViewById(R.id.using_arrow_left)).setText("\u25C0");
            ((TextView) findViewById(R.id.using_arrow_right)).setText("");
        }
        // reset the active player to 0
        if(currentPlayer == 1) swapPlayer();

    }

}

/*
    0   1   2   3   4   5
0   00  10  20  30  40  50
1   01  11  21  31  41  51
2   02  12  22  32  42  52
3   03  13  23  33  43  53
4   04  14  24  34  44  54
5   05  15  25  35  45  55

    0   1   2   3   4   5
0   00    10   [20]  [30]  [40]  [50]
1   01    11    21   [31]  [41]  [51]
2  [02]   12    22    32   [42]  [52]
3  [03]  [13]   23    33    43   [53]
4  [04]  [14]  [24]   34    44    54
5  [05]  [15]  [25]  [35]   45    55

    if(!(( (col <= 0 && row >= 2) || (col <= 1 && row >= 3) || (col <= 2 && row >= 4) || (col <= 3 && row >= 5) ) || ( (col >= 2 && row <= 0) || (col >= 3 && row <= 1) || (col >= 4 && row <= 2) || (col >= 5 && row <= 3) )

    if( (col >= 2 && row <= 0) || (col >= 3 && row <= 1) || (col >= 4 && row <= 2) || (col >= 5 && row <= 3) )

    ===================================

    if ( col <= 1 && row >= 3 || col == 2 && row >= 4 || col == 3 && row == 5 || col == 0 && row == 2 || col >= 4 && row <= 2 || col == 3 && row <= 1 || col == 2 && row == 0 || col == 5 && row == 3)

    if ( col >= 4 && row <= 2 || col == 3 && row <= 1 || col == 2 && row == 0 || col == 5 && row == 3

====================================

    0      1     2     3     4     5
0   [00]  [10]  [20]  [30]   40    50
1   [01]  [11]  [21]   31    41    51
2   [02]  [12]   22    32    42   [52]
3   [03]   13    23    33   [43]  [53]
4   04     14    24   [34]  [44]  [54]
5   05     15   [25]  [35]  [45]  [55]

    if ( col <= 1 && row <= 2 || col == 2 && row <= 1 || col == 0 && row == 3 || col == 3 && row == 0 || col >= 4 && row >= 3 || col == 3 && row >= 4 || col == 2 && row == 5 || col == 5 && row == 2 )

    if ( col >= 4 && row >= 3 || col == 3 && row >= 4 || col == 2 && row == 5 || col == 5 && row == 2 )

*/