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
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.adapters.GridViewCustomAdapter;

import java.util.ArrayList;
import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;

/** class HeroSide
 * classe che implementa la modalita' di gioco in cui il personaggio
 * giocabile e' l'Avventuriero
 */
public class HeroSide extends AppCompatActivity {
    //##### attributi di classe #####
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
        //si specifica il file che descrive il layout
        setContentView(R.layout.activity_hero_side);
        //scelta della clip audio
        mp = MediaPlayer.create(HeroSide.this,R.raw.the_good_fight);

        //##### schermata di caricamento #####
        LayoutInflater inflater = getLayoutInflater();
        //creazione del layout
        View load_layout = inflater.inflate(R.layout.loading_custom_toast, (ViewGroup)findViewById(R.id.loading_toast_container));
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
                data.add(em.getMapCell(i,j).getCellStatusToString());
            }//for colonne
        }//for righe
        //si crea l'adapter per il gridlayout della matrice di esplorazione
        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        //si visualizza la matrice di esplorazione
        list = (GridView) findViewById(R.id.grid_view);
        list.setAdapter(adapter);

    }//onCreate

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
                //gestione della prima voce
                startActivity(new Intent(this, GameInformation.class));
                return true;
            case R.id.Menu2:
                //gestione della seconda voce
                break;
            case R.id.Menu3:
                //gestione della terza voce
                break;
        }//end switch
        return false;
    }//onOptionsItemSelected(MenuItem)


}//end HeroSide
