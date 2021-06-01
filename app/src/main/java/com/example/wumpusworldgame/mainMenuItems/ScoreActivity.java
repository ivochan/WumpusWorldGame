package com.example.wumpusworldgame.mainMenuItems;
//serie di import
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wumpusworldgame.R;

import java.awt.font.TextAttribute;
import java.text.AttributedString;

/** class ScoreActivity
 *
 */
public class ScoreActivity extends AppCompatActivity {
    //##### attributi di classe #####
    private Button record_share_button;
    private Button score_share_button;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //##### inizializzazioni #####

        String share_score_message = "Hey, sfida i tuoi amici al gioco del mondo del Wumpus!"
        +"\n\nEcco il tuo punteggio:\n";

        //pulsante di condivisione del punteggio record
        record_share_button = (Button) findViewById(R.id.record_share_button);
        //pulsante di condivisione del punteggio attuale
        score_share_button = (Button) findViewById(R.id.current_share_button);

        //##### azioni #####

        //si preleva il file di salvataggio delle preferenze dell'activity che contiene questo fragment
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        //si identifica la preference relativa al nome del giocatore
        String name = sharedPrefs.getString("prefUsername",null);
        //si preleva il campo di testo associato al nome del giocatore del punteggio attuale
        TextView tn = (TextView)findViewById(R.id.current_player_name);
        //si aggiorna il nome del giocatore del punteggio attuale con il valore scelto dalle impostazioni
        tn.setText(name);

        String score = "0";


        //##### gestione dei pulsanti #####


        //verifica pressione del pulsante di condivisione del punteggio record
        record_share_button.setOnClickListener(new View.OnClickListener(){
            //il pulsante e' stato premuto
            @Override
            public void onClick(View view) {
                //si apre una dialog che mostra i dati da condividere
            }//onClick
        });

        //verifica pressione pulsante di condivisione del punteggio attuale
        score_share_button.setOnClickListener(new View.OnClickListener(){
            //dichiarazione dell'activity che realizza la modalita' di gioco dell'avventuriero
            @Override
            public void onClick(View view) {
                //si crea una alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ScoreActivity.this,R.style.AlertDialogTheme);


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
                String message = share_score_message+"\n"+name+", "+score+" punti";

                builder.setMessage(message);
                //la dialog si chiude cliccando al di fuori della sua area
                builder.setCancelable(true);


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


                        /*
                        //intestazione
                        String text = "Hey ho effettuato questo punteggio";
                        Uri pictureUri = Uri.parse("@mipmap/ic_launcher_red_monster.png");
                        //si crea l'intent che gestisce l'azione di condivisione delle info
                        Intent shareIntent = new Intent();
                        //si definisce il tipo di azione
                        shareIntent.setAction(Intent.ACTION_SEND);
                        //si inserisce il testo
                        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                        //si inserisce l'immagine
                        shareIntent.putExtra(Intent.EXTRA_STREAM, pictureUri);
                        shareIntent.setType("image/*");
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(Intent.createChooser(shareIntent, "Share images..."));
                        */

                    }
                });//setPositiveButton(String, DialogInterface)


                //si crea la dialog
                AlertDialog dialog = builder.create();
                //si visualizza la dialog
                dialog.show();

            }//onClick

        });//onClickListener






    }//onCreate


}//end ScoreActivity