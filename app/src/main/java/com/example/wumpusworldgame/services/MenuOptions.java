package com.example.wumpusworldgame.services;
//serie di import
import com.example.wumpusworldgame.activities.AutomaticPlayer;
import com.example.wumpusworldgame.activities.GameInformation;
import com.example.wumpusworldgame.activities.MainActivity;
import game.structure.map.GameMap;
/** class MenuOptions
 * questa classe fornisce dei metodi che permettono di
 * gestire le voci del menu principale, che verranno
 * richiamati da entrambe le activity che implementano
 * le due differenti modalita' di gioco.
 */
public class MenuOptions {

    /** metodo newGame(): Class<MainActivity>
     * questo metodo avvia una nuova partita,
     * restituendo la classe principale,
     * la MainActivity, in modo da poter effettuare
     * di nuovo la scelta della modalita' di gioco.
     * @return
     */
    public static  Class<MainActivity> newGame() {
        //si ritorna alla schermata iniziale
        return MainActivity.class;
    }//newGame()

    /** metodo gameInfo(int): Class<GameInformation>
     * questa metodo restituisce la classe GameInformation,
     * che racchiude le informazioni del gioco ed imposta
     * la modalita' di gioco attuale, in modo da garantire
     * la corretta navigazione all'indietro, dopo che e'
     * gia'stata avviata la sessione di gioco.
     * @param game_mode: int, costante indicativa della
     *                   modalita'di gioco.
     * @return  Class<GameInformation>.
     */
    public static Class<GameInformation> gameInfo(int game_mode) {
        //si specifica la modalita' di gioco
        GameInformation.setGameMode(game_mode);
        //si restituisce la scheda delle informazioni del gioco
        return GameInformation.class;
    }//gameInfo(int)




    public static void viewScore() {
    }

    public static Class<AutomaticPlayer> solveGame(int game_mode, GameMap gm) {
        //si specifica la modalita' di gioco
        GameInformation.setGameMode(game_mode);
        //risoluzione del gioco
        AutomaticPlayer.gameSessionSolving(game_mode, gm);
        //si restituisce la classe in cui verra' risolto il gioco
        return AutomaticPlayer.class;
    }//solveGame()

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


    public static void tutorial() {
    }
}//end MenuOptions
