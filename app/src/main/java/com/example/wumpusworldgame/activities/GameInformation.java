package com.example.wumpusworldgame.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wumpusworldgame.R;

import game.structure.text.GameMessages;

/** class GameInformation
 * questa classe fornisce delle informazioni sul gioco
 */
public class GameInformation extends AppCompatActivity {
    //##### attributi di classe #####
    public static int game_mode;
    //riproduttore audio
    MediaPlayer mp;
    //questo metodo viene invocato alla creazione dell'Activity
    /** metodo onCreate()
     * questo metodo viene invocato alla creazione
     * dell'Activity corrente
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si specifica il file che descrive il layout
        setContentView(R.layout.game_info);
         //##### inizializzazioni #####

        //riproduttore audio
        mp = MediaPlayer.create(GameInformation.this,R.raw.menu_music);
        //campo di testo dei crediti
        TextView tcredits = (TextView)findViewById(R.id.textCredits);

        //##### azioni della Activity #####

        //esecuzione clip audio
        mp.start();
        //visualizzazione dei crediti
        tcredits.setText(GameMessages.credits);

    }//onCreate(Bundle)
    //##### altri metodi #####


    /** metodo onPause(): void
     * questo metodo blocca l'esecuzione della clip audio
     * alla chiusura dell'app.
     */
    @Override
    protected void onPause() {
        //metodo della classe antenata
        super.onPause();
        //si ferma la clip audio quando l'app viene sospesa
        mp.release();
    }//onPause()

    /**
     *
     */
    @Override
    protected void onResume() {
        if(mp != null && !mp.isPlaying())
            mp.start();
        super.onResume();
    }

    @Override
    public void onBackPressed(){
        Intent myIntent;
        if(game_mode==1) {
            myIntent = new Intent(this, HeroSide.class);
        }
        else {
            myIntent = new Intent(this, WumpusSide.class);
        }
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;

    }

    /**
     *
     * @param value_game_mode
     */
    public static void setGameMode(int value_game_mode){
        game_mode=value_game_mode;
    }

    /**
     *
     * @return
     */
    public static int  getGameMode(){
        return game_mode;
    }


}//end GameInformation
