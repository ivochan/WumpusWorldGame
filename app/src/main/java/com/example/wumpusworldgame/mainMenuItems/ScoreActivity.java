package com.example.wumpusworldgame.mainMenuItems;
//serie di import
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.wumpusworldgame.R;
/** class ScoreActivity
 * questa classe visualizza tutti i punteggi ottenuti nel gioco
 * e permette all'utente di condividerli al di fuori dell'applicazione
 */
public class ScoreActivity extends AppCompatActivity {
    //##### attributi di classe #####

    //TODO riproduttore audio
    //private MediaPlayer mp;

    //file delle preferenze
    private SharedPreferences sharedPreferences;
    //pulsante di condivisione del record
    private Button record_share_button;

    //nome del giocatore che ha effettuato il record
    private String best_player;
    //valore del punteggio record
    private String highscore;
    //pulsante di condivisione de punteggio corrente
    private Button score_share_button;

    //valore del punteggio attuale
    private String score;
    //nome del giocatore del punteggio attuale
    private String player;


    /** metodo onCreate(Bundle): void
     * metodo di CREAZIONE dell'ACTIVITY
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //si invoca il metodo della super classe
        super.onCreate(savedInstanceState);
        //si stabilisce il layout dell'activity
        setContentView(R.layout.activity_score);

        //##### inizializzazioni #####

        //scelta della clip audio
        //mp = MediaPlayer.create(HeroSide.this,R.raw.the_good_fight);

        //si preleva il file di salvataggio delle preferenze
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //pulsante di condivisione del punteggio record
        record_share_button = findViewById(R.id.record_share_button);
        //pulsante di condivisione del punteggio attuale
        score_share_button = findViewById(R.id.current_share_button);

        //##### azioni #####

        //verifica dell'esecuzione della traccia audio
        //Utility.musicPlaying(mp, this);

        //si identifica la preference relativa al nome del giocatore corrente
        player = sharedPreferences.getString("prefUsername","---");
        //si preleva il campo di testo associato al nome del giocatore del punteggio attuale
        TextView tn = findViewById(R.id.current_player_name);
        //si aggiorna il nome del giocatore del punteggio attuale
        //con il valore scelto dalle impostazioni
        tn.setText(player);
        //TODO si preleva il valore del punteggio
        score = "0";

        //dati del punteggio record
        best_player = "TopPlayer";
        highscore = "1000";

        //##### gestione dei pulsanti #####

        //##### condivisione del punteggio record #####
        //verifica pressione del pulsante di condivisione del punteggio record
        record_share_button.setOnClickListener(view -> {
            //si apre una dialog che mostra i dati da condividere
            //si crea una alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this,
                    R.style.AlertDialogTheme);
            //si preleva la stringa da visualizzare come messaggio della dialog
            String record_share_message= getResources().getString(R.string.record_share_message);
            //si configura il layout della dialog
            settingDialog(builder, record_share_message, best_player, highscore);

            //pulsante di chiusura
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                /** metodo onClick(DialogInterface, int)
                 * questo metodo gestisce il comportamento dell'app
                 * al click del pulsante, definendo le azioni che
                 * devono essere svolte.
                 * In questo caso, alla pressione del pulsante "Cancel"
                 * viene chiusa la dialog
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //se si clicca annulla la dialog viene chiusa
                    dialog.dismiss();
                }//onClick(DialogInterface, int)
            });//setNegativeButton(String, DialogInterface)

            //pulsante di conferma
            builder.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
                /** metodo onClick(DialogInterface, int)
                 * questo metodo gestisce il comportamento dell'app
                 * al click del pulsante, definendo le azioni che
                 * devono essere svolte.
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //creazione dell'intent
                    Intent shareIntent = new Intent();
                    //si specifica l'azione da svolgere
                    shareIntent.setAction(Intent.ACTION_SEND);
                    //selezione dell'immagine da condividere
                    Uri imageUri = Uri.parse("android.resource://" + getPackageName()+ "/drawable/" + "red_little_monster_blue");
                    //si crea la stringa che conterra' il punteggio
                    String record_score = best_player+", "+highscore+" pt";
                    //si inserisce la stringa complessiva da condividere come testo
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.record_send_message)+"\n"+record_score);
                    //si inserisce l'immagine da condividere
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    //si specifa il tipo file da condividere (immagine/estensione)
                    shareIntent.setType("image/png");
                    //si forniscono i permessi di lettura dell'immagine
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //si visualizzano le app con cui effettuare questa azione
                    startActivity(Intent.createChooser(shareIntent,null));
                }//onClick(DialogInterface, int)
            });//setPositiveButton(String, DialogInterface)

            //si crea la dialog
            AlertDialog dialog = builder.create();
            //si visualizza la dialog
            dialog.show();
        });//setOnClickListener(View)

        //##### condivisione punteggio attuale #####
        //verifica pressione pulsante di condivisione del punteggio attuale
        score_share_button.setOnClickListener(view -> {
            //si crea una alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this,
                    R.style.AlertDialogTheme);
            //si preleva la stringa da visualizzare come messaggio della dialog
            String score_share_message = getResources().getString(R.string.score_share_message);
            //si configura il layout della dialog
            settingDialog(builder, score_share_message, player, score);

            //pulsante di chiusura
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                /** metodo onClick(DialogInterface, int)
                 * questo metodo gestisce il comportamento dell'app
                 * al click del pulsante, definendo le azioni che
                 * devono essere svolte.
                 * In questo caso, alla pressione del pulsante "Cancel"
                 * viene chiusa la dialog
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //se si clicca annulla la dialog viene chiusa
                    dialog.dismiss();
                }//onClick(DialogInterface, int)
            });//setNegativeButton(String, DialogInterface)

            //pulsante di conferma
            builder.setPositiveButton(R.string.share, new DialogInterface.OnClickListener() {
                /** metodo onClick(DialogInterface, int)
                 * questo metodo gestisce il comportamento dell'app
                 * al click del pulsante, definendo le azioni che
                 * devono essere svolte.
                 * @param dialog
                 * @param which
                 */
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //creazione dell'intent
                    Intent shareIntent = new Intent();
                    //si specifica l'azione da svolgere
                    shareIntent.setAction(Intent.ACTION_SEND);
                    //selezione dell'immagine da condividere
                    Uri imageUri = Uri.parse("android.resource://" + getPackageName()+ "/drawable/" + "red_little_monster_blue");
                    //si crea la stringa che conterra' il punteggio
                    String current_score = player+", "+score+" pt";
                    //si inserisce la stringa complessiva da condividere come testo
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.score_send_message)+"\n"+current_score);
                    //si inserisce l'immagine da condividere
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    //si specifa il tipo file da condividere (immagine/estensione)
                    shareIntent.setType("image/png");
                    //si forniscono i permessi di lettura dell'immagine
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //si visualizzano le app con cui effettuare questa azione
                    startActivity(Intent.createChooser(shareIntent,null));
                }//onClick(DialogInterface, int)
            });//setPositiveButton(String, DialogInterface)

            //si crea la dialog
            AlertDialog dialog = builder.create();
            //si visualizza la dialog
            dialog.show();
        });//setOnClickListener(View)

    }//onCreate

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

    //##### altri metodi #####

    /** metodo settingDialog(Alert.Builder, String, String, String)
     * questo emtodo si occupa di configurare il layout della dialog
     * impostando il testo e l'immagine.
     * Il testo cambia in base ai parametri ricevuti.
     * @param builder: Alert.Builder, costruttore della dialog;
     * @param share_message: String, messaggio da visualizzare nella dialog;
     * @param player: String, nome del giocatore;
     * @param score: String, valore del punteggio;
     */
    private void settingDialog(AlertDialog.Builder builder, String share_message, String player, String score){
        //si inserisce l'immagine nel layout della dialog
        builder.setView(R.layout.alert_share_image);
        //si definisce il testo del titolo
        TextView textView = new TextView(getApplicationContext());
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
        String message = share_message+"\n"+player+", "+score+" pt";
        //si visualizza il messaggio nella dialog
        builder.setMessage(message);
        //la dialog si chiude cliccando al di fuori della sua area
        builder.setCancelable(true);
    }//settingDialog(AlertDialog.Builder, String)

}//end ScoreActivity