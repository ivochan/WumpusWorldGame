package com.example.wumpusworldgame.mainMenuItems.settings;
//serie di import
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
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
        //si identifica la rpeference relativa al feedback
        Preference sendFeedback = findPreference("prefFeedback");

        sendFeedback.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            //
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //si crea una alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                //si definisce il testo del titolo
                TextView textView = new TextView(getContext());
                textView.setText(R.string.feed);
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(Color.parseColor("#5c007a"));
                textView.setTextColor(Color.WHITE);
                //si imposta il titolo della dialog
                builder.setCustomTitle(textView);
                //si imposta il messaggio della dialog
                builder.setMessage(R.string.feedback_subtitle);
                //la dialog non si chiude cliccando al di fuori della finestra
                builder.setCancelable(false);
                //
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //se si clicca annulla la dialog viene chiusa
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //se si clicca invia la dialog avvia il processo di invio del feeedback
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "ivochan17@gmail.com" });
                        intent.putExtra(Intent.EXTRA_SUBJECT, "app feedback");
                        intent.putExtra(Intent.EXTRA_TEXT, "mail body");
                        startActivity(Intent.createChooser(intent, ""));
                    }
                });
                //si crea la dialog
                AlertDialog dialog = builder.create();
                //si visualizza la dialog
                dialog.show();
                //return
                return false;
            }//onPreferenceClick(Preference)
        });//send

    }//onCreatePreference

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

}//end GameSettingsFragment
