package com.example.wumpusworldgame.gameMenuItems.automaticMode;
//import
import game.session.configuration.Starter;
import game.session.controller.Direction;
import game.structure.cell.Cell;
import game.structure.cell.CellStatus;
import game.structure.elements.PlayableCharacter;
import game.structure.map.GameMap;
/**
 *
 */
public class AutomaticPlayer {
    //##### attributi di classe #####

    private GameMap gm;
    private GameMap em;
    private int[] pg_pos;

    //non statica, deve essere resettata per ogni nuova istanza della classe
    private boolean gameOver = false;

    //per debug
    private String sensorInfo = new String();
    private String moveInfo  =new String();

    /**
     * @param gm
     * @param em
     */
    public AutomaticPlayer(GameMap gm, GameMap em) {
        //controllo sui parametri
        if (gm == null) throw new IllegalArgumentException("mappa di gioco nulla");
        if (em == null) throw new IllegalArgumentException("mappa di esplorazione nulla");
        //si assegnano i parametri agli attributi di classe
        this.gm = gm;
        this.em = em;
        //si preleva la posizione del pg
        pg_pos = PlayableCharacter.getPGposition();
    }

    /**
     * @param gm
     * @param em
     * @param pg_position
     */
    public AutomaticPlayer(GameMap gm, GameMap em, int[] pg_position) {
        //controllo sui parametri
        if (pg_position == null || pg_position.length < 2)
            throw new IllegalArgumentException("vettore posizione del pg non valido");
        if (gm == null) throw new IllegalArgumentException("mappa di gioco nulla");
        if (em == null) throw new IllegalArgumentException("mappa di esplorazione nulla");
        //si assegnano i parametri agli attributi di classe
        this.gm = gm;
        this.em = em;
        //inizializzazione del vettore posizione
        pg_pos = new int[2];
        //si copiano i valori nelle celle del vettore di classe
        pg_pos[0] = pg_position[0];
        pg_pos[1] = pg_position[1];
    }


    /**
     *
     */
    public void solve() {
        //
        String sense=new String();
        //ciclo di risoluzione
        while (!gameOver) {
            //il giocatore sceglie la mossa da eseguire
            chooseGameMove();
            //il giocatore sceglie la direzione della mossa
            chooseMoveDirection();
            //il giocatore effettua la mossa e viene aggiornata la mappa di esplorazione
            makeGameMove();
            //se la sessione di gioco e' conclusa si esce dal ciclo
        }

    }

    private void makeGameMove() {
        gameOver = true;
    }

    private void chooseMoveDirection() {
    }

    private void chooseGameMove() {
        //variabile ausiliaria per la direzione
        Direction dir;
        //variabile ausiliaria per i sensori
        boolean[] sensors = new boolean[2];
        //si preleva la cella in cui si trova il pg nella matrice di esplorazione
        Cell current = em.getMapCell(pg_pos[0],pg_pos[1]);
        //si preleva il contenuto del vettore dei sensori
        sensors = current.getSenseVector();
        //per dubug
        sensorInfo = current.senseVectorToString(true);
        //verifica del contenuto
        if(sensors[CellStatus.ENEMY_SENSE.ordinal()]) {
            //il nemico e' nelle vicinanze
            moveInfo="Il nemico e' in agguato!";
            //si verifica la disponibilita' del colpo
            if(Starter.getChanceToHit()) {
                //si sceglie la direzione in cui colpire
                dir = chooseDirection(pg_pos[0],pg_pos[1],gm);
                //almeno una delle celle non e' stata visitata se il sensore e' acceso
                moveInfo="Tentativo di sparo verso "+dir.toString();
                //si tenta il colpo
                //Controller.hitEnemy(shot_dir, gm);
                //si resetta il flag
                Starter.setChanceToHit(false);
            }//fi
            else {
                //non si hanno munizioni
                moveInfo="non si hanno munizioni";
                //si sceglie la direzione in cui fare muovere il pg
                //dir = chooseDirection(pg_pos[0], pg_pos[1], gm);
                //si sceglie la direzione in cui muovere il pg
                //status = Controller.movePG(dir, gm, em);
                //si controlla la mossa
                //Controller.makeMove(status, gm, em);
                //aggiornamento del percorso

            }//else
        }//fi
        else if(sensors[CellStatus.DANGER_SENSE.ordinal()]) {
            //il pericolo e' vicino
            moveInfo="Il pericolo e' vicino...";
            //si preferisce come direzione una cella non visitata
            //dir = chooseDirection(pg_pos[0], pg_pos[1], gm);
            //si sceglie la direzione in cui muovere il pg
            //status = Controller.movePG(dir, gm, em);
            //si controlla la mossa
            //Controller.makeMove(status, gm, em);
            //aggiornamento del percorso
        }
        else {
            //entrambi i sensori sono spenti
            moveInfo="posto sicuro";
            //si sceglie una direzione a caso, tra quelle non esplorate
            //dir = chooseDirection(pg_pos[0], pg_pos[1], gm);
            //si sceglie la direzione in cui muovere il pg
            //status = Controller.movePG(dir, gm, em);
            //si controlla la mossa
            //Controller.makeMove(status, gm, em);
            //aggiornamento del percorso
            //if(status!=-1)updateRunPath(gm.getMapCell(pg_pos[0], pg_pos[1]));
        }

    }


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

    /** metodo pickCell(boolean []): int
     * questo metodo seleziona casualmente una cella
     * tra quelle indicate come true, nel vettore ricevuto come
     * parametro.
     * @param vcells: boolean[], vettore delle celle tra cui
     * 							 selezionare quella in cui effettuare
     * 							 la mossa;
     * @return random: int, indice della cella da estrarre.
     */
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

    /** metodo verifyCell(int, int, GameMap): boolean
     * questo metodo verifica se la cella in cui si vuole spostare
     * il personaggio giocabile sia idonea o no, in base al suo contenuto.
     * Infatti, per essere una scelta valida, la cella non deve essere ne'+
     * un sasso ne' essere stata gia' visitata.
     * @param i: int, indice di riga della cella in cui si trova il pg;
     * @param j: int, indice di colonna della cella in cui si trova il pg;
     * @param em: GameMap, mappa di esplorazione, visibile al pg;
     * @return boolean: true, se la cella e' idonea, false altrimenti,. .
     */
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

    /** metodo checkCells(boolean []): boolean
     * questo metodo verifica se questo vettore contiene almeno una
     * cella che sia pari a true. Se cosi' non e' sceglie una cella
     * a caso tra quelle gia' visitate.
     * @param ok_cells: boolean [], vettore di celle risultate idonee
     * @return check: boolean, questo flag sara' true se almeno una cella
     * 						   del vettore e' risultata idonea, false altrimenti.
     */
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

}//end AutomaticPlayer

