package com.example.wumpusworldgame.gameMenuItems.automaticMode;
//serie di import
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameSession.AutomaticPlayer;
import com.example.wumpusworldgame.gameSession.GridViewCustomAdapter;
import java.util.ArrayList;

import game.structure.cell.Cell;
import game.structure.map.GameMap;

/**
 *
 */
public class HeroAutomaticMode extends AppCompatActivity {
    //##### attributi di classe #####
    //file delle preferenze
    private SharedPreferences sharedPreferences;
    //nome del giocatore
    private String player_name;

    //##### dati di gioco #####
    //per la matrice di esplorazione
    private static GridView grid;
    //adapter per la matrice di esplorazione
    private GridViewCustomAdapter adapter;
    //dati della matrice di esplorazione
    private ArrayList<String> solved_game_data;

    //##### campi di testo #####
    //messaggi di gioco
    private static TextView game_message;
    //numero di colpi
    private TextView shots;
    //punteggio ottenuto
    private static TextView score;
    //messaggio di calcolo della soluzione
    private String intro_message;

    /** metodo onCreate(Bunde): void
     * ACTIVITY CREATA
     * questo metodo viene invocato alla creazione dell'Activity,
     * definendo le operazioni principali che deve svolgere ed
     * il layout che la caratterizza.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);

        //si mostra la schermata di gioco
        setContentView(R.layout.activity_hero_automatic_mode);
        //##### inizializzazioni #####

        //si preleva il file di salvataggio delle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //si identifica la preference relativa al nome del giocatore corrente
        player_name = sharedPreferences.getString("prefUsername", "");
        //si identifica il campo di testo nel layout
        game_message = findViewById(R.id.message_box);
        //messaggio da visualizzare come introduzione
        intro_message = "Ecco la tua soluzione" + " " + player_name + "!";

        //schermata di caricamento per il calcolo della soluzione
        AutomaticPlayer.showLoadingScreen(this, this.getLayoutInflater());

        game_message.setText(intro_message);

        GameMap gameMap = new GameMap();

        Bundle bundle = getIntent().getExtras();
        try {
            gameMap = (GameMap)bundle.get("game_matrix");
        }catch(Exception e){
            Log.i(" Error at bundle " , e.toString());
        }

        int r = gameMap.getRows();

        String riga = new String(""+r);

        game_message.setText(riga);

/*
        Bundle b = new Bundle();

        GameMap map = new GameMap();

        map = (GameMap) b.getSerializable("map");
        */


        //adapter = new GridViewCustomAdapter(this,solved_game_data,solved_game_data);

        //si visualizza la matrice di esplorazione
        grid = findViewById(R.id.grid_view);
        //oggetto che permette di visualizzare i dati
        //grid.setAdapter(adapter);

    }//onCreate(Bundle)

    //##### metodi per la gestione dell'activity #####

    /** metodo onStart(): void
     * ACTIVITY VISIBILE
     * questo metodo si occupa di attivare le funzionalita'
     * ed i servizi che devono essere mostrati all'utente
     */
    @Override
    public void onStart() {
        //si invoca il metodo della super classe
        super.onStart();
    }//onStart()

    /** metodo onResume():void
     * ACTIVITY RICEVE INTERAZIONE
     */
    @Override
    protected void onResume() {
        //si invoca il metodo della super classe
        super.onResume();
    }//onResume()

    /** metodo onPause(): void
     * metodo opposto di onResume()
     * ACTIVITY CESSA INTERAZIONE
     * questo metodo viene invocato per notificare la cessata
     * interruzione dell'utente con l'activity corrente
     */
    @Override
    protected void onPause() {
        //si invoca il metodo della super classe
        super.onPause();
        //si ferma la clip audio quando l'app viene sospesa
        //mp.pause();
    }//onPause()

    /** metodo onStop(): void
     * metodo opposto di onStart()
     * ACTIVITY NON VISIBILE
     */
    @Override
    public void onStop() {
        //si invoca il metodo della super classe
        super.onStop();
    }//onStop()

    /** metodo onDestroy(): void
     * metodo opposto di onCreate()
     * ACTIVITY DISTRUTTA
     */
    @Override
    public void onDestroy() {
        //si invoca il metodo della super classe
        super.onDestroy();
        //si rilascia la risorsa del mediaplayer
        //mp.release();
    }//onDestroy()

    /** metodo onRestart(): void
     * l'utente ritorna all'activity
     * viene invocato prima di onCreate()
     */
    @Override
    public void onRestart() {
        //si invoca il metodo della super classe
        super.onRestart();
    }//onRestart()

    /** metodo onBackPressed(): void
     * questo metodo implementala navigazione all'indietro
     * permettendo di ritornare dall'activity corrente a quella
     * appena precedente, utilizzando il tasto di navigazione BACK.
     * Le activity vengono conservate in memoria, quando non sono
     * piu' in primo piano, secondo un ordine a Stack.
     */
    @Override
    public void onBackPressed() {
        //si invoca il metodo della super classe
        super.onBackPressed();
    }//onBackPressed()

}//end HeroAutomaticMode
