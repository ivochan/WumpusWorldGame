package com.example.wumpusworldgame;
//serie di import
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
/** class ScoreActivity
 *  questa classe mostrera' la classifica dei punteggi
 */
public class ScoreActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }
}//end ScoreActivity