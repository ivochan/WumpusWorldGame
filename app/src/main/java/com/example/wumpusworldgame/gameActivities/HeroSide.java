package com.example.wumpusworldgame.gameActivities;
//serie di import
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameMenuItems.AutomaticGameSolving;
import com.example.wumpusworldgame.gameSession.GameController;
import com.example.wumpusworldgame.gameSession.GridViewCustomAdapter;
import com.example.wumpusworldgame.gameMenuItems.gameModeTutorials.HeroModeTutorial;
import com.example.wumpusworldgame.services.Utility;
import java.util.LinkedList;
import game.session.controller.Direction;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class HeroSide
 * classe che implementa la modalita' di gioco in cui il personaggio
 * giocabile e' l'Avventuriero.
 */
public class HeroSide extends AppCompatActivity {
    //##### attributi di classe #####

    //intent utilizzato per riseguire il metodo onCreate() di questa classe
    private Intent starterIntent;
    //file delle preferenze
    private SharedPreferences sharedPreferences;
    //nome del giocatore
    private String player_name;
    //riproduttore audio
    private MediaPlayer mp;
    //matrice di gioco
    private GameMap gm;
    //matrice di esplorazione
    private GameMap em;
    //per la matrice di esplorazione
    private static GridView grid;
    //adapter per la matrice di esplorazione
    private GridViewCustomAdapter adapter;
    //dati da mostrare nella matrice di esplorazione
    private LinkedList<String> data;
    //dati della matrice di gioco
    private LinkedList<String> game_data;
    //##### campi di testo #####
    //messaggi di gioco
    private TextView game_message;
    //numero di colpi
    private TextView shots;
    //punteggio ottenuto
    private TextView score;
    //messaggio di inizio partita
    private String intro_message;
    //messaggio iniziale dei sensori
    private String sensor_info;
    //##### pulsanti del controller di gioco #####
    private ImageButton hit_button;
    private ImageButton up_button;
    private ImageButton down_button;
    private ImageButton left_button;
    private ImageButton right_button;

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
        setContentView(R.layout.activity_hero_side);

        //##### inizializzazioni #####

        //si memorizza l'intent di questa activity
        starterIntent = getIntent();

        //si preleva il file di salvataggio delle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //si identifica la preference relativa al nome del giocatore corrente
        player_name = sharedPreferences.getString("prefUsername", "");
        //si identifica il campo di testo nel layout
        game_message = findViewById(R.id.message_box);
        //si compone il messaggio di benvenuto
        intro_message = getResources().getString(R.string.game_message_intro) + " " + player_name + "!";

        //dati da mostrare nella matrice di esplorazione
        data = new LinkedList<>();
        //dati della matrice di gioco
        game_data = new LinkedList<>();

        //##### inizializzazioni dei pulsanti #####
        hit_button = findViewById(R.id.imageButtonHIT);
        up_button = findViewById(R.id.imageButtonUP);
        down_button = findViewById(R.id.imageButtonDOWN);
        left_button = findViewById(R.id.imageButtonLEFT);
        right_button = findViewById(R.id.imageButtonRIGHT);

        //identificazione del campo di testo che visualizza il numero di colpi rimasti
        shots = findViewById(R.id.shot_value);
        //identificazione del campo di testo che visualizza il punteggio
        score = findViewById(R.id.score_value);

        //scelta della clip audio
        mp = MediaPlayer.create(HeroSide.this, R.raw.the_good_fight);

        //##### schermata di caricamento #####
        Utility.showLoadingScreen(this, getLayoutInflater());
        //##### schermata di gioco #####

        //creazione della matrice di gioco
        gm = new GameMap();
        //creazione della matrice di esplorazione
        em = new GameMap();
        //riempimento delle matrici
        MapConfiguration.init(gm, em);
        //dimensioni della matrice di gioco, analoghe a quelle della matrice di esplorazione
        int rows = gm.getRows();
        int columns = gm.getColumns();

        //##### salvataggio della matrice di gioco #####

        //si iterano le celle della matrice
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //si aggiunge la cella corrente alla LinkedList
                game_data.add(gm.getMapCell(i, j).statusToString());
            }//for colonne
        }//for righe

        //##### visualizzazione della matrice di esplorazione #####

        //si iterano le celle della matrice
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //si aggiunge la cella corrente alla LinkedList
                data.add(em.getMapCell(i, j).statusToString());
            }//for colonne
        }//for righe

        //si crea l'adapter per il gridlayout della matrice di esplorazione
        //GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        adapter = new GridViewCustomAdapter(this, data, game_data);
        //si visualizza la matrice di esplorazione
        grid = findViewById(R.id.grid_view);
        //oggetto che permette di visualizzare i dati
        grid.setAdapter(adapter);

        //configurazioni da fare all'avvio della partita
        sensor_info = GameController.linkStart(this, gm);
        //si concatena questa stringa a quella di inizio partita
        intro_message += "\n" + sensor_info;
        //si visualizza la frase di inizio partita
        game_message.setText(intro_message);

        //verifica dell'esecuzione della traccia audio
        Utility.musicPlaying(mp, this);

        //##### gestione dei pulsanti #####

        //pulsante HIT
        hit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si tenta il colpo
                GameController.gamePadHit(game_message);
            }//onClick(View)
        });//setOnClickListener(View.OnClickListener())

        //pulsante UP
        up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si muove il personaggio verso sopra
                GameController.gamePadMove(Direction.UP,gm,em,game_message,shots,adapter,grid);
            }//onClick(View)
        });//setOnClickListener(View.OnClickListener())

        //pulsante DOWN
        down_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si muove il personaggio verso sotto
                GameController.gamePadMove(Direction.DOWN,gm,em,game_message,shots,adapter,grid);
            }//onClick(View)
        });//setOnClickListener(View.OnClickListener())

        //pulsante LEFT
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si muove il personaggio verso sinistra
                GameController.gamePadMove(Direction.LEFT,gm,em,game_message,shots,adapter,grid);
            }//onClick(View)
        });//setOnClickListener(View.OnClickListener())

        //pulsante RIGHT
        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si muove il personaggio verso destra
                GameController.gamePadMove(Direction.RIGHT,gm,em,game_message,shots,adapter,grid);
            }//onClick(View)
        });//setOnClickListener(View.OnClickListener())

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

    /** metodo onCreateOptionsMenu(Menu): boolean
     * questo metodo serve per visualizzare il menu
     * nella activity corrente
     * @param menu: Menu, oggetto che costituisce il menu
     * @return true: boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //si preleva l'oggetto inflater associato al menu
        MenuInflater inflater = getMenuInflater();
        //si definisce il layout del menu
        inflater.inflate(R.menu.game_menu,menu);
        //si visualizza il menu nel layout (tre puntini in alto a destra)
        return true;
    }//onCreateOptionsMenu(Menu)

    /** metodo onOptionsItemSelected(MenuItem): boolean
     * questo metodo si occupa di gestire le azioni che
     * devono essere svolte quando si seleziona una delle
     * voci del menu.
     * @param item: MenuItem, voce del menu;
     * @return true: boolean, per qualsiasi voce del menu che
     *                        e' stata gestita, altrimenti
     *                        super.onOptionsItemSelected(item).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //variabile ausiliaria per la nuova activity
        Intent intent;
        //id della voce del menu che e' stata selezionata
        int itemId = item.getItemId();
        //switch case sulle varie voci del menu
        switch(itemId){
            //NUOVA PARTITA
            case R.id.item_new_game:
                //si chiude l'activity corrente
                this.finish();
                //si esegue un'altra istanza di questa activity, richiamando il metodo onCreate()
                startActivity(starterIntent);
                //si poteva usare recreate() ma ha l'animazione per la transizione
                return true;
            //RISOLVI
            case R.id.item_solve_game:
                //creazione dell'intent per la risoluzione automatica della partita
                intent = new Intent(this, AutomaticGameSolving.class);
                //si avvia l'istanza dell'activity corrispondente
                startActivity(intent);
                //si interrompe il metodo corrente con successo
                return true;
            //TUTORIAL
            case R.id.item_game_tutorial:
                //creazione dell'intent
                intent = new Intent(this, HeroModeTutorial.class);
                //si avvia l'istanza dell'activity corrispondente
                startActivity(intent);
                //si interrompe il metodo corrente con successo
                return true;
            default:
                //caso di default
                return super.onOptionsItemSelected(item);
        }//end switch
    }//onOptionsItemSelected(MenuItem)


}//end HeroSide
