package com.example.wumpusworldgame.mainMenuItems;
//serie di import
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.wumpusworldgame.R;
/** class ScoreActivity
 *
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




        //##### inizializzazioni #####

        //si preleva il file di salvataggio delle preferenze dell'activity che contiene questo fragment
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //si identifica la preference relativa al nome del giocatore
        String name = sharedPrefs.getString("prefUsername",null);

        TextView tn = (TextView)findViewById(R.id.current_player_name);
        tn.setText(name);



    }
}