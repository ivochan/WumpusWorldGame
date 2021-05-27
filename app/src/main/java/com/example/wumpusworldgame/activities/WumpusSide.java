package com.example.wumpusworldgame.activities;
//serie di import
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.adapters.GridViewCustomAdapter;
import com.example.wumpusworldgame.services.Utility;
import java.util.ArrayList;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class WumpusSide
 * classe che implementa la modalita' di gioco in cui
 * il personaggio giocabile e' il Wumpus
 */
public class WumpusSide extends AppCompatActivity {
    //##### attributi di classe #####

    //id della modalita' di gioco
    public final static int WUMPUS = 1;

    //riproduttore audio
    private MediaPlayer mp;
    //matrice di gioco
    private GameMap gm;
    //matrice di esplorazione
    private GameMap em;
    //per la matrice di esplorazione
    private GridView list;
    //dati da mostrare nella matrice di esplorazione
    private ArrayList<String> data = new ArrayList<>();
    //dati della matrice di gioco
    private ArrayList<String> game_data = new ArrayList<String>();

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
        setContentView(R.layout.wumpus_side_activity);

        //scelta della clip audio
        mp = MediaPlayer.create(WumpusSide.this,R.raw.the_good_fight);

        //##### schermata di caricamento #####

        //si preleva il puntatore al layout del contesto corrente, dell'activity attuale
        LayoutInflater inflater = getLayoutInflater();
        //si definisce il layout del toast che implementa la schermata di caricamento
        //View loading_layout = inflater.inflate(R.layout.loading_custom_toast,
         //       (ViewGroup)findViewById(R.id.loading_toast_container));
        //si visualizza la schermata di caricamento
       // Utility.showLoadingScreen(getApplicationContext(),loading_layout);

        //##### schermata di gioco #####

        //esecuzione clip audio
        mp.start();
        //creazione della matrice di gioco
        gm = new GameMap();
        //creazione della matrice di esplorazione
        em = new GameMap();
        //riempimento delle matrici
        MapConfiguration.init(gm,em);
        //dimensioni della matrice di gioco, analoghe a quelle della matrice di esplorazione
        int r = gm.getRows();
        int c = gm.getColumns();

        //##### salvataggio della matrice di gioco #####

        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                //si aggiunge la cella corrente all'arraylist
                game_data.add(gm.getMapCell(i,j).statusToString());
            }//for colonne
        }//for righe

        //##### visualizzazione della matrice di esplorazione #####

        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                //si aggiunge la cella corrente all'arraylist
                data.add(em.getMapCell(i,j).statusToString());
            }//for colonne
        }//for righe

        //si crea l'adapter per il gridlayout della matrice di esplorazione
        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        //si visualizza la matrice di esplorazione
        list = (GridView) findViewById(R.id.grid_view);
        list.setAdapter(adapter);

        //verifica dell'esecuzione della traccia audio
        Utility.musicPlaying(mp, this);

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
        mp.pause();
    }//onPause()

    /** metodo onStop(): void
     * metodo opposto di onStart()
     * ACTIVITY NON VISIBILE
     */
    @Override
    public void onStop(){
        //si invoca il metodo della super classe
        super.onStop();
    }//onStop()

    /** metodo onDestroy(): void
     * metodo opposto di onCreate()
     * ACTIVITY DISTRUTTA
     */
    @Override
    public void onDestroy(){
        //si invoca il metodo della super classe
        super.onDestroy();
        //si rilascia la risorsa del mediaplayer
        mp.release();
    }//onDestroy()

    /** metodo onRestart(): void
     * l'utente ritorna all'activity
     * viene invocato prima di onCreate()
     */
    @Override
    public void onRestart(){
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

    //##### metodi per la gestione del menu #####


}//end WumpusSide