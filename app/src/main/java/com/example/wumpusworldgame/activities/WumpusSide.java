package com.example.wumpusworldgame.activities;
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
import java.util.ArrayList;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;
/** class WumpusSide
 * classe che implementa la modalita' di gioco in cui
 * il personaggio giocabile e' il Wumpus
 */
public class WumpusSide extends AppCompatActivity {
    //##### attributi di classe #####
    private final int game_mode = 0;
    //riproduttore audio
    MediaPlayer mp;
    //matrice di gioco
    GameMap gm;
    //matrice di esplorazione
    GameMap em;
    //per la matrice di esplorazione
    GridView list;
    //dati da mostrare nella matrice
    ArrayList<String> data = new ArrayList<>();
    //questo metodo viene invocato alla creazione dell'Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);

        //si mostra la schermata di gioco
        setContentView(R.layout.activity_wumpus_side);

        //scelta della clip audio
        mp = MediaPlayer.create(WumpusSide.this,R.raw.the_good_fight);

        //##### schermata di caricamento #####
        LayoutInflater inflater = getLayoutInflater();
        //creazione del layout
        View load_layout = inflater.inflate(R.layout.loading_custom_toast,
                (ViewGroup)findViewById(R.id.loading_toast_container));
        //creazione del toast
        Toast load_toast = new Toast(getApplicationContext());
        //applicazione del layout di caricamento
        load_toast.setGravity(Gravity.CENTER, 0, 0);
        load_toast.setDuration(Toast.LENGTH_SHORT);
        load_toast.setView(load_layout);
        load_toast.show();
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
                //si aggiunge la cella corrente all'arraylist
                data.add(gm.getMapCell(i,j).statusToString());
            }//for colonne
        }//for righe
        //si crea l'adapter per il gridlayout della matrice di esplorazione
        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        //si visualizza la matrice di esplorazione
        list = (GridView) findViewById(R.id.grid_view);
        list.setAdapter(adapter);


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

    /**
     *
     */
    @Override
    protected void onResume() {
        if(mp != null && !mp.isPlaying())
            mp.start();
        super.onResume();
    }

    /** metodo onCreateOptionsMenu(Menu): boolean
     * questo metodo serve per visualizzare il menu
     * nella activity corrente     *
     * @param menu
     * @return true
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
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        int id=item.getItemId();
        switch(id){
            case R.id.Menu1:
                //ritorno alla schermata iniziale
                newGame();
                return true;
            case R.id.Menu2:
                //risoluzione automatica della partita
                solveGame();
                break;
            case R.id.Menu3:
                //informazioni di gioco
                gameInfo();
                return true;
            case R.id.Menu4:
                //elenco dei punteggi
                viewScore();
                break;
            case R.id.Menu5:
                //impostazioni
                changeSettings();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }//end switch
        return false;
    }//onOptionsItemSelected(MenuItem)

    private void changeSettings() {
    }

    private void viewScore() {
    }

    private void solveGame() {
    }

    private void gameInfo() {
        //si specifica la modalita' di gioco
        GameInformation.setGameMode(game_mode);
        //si crea l'intent associato
        Intent intent = new Intent(this, GameInformation.class);
        //si apre la scheda delle informazioni del gioco
        startActivity(intent);
    }

    //##### metodi per la gestione del menu #####
    private void newGame() {
        //si ritorna alla schermata iniziale
        //per scegliere la modalita' di gioco
        Intent intent = new Intent(this,MainActivity.class);
        //si avvia l'activity
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear back stack
        startActivity(myIntent);
        finish();
        return;
    }

}
