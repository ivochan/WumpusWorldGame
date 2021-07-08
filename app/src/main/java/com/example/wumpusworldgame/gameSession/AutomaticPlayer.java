package com.example.wumpusworldgame.gameSession;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.wumpusworldgame.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import game.player.agent.RandomAgent;
import game.session.configuration.Starter;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
import game.structure.text.GameMessages;
/**
 *
 */
public class AutomaticPlayer {

    private static ArrayList<String> automaticPlayer(GameMap gm, GameMap em){
        em.clear();
        //avvio della partita
        Starter.setGameStart(true);
        //lista che conterra' i dati della soluzione
        ArrayList<String> solution = new ArrayList<>();
        //si istanzia il giocatore automatico
        RandomAgent player = new RandomAgent();
        //risoluzione
        while(Starter.getGameStart()){
            //si effettua la mossa
            chooseMove(em, gm, player);
            //TODO si memorizza il punteggio

        }//end while sessione di gioco
        //dimensioni della matrice di gioco, analoghe a quelle della matrice di esplorazione
        int rows = gm.getRows();
        int columns = gm.getColumns();
        //si iterano le celle della matrice
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                //si aggiunge la cella corrente alla LinkedList
                solution.add(em.getMapCell(i, j).statusToString());
            }//for colonne
        }//for righe
        //si restituisce
        return solution;
    }

    public static void chooseMove(GameMap em, GameMap gm, RandomAgent player) {
        //variabile ausiliaria per lo stato della mossa
        int status = 0;
        //variabile ausiliaria per la direzione
        Direction dir;
        //variabile ausiliaria per i sensori
        boolean[] sensors = new boolean[2];
        //si preleva la posizione del pg
        int  [] pg_pos = PlayableCharacter.getPGposition();
        //si preleva la cella in cui si trova
        Cell cur = em.getMapCell(pg_pos[0], pg_pos[1]);
        //si preleva il vettore dei sensori
        sensors = cur.getSenseVector();
        //verifica del contenuto
        if(sensors[CellStatus.ENEMY_SENSE.ordinal()]) {
            //il nemico e' nelle vicinanze
            if(Starter.getChanceToHit()) {
                //si verifica la disponibilita' del colpo
                //almeno una delle celle non e' stata visitata se il sensore e' acceso
                Direction shot_dir = player.chooseDirection(pg_pos[0], pg_pos[1], gm);
                //si tenta il colpo
                Controller.hitEnemy(shot_dir, gm);
                //si resetta il flag
                Starter.setChanceToHit(false);
            }//fi
            else {
                //non si hanno munizioni
                System.out.println(GameMessages.no_hit);
                //si sceglie la direzione in cui fare muovere il pg
                dir = player.chooseDirection(pg_pos[0], pg_pos[1], gm);
                //si sceglie la direzione in cui muovere il pg
                status = Controller.movePG(dir, gm, em);
                //si controlla la mossa
                Controller.makeMove(status, gm, em);
                //TODO aggiornamento del percorso
                if(status!=-1) {
                   // updateRunPath(gm.getMapCell(pg_pos[0], pg_pos[1]));
                }

            }//else
        }//fi
        else if(sensors[CellStatus.DANGER_SENSE.ordinal()]) {
            //il pericolo e' vicino
            //si preferisce come direzione una cella non visitata
            dir = player.chooseDirection(pg_pos[0], pg_pos[1], gm);
            //si sceglie la direzione in cui muovere il pg
            status = Controller.movePG(dir, gm, em);
            //si controlla la mossa
            Controller.makeMove(status, gm, em);
            //TODO aggiornamento del percorso
            if(status!=-1){
                //updateRunPath(gm.getMapCell(pg_pos[0], pg_pos[1]));
            }
        }
        else {
            //si sceglie una direzione a caso, tra quelle non esplorate
            dir = player.chooseDirection(pg_pos[0], pg_pos[1], gm);
            //si sceglie la direzione in cui muovere il pg
            status = Controller.movePG(dir, gm, em);
            //si controlla la mossa
            Controller.makeMove(status, gm, em);
            //TODO aggiornamento del percorso
            if(status!=-1){
                //updateRunPath(gm.getMapCell(pg_pos[0], pg_pos[1]));
            }
        }


        Starter.setGameStart(false);
    }//chooseMove(GameMap, GameMap)
/*
    private static void updateRunPath(Cell c){
        //si inserisce la cella in coda, nella lista di quelle visitate
        run.add(c);
    }
*/
    /*
    public static ArrayList<String> runPathToString() {
        //variabile ausiliaria
        int [] position = new int[2];
        //stringa da stampare
        ArrayList<String> run_list = new ArrayList();
        //si iterano le celle visitate
        for(Cell c: run) {
            //si preleva la posizione della cella in esame
            position = c.getPosition();
            //si inserisce nella lista
            run_list.add("("+position[0]+','+position[1]+")");
        }//end for
        return run_list;
    }//runPathToString()

*/


    /** metodo showLoadingScreen(Activity, LayoutInflater)
     * questo metodo realizza una schermata di caricamento
     * che viene mostrata appena si richiede la modalita'
     * di risoluzione automatica della partita di gioco attuale
     * @param inflater
     * @param activity
     */
    public static void showLoadingScreen(Activity activity, LayoutInflater inflater) {
        //si inizializza una dialog
        Dialog customDialog;
        //si assegna alla dialog il layout
        View customView = inflater.inflate(R.layout.solving_screen_dialog, null);
        //si costruisce la dialog specificandone lo stile personalizzato
        customDialog = new Dialog(activity, R.style.CustomDialog);
        //si definisce il layout alla dialog attuale
        customDialog.setContentView(customView);
        //si visualizza la dialog
        customDialog.show();
        //si istanzia l'oggetto timer per defire il tempo in cui la finestra sara' visibile
        final Timer t = new Timer();
        //l'oggetto timer inizia la schedulazione dei processi attivi
        t.schedule(new TimerTask() {
            public void run() {
                //il task e' attivo quindi la dialog viene chiusa
                customDialog.dismiss();
                //viene fermato il thread timer
                t.cancel();
            }//run()
        }, 1000);
    }//showLoadingScreen(Activity, LayoutInflater)

}//end AutomaticPlayer
