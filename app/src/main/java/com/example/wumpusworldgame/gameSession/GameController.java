package com.example.wumpusworldgame.gameSession;
//serie di import
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameActivities.HeroSide;
import com.example.wumpusworldgame.gameMenuItems.automaticMode.automaticModeActivities.HeroAutomaticMode;
import com.example.wumpusworldgame.services.Utility;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import game.session.configuration.Starter;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.session.score.Score;
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
    //List contentente gli elementi della mappa di esplorazione
    private static ArrayList<String> update_data=new ArrayList<>();
    //flag da usare per la stringa nella dialog di fine partita
    private static boolean victory=false;

    //##### metodi utilizzati nella schermata di gioco #####

    /** metodo gamePadMove(Direction,GameMap,GameMap,TextView,TextView,List<String>,GridViewCustomAdapter): void
     * questo metodo viene utilizzato per gestire le azioni che dovranno essere
     * effettuate dai pulsanti che costituiscono, virtualmente, il "gamepad", cioe'
     * il controller di gioco tramite cui l'utente puo' effetttuare le sue mosse.
     * @param direction
     * @param gm
     * @param em
     * @param game_message
     * @param shots
     * @param adapter
     */
    public static void gamePadMove(Direction direction, GameMap gm, GameMap em, TextView game_message, TextView shots, TextView score_box, Score score, GridViewCustomAdapter adapter) {
        //si controlla se la partita e' iniziata
        if(Starter.getGameStart()) {
            //si controlla se il giocatore ha richiesto di colpire
            if(Starter.getTryToHit()){
                //si tenta di colpire il nemico
                GameController.tryToHit(direction,gm,game_message,shots,score_box,score);
            }//fi
            else {
                //se non ha richiesto di colpire, allora si deve muovere il pg
                GameController.movePlayer(direction,gm,em,game_message,score_box,score,adapter);
                //si controlla se la partita e' conclusa
                if(!Starter.getGameStart()){
                   //se conclusa si esegue il metodo di fine gioco
                   endGameSession(score);
                }//fi
            }//else
        }//fi
        //allora la partita e' conclusa
        else {
            //si controlla se l'utente aveva chiesto la soluzione
            if(HeroAutomaticMode.getSolutionRequest()){
                //messaggio di soluzione visionata
                game_message.setText(R.string.solution_request);
            }
            else {
                //messaggio di fine partita
                game_message.setText(R.string.end_game);
            }
        }//else

    }//gamePadMove(Direction,GameMap,GameMap,TextView,TextView,List<String>,GridViewCustomAdapter)

    /** metodo gamePadHit(TextView): void
     * questo metodo realizza l'azione vera e propria che consente al giocatore di provare a
     * colpire il nemico
     * @param game_message
     */
    public static void gamePadHit(TextView game_message){
        //si controlla se il gioco e' stato avviato
        if(Starter.getGameStart()){
            //si controlla se si ha a disposizione il colpo
            if(Starter.getChanceToHit()){
                //si setta il flag del tentativo del colpo in atto
                Starter.setTryToHit(true);
                //si richiede la direzione in cui tentare il colpo
                game_message.setText(R.string.choose_direction);
            }//fi
            else {
                //non si hanno munizioni percio' non si puo' tentare il colpo
                game_message.setText(R.string.no_hit);
            }//else
        }//fi
    }//gamePadHit(TextView)

    //##### metodi di gestione della mossa del pg #####

    /** metodo movePlayer(Direction,GameMap,GameMap,TextView,TextView,List<String>,GridViewCustomAdapter)
     * questo metodo controlla il risultato a cui ha portato la mossa effettuata dal giocatore,
     * aggiorna la mappa di esplorazione e la visualizza a schermo, fornendo all'utente dei messaggi
     * sullo stato del gioco.
     * @param direction
     * @param gm
     * @param em
     * @param game_message
     * @param adapter
     */
    private static void movePlayer(Direction direction, GameMap gm, GameMap em, TextView game_message, TextView score_box, Score score, GridViewCustomAdapter adapter){
        //si riceve la mossa che il giocatore vuole effettuare
        int status = movePG(direction,gm, em, score);
        //si effettua la mossa
        String info = makeMoveInTheMap(status,gm);
        //si stampa un messaggio informativo sullo stato della mossa
        game_message.setText(info);
        //si aggiorna il valore del punteggio
        score_box.setText(""+score.getScore());
        //si aggiorna la list che contiene la mappa di esplorazione da visualizzare
        updateExplorationMap(gm,em);
        //si aggiorna l'adapter
        adapter.swapItems(update_data);
    }//movePlayer()

    /** metodo updateExplorationMap(GameMap, GameMap): void
     * questo metodo inserisce nella list che verra' fornita all'adapter
     * per mostrare a video lo stato corrente della mappa di gioco,
     * il contenuto della mappa di esplorazione, aggiornata ogni volta
     * che il giocatore scopre una nuova casella del terreno di gioco.
     * @param gm
     * @param em
     */
    public static void updateExplorationMap(GameMap gm, GameMap em){
        //dimensioni della matrice di gioco, analoghe a quelle della matrice di esplorazione
        int r = gm.getRows();
        int c = gm.getColumns();
        //##### matrice di esplorazione #####
        update_data.clear();
        //si iterano le celle della matrice
        for (int i = 0; i < r; i++) {
            for(int j=0;j<c;j++) {
                //si aggiunge la cella corrente alla List
                update_data.add(em.getMapCell(i,j).statusToString());
            }//for colonne
        }//for righe
    }//updateExplorationMap()

    /** metodo movePG(Direction, int[], GameMap, GameMap): int
     * questo metodo si occupa di verificare se la mossa scelta dal giocatore sia
     * valida oppure meno, controllando se la cella in cui effettuare la mossa esista,
     * che tipo di contenuto abbia e cosa comporti spostarvici il pg.
     * Il risultato delle operazioni di controllo verra' indiicato da una variabile
     * di tipo intero.
     * @param move: Direction, direzione in cui effettuare lo spostamento del pg;
     * @param gm: GameMap, mappa che racchiude le informazioni con cui e' stato configurata
     * 					   la partita di gioco corrente;
     * @param em: GameMap, mappa che racchiude le infomazioni del gioco conosciute all'utente,
     * 					   come le celle gia' visitate;
     * @return status: int, intero che indica il risultato dell'esecuzione di questo metodo,
     * 				   nello specifico, se questa variabile assume il valore:
     * 				 -  1, allora il pg e' finito nella cella del nemico, ha perso;
     * 				 -  2, il pg e' finito nella cella con il premio, ha vinto;
     * 				 - -1, la mossa non era valida oppure prevedeva di andare in una cella non accessibile;
     * 				 -  0, la mossa e' valida e il pg viene spostato, aggiornando la mappa di esplorazione
     * 					   e la sua posizione corrente, segnando la cella in cui si trovata prima come visitata.
     */
      private static int movePG(Direction move, GameMap gm, GameMap em, Score score) {
          //variabile ausiliaria
          int status;
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
                  em.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                  //si aggiorna lo status
                  status = 1;
              }//fi
              else if (cs.equals(CellStatus.DANGER)) {
                  //il pg e' caduto nella trappola
                  //si aggiorna la posizione
                  PlayableCharacter.setPGposition(cell_pos);
                  //si aggiunge alla mappa di esplorazione
                  em.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                  //si aggiorna lo status
                  status = 1;
              }//fi
              else if (cs.equals(CellStatus.FORBIDDEN)) {
                  //questa cella e' vietata perche' e' un sasso
                  //si aggiunge alla mappa di esplorazione
                  em.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                  //il pg rimane dove si trova
                  status = -1;
              }//fi
              else if (cs.equals(CellStatus.AWARD)) {
                  //il pg vince
                  //si aggiorna la posizione
                  PlayableCharacter.setPGposition(cell_pos);
                  //si aggiunge alla mappa di esplorazione
                  em.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                  //si restituisce lo stato
                  status = 2;
              }//fi
              else { //CellStatus.SAFE
                  //il pg si trova in una cella libera
                  //la cella in cui si trovava prima il pg si segna come visitata
                  em.getMapCell(pg_pos[0], pg_pos[1]).setCellStatus(CellStatus.OBSERVED);
                  //si aggiorna la posizione del pg
                  PlayableCharacter.setPGposition(cell_pos);
                  //si preleva il contenuto della cella in cui si trova attualmente il pg
                  Cell c = gm.getMapCell(cell_pos[0], cell_pos[1]);
                  //si copia questa cella nella matrice di esplorazione
                  em.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(c);
                  //il contenuto di questa cella nella mappa di esplorazione e' il pg
                  em.getMapCell(cell_pos[0], cell_pos[1]).setCellStatus(CellStatus.PG);
                  //si restituisce lo stato
                  status = 0;
              }//else
              //##### gestione del punteggio #####
              score.updateScore(cs);
          }//fi indici di mossa corretti
          else {
              //indici di mossa non validi
              status = -2;
          }//esle
          return status;
    }//movePG(Direction, GameMap, GameMap)

    /** metodo makeMoveInTheMap(int, GameMap, GameMap): String
     * questo metodo effettua la mossa vera e propria nella mappa di gioco
     * @param status: int, rappresenta il risultato della mossa effettuata;
     * @param gm: GameMap, mappa di gioco;
     * @return info: String, restituisce una stringa informativa sulla mossa
     *                      che e' stata effettuata, valutata in base al
     *                      valore della variabile statuts.
     */
    private static String makeMoveInTheMap(int status, GameMap gm){
        //stringa del messaggio da passare alla dialog
        String result="";
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
                    //modalita' eroe
                    if(currentActivity instanceof HeroSide)
                        info = currentActivity.getResources().getString(R.string.hero_enemy);
                    //modalita' wumpus
                    else
                        info = currentActivity.getResources().getString(R.string.wumpus_enemy);
                }//fi Enemy
                //else Danger
                else {
                    //modalita' eroe
                    if(currentActivity instanceof HeroSide)
                        info = currentActivity.getResources().getString(R.string.hero_danger);
                    //modalita' wumpus
                    else
                        info = currentActivity.getResources().getString(R.string.wumpus_danger);
                }//esle Danger
                //si preleva la stringa del risultato della partita
                result= currentActivity.getString(R.string.looser);
                //messaggio di fine partita
                info += "\n"+result;
                //fine della partita
                linkStop(false);
                break;
            case 2:
                //premio
                info = "Wow! "+currentActivity.getString(R.string.hero_award);
                //si preleva la stringa del risultato della partita
                result= currentActivity.getString(R.string.winner);
                //messaggio di fine partita: vittoria
                info += "\n"+result;
                //fine della partita
                linkStop(true);
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
        String danger_sense="";
        String enemy_sense="";
        String sensor_info="";
        //si fornisce il contenuto del vettore dei sensori
        //per la cella in cui si trova il pg
        //vettore dei sensori
        boolean [] sensors = new boolean[2];
        //si acquisiscono le informazioni del vettore dei sensori
        sensors=gm.getMapCell(pg_pos[0], pg_pos[1]).getSenseVector();
        //nessun tipo di pericolo
        if(!sensors[0] && !sensors[1]) {
            //nessun pericolo
            sensor_info = currentActivity.getResources().getString(R.string.safe_cell);
            //si restituisce la stringa
            return sensor_info;
        }//fi nessun avviso
        else {
            //vicinanza del nemico
            if (sensors[0]) {
                if (currentActivity instanceof HeroSide) {
                    enemy_sense = currentActivity.getResources().getString(R.string.hero_enemy_sense);
                }//fi
                else {
                    enemy_sense = currentActivity.getResources().getString(R.string.wumpus_enemy_sense);
                }//else
            }//fi nemico
            //vicinanza del pericolo
            if (sensors[1]) {
                if (currentActivity instanceof HeroSide) {
                    danger_sense = currentActivity.getResources().getString(R.string.hero_danger_sense);
                }//fi
                else {
                    danger_sense = currentActivity.getResources().getString(R.string.wumpus_danger_sense);
                }//else
            }//fi pericolo
            //si aggiorna la stringa da restituire
            sensor_info = enemy_sense + "\n" + danger_sense;
            //si restituisce
            return sensor_info;
        }//else
    }//checkEnvironment(int[], GameMap)

    //##### metodi di gestione dello sparo #####

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
    public static void tryToHit(Direction direction, GameMap gm, TextView game_message, TextView shots, TextView score_box, Score score) {
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
                //si aggiorna il punteggio
                score.hitScore();
                //si visualizza il punteggio aggiornato
                score_box.setText(""+score.getScore());
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

    //##### metodi di gestione della partita: avvio #####

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

    //##### metodi di gestione della partita: chiusura #####

    /** metodo linkStop(boolean): void
     * questo metodo si occupa di eseguire delle operazioni necessarie alla chiusura della
     * partita, come disabilitare il flag di avvio del gioco e quello che consentira' di
     * stabilire il messaggio sulla conclusione della partita,
     * per comunicare poi all'utente la vittoria o la sconfitta
     * @param status: boolean, flag che indica come si e' conclusa la partita, cioe'
     *              se true, allora il giocatore ha vinto, se false allora ha perso.
     */
    private static void linkStop(boolean status){
        //si disabilita il flag che indica che la partita e' ancora in corso
        Starter.setGameStart(false);
        //si imposta il flag sulla conclusione della partita
        victory=status;
    }//linkStop(boolean)

    /** metodo endGameSession(): void
     * questo metodo si occupa di visualizzare una dialog che richiede al giocatore
     * di condividere il punteggio ottenuto a fine partita
     */
    private static void endGameSession(Score score){
        //stringa che conterra' il messaggio da visualizzare nella dialog
        String result="";
        //si preleva il file di salvataggio delle preferenze del giocatore
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentActivity);
        //si identifica la preference relativa al nome del giocatore corrente
        String player = sharedPreferences.getString("prefUsername","");
        //si preleva il punteggio
        String points = String.valueOf(score.getScore());
        //si crea una alert dialog per chiedere al giocatore se vuole condividere il suo punteggio
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity,R.style.GameAlertDialogTheme);
        //si specifica il messaggio da visualizzare nella dialog di richiesta di condivisione del punteggio
        result=(victory?currentActivity.getString(R.string.winner):currentActivity.getString(R.string.looser))
        +"\n\n"+currentActivity.getResources().getString(R.string.current_score_share_request);
        //si configura il layout della dialog
        settingDialog(builder, result, player, points);
        //pulsante di chiusura
        builder.setNegativeButton(R.string.nope, new DialogInterface.OnClickListener() {
            //metodo onClick(DialogInterface, int)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //se si clicca annulla la dialog viene chiusa
                dialog.dismiss();
            }//onClick(DialogInterface, int)
        });//setNegativeButton(String, DialogInterface)
        //pulsante di conferma
        builder.setPositiveButton(R.string.yeah, new DialogInterface.OnClickListener() {
            //metodo onClick(DialogInterface, int)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //si verificano i permessi di accesso alla memoria esterna
                //si chiede all'utente di poter accedere alla memoria
                Utility.requestStoragePermission(currentActivity);
                //l'accesso alla memoria non e' consentito
                if(!Utility.storagePermissionGranted(currentActivity)){
                    //messaggio da visualizzare quando i permessi non sono stati garantiti
                    Toast.makeText(currentActivity,
                            currentActivity.getString(R.string.memory_access_denied),
                            Toast.LENGTH_LONG).show();
                }//fi
                //l'accesso alla memoria e' consentito
                else{
                    //si visualizza l'anteprima della condivisione
                    shareGameRank(player, points);
                }//esle
            }//onClick(DialogInterface, int)
        });//setPositiveButton(String, DialogInterface)
        //si crea la dialog
        AlertDialog share_dialog = builder.create();
        //visualizzazione della dialog
        share_dialog.show();
    }//endGameSession()

    /** metodo shareGameRank(): void
     * questo metodo si occupa di eseguire delle operazioni necessarie alla chiusura della
     * partita, come disabilitare il flag di avvio del gioco e visualizzare il punteggio ottenuto.
     */
    public static void shareGameRank(String player, String points){
        //si crea una alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity,R.style.GameAlertDialogTheme);
        //si preleva la stringa da visualizzare come messaggio della dialog
        String score_share_message = currentActivity.getResources().getString(R.string.current_score_share_message);
        //si configura il layout della dialog
        settingDialog(builder, score_share_message, player, points);
        //pulsante di chiusura
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            //metodo onClick(DialogInterface, int)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //se si clicca annulla la dialog viene chiusa
                dialog.dismiss();
            }//onClick(DialogInterface, int)
        });//setNegativeButton(String, DialogInterface)

        //pulsante di conferma
        builder.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
            //metodo onClick(DialogInterface, int)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //si identifica il campo del layout di cui si vuole catturare la schermata
                View end_game_map = currentActivity.findViewById(R.id.grid_view);
                //si dispone dei permessi quindi si effettua uno screenshot
                File imageFile = takeScreenshot(end_game_map);
                //si invia lo screenshot
                shareScreenshot(imageFile, player, points);
            }//onClick(DialogInterface, int)
        });//setPositiveButton(String, DialogInterface)
        //si preleva il componente grafico di cui fare lo screenshot
        View view = currentActivity.findViewById(R.id.grid_view);
        //si abilita la cattura dello schermo
        view.setDrawingCacheEnabled(true);
        //si crea l'immagine bitmap di questo screenshot
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        //si disabilita la possibilita' di tenerlo in cache
        view.setDrawingCacheEnabled(false);
        //si istanzia un oggetto di tipo immagine
        ImageView imageView = new ImageView(currentActivity);
        //si specifica il suo contenuto con la cattura dello schermo effettuata
        imageView.setImageBitmap(bitmap);
        //si crea la dialog
        AlertDialog dialog = builder.create();
        //si visualizza la mappa di gioco nella dialog
        dialog.setView(imageView);
        //visualizzazione della dialog
        dialog.show();
    }//endGameSession()

    /** metodo settingDialog(Alert.Builder, String, String, String)
     * questo emtodo si occupa di configurare il layout della dialog
     * impostando il testo e l'immagine.
     * Il testo cambia in base ai parametri ricevuti.
     * @param builder: Alert.Builder, costruttore della dialog;
     * @param text_message: String, messaggio da visualizzare nella dialog;
     * @param player: String, nome del giocatore;
     * @param points: String, valore del punteggio;
     */
    private static void settingDialog(AlertDialog.Builder builder, String text_message, String player, String points){
        //si assegna un layout alla dialog
        builder.setView(R.layout.alert_dialog);
        //si definisce il testo del titolo
        TextView textView = new TextView(currentActivity);
        //testo del titolo della dialog
        textView.setText(R.string.share_score_title);
        //padding del titolo
        textView.setPadding(20, 30, 20, 30);
        //dimensione del titolo
        textView.setTextSize(20F);
        //sfondo del titolo
        textView.setBackgroundColor(Color.parseColor("#5c007a"));
        //colore del testo del titolo
        textView.setTextColor(Color.WHITE);
        //si imposta il titolo della dialog
        builder.setCustomTitle(textView);
        //si imposta il messaggio della dialog
        String message = text_message+"\n\n"+player+" "+points+" pt";
        //si visualizza il messaggio nella dialog
        builder.setMessage(message);
        //la dialog si chiude cliccando al di fuori della sua area
        builder.setCancelable(true);
    }//settingDialog(AlertDialog.Builder, String)

    //##### metodi per effettuare lo screenshot della mappa di gioco finale #####

    /** metood takeScreenshot(View): void
     * questo metodo effettua uno screenshot dell'oggetto view che riceve come parametro
     * e lo salva nella memoria esterna se i permessi di scrittura/lettura sono stati
     * approvati dall'utente.
     * @param view : View, oggetto del layout di cui effettuare lo screenshot.
     */
    private static File takeScreenshot(View view) {
        //inizializzazione del file da restituire
        File imageFile = null;
        //blocco try-catch per la creazione del file
        try {
            //si crea la cartella in cui conservare il file
            File mainDir =
                    new File(currentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FileShare");
            //si controlla se questa cartella esiste
            if (!mainDir.exists()) {
                //se non esiste viene creata
                mainDir.mkdir();
            }//fi
            //si assegna il nome al file che conterra lo screenshot
            String path = mainDir + "/"+"WumpusWorldGameMap" + ".png";
            //si abilita la possibilita' di tenerlo in cache
            view.setDrawingCacheEnabled(true);
            //si crea l'immagine bitmap di questo screenshot
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            //si disabilita la possibilita' di tenerlo in cache
            view.setDrawingCacheEnabled(false);
            //si memorizza il file con il contenuto bitmap nel path definito prima
            imageFile = new File(path);
            //si crea il file vero e proprio
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            //si comprime la qualita' del file di tipo immagine
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            //si svuota il buffer di scrittura
            fileOutputStream.flush();
            //si chiude il flusso di scrittura
            fileOutputStream.close();
            //fine del processo
        }catch (IOException e) {
            //errore
            e.printStackTrace();
        }//end try-catch
        // si restituisce il file immagine creato
        return imageFile;
    }//takeScreenShot(View)

    /** metodo shareScreenshot(File): void
     * questo metodo implementa la condivione, tramite Intent, del file immagine
     * ricevuto come parametro.
     * @param imageFile: File, immagine da condividere al di fuori dell'applicazione.
     */
    private static void shareScreenshot(File imageFile, String player, String score) {
        //intent che effettuera' l'azione di condivisione
        Intent shareIntent = new Intent();
        //variabile che rappresentera' il file immagine
        Uri uri;
        //controllo sulla versione dell'sdk
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //per la condivisione si deve utilizzare un Content Provider
            uri = FileProvider.getUriForFile(currentActivity, currentActivity.getApplicationContext().getPackageName() + ".provider", imageFile);
        }//fi
        else {
            //per le versioni inferiori alla sdk 23
            uri = Uri.fromFile(imageFile);
        }//else
        //si specifica l'azione di invio per l'intent
        shareIntent.setAction(Intent.ACTION_SEND);
        //si specifica che verra' inviata un'immagine
        shareIntent.setType("image/*");
        //si crea la stringa che conterra' il punteggio
        String current_score = player+"   "+score+" pt";
        //si specifica il testo da condividere oltre l'immagine
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                currentActivity.getString(R.string.share_game_map_and_score)
                        +current_score+"\n"+currentActivity.getString(R.string.app_link));
        //si invia l'immagine
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //blocco try-catch sulla scelta delle app esterne per il servizio di condivisione
        try {
            //si visualizzano le applicazioni idonee alla condivisione dell'immagine
            currentActivity.startActivity(Intent.createChooser(shareIntent, "Share With"));
        }catch (ActivityNotFoundException e){
            //nessuna app e' adatta a svolgere questa azione
            Toast.makeText(currentActivity,currentActivity.getString(R.string.no_app),
                    Toast.LENGTH_SHORT).show();
        }//end try-catch
    }//shareScreenshot(File)

}//end GameController
