package com.example.wumpusworldgame.gameActivities;
//serie di import
import android.app.Activity;
import android.widget.TextView;
import com.example.wumpusworldgame.R;
import java.util.ArrayList;

import game.session.configuration.Starter;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/** class GameController
 * questa classe si occupa di collegare il front-end con il back-end
 * implementando le effettive funzionalita' dell'applicazione,
 * in modo da far in modo che alla pressione dei pulsanti del gamepad
 * corrisponda una mossa nella mappa di gioco.
 */
public class GameController {
    //##### attributi di classe #####
    //activity corrente
    private static Activity currentActivity;

    private boolean end_game=false;

    /** metodo setGameActivity(Activity): void
     * questo metodo preleva l'activity da cui e' stato invocato
     * e la memorizza come activity corrente, in modo da poter
     * accedere alla risorse di progetto, come le stringhe
     * @param activity: Activity, da cui e' stato chiamato il metodo
     */
    public static void setGameActivity(Activity activity){
        //si assegna questa activity come attributo di classe
        currentActivity = activity;
    }//setGameActivity(Activity)

    public static ArrayList<String> makePGmove(Direction direction, GameMap gm, GameMap em, TextView game_message, ArrayList<String> data){
        //si riceve la mossa che il giocatore vuole effettuare
        int status = movePG(direction,gm, em);
        //si effettua la mossa
        String info = movePGinTheMap(status,gm, em);
        //si stampa un messaggio informativo sullo stato della mossa
        game_message.setText(info);
       //si restituisce la matrice di eplorazione
        //dimensioni della matrice di gioco, analoghe a quelle della matrice di esplorazione
        int r = gm.getRows();
        int c = gm.getColumns();

        //##### matrice di esplorazione #####
        data = new ArrayList<>();
        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                //si aggiunge la cella corrente all'arraylist
                data.add(em.getMapCell(i,j).statusToString());
            }//for colonne
        }//for righe
        return data;
    }//makePGmove
/*

    /** metodo movePG(Direction, int[], GameMap, GameMap): int
     * questo metodo si occupa di verificare se la mossa scelta dal giocatore sia
     * valida oppure meno, controllando se la cella in cui effettuare la mossa esista,
     * che tipo di contenuto abbia e cosa comporti spostarvici il pg.
     * Il risultato delle operazioni di controllo verra' indiicato da una variabile
     * di tipo intero.
     * @param move: Direction, direzione in cui effettuare lo spostamento del pg;
     * @param gm: GameMap, mappa che racchiude le informazioni con cui e' stato configurata
     * 					   la partita di gioco corrente;
     * @param ge: GameMap, mappa che racchiude le infomazioni del gioco conosciute all'utente,
     * 					   come le celle gia' visitate;
     * @return status: int, intero che indica il risultato dell'esecuzione di questo metodo,
     * 				   nello specifico, se questa variabile assume il valore:
     * 				 -  1, allora il pg e' finito nella cella del nemico, ha perso;
     * 				 -  2, il pg e' finito nella cella con il premio, ha vinto;
     * 				 - -1, la mossa non era valida oppure prevedeva di andare in una cella non accessibile;
     * 				 -  0, la mossa e' valida e il pg viene spostato, aggiornando la mappa di esplorazione
     * 					   e la sua posizione corrente, segnando la cella in cui si trovata prima come visitata.
     */
      public static int movePG(Direction move, GameMap gm, GameMap ge) {
        //vettore della posizione successiva
        int [] cell_pos;
        //si preleva la posizione del pg
        int [] pg_pos = PlayableCharacter.getPGposition();
        //si controlla il risultato della direzione scelta
        cell_pos = Controller.findCell(move, pg_pos);
        //la cella indicata da next_pos esiste
        if(Controller.checkCell(cell_pos, gm)) {
            //si controlla il contenuto della cella in questione
            CellStatus cs = gm.getMapCell(cell_pos[0], cell_pos[1]).getCellStatus();
            //controllo sullo stato
            if (cs.equals(CellStatus.ENEMY)) {
                //il pg e' stato ucciso dal nemico
                //si aggiorna la posizione
                PlayableCharacter.setPGposition(cell_pos);
                //si aggiunge alla mappa di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).
                        copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                return 1;
            }//fi
            else if (cs.equals(CellStatus.FORBIDDEN)) {
                //questa cella e' vietata perche' e' un sasso
                //si aggiunge alla mappa di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).
                        copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                //il pg rimane dove si trova
                return -1;
            }//fi
            else if (cs.equals(CellStatus.AWARD)) {
                //il pg vince
                //si aggiorna la posizione
                PlayableCharacter.setPGposition(cell_pos);
                //si aggiunge alla mappa di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).
                        copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                return 2;
            }//fi
            else if (cs.equals(CellStatus.DANGER)) {
                //il pg e' caduto nella trappola
                //si aggiorna la posizione
                PlayableCharacter.setPGposition(cell_pos);
                //si aggiunge alla mappa di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).
                        copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                return 1;
            }//fi
            else { //CellStatus.SAFE
                    //il pg si trova in una cella libera
                    //la cella in cui si trovava prima il pg si segna come visitata
                    ge.getMapCell(pg_pos[0], pg_pos[1]).setCellStatus(CellStatus.OBSERVED);
                    //si aggiorna la posizione del pg
                    PlayableCharacter.setPGposition(cell_pos);
                    //si preleva il contenuto della cella in cui si trova attualmente il pg
                    Cell c = gm.getMapCell(cell_pos[0], cell_pos[1]);
                    //si copia questa cella nella matrice di esplorazione
                    ge.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(c);
                    //il contenuto di questa cella nella mappa di esplorazione e' il pg
                    ge.getMapCell(cell_pos[0], cell_pos[1]).setCellStatus(CellStatus.PG);
                    return 0;
            }
        }
        //indici di mossa non validi
        return -2;
    }//movePG(Direction, GameMap, GameMap)

    /**
     *
     * @param status
     * @param gm
     * @param em
     * @return
     */
    private static String movePGinTheMap(int status, GameMap gm, GameMap em) {
        //variabile ausiliaria per la stringa di info sullo stato della mossa
        String info="";
        //si preleva la posizione del pg
        int [] pg_pos = PlayableCharacter.getPGposition();

        //realizzazione della mossa
        switch(status) {
            case -1 :
                //la cella contiene un sasso che blocca il passaggio
                info = currentActivity.getResources().getString(R.string.forbidden_cell);
                break;
            case -2 :
                //la mossa scelta dal giocatore non e' valida
                info = currentActivity.getResources().getString(R.string.wrong_move);
                break;
            case 0 :
                //la cella in cui si e' effettuata la mossa e' sicura
                info = checkEnvironmentSensors(pg_pos, gm);
                //si forniscono al giocatore delle informazioni sull'ambiente circostante
                break;
            case 1:
                //la cella in cui si e' mosso il pg contiene il nemico oppure un pericolo
                Cell c = gm.getMapCell(pg_pos[0], pg_pos[1]);
                //info sullo stato della cella
                CellStatus cs = c.getCellStatus();
                //stampa del messaggio se nemico o pericolo
                if(cs.equals(CellStatus.ENEMY)){
                    //nemico
                    if(currentActivity instanceof HeroSide){
                        info = currentActivity.getResources().getString(R.string.hero_enemy);
                    }
                    else {
                        info = currentActivity.getResources().getString(R.string.wumpus_enemy);
                    }
                }
                else {
                    //pericolo
                    if(currentActivity instanceof HeroSide){
                        info = currentActivity.getResources().getString(R.string.hero_danger);
                    }
                    else {
                        info = currentActivity.getResources().getString(R.string.wumpus_danger);
                    }
                }
                

                info += "\n"+currentActivity.getString(R.string.looser);
                //fine della partita
                Starter.setGameStart(false);
                break;
            case 2:
                info = "Wow! "+currentActivity.getString(R.string.hero_award);
                info += "\n"+currentActivity.getString(R.string.winner);
                //fine della partita
                Starter.setGameStart(false);
                break;
            default:
                break;
        }//end switch
        return info;
    }//checkMove(int)


    /** metodo checkEnvironment(int[], GameMap): void
     * questo metodo fornisce il contenuto del vettore dei sensori
     * per la cella in cui si trova il pg, in modo da informare
     * il giocatore sull'ambiente circostante.
     * @param pg_pos: int, vettore che contiene la posizione del pg
     * 				 all'interno della mappa di gioco;
     * @param gm: GameMap, mappa di gioco;
     */
    private static String checkEnvironmentSensors(int[] pg_pos, GameMap gm) {
        //variabile ausiliaria per le informazioni di gioco
        String sensor_info = "";
        //si fornisce il contenuto del vettore dei sensori
        //per la cella in cui si trova il pg
        //vettore dei sensori
        boolean [] sensors = new boolean[2];
        //stampa della posizione corrente del pg
        //System.out.println("Ti trovi nella cella ("+pg_pos[0]+','+pg_pos[1]+')');
        //si acquisiscono le informazioni del vettore dei sensori
        sensors=gm.getMapCell(pg_pos[0], pg_pos[1]).getSenseVector();
        //vicinanza del nemico
        if(sensors[0]){
            if(currentActivity instanceof HeroSide){
                sensor_info = currentActivity.getResources().getString(R.string.hero_enemy_sense);
            }
            else {
                sensor_info = currentActivity.getResources().getString(R.string.wumpus_enemy_sense);
            }
        }
        //vicinanza del pericolo
        if(sensors[1]){
            if(currentActivity instanceof HeroSide){
                sensor_info = currentActivity.getResources().getString(R.string.hero_danger_sense);
            }
            else {
                sensor_info = currentActivity.getResources().getString(R.string.wumpus_danger_sense);
            }
        }
        //nessun tipo di pericolo
        if(!sensors[0] && !sensors[1]) {
            sensor_info = currentActivity.getResources().getString(R.string.safe_cell);
        }
        return sensor_info;
    }//checkEnvironment(int[], GameMap)

    public static void endGameSession(){

    }
}//end GameController
