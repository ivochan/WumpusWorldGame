package com.example.wumpusworldgame.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wumpusworldgame.R;

import org.w3c.dom.Text;

import game.structure.text.GameMessages;
import game.structure.text.GameTranslations;

/** class GameInformation
 * questa classe fornisce delle informazioni sul gioco
 */
public class GameInformation extends AppCompatActivity {
    //##### attributi di classe #####
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
        setContentView(R.layout.game_info_activity);
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

}//end GameInformation
