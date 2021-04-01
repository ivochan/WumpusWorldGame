package com.example.wumpusworldgame.activities;

import android.os.Bundle;
import android.widget.GridView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.adapter.GridViewCustomAdapter;

import java.util.ArrayList;

import game.structure.map.GameMap;
import game.structure.map.MapConfiguration;

/** class HeroSide
 * classe che implementa la modalita' di gioco in cui il personaggio
 * giocabile e' l'Avventuriero
 */
public class HeroSide extends AppCompatActivity {
    private GridView list;
    ArrayList<String> data = new ArrayList<String>();
    //##### attributi di classe #####
    GameMap gm;
    GameMap em;
    //questo metodo viene invocato alla creazione dell'Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si specifica il file che descrive il layout
        setContentView(R.layout.activity_hero_side);

        //creazione della matrice di gioco
        gm = new GameMap();
        //creazione della matrice di esplorazione
        em = new GameMap();
        //riempimento delle matrici
        MapConfiguration.init(gm,em);

        //dimensioni della matrice
        int r = gm.getRows();
        int c = gm.getColumns();
        //##### visualizzazione della matrice di gioco #####

        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                data.add(gm.getMapCell(i,j).getCellStatus().toString());
            }//for colonne
        }//for righe

        GridViewCustomAdapter adapter = new GridViewCustomAdapter(this, data);

        list = (GridView) findViewById(R.id.grid_view);
        list.setAdapter(adapter);

    }//onCreate
}//end HeroActivity
