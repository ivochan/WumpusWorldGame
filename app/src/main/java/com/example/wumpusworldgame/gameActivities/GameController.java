package com.example.wumpusworldgame.gameActivities;
//serie di import
import android.app.Activity;
import android.widget.TextView;
import com.example.wumpusworldgame.R;
import java.util.ArrayList;
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
        int status = Controller.movePG(direction,gm, em);
        //si effettua la mossa
        String info = movePGinTheMap(status,gm, em);
        //si stampa un messaggio informativo sullo stato della mossa
        game_message.setText(status+" "+info);
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
    }



    private static String movePGinTheMap(int status, GameMap gm, GameMap em) {
        //variabile ausiliaria per la stringa di info sullo stato della mossa
        String info="";
        //si preleva la posizione del pg
        int [] pg_pos = PlayableCharacter.getPGposition();

        //realizzazione della mossa
        switch(status) {
            case -2 :
                info = currentActivity.getResources().getString(R.string.forbidden_cell);
                break;
            case -1 :
                info = currentActivity.getResources().getString(R.string.wrong_move);
                break;
            case 0 :
                //informazioni sulla posizione
                info = checkEnvironmentSensors(pg_pos, gm);
                break;
            case 1:
                //informazioni della cella in cui si e' mosso il pg
                Cell c = gm.getMapCell(pg_pos[0], pg_pos[1]);
                //info sullo stato
                CellStatus s = c.getCellStatus();
                //stampa del messaggio se nemico o pericolo
                if(s.equals(CellStatus.ENEMY)){
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

                break;
            case 2:
                info = "Wow!"+currentActivity.getString(R.string.hero_award);
                info += "\n"+currentActivity.getString(R.string.winner);
                //fine della partita
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
