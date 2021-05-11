package com.example.wumpusworldgame.menuItems;
//serie di import
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.activities.HeroSide;
import com.example.wumpusworldgame.activities.WumpusSide;
import game.structure.text.GameMessages;
/** class GameInformationActivity
 * questa classe fornisce delle informazioni sul gioco
 */
public class GameInformationActivity extends AppCompatActivity {
    //##### attributi di classe #####

    //intero che specifica la modalita' di gioco
    public static int game_mode;
    //riproduttore audio
    MediaPlayer mp;

    //questo metodo viene invocato alla creazione dell'Activity
    /** metodo onCreate()
     * questo metodo viene invocato alla creazione
     * dell'Activity corrente
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //con super si invoca il metodo omonimo della classe antenata
        super.onCreate(savedInstanceState);
        //si specifica il file che descrive il layout
        setContentView(R.layout.game_info_activity);

         //##### inizializzazioni #####

        //riproduttore audio
        mp = MediaPlayer.create(GameInformationActivity.this,R.raw.menu_music);
        //campo di testo dei crediti
        TextView tcredits = (TextView)findViewById(R.id.textCredits);

        //##### esecuzione delle azioni #####

        //esecuzione clip audio
        mp.start();
        //visualizzazione dei crediti
        tcredits.setText(GameMessages.credits);

    }//onCreate(Bundle)

    //##### metodi accessori #####

    /** metodo setGameMode(int): void
     * questo metodo assegna il valore dell'intero,
     * ricevuta come parametro, all'attributo di classe.
     * @param value_game_mode: int, intero che indica da quale
     *                       activity e' stata avviata quella corrente.
     */
    public static void setGameMode(int value_game_mode){
        //si assegna il parametro alla variabile di classe
        game_mode=value_game_mode;
    }//setGameMode(int)

    /** metodo getGameMode(): int
     * questo metodo restituisce l'intero che identifica
     * la modalita' di gioco, ovvero l'activity da cui e'
     * stata avviata quella corrente.
     * @return game_mode: int, intero che rappresenta la
     *                  modalita' di gioco.
     */
    public static int getGameMode(){
        //si restituisce il valore dell'attributo di classe
        return game_mode;
    }//getGameMode()

    //##### metodi per la gestione dell'activity #####

    /** metodo onStart(): void
     * ACTIVITY VISIBILE
     * questo metodo si occupa di attivare le funzionalita'
     * ed i servizi che devono essere mostrati all'utente
     */
    @Override
    public void onStart() {
        //si invoca il metodo della super classe
        super.onStart();
        //si avvia la clip audio
        mp.start();
    }//onStart()

    /** metodo onResume():void
     * ACTIVITY RICEVE INTERAZIONE
     */
    @Override
    protected void onResume() {
        //si invoca il metodo della super classe
        super.onResume();
    }//onResume()

    /** metodo onPause(): void
     * metodo opposto di onResume()
     * ACTIVITY CESSA INTERAZIONE
     * questo metodo viene invocato per notificare la cessata
     * interruzione dell'utente con l'activity corrente
     */
    @Override
    protected void onPause() {
        //si invoca il metodo della super classe
        super.onPause();
        //si ferma la clip audio quando l'activity viene sospesa
        mp.pause();
    }//onPause()

    /** metodo onStop(): void
     * metodo opposto di onStart()
     * ACTIVITY NON VISIBILE
     */
    @Override
    public void onStop(){
        //si invoca il metodo della super classe
        super.onStop();
    }//onStop()

    /** metodo onDestroy(): void
     * metodo opposto di onCreate()
     * ACTIVITY DISTRUTTA
     */
    @Override
    public void onDestroy(){
        //si invoca il metodo della super classe
        super.onDestroy();
        //si rilascia la risorsa del mediaplayer
        mp.release();
    }//onDestroy()

    /** metodo onRestart(): void
     * l'utente ritorna all'activity
     * viene invocato prima di onCreate()
     */
    @Override
    public void onRestart(){
        //si invoca il metodo della super classe
        super.onRestart();
    }//onRestart()

    /** metodo onBackPressed(): void
     * questo metodo implementala navigazione all'indietro
     * permettendo di ritornare dall'activity corrente a quella
     * appena precedente, utilizzando il tasto di navigazione BACK.
     * Le activity vengono conservate in memoria, quando non sono
     * piu' in primo piano, secondo un ordine a Stack.
     */
    @Override
    public void onBackPressed(){
        //variabile ausiliaria
        Intent myIntent;
        //HERO SIDE
        if(game_mode==1) {
            //ritorno all'activity HeroSide, quella precedente
            myIntent = new Intent(this, HeroSide.class);
        }//fi
        //WUMPUS SIDE
        else { //game_mode==0
            //ritorno all'activity WumpusSide, quella precedente
            myIntent = new Intent(this, WumpusSide.class);
        }//esle
        /*si imposta questo flag in modo che se l'activity che sta per essere
         *avviata e' gia' in esecuzione nel task corrente, allora, invece di
         *di lanciare una nuova istanza, tutte le altre activity che si trovano
         *in cima allo stack verrano chiuse, in modo che questa Intent venga
         *riportata all'activity precendete, che ora si trova in cima allo stack.
         *CLEAR BACK STACK
         */
        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //avvio dell'activity
        startActivity(myIntent);
        //chiusura dell'activity corrente, verra' eseguito il metodo onDestroy()
        finish();
        /*per ritornare all'activity precedente
         *e' stato aggiunto nel manifest
         *android:launchMode="singleTop"
         */
        return;
    }//onBackPressed()

}//end GameInformationActivity
