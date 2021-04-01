package com.example.wumpusworldgame.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wumpusworldgame.R;

/** class WumpusSide
 * classe che implementa la modalita' di gioco in cui
 * il personaggio giocabile e' il Wumpus
 */
public class WumpusSide extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si mostra la schermata di gioco
        setContentView(R.layout.activity_wumpus_side);
    }//onCreate()
}
