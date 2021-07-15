package com.example.wumpusworldgame.gameMenuItems.automaticMode;
//import
import android.widget.TextView;
import game.session.controller.Controller;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/** class AutomaticPlayer
 *
 */
public class AutomaticPlayer {
    //##### attributi di classe #####

    //mappa di gioco
    private GameMap gm;
    //mappa di esplorazione
    private GameMap em;
    //posizione del pg
    private  int[] pg_position = new int[2];

    //flag non statica, deve essere resettata per ogni nuova istanza della classe
    private boolean gameOver;
    //flag che indica il termine della risoluzione della partita per aggiornare la grafica
    private static boolean endAutomaticSession;
    //flag statica per la disponibilita' delle munizioni per lo sparo
    private static boolean automaticChanceToHit;

    //##### stringhe di debug #####
    private String sensorInfo = new String();
    private String moveInfo = new String();

    /** costruttore AutomaticPlayer(GameMap, GameMap)
     * @param gm
     * @param em
     */
    public AutomaticPlayer(GameMap gm, GameMap em) {
        //controllo sui parametri
        if (gm == null) throw new IllegalArgumentException("mappa di gioco nulla");
        if (em == null) throw new IllegalArgumentException("mappa di esplorazione nulla");
        //si assegnano i parametri agli attributi di classe
        this.gm=gm;
        this.em=em;
        //variabile temporanea per prelevare la posizione del pg
        int [] temp_pg_pos = PlayableCharacter.getPGposition();
        //si assegna questa posizione all'attributo di classe
        this.pg_position[0] = temp_pg_pos[0];
        this.pg_position[1] = temp_pg_pos[1];
        //si resetta il flag della sessione di gioco automatica
        setEndAutomaticSession(false);
        //flag di displonibilita' del colpo
        automaticChanceToHit = true;
        //debug
        //shot= " shot: ";
    }//end AutomaticPlayer(GameMap, GameMap)

    //##### metodo di risoluzione della mappa di gioco #####

    /** metodo solve() : void
     *
     */
    public void solve(TextView shots) {
        //si visualizza il numero di colpi
        shots.setText("1");
        //ciclo di risoluzione
        while (!gameOver) {
            //il giocatore sceglie la mossa da eseguire
            chooseGameMove();
            //aggiornamento del campo di testo che identifica lo sparo
            if(!automaticChanceToHit)shots.setText("0");
        }//end while
        // la sessione di gioco e' conclusa
    }//solve()

    //##### metodi per la scelta e la realizzazione della mossa #####

    /** metodo chooseGameMove()
     *
     */
    private void chooseGameMove() {
        //variabile ausiliaria
        String s = new String();
        //variabile ausiliaria per lo stato della mossa
        int status = 3;
        //variabile ausiliaria per la direzione
        Direction dir;
        //variabile ausiliaria per i sensori
        boolean[] sensors = new boolean[2];
        //si preleva la cella in cui si trova il pg nella matrice di esplorazione
        Cell current = em.getMapCell(pg_position[0],pg_position[1]);
        //si preleva il contenuto del vettore dei sensori
        sensors = current.getSenseVector();
        //per dubug
        sensorInfo = current.senseVectorToString(true);
        //verifica del contenuto
        if(sensors[CellStatus.ENEMY_SENSE.ordinal()]) {
            //il nemico e' nelle vicinanze
            moveInfo="Il nemico e' in agguato!";
            //si verifica la disponibilita' del colpo
            if(automaticChanceToHit){
                //si sceglie la direzione in cui colpire
                dir = chooseDirection(pg_position[0],pg_position[1],gm);
                //almeno una delle celle non e' stata visitata se il sensore e' acceso
                moveInfo="Tentativo di sparo verso "+dir;
                //si tenta il colpo
                Controller.hitEnemy(dir, gm);
                //si resetta il flag
                automaticChanceToHit = false;
                //shot+="yep";
            }//fi
            else {
                //non si hanno munizioni
                moveInfo="non si hanno munizioni";
                //si sceglie la direzione in cui fare muovere il pg
                dir = chooseDirection(pg_position[0], pg_position[1], gm);
                //si verifica il risultato della mossa
                status = movePG(dir, gm, em);
                moveInfo+="\nmuovo verso "+dir;
                //si controlla la mossa
                s = makeMove(status, gm);
                moveInfo +="\n"+s;
                //aggiornamento del percorso
            }//else
        }//fi
        else if(sensors[CellStatus.DANGER_SENSE.ordinal()]) {
            //il pericolo e' vicino
            moveInfo="Il pericolo e' vicino...";
            //si preferisce come direzione una cella non visitata
            dir = chooseDirection(pg_position[0], pg_position[1], gm);
            //si verifica il risultato della mossa
            status = movePG(dir, gm, em);
            moveInfo+="\nmuovo verso "+dir;
            //si controlla la mossa
            s = makeMove(status, gm);
            moveInfo+="\n"+s;
            //aggiornamento del percorso
        }
        else {
            //entrambi i sensori sono spenti
            moveInfo="posto sicuro";
            //si sceglie una direzione a caso, tra quelle non esplorate
            dir = chooseDirection(pg_position[0], pg_position[1], gm);
            //si verifica il risultato della della mossa
            status = movePG(dir, gm, em);
            moveInfo+="\nmuovo verso "+dir;
            //si controlla la mossa
            s = makeMove(status, gm);
            moveInfo +="\n"+s;
            //aggiornamento del percorso
            //if(status!=-1)updateRunPath(gm.getMapCell(pg_pos[0], pg_pos[1]));
        }
        //moveInfo+=shot;
    }//end chooseGameMove()

    /** metodo makeMove(int, GameMap): String
     *
     * @param status
     * @param gm
     * @return
     */
    public String makeMove(int status, GameMap gm) {
        //variabile ausilaria per la cella della mappa
        Cell c = new Cell();
        //variabile ausiliaria
        String s  =new String();
        //risultato della mossa effettuata
        switch(status) {
            //codici di uscita associati alla mossa
            case -1 :
                s = "Il passaggio e' bloccato da un sasso.\nRipeti la mossa!";
                break;
            case 0 :
                //informazioni sulla posizione
                s = "ti trovi in "+getPGposition();
                //si preleva la cella in cui si trova il pg
                c = gm.getMapCell(pg_position[0],pg_position[1]);
                //si visualizzano le informazioni dei sensori
                s +="\n"+c.senseVectorToString(true);
                break;
            case 1:
                //informazioni della cella in cui si e' mosso il pg
                c = gm.getMapCell(pg_position[0], pg_position[1]);
                //info sullo stato
                CellStatus cs = c.getCellStatus();
                //stampa del messaggio se nemico
                if(cs.equals(CellStatus.ENEMY)){
                    s = "Hai perso, ti ha ucciso il nemico";
                }
                else {
                    //stampa del messaggio se pericolo
                    s = "Hai perso, sei caduto in trappola";
                }
                //fine della partita
                gameOver=true;
                //flag di termine della sessione di gioco automatica
                setEndAutomaticSession(true);
                break;
            case 2:
                //hai trovato il premio
                s = "Hai vinto";
                //fine della partita
                gameOver=true;
                //flag di termine della sessione di gioco automatica
                setEndAutomaticSession(true);
                break;
            case -2:
                s = "Comando non valido...\nRipeti la mossa!";
                break;
            default:
                break;
        }//end switch
        return s;
    }//makeMove(int)

    /** metodo movePG(Direction, GameMap, GameMap): int
     * metodo che realizza la mossa del pg
     * @param move
     * @param gm
     * @param ge
     * @return
     */
    public int movePG(Direction move, GameMap gm, GameMap ge) {
        //vettore della posizione successiva
        int [] cell_pos= new int[2];
        //variabile da restituire
        int status = 0;
        //si controlla il risultato della direzione scelta
        cell_pos = Controller.findCell(move, pg_position);
        //la cella indicata da next_pos esiste
        if(Controller.checkCell(cell_pos, gm)) {
            //si controlla il contenuto della cella in questione
            CellStatus cs = gm.getMapCell(cell_pos[0], cell_pos[1]).getCellStatus();
            //controllo sullo stato
            if(cs.equals(CellStatus.ENEMY)) {
                //il pg e' stato ucciso dal nemico
                status = 1;
                //si deve aggiornare la posizione del PG
                setPGposition(cell_pos);
            }//fi
            else if(cs.equals(CellStatus.FORBIDDEN)) {
                //questa cella e' vietata perche' e' un sasso
                status = -1;
                //si aggiunge alla mappa di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).
                        copyCellSpecs(gm.getMapCell(cell_pos[0], cell_pos[1]));
                //il pg rimane dove si trova
            }//fi
            else if(cs.equals(CellStatus.AWARD)) {
                //il pg vince
                status = 2 ;
                //si aggiorna la posizione
                setPGposition(cell_pos);
            }//fi
            else if(cs.equals(CellStatus.DANGER)) {
                //il pg e' caduto nella trappola
                status = 1;
                //si aggiorna la posizione
                setPGposition(cell_pos);
            }//fi
            else {//CellStatus.SAFE
                //il pg si trova in una cella libera
                status = 0;
                //la cella in cui si trovava prima il pg si segna come visitata
                ge.getMapCell(pg_position[0], pg_position[1]).setCellStatus(CellStatus.OBSERVED);
                //si aggiorna la posizione del pg
                setPGposition(cell_pos);
                //si preleva il contenuto della cella in cui si trova attualmente il pg
                Cell c = gm.getMapCell(cell_pos[0], cell_pos[1]);
                //si copia questa cella nella matrice di esplorazione
                ge.getMapCell(cell_pos[0], cell_pos[1]).copyCellSpecs(c);
                //il contenuto di questa cella nella mappa di esplorazione e' il pg
                ge.getMapCell(cell_pos[0], cell_pos[1]).setCellStatus(CellStatus.PG);
            }//esle
            //aggiornamento del punteggio
        }//fi indici di mossa corretti
        else {
            //comando non valido, oppure la cella non esiste
            status = -2;
        }//esle
        //si restituisce il codice associato al tipo di mossa
        return status;
    }//movePG(Direction, GameMap, GameMap)

    //##### metodi accessori #####

    /** metodo setPGposition(int[]): void
     * metodo che si occupa di aggiornare la posizione del
     * pg nella mappa di esplorazione, dopo che e' stata effettuata
     * la mossa nella mappa di gioco
     * @param position
     */
    public void setPGposition(int [] position) {
        //controllo sul vettore della posizione
        if(position==null) throw new IllegalArgumentException("vettore della posizione nullo");
        //controllo sulla dimensione del vettore
        if(position.length!=2) throw new IllegalArgumentException("il vettore della posizione"
                + " non ha una dimensione valida");
        //si preleva l'indice di riga
        int i = position[0];
        //si preleva l'indice di colonna
        int j = position[1];
        //si assegna l'indice di riga
        pg_position[0]=i;
        //si assegna l'indice della colonna
        pg_position[1]=j;
    }//setPGposition(int,int, Map)

    /**
     *
     * @return
     */
    public String getPGposition(){
        //variabile ausiliaria
        String position = new String();
        //si riempie la stringa
        position+="("+pg_position[0]+','+pg_position[1]+')';
        //si restituisce la stringa che indica la posizione del pg
        return position;
    }

    /**
     *
     * @return
     */
    public static boolean getEndAutomaticSession(){
        //si restituisce il flag
        return endAutomaticSession;
    }//getEndAutomaticSession()

    /**
     *
     * @param flag
     * @return
     */
    public static void setEndAutomaticSession(boolean flag){
        //si assegna il flag all'attributo di classe
        endAutomaticSession=flag;
    }//setEndAutomaticSession(boolean)

    //##### metodi accessori per le stampe di debug #####

    /**
     *
     * @return
     */
    public String getSensorInfo(){
        return sensorInfo;
    }

    /**
     *
     * @return
     */
    public String getMoveInfo(){
        return moveInfo;
    }

    //##### metodi per la scelta della direzione #####

    public Direction chooseDirection(int i, int j, GameMap em) {
        //vettore ausiliario per le celle accessibili e non visitata
        boolean [] ok_cells = new boolean[4];
        //vettore ausiliario per le celle esistenti
        boolean [] cells = new boolean[4];
        //indice di cella casuale
        int random = 0;
        //variabile ausiliaria per l'indice di cella
        int index = 0;
        //si prelevano le direzioni possibili
        Direction [] directions = Direction.values();
        //si verificano le celle dispoonibili
        if(em.cellExists(i-1, j)) {
            //si calcola l'indice
            index = Direction.UP.ordinal();
            //la cella esiste
            cells[index] = true;
            //si verifica la cella e si aggiorna il vettore
            ok_cells[index] = verifyCell(i-1,j, em);
        }//UP
        if(em.cellExists(i+1, j)) {
            //si calcola l'indice
            index = Direction.DOWN.ordinal();
            //la cella esiste
            cells[index] = true;
            //si verifica la cella e si aggiorna il vettore
            ok_cells[index] = verifyCell(i+1,j, em);
        }//DOWN
        if(em.cellExists(i, j-1)) {
            //si calcola l'indice
            index = Direction.LEFT.ordinal();
            //la cella esiste
            cells[index] = true;
            //si verifica la cella e si aggiorna il vettore
            ok_cells[index] = verifyCell(i,j-1, em);
        }// LEFT
        if(em.cellExists(i, j+1)) {
            //si calcola l'indice
            index = Direction.RIGHT.ordinal();
            //la cella esiste
            cells[index] = true;
            //si verifica la cella e si aggiorna il vettore
            ok_cells[index] = verifyCell(i,j+1, em);
        }//RIGHT
        //controllo sul contenuto del vettore ok_cells
        if(checkCells(ok_cells)){
            //si sceglie casualmente una cella tra quelle idonee
            random = pickCell(ok_cells);
        }//fi
        else {
            //si prende una cella a caso tra quelle accessibili ma gia' visitate
            random = pickCell(cells);
        }//else
        //si estrae la direzione scelta
        Direction dir = directions[random];
        //si restituisce la direzione
        return dir;
    }//chooseDirection(int, int, GameMap)

    //TODO da richiamare dalla libreria
     private int pickCell(boolean [] vcells) {
        //range del numero casuale
        int range = vcells.length;
        //variabile ausiliaria
        boolean found = false;
        //numero casuale
        int random = 0;
        //si sceglie casualmente una cella tra quelle idonee
        while(!found) {
            //si genera un numero casuale
            random = (int)(Math.random()*range);
            //System.out.println("random "+random);
            //si accede alla cella corrispondente
            found = vcells[random];
            //se true si esce dal ciclo
        }//end while
        //si restituisce l'indice della cella selezionata
        return random;
    }//pickCell(boolean[])

    //TODO da richiamare dalla libreria
     private boolean verifyCell(int i, int j, GameMap em) {
        //si preleva la cella
        CellStatus cs = em.getMapCell(i, j).getCellStatus();
        //si verifica che non e' un sasso
        if(!cs.equals(CellStatus.FORBIDDEN)) {
            //si verifica che la cella non e' stata visitata
            if(!cs.equals(CellStatus.OBSERVED)) {
                //la cella e' idonea
                return true;
            }//fi
            return false;
        }//fi
        //la cella non e' idonea
        return false;
    }//verifyCell(int, int, GameMap)

    //TODO da richiamare dalla libreria
      private boolean checkCells(boolean [] ok_cells) {
        //variabile ausiliaria per il controllo
        boolean check = false;
        //si itera il vettore
        for(int i=0; i<ok_cells.length;i++) {
            //System.out.println("cella "+i+" = "+ok_cells[i]);
            //si preleva il contenuto della cella
            if(ok_cells[i]) check = true;
        }//end for
        return check;
    }//checkCells(boolean [])

    /*
    public static void hitEnemy(Direction dir, GameMap gm) {
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
        enemy_indices = findCell(dir, pg_pos);
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
            defeated = searchForEnemy(i, j, dir, gm);
            //si controlla se e' stato colpito
            if(defeated) {
                //colpo andato a segno
                System.out.println(GameMessages.hit);
                //si aggiornano i sensori
                resetEnemySensor(gm);
                //si aggiorna il punteggio
                Score.hitScore();
            }//fi
            else {
                //colpo errato
                System.out.println(GameMessages.wasted_shot);
            }//esle
        }//fi indici cella
        else {
            //gli indici di cella non sono validi
            System.out.println(GameMessages.failed_shot);
        }//esle
    }//hitEnemy()
     */
}//end AutomaticPlayer

