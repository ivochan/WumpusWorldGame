package com.example.wumpusworldgame.gameMenuItems.automaticMode;
//serie di import
import android.widget.TextView;
import game.structure.map.GameMap;
/** AutomaticModeAgent: class
 * agente automatico utilizzato per effettuare la risoluzione
 * automatica della partita in corso, richiamato direttamente
 * dalla libreria del backend
 */
public class AutomaticModeAgent {
    //##### attributi di classe #####

    //mappa di gioco
    private GameMap gm;
    //mappa di esplorazione
    private GameMap em;

    //##### metodo di risoluzione della mappa di gioco #####

    /** metodo solve() : void
     *
     */
    public void solve(TextView shots) {
        //si visualizza il numero di colpi
        shots.setText("1");
        //si istanzia il giocatore automatico
        AutomaticAgent automaticAgent = new AutomaticAgent(gm,em);
        //ciclo di risoluzione
        while (!AutomaticAgent.getGameSessionFlag()) {
            //il giocatore sceglie la mossa da eseguire
            AutomaticAgent.chooseGameMove();
            //aggiornamento del campo di testo che identifica lo sparo
            if(!AutomaticAgent.getHitFlag)shots.setText("0");
        }//end while
        // la sessione di gioco e' conclusa
    }//solve()

}//end AutoaticModeAgent
