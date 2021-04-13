package com.example.wumpusworldgame.activities;
//serie di import
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.services.TypeWriter;

/** class MainActivity
 * questa classe rappresenta la finestra principale dell'applicazione,
 * quella che si apre al suo avvio.
 * Deve estendere la classe AppCompatActivity proprio per essere implementata come Activity.
 */
public class MainActivity extends AppCompatActivity {

    //##### attributi di classe #####
    //testo animato
    TypeWriter typeWriter;
    final static int delay = 25;
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

        //testo animato
        typeWriter = findViewById(R.id.storia);
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

        //esecuzione del testo animato
        animatedText(typeWriter);
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

    //##### metodi per la gestione della clip audio #####

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

    /** metodo onResume():void
     * questo metodo riprende l'esecuzione della clip
     * audio se e' gia' stato istanziato il riproduttore
     * musicale, se cosi' non e', allora lo crea.
     */
    @Override
    protected void onResume() {
        if(mp != null && !mp.isPlaying())
            mp.start();
        super.onResume();
    }//onResume()

    //##### altri metodi #####

    /** metodo animatedText(TypeWriter): void
     * questo metodo realizza l'animazione del
     * testo che fornisce l'introduzione al gioco
     * @param tw: TypeWriter
     */
    protected void animatedText(TypeWriter tw){
        //corpo del testo
        tw.setText("");
        //intervallo di tempo tra un carattere ed il successivo
        tw.setCharacterDelay(delay);
        //testo da mostrare con l'animazione
        tw.animateText(""+getResources().getString(R.string.game_intro));
    }//animatedText(TypeWriter)

}//end MainActivity