package com.example.wumpusworldgame.gameMenuItems;
//serie di import
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameActivities.GridViewCustomAdapter;
import java.util.ArrayList;
import game.structure.map.GameMap;
/** class AutomaticPlayer
 * activity che realizza la risoluzione della partita corrente
 * con il giocatore automatico
 */
public class AutomaticPlayer extends AppCompatActivity {
    //##### attributi di classe #####

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

    //questo metodo viene invocato alla creazione dell'Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si specifica il file che descrive il layout
        setContentView(R.layout.activity_automatic_game);
        //scelta della clip audio
        //TODO scegliere la musica
        mp = MediaPlayer.create(AutomaticPlayer.this,R.raw.the_good_fight);

        //##### schermata di gioco #####

        //esecuzione clip audio
        mp.start();
        //TODO risoluzione della matrice di gioco
        //gm = new GameMap();
        //creazione della matrice di esplorazione
        em = new GameMap();
        //TODO dimensioni della matrice di gioco, analoghe a quelle della matrice di esplorazione
        int r = em.getRows();
        int c = em.getColumns();
        //##### visualizzazione della matrice di esplorazione #####
        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                data.add(em.getMapCell(i,j).getCellStatus().toString());
            }//for colonne
        }//for righe
        //si crea l'adapter per il gridlayout della matrice di esplorazione
        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);
        //si visualizza la matrice di esplorazione
        list = (GridView) findViewById(R.id.grid_view);
        list.setAdapter(adapter);
    }//onCreate

    //TODO metodo di risoluzione del gioco
    public static void gameSessionSolving(int game_mode, GameMap gm) {
        //
    }

}//end AutomaticPlayer
