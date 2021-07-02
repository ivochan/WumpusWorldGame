package com.example.wumpusworldgame.mainMenuItems.tutorial;
//serie di import
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.wumpusworldgame.R;
/** class MainTutorialActivity
 * questa classe fornisce un breve tutorial introduttivo al gioco
 */
public class MainTutorialActivity extends AppCompatActivity {
    //##### attributi di classe #####

    //pulsante avanti
    private Button next_button;
    //intent della pagina successiva
    private Intent next_page;

    //TODO riproduttore audio
    //private MediaPlayer mp;

    /** metodo onCreate(Bundle): void
     * questo metodo si occupa della CREAZIONE dell'ACTIVITY
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //si invoca il metodo della super classe
        super.onCreate(savedInstanceState);
        //si assegna il layout all'activity corrente
        setContentView(R.layout.activity_main_tutorial);

        //##### inizializzazioni #####

        //pulsante di navigazione in avanti
        next_button = findViewById(R.id.next_page_button);
        //intent della activity che corrisponde alla pagina successiva del tutorial
        next_page = new Intent(this, GamePadMainTutorialActivity.class);

        //scelta della clip audio
        //mp = MediaPlayer.create(HeroSide.this,R.raw.the_good_fight);

        //##### azioni #####

        //verifica dell'esecuzione della traccia audio
        //Utility.musicPlaying(mp, this);

        //##### gestione del pulsante #####


        //metodo setOnClickListener(View.OnclickListener())
        next_button.setOnClickListener(new View.OnClickListener() {
            /** metodo onClick(View): void
             * questo metodo definisce le azioni che si devono eseguire
             * alla pressione del punsante.
             * In questo caso verra' avviata una nuova activity, il cui
             * layout conterra' la descrizione dei comandi di gioco,
             * percio' rappresenta la seconda pagina del tutorial.
             * @param view: View
             */
            @Override
            public void onClick(View view) {
                //si avvia l'activity che corrisponde alla pagina successiva del tutorial
                startActivity(next_page);
            }//onClick(View)
        });//setOnClickListener(View.OnclickListener())

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
        //si ferma la clip audio quando l'app viene sospesa
        //mp.pause();
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
        //mp.release();
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
    public void onBackPressed() {
        //si invoca il metodo della super classe
        super.onBackPressed();
    }//onBackPressed()

}//end MainTutorialActivity