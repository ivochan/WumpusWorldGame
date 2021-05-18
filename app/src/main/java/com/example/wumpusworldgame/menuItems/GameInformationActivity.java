package com.example.wumpusworldgame.menuItems;
//serie di import
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wumpusworldgame.R;
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
        //si rischiama il metodo della super classe
        super.onBackPressed();
    }//onBackPressed()

}//end GameInformationActivity
