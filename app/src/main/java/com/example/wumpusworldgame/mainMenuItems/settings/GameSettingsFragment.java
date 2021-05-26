package com.example.wumpusworldgame.mainMenuItems.settings;
//serie di import
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.services.Utility;

import java.util.ArrayList;

/** class GameSettingsFragments
 * questo fragment implementa, effettivamente, la serie di impostazioni
 */
public class GameSettingsFragment extends PreferenceFragmentCompat {

    /** metodo onCreatePreferences(Bundle, String)
     * @param savedInstanceState
     * @param rootKey
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //si imposta il file che contiene le preferenze
        setPreferencesFromResource(R.xml.game_settings_root_preferences, rootKey);

        //##### inizializzazioni #####

        //si preleva il file di salvataggio delle preferenze dell'activity che contiene questo fragment
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //##### nome del giocatore #####

        //si identifica la preference relativa al nome del giocatore
        EditTextPreference editUsername = findPreference("prefUsername");
        //si aggiorna il nome del giocatore
        updatePlayerName(sharedPrefs,editUsername);

        //##### invio del feeedback #####

        //si identifica la preference relativa al feedback
        Preference sendFeedback = findPreference("prefFeedback");
        //si gestisce l'invio del feeedback
        sendFeedbackRequest(sendFeedback);


    }//onCreatePreference

    //##### metodi di gestione delle preferences #####

    /** metodo updatePlayerName(SharedPreferences, EditTextPreference): void
     * metodo che aggiorna il nome del giocatore
     * @param sharedPrefs
     * @param editUsername
     */
    private static void updatePlayerName(SharedPreferences sharedPrefs, EditTextPreference editUsername){
        //si preleva il nome inserito
        String username = sharedPrefs.getString("prefUsername","");
        //si verifica il contenuto
        if(username.equals("")){
            //la stringa e' nulla, percio' si visualizza il messaggio di info
            editUsername.setSummary(R.string.player_info);
        }//fi
        else {
            //e' stato inserito il nome percio' si visualizza nel campo summary
            editUsername.setSummary(username);
        }//esle
    }//updatePlayerName(SharedPreferences, EditTextPreference)

    /** metodo sendFeedbackRequest(Preference)
     * questo metodo si occupa di gestire, se richiesto dall'utente,
     * l'invio di unna mail di feedback allo sviluppatore.
     * @param sendFeedback: Prenference
     */
    private void sendFeedbackRequest(Preference sendFeedback){
        //gestione del click tramite listener
        sendFeedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            //metodo di gestione del click sulla preference
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //si crea una alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //metodo che configuara l'aspetto della dialog
                settingDialog(builder);

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

                //pulsante di invio del feedback
                builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    /** metodo onClick(DialogInterface, int)
                     * questo metodo gestisce il comportamento dell'app
                     * al click del pulsante, definendo le azioni che
                     * devono essere svolte.
                     * In questo caso verra' avviata la procedura che si occupera'
                     * di far scegliere all'utente il provider di mail che preferisce
                     * per inviare il feedback.
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //si inizializza l'intent che gestisce l'invio di una mail
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        //si specifica il tipo di applicazione che interessa questa azione (email)
                        intent.setData(Uri.parse("mailto:"));
                        //si specifica l'indirizzo a cui inviare la mail
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {Utility.SUPPORT_EMAIL});
                        //si specifica il contenuto dell'oggetto
                        intent.putExtra(Intent.EXTRA_SUBJECT,Utility.EMAIL_SUBJECT );
                        //variabile ausiliaria che conterra' le informazioni sul dispositivo
                        String info = null;
                        //si determina la dimensione dello schermo
                        String screen_inch = getScreenSize();
                        //si raccolgono informazioni sul dispositivo in uso
                        info = Utility.collectDeviceInfo(screen_inch);
                        //si imposta il corpo della mail
                        intent.putExtra(android.content.Intent.EXTRA_TEXT, info);
                        //blocco try-catch
                        try {
                            //si apre il gestore per scegliere l'app con cui inviare la mail
                            startActivity(Intent.createChooser(intent, "Send mail using ..."));
                            //si scrive nel file di log che l'invio e' riuscito
                            Log.i("email sended", "Email's been sended!");
                        }//try
                        catch (android.content.ActivityNotFoundException ex) {
                            //si visualizza un messaggio se non ci sono app per inviare la mail
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }//catch
                    }//onClick(Dialog Interface, int)
                });//setPositiveButton(String, DialogInterface)

                //si crea la dialog
                AlertDialog dialog = builder.create();
                //si visualizza la dialog
                dialog.show();
                //return
                return false;
            }//onPreferenceClick(Preference)
        });//send
    }//sendFeedbackRequest(Preference)

    /** metodo settingDialog(AlertDialog.Builder): void
     * questo metodo definisce la struttura della dialog che richiede
     * di confermare o meno l'invio del feeedback
     * @param builder
     */
    private void settingDialog(AlertDialog.Builder builder){
        //si definisce il testo del titolo
        TextView textView = new TextView(getContext());
        //testo del titolo della dialog
        textView.setText(R.string.feed);
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
        builder.setMessage(R.string.feedback_request);
        //la dialog si chiude cliccando al di fuori della sua area
        builder.setCancelable(true);
    }//settingDialog(AlertDialog.Builder)

    /**
     *
     * @return
     */
    private String getScreenSize() {
        //TODO documentazione
        Context context =getContext();
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width=point.x;
        int height=point.y;
        double wi=(double)width/(double)displayMetrics.xdpi;
        double hi=(double)height/(double)displayMetrics.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return String.valueOf(Math.round((Math.sqrt(x+y)) * 10.0) / 10.0);
    }
}//end GameSettingsFragment
