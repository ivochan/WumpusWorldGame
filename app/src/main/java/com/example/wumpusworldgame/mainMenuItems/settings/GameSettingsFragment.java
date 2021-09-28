package com.example.wumpusworldgame.mainMenuItems.settings;
//serie di import
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
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
import com.example.wumpusworldgame.appLaunch.MainActivity;
import com.example.wumpusworldgame.services.Utility;
import game.session.score.ScoreUtility;
/** class GameSettingsFragments
 * questo fragment implementa, effettivamente, la serie di impostazioni
 */
public class GameSettingsFragment extends PreferenceFragmentCompat {
    //##### attributi di classe #####
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

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
        setPlayerName(sharedPrefs,editUsername);

        //##### invio del feeedback #####

        //si identifica la preference relativa al feedback
        Preference sendFeedback = findPreference("prefFeedback");
        //si gestisce l'invio del feeedback
        sendFeedbackRequest(sendFeedback);

        //##### gestione dei dati di gioco #####

        Preference deleteGameData = findPreference("prefDeleteData");
        //si gestisce la cancellazione dei dati
        deleteScoreData(deleteGameData);


    }//onCreatePreference

    /** metodo setPlayerName(SharedPreferences, EditTextPreference): void
     * questo metodo si occupa di aggiornare il campo di testo editabile in
     * cui l'utente inserisce il nome che vuole utilizzare da giocatore
     * @param sharedPreferences
     * @param editTextPreference
     */
    private void setPlayerName(SharedPreferences sharedPreferences, EditTextPreference editTextPreference){
        //si preleva il nome che e' stato scelto dall'utente
        String username = sharedPreferences.getString("prefUsername", "");
        //si eleminano gli spazi
        username = username.trim().replaceAll(" ","");
        //si verifica il contenuto
        if (username.isEmpty()) {
            //la stringa e' nulla, percio' si visualizza il messaggio di info
            editTextPreference.setSummary(R.string.player_info);
        }//fi
        else {
            //e' stato inserito il nome percio' si visualizza nel campo summary
            editTextPreference.setSummary(username);
        }//else
        //si aggiorna il nome del giocatore
        editTextPreference.setText(username);
        //l'ascoltatore reagisce ad una modifica successiva della variabile
        editTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            //e' stato modificato il campo di testo per il nome del giocatore
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                //si preleva il nome che e' stato scelto dall'utente
                String username = (String)newValue;
                //si eliminano gli spazi
                username = username.trim().replaceAll(" ","");
                //si verifica il contenuto
                if (username.isEmpty()) {
                    //la stringa e' nulla, percio' si visualizza il messaggio di info
                    editTextPreference.setSummary(R.string.player_info);
                }//fi
                else {
                    //e' stato inserito il nome percio' si visualizza nel campo summary
                    editTextPreference.setSummary(username);
                }//else
                //si aggiorna il nome del giocatore
                editTextPreference.setText(username);
                //modifica valida
                return true;
            }//onPreferenceChangeListener()
        });//setOnPreferenceChangeListener()
    }//setPlayerName()

    //##### metodi di gestione delle preferences #####

    private void deleteScoreData(Preference deleteGameData) {
        //gestione del click tramite listener
        deleteGameData.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            //metodo di gestione del click sulla preference
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //si crea una alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
                //metodo che configura l'aspetto della dialog
                settingDeleteDataDialog(builder);
                //pulsante di chiusura
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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

                //pulsante di conferma per la cancellazione dei dati
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    /** metodo onClick(DialogInterface, int)
                     * questo metodo gestisce il comportamento dell'app
                     * al click del pulsante, definendo le azioni che
                     * devono essere svolte.
                     * In questo caso verra' avviata la procedura che si occupera'
                     * della cancellazione dei dati di gioco
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //si preleva il path del file dei punteggi
                        String score_file_path = MainActivity.getScoreFilePath();
                        //si elimina il file che contiene i dati di gioco
                        ScoreUtility.deleteScoreFile(score_file_path);
                        //si ricrea un file vuoto
                        //ScoreUtility.createScoreFile(score_file_path);
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
    }

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogTheme);
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
                        //si determina la dimensione dello schermo del dispositivo
                        String screen_inch = getScreenSize();
                        //si inizializza l'intent che gestisce l'invio di una mail
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        //creazione della struttura della mail
                        configEmail(intent, screen_inch);
                        //invio della mail
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

    /** metodo settingDeleteDialog(AlertDialog.Builder): void
     * questo metodo definisce la struttura della dialog che richiede
     * di confermare o meno l'eliminazione dei dati di gioco
     * @param builder
     */
    private void settingDeleteDataDialog(AlertDialog.Builder builder){
        //si definisce il testo del titolo
        TextView textView = new TextView(getContext());
        //testo del titolo della dialog
        textView.setText(R.string.delete_data_title);
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
        builder.setMessage(R.string.delete_data_request);
        //la dialog si chiude cliccando al di fuori della sua area
        builder.setCancelable(true);
    }//settingDataDialog(AlertDialog.Builder, String, String)

    //##### metodi per la gestione del feedback #####

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

    /** metodo configEmail(Intent, String): void
     * questo metodo si occupa di stabilire a chi inviare l'email
     * e costruire il suo contenuto raccolgiendo le specifiche del
     * dispositivo in uso.
     * @param intent: Intent, e' l'oggetto intent che rappresenta l'azione
     *              dell'invio della email.
     * @param screen_inch: String, stringa che descrive la dimensione
     *                   dello schermo del dispositivo in uso, in pollici.
     */
    private void configEmail(Intent intent, String screen_inch){
        //si specifica il tipo di applicazione che interessa questa azione (email)
        intent.setData(Uri.parse("mailto:"));
        //si specifica l'indirizzo a cui inviare la mail
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {Utility.SUPPORT_EMAIL});
        //si preleva il contenuto dell'oggetto
        String email_subject=getResources().getString(R.string.email_subject);
        //si specifica il contenuto dell'oggetto
        intent.putExtra(Intent.EXTRA_SUBJECT,email_subject);
        //variabile ausiliaria che conterra' le informazioni sul dispositivo
        String info = null;
        //si raccolgono informazioni sul dispositivo in uso
        info = Utility.collectDeviceInfo(screen_inch);
        //si imposta il corpo della mail
        intent.putExtra(android.content.Intent.EXTRA_TEXT, info);
    }//configMail(Intent,String)

    /** metodo getScreenSize(): String
     * questo metodo calcola la dimensione dello schermo del dispositivo
     * e la restituisce come stringa
     * @return String, valore in pollici della dimensione dello schermo
     */
    private String getScreenSize() {
        //contesto dell'activity attuale
        Context context =getContext();
        //
        Point point = new Point();
        //si calcola la dimensione del display
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealSize(point);
        //si estraggono le misure calcolate
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //larghezza
        int width=point.x;
        //lunghezza
        int height=point.y;
        //calcolo della densita' dei pixel per la larghezza
        double wi=(double)width/(double)displayMetrics.xdpi;
        //calcolo della densita' dei pixel per la lunghezza
        double hi=(double)height/(double)displayMetrics.ydpi;
        //calcolo della larghezza
        double x = Math.pow(wi,2);
        //calcolo della lunghezza
        double y = Math.pow(hi,2);
        //si restituisce la stringa che rappresenta la dimensione in pollici
        return String.valueOf(Math.round((Math.sqrt(x+y)) * 10.0) / 10.0);
    }//getScreenSize()

}//end GameSettingsFragment
