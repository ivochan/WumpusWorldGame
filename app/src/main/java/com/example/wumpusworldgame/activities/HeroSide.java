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
    //costante che indica la modalita' di gioco Hero Side
    private final int game_mode = 1;
    //riproduttore audio
    private MediaPlayer mp;
    //matrice di gioco
    private GameMap gm;
    //matrice di esplorazione
    private GameMap em;
    //per la matrice di esplorazione
    private GridView list;
    //dati da mostrare nella matrice
    private ArrayList<String> data = new ArrayList<>();
    //oggetto toast
    private Toast loading_toast;
    //layout del toast
    private View loading_layout;
    //questo metodo viene invocato alla creazione dell'Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si specifica il file che descrive il layout
        setContentView(R.layout.activity_hero_side);
        //scelta della clip audio
        mp = MediaPlayer.create(HeroSide.this,R.raw.the_good_fight);
        //##### schermata di caricamento #####
        LayoutInflater inflater = getLayoutInflater();
        //creazione del layout
        loading_layout = inflater.inflate(R.layout.loading_custom_toast,
                (ViewGroup)findViewById(R.id.loading_toast_container));
        //visualizzazione del toast
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
        //##### visualizzazione della matrice di esplorazione #####
        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                data.add(em.getMapCell(i,j).getCellStatusToString());
            }//for colonne
        }//for righe
        //si crea l'adapter per il gridlayout della matrice di esplorazione
        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        //si visualizza la matrice di esplorazione
        list = (GridView) findViewById(R.id.grid_view);
        list.setAdapter(adapter);
    }//onCreate

    //##### metodi per il riproduttore audio #####

    /** metodo onPause(): void
     * questo metodo mette in pausa l'esecuzione della clip audio
     * alla chiusura dell'app.
     */
    @Override
    protected void onPause() {
        //metodo della classe antenata
        super.onPause();
        //si ferma la clip audio quando l'app viene sospesa
        mp.release();
    }//onPause()

    /** metodo onResume(): void
     * questo metodo riavvia l'esecuzione della clip audio
     * quando viene mandata nuovamente in esecuzione
     * l'activity corrente
     */
    @Override
    protected void onResume() {
        //si controlla se si sta riproducendo la clip audio
        if(mp != null && !mp.isPlaying()){
            //se non si sta riproducendo allora viene avviata da capo
            mp.start();
        }
        //se si stava riproducendo ma e' stata messa in pausa allora viene ripresa
        super.onResume();
    }//onResume()

    //##### metodi per la gestione del menu #####

    /** metodo onCreateOptionsMenu(Menu): boolean
     * questo metodo serve per visualizzare il menu
     * nella activity corrente
     * @param menu: Menu, oggetto che costituisce il menu
     * @return true: boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }//onCreateOptionsMenu(Menu)

    /** metodo onOptionsItemSelected(MenuItem): boolean
     * questo metodo si occupa di gestire le azioni che
     * devono essere svolte quando si seleziona una delle
     * voci del menu
     * @param item: MenuItem, voce del menu;
     * @return true: boolean, per qualsiasi voce del menu che
     *                        e' stata gestita, altrimenti
     *                        super.onOptionsItemSelected(item).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //variabile ausiliaria
        Intent intent;
        //id della voce del menu che e' stata selezionata
        int id=item.getItemId();
        //switch case sulle varie voci del menu
        switch(id){
            case R.id.Menu1:
                //intent che riporta alla mainActivity
                intent = new Intent(this, MenuOptions.newGame());
                //ritorno alla schermata iniziale
                startActivity(intent);
                //parametro da restituire dopo aver svolto le azioni richieste
                return true;
            case R.id.Menu2:
                //risoluzione automatica della partita
                MenuOptions.solveGame();
                break;
            case R.id.Menu3:
                //intent che riporta alla mainActivity
                intent = new Intent(this, MenuOptions.gameInfo(game_mode));
                //si apre l'activity delle informazioni di gioco
                startActivity(intent);
                //parametro da restituire dopo aver svolto le azioni richieste
                return true;
            case R.id.Menu4:
                //elenco dei punteggi
                MenuOptions.viewScore();
                break;
            case R.id.Menu5:
                //impostazioni
                MenuOptions.changeSettings();
                break;
            default:
                //caso di default
                return super.onOptionsItemSelected(item);
        }//end switch
        return false;
    }//onOptionsItemSelected(MenuItem)

    //##### altri metodi #####

    /** metodo showLoadingToast(): void
     * questo metodo visualizza la schermata di caricamento
     * prima dell'avvio della sessione di gioco
     * utilizzando il layout definito per il Toast
     */
    protected void showLoadingToast(){
        loading_toast = new Toast(getApplicationContext());
        loading_toast.setGravity(Gravity.CENTER, 0, 0);
        loading_toast.setDuration(Toast.LENGTH_SHORT);
        loading_toast.setView(loading_layout);
        loading_toast.show();
    }//showLoadingToast()

    /** metodo onBackPressed(): void
     * questo metodo implementala navigazione all'indietro
     * permettendo di ritornare dall'activity corrente a quella
     * appena precedente, utilizzando il tasto di navigazione BACK.
     * Le activity vengono conservate in memoria, quando non sono
     * piu' in primo piano, secondo un ordine a Stack.
     */
    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }//onBackPressed()

}//end HeroSide
