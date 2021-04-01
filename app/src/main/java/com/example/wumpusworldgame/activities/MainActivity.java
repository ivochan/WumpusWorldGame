package com.example.wumpusworldgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.wumpusworldgame.R;

/** class MainActivity
 * questa classe rappresenta la finestra principale dell'applicazione,
 * quella che si apre al suo avvio.
 * Deve estendere la classe AppCompatActivity proprio per essere implementata come Activity.
 */
public class MainActivity extends AppCompatActivity {
    //##### attributi di classe #####
    //riproduttore audio
    MediaPlayer mp;
    //pulsante modalita' eroe
    Button button_hero;
    //pulsante modalita' wumpus
    Button button_wumpus;
    /* le Intent che servono per passare da un'Activity all'altra
     * devono essere create sulla classe che realizza l'activity corrente
     * e ricevere come ulteriore paramentro l'activity che devono eseguire
     */
    //intent modalita' eroe
    Intent hero_game;
    //intent modalita' wumpus
    Intent wumpus_game;

    //##### struttura dell'Activity #####

    /** metodo onCreate()
     * questo metodo viene invocato alla creazione
     * dell'Activity corrente
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //si invoca il metodo della classe antenata
        super.onCreate(savedInstanceState);
        /* impostazione del layout
         * la struttura grafica scelta per l'Activity e' specificata nel file
         * activity_main.xml nella directory res/layout
         * Gli ID delle risorse sono conservati in una classe Java denominata R,
         * Si puo' accedere ad una risorsa:
         * - tramite R.tipo_risorsa.nome_risorsa, in Java;
         * - con @tipo_risorsa/nome_risorsa, in XML;
         */
        setContentView(R.layout.activity_main);
        //##### inizializzazioni #####

        //riproduttore audio
        mp = MediaPlayer.create(MainActivity.this,R.raw.fato_shadow_main_menu);
        //pulsante modalita' eroe
        button_hero = findViewById(R.id.button_hero);
        //pulsante modalita' wumpus
        button_wumpus = findViewById(R.id.button_wumpus);
        //dichiarazione dell'activity che realizza la modalita' di gioco dell'avventuriero
        hero_game = new Intent(MainActivity.this, HeroSide.class);
        //dichiarazione dell'activity che realizza la modalita' di gioco del wumpus
        wumpus_game = new Intent(MainActivity.this, WumpusSide.class);

        //##### azioni della Activity #####

        //esecuzione clip audio
        mp.start();
        //verifica pressione del pulsante eroe
        button_hero.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {
                //si esegue l'activity HeroSide
                startActivity(hero_game);
            }//onClick
        });
        //verifica pressione pulsante wumpus
        button_wumpus.setOnClickListener(new View.OnClickListener(){
            //dichiarazione dell'activity che realizza la modalita' di gioco dell'avventuriero
            @Override
            public void onClick(View view) {
                //si esegue l'activity WumpusSide
                startActivity(wumpus_game);
            }//onClick
        });
    }//onCreate()

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

}//end MainActivity