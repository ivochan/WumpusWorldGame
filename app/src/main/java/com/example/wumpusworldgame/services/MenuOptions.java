package com.example.wumpusworldgame.services;

import android.content.Intent;

import com.example.wumpusworldgame.activities.GameInformation;
import com.example.wumpusworldgame.activities.HeroSide;
import com.example.wumpusworldgame.activities.MainActivity;

import game.player.agent.RandomAgent;
import game.session.configuration.Starter;
import game.session.score.Score;
import game.session.score.ScoreMemo;

public class MenuOptions {

    public static void viewScore() {
    }

    public static void solveGame() {
    }

    public static void changeSettings() {
    }

/*
    private void solveGame(Intent intent) {
        //flag avvio partita
        Starter.setGameStart(true);
        //si inizializza il punteggio
        Score.resetScoreData();
        //si inizializza il file
        ScoreMemo.createScoreFile();
        //si istanzia il giocatore automatico
        RandomAgent player = new RandomAgent();
        //avvio della partita
        while(Starter.getGameStart()){
            //si effettua la mossa
            player.chooseMove(em, gm);
        }//end while sessione di gioco
        //si stampa la mappa di esplorazione
        System.out.println(em.gameMaptoString());
        //si mostra il percorso compiuto
        player.showRunPath();
        //stato della partita attuale
        Starter.setGameStart(false);
        //si resetta la disponibilita' del colpo
        Starter.setChanceToHit(true);
        //punteggio
        Score.totalScore();
        System.out.println("Questo e' il tuo punteggio:\n"+Score.getScore());
        //si memorizza il punteggio
        ScoreMemo.saveScore(Score.ScoreToString());


    }
*/
    /**
     *
     * @param game_mode
     * @return
     */
    public static Class<GameInformation> gameInfo(int game_mode) {
        //si specifica la modalita' di gioco
        GameInformation.setGameMode(game_mode);
        //si restituisce la scheda delle informazioni del gioco
        return GameInformation.class;
    }//


    /**
     *
     * @return
     */
    public static  Class<MainActivity> newGame() {
        //si ritorna alla schermata iniziale
        return MainActivity.class;
    }//

}
