package com.example.wumpusworldgame.gameActivities;
//serie di import
import android.app.Activity;
import android.widget.GridView;
import android.widget.TextView;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameActivities.adapter.GridViewCustomAdapter;
import java.util.ArrayList;
import game.session.configuration.Starter;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
import game.structure.text.GameMessages;
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

    //##### metodi utilizzati nella schermata di gioco #####

    /**
     *
     * @param direction
     * @param gm
     * @param em
     * @param game_message
     * @param shots
     * @param data
     * @param list
     * @param adapter
     */
    public static void gamePadMove(Direction direction, GameMap gm, GameMap em, TextView game_message, TextView shots, ArrayList<String> data, GridView list, GridViewCustomAdapter adapter){

        if(Starter.getGameStart()) {

            if(Starter.getTryToHit()){
                GameController.tryToHit(direction,gm,game_message,shots);
            }
            else {

                GameController.movePlayer(direction,gm,em,game_message,data,list,adapter);
            }
        }
        else {
            game_message.setText(R.string.end_game);
        }
    }


    /**
     *
     * @param game_message
     */
    public static void gamePadHit(TextView game_message){
        if(Starter.getGameStart()){


            if(Starter.getChanceToHit()){

                Starter.setTryToHit(true);
                game_message.setText(R.string.choose_direction);
            }
            else {
                game_message.setText(R.string.no_hit);
            }
        }
    }


    //##### metodi di gestione della mossa del pg #####

    /**
     *
     * @param direction
     * @param gm
     * @param em
     * @param game_message
     * @param data
     * @param list
     * @param adapter
     */
    private static void movePlayer(Direction direction, GameMap gm, GameMap em, TextView game_message, ArrayList<String> data, GridView list, GridViewCustomAdapter adapter){
        //si realizza la mossa, aggiornando la matrice di esplorazione
        data = GameController.makePGmove(direction, gm, em, game_message, data);
        //si aggiorna l''adapter
        adapter = new GridViewCustomAdapter(GridViewCustomAdapter.getCurrentActivity(), data);
        //oggetto che permette di visualizzare i dati
        list.setAdapter(adapter);
    }//movePlayer()

    /**
     *
     * @param direction
     * @param gm
     * @param em
     * @param game_message
     * @param data
     * @return
     */
    private static ArrayList<String> makePGmove(Direction direction, GameMap gm, GameMap em, TextView game_message, ArrayList<String> data){
        //si riceve la mossa che il giocatore vuole effettuare
        int status = movePG(direction,gm, em);
        //si effettua la mossa
        String info = makeMoveInTheMap(status,gm, em);
        //si stampa un messaggio informativo sullo stato della mossa
        game_message.setText(info);
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
      private static int movePG(Direction move, GameMap gm, GameMap ge) {
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
                //si restituisce lo stato
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
                //si restituisce lo stato
                return 2;
            }//fi
            else if (cs.equals(CellStatus.DANGER)) {
                //il pg e' caduto nella trappola
                //si aggiorna la posizione
                PlayableCharacter.setPGposition(cell_pos);
                //si aggiunge alla mappa di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).
                        copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                //si restituisce lo stato
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
                //si restituisce lo stato
                return 0;
            }//else
        }//fi indici di mossa corretti
        else {
            //indici di mossa non validi
            return -2;
        }//esle
    }//movePG(Direction, GameMap, GameMap)

    /** metodo makeMoveInTheMap(int, GameMap, GameMap): String
     * questo metodo effettua la mossa vera e propria nella mappa di gioco
     * @param status: int, rappresenta il risultato della mossa effettuata;
     * @param gm: GameMap, mappa di gioco;
     * @param em: GameMap, mappa di esplorazione;
     * @return info: String, restituisce una stringa informativa sulla mossa
     *                      che e' stata effettuata, valutata in base al
     *                      valore della variabile statuts.
     */
    private static String makeMoveInTheMap(int status, GameMap gm, GameMap em) {
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
                        //modalita' eroe
                        info = currentActivity.getResources().getString(R.string.hero_enemy);
                    }
                    else {
                        //modalita' wumpus
                        info = currentActivity.getResources().getString(R.string.wumpus_enemy);
                    }
                }//fi Enemy
                else {
                    //pericolo
                    if(currentActivity instanceof HeroSide){
                        //modalita' eroe
                        info = currentActivity.getResources().getString(R.string.hero_danger);
                    }
                    else {
                        //modalita' wumpus
                        info = currentActivity.getResources().getString(R.string.wumpus_danger);
                    }
                }//fi Danger
                //messaggio di fine partita
                info += "\n"+currentActivity.getString(R.string.looser);
                //fine della partita
                endGame();
                break;
            case 2:
                //premio
                info = "Wow! "+currentActivity.getString(R.string.hero_award);
                //messaggoi di fine partita: vittoria
                info += "\n"+currentActivity.getString(R.string.winner);
                //fine della partita
                endGame();
                break;
            default:
                break;
        }//end switch
        return info;
    }//makeMoveInTheMap(int, GameMap, GameMap)

    //##### metodo di gestione dei sensori #####

    /** metodo checkEnvironment(int[], GameMap): void
     * questo metodo fornisce il contenuto del vettore dei sensori
     * per la cella in cui si trova il pg, in modo da informare
     * il giocatore sull'ambiente circostante.
     * @param pg_pos: int, vettore che contiene la posizione del pg
     * 				 all'interno della mappa di gioco;
     * @param gm: GameMap, mappa di gioco;
     */
    public static String checkEnvironmentSensors(int[] pg_pos, GameMap gm) {
        //variabile ausiliarie per le informazioni di gioco
        String danger_sense= "";
        String enemy_sense ="";
        String sensor_info="";
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
                enemy_sense = currentActivity.getResources().getString(R.string.hero_enemy_sense);
            }
            else {
                enemy_sense = currentActivity.getResources().getString(R.string.wumpus_enemy_sense);
            }
        }
        //vicinanza del pericolo
        if(sensors[1]){
            if(currentActivity instanceof HeroSide){
                danger_sense = currentActivity.getResources().getString(R.string.hero_danger_sense);
            }
            else {
                danger_sense = currentActivity.getResources().getString(R.string.wumpus_danger_sense);
            }
        }
        //si aggiorna la variabile da restituire
        sensor_info = danger_sense +"..."+enemy_sense;
        //nessun tipo di pericolo
        if(!sensors[0] && !sensors[1]) {
            sensor_info = currentActivity.getResources().getString(R.string.safe_cell);
        }
        return sensor_info;
    }//checkEnvironment(int[], GameMap)

    //##### metodi di gestioni dello sparo #####

    /** metodo tryToHit(Direction, GameMap, GameMap): void
     * questo metodo controlla la direzione in cui si vuole provare
     * a colpire il nemico, verifica se la cella indicata dall'utente
     * esiste e verifica se in essa sia posizionato il nemico oppure no.
     * Se il colpo va a segno, il giocatore guadagna punti, altrimenti
     * ha perso l'unico tentativo di poterlo sconfiggere.
     * @param direction: Direction, direzione in cui si vuole lanciare il colpo;
     * @param gm: GameMap, mappa che rappresenta il terreno di gioco;
     * @param game_message: TextView
     * @return defeated: boolean, indica se il nemico e' stato colpito o meno
     */
    public static void tryToHit(Direction direction, GameMap gm, TextView game_message, TextView shots) {
        //si gestiscono i flag relativo al tentativo di sparo
        firedShot();
        //si visualizza il numero di colpi
        shots.setText("0");
        //variabile ausiliaria
        String hit_info = "";
        //si preleva la posizione attuale del pg
        int [] pg_pos = PlayableCharacter.getPGposition();
        //flag per indicare se il nemico e' stato colpito
        boolean defeated;
        //vettore degli indici di cella
        int[] enemy_indices=new int[2];
        //variabili ausiliarie per la validita' degli indici
        boolean iok=false;
        boolean jok=false;
        //si preleva la direzione in cui si vuole colpire
        enemy_indices = Controller.findCell(direction, pg_pos);
        //indice riga
        int i=enemy_indices[0];
        //indice colonna
        int j=enemy_indices[1];
        //controllo sull'indice riga
        if(i>=0 && i<gm.getRows())iok=true;
        //controllo sull'indice colonna
        if(j>=0 && j<gm.getColumns())jok=true;
        //la cella esiste se entrambi gli indici sono validi
        if(iok && jok) {
            //si cerca se il nemico si trova in una delle celle in questa direzione
            defeated = Controller.searchForEnemy(i, j, direction, gm);
            //si controlla se e' stato colpito
            if(defeated) {
                //colpo andato a segno
                //si preleva la stringa da restituire
                hit_info=currentActivity.getResources().getString(R.string.hit);
                //si visualizza il messaggio di colpo andato a segno
                game_message.setText(hit_info);
                //si aggiornano i sensori
                Controller.resetEnemySensor(gm);
                //TODO si aggiorna il punteggio
            }//fi
            else {
                //nemico mancato
                //si preleva la stringa da restituire
                hit_info=currentActivity.getResources().getString(R.string.wasted_shot);
                //si visualizza il messaggio di colpo errato
                game_message.setText(hit_info);
            }//esle
        }//fi indici cella
        else {
            //colpo errato:gli indici di cella non sono validi
            //si preleva la stringa da restituire
            hit_info=currentActivity.getResources().getString(R.string.failed_shot);
            //si visualizza il messaggio di colpo errato
            game_message.setText(GameMessages.failed_shot);
        }//esle
    }//hitEnemy()

    /** metodo firedShot(): void
     * questo metodo di supporto al metodo tryToHitEnemy(Direction, GameMap, TextView, TextView)
     * si occupa di gestire due falg, quello che identifica la richiesta di voler tentare il
     * colpo, che viene abilitato alla pressione del button HIT e quello che indica se il
     * giocatore dispone o meno di munizioni per eseguire questa azione.
     * Questi flag, dopo aver tentato il colpo, devono essere disabilitati.
     */
    private static void firedShot(){
        //si disabilita il flag che garantisce la possibilita' di tentare il colpo
        Starter.setChanceToHit(false);
        //si disabilita il flag che indica la richiesta di tentare il colpo
        Starter.setTryToHit(false);
    }//firedShot()

    //##### metodi di gestione della partita #####

    /** metodo linkStart(Activity, GameMap)
     * questo metodo si occupa di identificare l'activity da cui e'
     * invocato, in modo da conoscere la modalita' di gioco che e' stata
     * selezionata dal giocatore e poi esegue tutte le configurazioni iniziali,
     * come abilitare il flag di inizio partita e quello della disponibilita'
     * delle munizioni per colpire il nemico.
     * @param activity: activity, activity che in cui e' stato invocato il
     *                metodo, HeroSide oppure WumpusSide.
     * @param gm: GameMap, mappa che rappresenta il terreno di gioco.
     * @return sensor_info: String, stringa che contiene le informazioni
     *                      sull'ambiente circostante al pg.
     */
    public static String linkStart(Activity activity, GameMap gm){
        //variabile ausiliaria
        String sensor_info = "";
        //si assegna l'activity in cui viene eseguito questo metodo
        //come activity corrente, per poter accedere alle risorse di
        //progetto, come le stringhe.
        currentActivity=activity;
        //si abilita il flag di avvio della partita
        Starter.setGameStart(true);
        //si abilita il flag di disponibilita' del colpo
        Starter.setChanceToHit(true);
        //si abilita il flag che identifica la richiesta di tentare il colpo
        Starter.setTryToHit(false);
        //si preleva la posizione del pg
        int[] pg_pos = PlayableCharacter.getPGposition();
        //si verifica lo stato dei sensori attorno al pg
        sensor_info = GameController.checkEnvironmentSensors(pg_pos,gm);
        //si restituisce la stringa che lo contiene
        return sensor_info;
    }//linkStart(Activity)

    /** metodo endGame(): void
     * questo metodo si occupa di eseguire delle operazioni necessarie alla chiusura della
     * partita, come disabilitare il flag di avvio del gioco e visualizzare il punteggio ottenuto.
     */
    public static void endGame(){
        //fine della partita
        Starter.setGameStart(false);
        //TODO si aggiorna il punteggio
        //visualizzazione del punteggio
        //TODO visualizzare una dialog con il punteggio ottenuto
        //TODO visualizzare la mappa di gioco con tutte le celle visibili
    }//endGame()

}//end GameController
