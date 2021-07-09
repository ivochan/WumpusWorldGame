package com.example.wumpusworldgame.gameMenuItems.automaticMode;

import game.structure.map.GameMap;

public class AutomaticPlayer {
    //##### attributi di classe #####

    private GameMap gm;
    private GameMap em;
    private int[] pg_pos;

    /**
     *
     * @param gm
     * @param em
     */
    public AutomaticPlayer(GameMap gm, GameMap em){
        //controllo sui parametri
        if(gm==null)throw new IllegalArgumentException("mappa di gioco nulla");
        if(em==null)throw new IllegalArgumentException("mappa di esplorazione nulla");
        //si assegnano i parametri agli attributi di classe
        this.gm=gm;
        this.em=em;
    }

    /**
     *
     * @param gm
     * @param em
     * @param pg_position
     */
    public AutomaticPlayer(GameMap gm,GameMap em, int[] pg_position){
        //controllo sui parametri
        if(pg_position==null || pg_position.length<2)
            throw new IllegalArgumentException("vettore posizione del pg non valido");
        if(gm==null)throw new IllegalArgumentException("mappa di gioco nulla");
        if(em==null)throw new IllegalArgumentException("mappa di esplorazione nulla");
        //si assegnano i parametri agli attributi di classe
        this.gm=gm;
        this.em=em;
        //inizializzazione del vettore posizione
        pg_pos = new int[2];
        //si copiano i valori nelle celle del vettore di classe
        pg_pos[0]=pg_position[0];
        pg_pos[1]=pg_position[1];
    }


    

}
