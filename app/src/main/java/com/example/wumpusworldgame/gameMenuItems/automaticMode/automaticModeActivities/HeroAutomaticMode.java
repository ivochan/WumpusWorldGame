package com.example.wumpusworldgame.gameMenuItems.automaticMode.automaticModeActivities;
//serie di import
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameMenuItems.automaticMode.AutomaticModeGridViewAdapter;
import com.example.wumpusworldgame.services.Utility;
import java.util.ArrayList;
import java.util.LinkedList;
import game.automatic_player.AutomaticPlayer;
import game.session.configuration.Starter;
import game.structure.cell.Cell;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/** class HeroAutomaticMode
 * giocatore automatico
 */
public class HeroAutomaticMode extends AppCompatActivity {
    //##### attributi di classe #####
    //file delle preferenze
    private SharedPreferences sharedPreferences;
    //nome del giocatore
    private String player_name;

    //##### dati di gioco #####

    private static boolean solution_request = false;

    //matrice di gioco
    private GameMap gameMap;
    //matrice di esplorazione
    private GameMap expMap;

    //##### componenti per la grafica #####

    //grid per la matrice di esplorazione
    private GridView grid;
    //adapter per la matrice di esplorazione
    private AutomaticModeGridViewAdapter adapter;
    //dati da mostrare nella matrice di esplorazione
    private ArrayList<String> data;
    //dati della matrice di gioco
    private ArrayList<String> game_data;

    //##### campi di testo #####
    //intro
    private TextView text_message;
    //messaggio
    private TextView game_message;
    //lista delle celle visitate
    private TextView run_box;
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

        //dati della matrice di esplorazione
        data = new ArrayList();
        //dati della matrice di gioco
        game_data =  new ArrayList();

        //##### dati delle preferenze #####

        //si preleva il file di salvataggio delle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //si identifica la preference relativa al nome del giocatore corrente
        player_name = sharedPreferences.getString("prefUsername", "");
        //si identifica il campo del testo del titolo
        text_message = findViewById(R.id.textMessage);
        //si identifica il campo di testo nel layout
        game_message = findViewById(R.id.message_box);
        //si identifica il campo di testo che conterra' la run list
        run_box = findViewById(R.id.run_box);
        //messaggio da visualizzare come introduzione
        intro_message = this.getText(R.string.solution) + " " + player_name + "!";

        //##### esecuzione delle operazioni #####

        //schermata di caricamento per il calcolo della soluzione
        Utility.showSolvingScreen(this, this.getLayoutInflater());

        //messaggio sopra la grid
        game_message.setText(intro_message);
        //lista delle celle visitate
        run_box.setText("");

        //##### prelievo dei dati di gioco #####

        //matrice di gioco
        gameMap =(GameMap)getIntent().getSerializableExtra("game_map");

        //matrice di esplorazione
        expMap = (GameMap)getIntent().getSerializableExtra("exp_map");

        //indice riga del pg
        int i_pg_pos = getIntent().getIntExtra("i_pg_pos",-1);
        //indice colonna del pg
        int j_pg_pos = getIntent().getIntExtra("j_pg_pos",-1);
        //si inseriscono gli indici in un vettore posizione
        int [] pg_pos = { i_pg_pos,j_pg_pos};
        //si modifica la posizione del pg ripristinandola al suo valore iniziale
        PlayableCharacter.setPGposition(pg_pos);

        //##### risoluzione #####

        //controllo
        if(!Starter.getGameStart()){
            //la partita nella classe di gioco e' terminata
            text_message.setText(this.getText(R.string.game_over));
            game_message.setText(this.getText(R.string.nothing_to_do));
        }
        else {
            solution_request = true;
            //path
            LinkedList<Cell> run_path = new LinkedList<>();
            //la partita nella classe di gioco e' in corso
            text_message.setText(this.getText(R.string.game_in_progress));
            //game_message.setText("Mappa di gioco:\n"+gameMap);
            //si istanzia il giocatore automatico
            int status = AutomaticPlayer.solveGame(gameMap, expMap,run_path);
            //percorso compiuto
            String path = AutomaticPlayer.runPathToString(run_path);
            //visualizzazione del percorso
            run_box.setText(this.getText(R.string.run_path_title)+":\n\n"+path);
            //visualizzazione della mappa per debug
            //game_message.setText(""+AutomaticPlayer.printStatusMessage(status)+"\nMappa di esplorazione:\n"+expMap);
            //si disabilita la possibilita' di continuare la partita una volta
            //che l'utente ritorna alla schermata della sessione di gioco
            Starter.setGameStart(false);
        }

        //###### visualizzazione  ######

        //si iterano le celle della matrice
        for (int i = 0; i < gameMap.getRows(); i++) {
            for (int j = 0; j < gameMap.getColumns(); j++) {
                //si aggiunge la cella corrente alla List che rappresenta la matrice di gioco
                game_data.add(gameMap.getMapCell(i, j).statusToString());
                //si aggiunge la cella corrente allaList che rappresenra la mappa di esplorazione
                data.add(expMap.getMapCell(i, j).statusToString());
            }//for colonne
        }//for righe

        //si istanzia l'adapter
        adapter = new AutomaticModeGridViewAdapter(this,data,game_data);
        //si visualizza la matrice di esplorazione
        grid = findViewById(R.id.grid_view);
        //oggetto che permette di visualizzare i dati
        grid.setAdapter(adapter);

    }//onCreate(Bundle)

    public static boolean getSolutionRequest(){
        return solution_request;
    }

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
