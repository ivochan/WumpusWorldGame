package com.example.wumpusworldgame.gameActivities;
//serie di import
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.services.Utility;
import java.util.ArrayList;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class HeroSide
 * classe che implementa la modalita' di gioco in cui il personaggio
 * giocabile e' l'Avventuriero.
 */
public class HeroSide extends AppCompatActivity {
    //##### attributi di classe #####

    //id della modalita' di gioco
    public final static int HERO = 0;
    //intent utilizzato per riseguire il metodo onCreate() di questa classe
    private Intent starterIntent;
    //file delle preferenze
    private SharedPreferences sharedPreferences;
    //nome del giocatore
    String player_name;
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
    //##### pulsanti del controller di gioco #####
    private Button hit_button;
    private Button up_button;
    private Button down_button;
    private Button left_button;
    private Button right_button;

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
        setContentView(R.layout.hero_side_activity);

        //si memorizza l'intent di questa activity
        starterIntent = getIntent();

        //si preleva il file di salvataggio delle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //si identifica la preference relativa al nome del giocatore corrente
        player_name = sharedPreferences.getString("prefUsername","");
        //si identifica il campo di testo nel layout
        TextView game_message = findViewById(R.id.message_box);
        //si compone il messaggio di benvenuto
        String intro_message = getResources().getString(R.string.game_message_intro)+" "+player_name+"!";
        //si visualizza la frase di inizio partita
        game_message.setText(intro_message);

        //scelta della clip audio
        mp = MediaPlayer.create(HeroSide.this,R.raw.the_good_fight);

        //##### schermata di caricamento #####
        Utility.showLoadingScreen(this, getLayoutInflater());

        //##### schermata di gioco #####

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
        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, game_data);
        //GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        //si visualizza la matrice di esplorazione
        list = (GridView) findViewById(R.id.grid_view);
        //oggetto che permette di visualizzare i dati
        list.setAdapter(adapter);

        //verifica dell'esecuzione della traccia audio
        Utility.musicPlaying(mp, this);

        //##### gestione dei pulsanti #####
/*
        //verifica pressione del pulsante HIT
        hit_button.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {


            }//onClick(View)
        });//setOnClickListener(View.onClickListener()

        //verifica pressione del pulsante DOWN
        down_button.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {

            }//onClick(View)
        });//setOnClickListener(View.onClickListener()

        //verifica pressione del pulsante UP
        up_button.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {

            }//onClick(View)
        });//setOnClickListener(View.onClickListener()

        //verifica pressione del pulsante LEFT
        left_button.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {

            }//onClick(View)
        });//setOnClickListener(View.onClickListener()

        //verifica pressione del pulsante RIGHT
        right_button.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {

            }//onClick(View)
        });//setOnClickListener(View.onClickListener()

*/



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
                //TODO
                break;
            //TUTORIAL
            case R.id.item_game_tutorial:
                //creazione dell'intent
                //TODO
                break;
            //PUNTEGGI
            case R.id.item_score:
                //TODO gestire il ritorno alla activity corrente
                //creazione dell'intent
                //intent = new Intent(this, ScoreActivity.class);
                //avvio dell'activity corrispondente
                //startActivity(intent);
                //viene aperta l'activity
                //return true;
                break;
            default:
                //caso di default
                return super.onOptionsItemSelected(item);
        }//end switch
        return false;
    }//onOptionsItemSelected(MenuItem)

}//end HeroSide
