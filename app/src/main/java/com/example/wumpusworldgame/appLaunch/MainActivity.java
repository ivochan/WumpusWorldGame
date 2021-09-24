package com.example.wumpusworldgame.appLaunch;
//serie di import
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameActivities.HeroSide;
import com.example.wumpusworldgame.gameActivities.WumpusSide;
import com.example.wumpusworldgame.mainMenuItems.GameInformationActivity;
import com.example.wumpusworldgame.mainMenuItems.score.RankActivity;
import com.example.wumpusworldgame.mainMenuItems.tutorial.MainTutorialActivity;
import com.example.wumpusworldgame.mainMenuItems.settings.GameSettingsActivity;
import com.example.wumpusworldgame.services.TypeWriter;
import com.example.wumpusworldgame.services.Utility;
import game.session.score.ScoreUtility;
/** class MainActivity
 * questa classe rappresenta la finestra principale dell'applicazione,
 * quella che si apre al suo avvio.
 * Deve estendere la classe AppCompatActivity proprio
 * per essere implementata come Activity.
 */
public class MainActivity extends AppCompatActivity {
    //##### attributi di classe #####

    //nome del file dei punteggi
    private static String score_file_path;

    //testo animato
    private TypeWriter typeWriter;
    //velocita' di scorrimento
    private final static int delay = 60;

    //riproduttore audio
    private MediaPlayer mp;

    //pulsante modalita' eroe
    private Button button_hero;
    //pulsante modalita' wumpus
    private Button button_wumpus;
    /* le Intent che servono per passare da un'Activity all'altra
     * devono essere create sulla classe che realizza l'activity corrente
     * e ricevere come ulteriore paramentro l'activity che devono eseguire
     */
    //intent modalita' eroe
    private Intent hero_game;
    //intent modalita' wumpus
    private Intent wumpus_game;

    //##### struttura dell'Activity #####

    /** metodo onCreate(): void
     * ACTIVITY CREATA
     * questo metodo viene invocato alla creazione
     * dell'Activity e si utilizza per definire
     * le configurazioni di base e definire il layout dell'interfaccia
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //si invoca il metodo della classe antenata
        super.onCreate(savedInstanceState);
        /* impostazione del layout
         * la struttura grafica scelta per l'Activity e' specificata nel file
         * activity_main.xml nella directory res/layout
         * Gli ID delle risorse sono conservati in una classe Java denominata R,
         * Si puo' accedere ad una risorsa:
         * - tramite R.tipo_risorsa.nome_risorsa, in Java;
         * - con @tipo_risorsa/nome_risorsa, in XML;
         */
        setContentView(R.layout.activity_main);

        //##### inizializzazioni #####

        //testo animato
        typeWriter = findViewById(R.id.storia);

        //riproduttore audio
        mp = MediaPlayer.create(MainActivity.this,R.raw.fato_shadow_main_menu);

        //verifica dell'esecuzione della traccia audio
        Utility.musicPlaying(mp, this);

        //pulsante modalita' eroe
        button_hero = findViewById(R.id.button_hero);
        //dichiarazione dell'activity che realizza la modalita' di gioco dell'avventuriero
        hero_game = new Intent(MainActivity.this, HeroSide.class);

        //pulsante modalita' wumpus
        button_wumpus = findViewById(R.id.button_wumpus);
        //dichiarazione dell'activity che realizza la modalita' di gioco del wumpus
        wumpus_game = new Intent(MainActivity.this, WumpusSide.class);

        //##### gestione dei pulsanti #####

        //verifica pressione del pulsante eroe
        button_hero.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {
                //si esegue l'activity HeroSide
                startActivity(hero_game);
            }//onClick
        });

        //verifica pressione pulsante wumpus
        button_wumpus.setOnClickListener(new View.OnClickListener(){
            //dichiarazione dell'activity che realizza la modalita' di gioco dell'avventuriero
            @Override
            public void onClick(View view) {
                //si esegue l'activity WumpusSide
                startActivity(wumpus_game);
            }//onClick
        });

        //##### gestione del file dei punteggi #####

        //path in cui creare il file dei punteggi
        score_file_path = new String();
        //si inizializza il file
        ScoreUtility.createScoreFile(score_file_path);

    }//onCreate()

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
        //si anima il testo
        animatedText(typeWriter);
    }//onStart()

    /** metodo onResume():void
     * ACTIVITY RICEVE INTERAZIONE
     */
    @Override
    protected void onResume() {
        //si invoca il metodo della super classe
        super.onResume();
        //verifica dell'esecuzione della traccia audio
        Utility.musicPlaying(mp, this);
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

    //##### altri metodi #####

    /** metodo animatedText(TypeWriter): void
     * questo metodo realizza l'animazione del
     * testo che fornisce l'introduzione al gioco
     * @param tw: TypeWriter
     */
    protected void animatedText(TypeWriter tw){
        //corpo del testo
        tw.setText("");
        //intervallo di tempo tra un carattere ed il successivo
        tw.setCharacterDelay(delay);
        //testo da mostrare con l'animazione
        tw.animateText(""+getResources().getString(R.string.game_intro));
    }//animatedText(TypeWriter)

    //##### metodi per la gestione del menu #####

    /** metodo onCreateOptionsMenu(Menu): boolean
     * questo metodo serve per visualizzare il menu
     * nella activity corrente
     * @param menu: Menu, oggetto che costituisce il menu
     * @return true: boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //si preleva l'oggetto inflater associato al menu
        MenuInflater inflater = getMenuInflater();
        //si definisce il layout del menu
        inflater.inflate(R.menu.main_menu,menu);
        //si visualizza il menu nel layout (tre puntini in alto a destra)
        return true;
    }//onCreateOptionsMenu(Menu)

    /** metodo onOptionsItemSelected(MenuItem): boolean
     * questo metodo si occupa di gestire le azioni che
     * devono essere svolte quando si seleziona una delle
     * voci del menu.
     * @param item: MenuItem, voce del menu;
     * @return true: boolean, per qualsiasi voce del menu che
     *                        e' stata gestita, altrimenti
     *                        super.onOptionsItemSelected(item).
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //variabile ausiliaria per la nuova activity
        Intent intent;
        //id della voce del menu che e' stata selezionata
        int itemId = item.getItemId();
        //switch case sulle varie voci del menu
        switch(itemId){
            //INFORMAZIONI
            case R.id.item_game_info:
                //creazione dell'intent
                intent = new Intent(this, GameInformationActivity.class);
                //avvio dell'activity corrispondente
                startActivity(intent);
                //viene aperta l'activity
                return true;
            //IMPOSTAZIONI
            case R.id.item_settings:
                //creazione dell'intent
                intent = new Intent(this, GameSettingsActivity.class);
                //avvio dell'activity corrispondente
                startActivity(intent);
                //viene aperta l'activity
                return true;
            //TUTORIAL
            case R.id.item_tutorial:
                //creazione dell'intent
                intent = new Intent(this, MainTutorialActivity.class);
                //avvio dell'activity corrispondente
                startActivity(intent);
                //viene aperta l'activity
                return true;
            //PUNTEGGI
            case R.id.item_score:
                //creazione dell'intent
                intent = new Intent(this, RankActivity.class);
                //avvio dell'activity corrispondente
                startActivity(intent);
                //viene aperta l'activity
                return true;
            default:
                //caso di default
                return super.onOptionsItemSelected(item);
        }//end switch
    }//onOptionsItemSelected(MenuItem)

    /** metodo getScoreFilePath(): String
     * questo metodo restituisce il path in cui si trova il file dei punteggi
     * sotto forma di stringa
     * @return score_file_path: String
     */
    public static String getScoreFilePath(){
        return score_file_path;
    }//getScoreFilePath()

}//end MainActivity