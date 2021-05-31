package com.example.wumpusworldgame.mainMenuItems;
//serie di import
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wumpusworldgame.R;
/** class MainTutorialActivity
 * questa classe fornisce un breve tutorial introduttivo al gioco
 */
public class MainTutorialActivity extends AppCompatActivity {
    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tutorial);
    }
}