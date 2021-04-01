package com.example.wumpusworldgame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/** class HeroSide
 * classe che implementa la modalita' di gioco in cui il personaggio
 * giocabile e' l'Avventuriero
 */
public class HeroSide extends AppCompatActivity {
    //questo metodo viene invocato alla creazione dell'Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si specifica il file che descrive il layout
        setContentView(R.layout.activity_hero_side);

    }//onCreate
}//end HeroActivity
