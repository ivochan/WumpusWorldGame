package com.example.wumpusworldgame.activities;
//serie di import
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.adapters.GridViewCustomAdapter;
import com.example.wumpusworldgame.services.MenuOptions;
import java.util.ArrayList;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class HeroSide
 * classe che implementa la modalita' di gioco in cui il personaggio
 * giocabile e' l'Avventuriero
 */
public class HeroSide extends AppCompatActivity {
    //##### attributi di classe #####
    private final int game_mode = 1;
    //riproduttore audio
    MediaPlayer mp;
    //matrice di gioco
    GameMap gm;
    //matrice di esplorazione
    GameMap em;
    //per la matrice di esplorazione
    GridView list;
    //dati da mostrare nella matrice di esplorazione
    ArrayList<String> data = new ArrayList<>();
    //dati della matrice di gioco
    ArrayList<String> game_data = new ArrayList<String>();
    //oggetto toast
    Toast loading_toast;
    //layout del toast
    View loading_layout;

    /** metodo onCreate(Bunde): void
     * ACTIVITY CREATA
     * questo metodo viene invocato alla creazione dell'Activity,
     * definendo le operazioni principali che deve svolgere ed
     * il layout che la caratterizza.
     * @param savedInstanceState
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);

        //si mostra la schermata di gioco
        setContentView(R.layout.activity_hero_side);

        //scelta della clip audio
        mp = MediaPlayer.create(HeroSide.this,R.raw.the_good_fight);

        //##### schermata di caricamento #####
        LayoutInflater inflater = getLayoutInflater();
        //creazione del layout
        loading_layout = inflater.inflate(R.layout.loading_custom_toast,
                (ViewGroup)findViewById(R.id.loading_toast_container));

        //creazione del toast
        showLoadingToast();

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
        //si avvia la clip audio
        mp.start();
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
     *
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
        inflater.inflate(R.menu.main_menu,menu);
        //si visualizza il menu nel layout (tre puntini in alto a destra)
        return true;
    }//onCreateOptionsMenu(Menu)

    /** metodo onOptionsItemSelected(MenuItem): boolean
     * questo metodo si occupa di gestire le azioni che
     * devono essere svolte quando si seleziona una delle
     * voci del menu
     * Quasi tutte le voci del menu verranno definite come
     * Alert Dialog.
     *
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
            case R.id.Menu1: //NUOVA PARTITA
                //creazione dell'intent
                intent = new Intent(this, MenuOptions.newGame());
                //avvio dell'activity corrispondente
                startActivity(intent);
                //ritorno alla schermata iniziale
                return true;
            case R.id.Menu2: //RISOLVI
                //creazione dell'intent per la risoluzione automatica della partita
                //TODO
                return true;
            case R.id.Menu3: //INFORMAZIONI
                gameInfo();
                return true;
            case R.id.Menu4:
                //comandi

                break;
            case R.id.Menu5:
                //punteggi

                break;
            case R.id.Menu6:
                //impostazioni

            default:
                //caso di default
                return super.onOptionsItemSelected(item);
        }//end switch
        return false;

    }//onOptionsItemSelected(MenuItem)

    //##### metodi per le voci del menu #####

    private void gameInfo() {
        //si specifica la modalita' di gioco
        GameInformation.setGameMode(game_mode);
        //si crea l'intent associato
        Intent intent = new Intent(this, GameInformation.class);
        //si apre la scheda delle informazioni del gioco
        startActivity(intent);
    }//gameInfo()

    //##### altri metodi #####

    /** metodo showLoadingToast(): void
     * questo metodo visualizza la schermata di caricamento
     * prima dell'avvio della sessione di gioco
     * utilizzando il layout definito per il Toast
     */
    private void showLoadingToast(){
        loading_toast = new Toast(getApplicationContext());
        loading_toast.setGravity(Gravity.CENTER, 0, 0);
        loading_toast.setDuration(Toast.LENGTH_SHORT);
        loading_toast.setView(loading_layout);
        loading_toast.show();
    }//showLoadingToast()

}//end HeroSide
