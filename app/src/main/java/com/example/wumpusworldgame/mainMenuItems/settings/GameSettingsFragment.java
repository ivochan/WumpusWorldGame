package com.example.wumpusworldgame.mainMenuItems.settings;
//serie di import
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;

/** class GameSettingsFragments
 * questo fragment implementa, effettivamente, la serie di impostazioni
 */
public class GameSettingsFragment extends PreferenceFragmentCompat {
    //##### attributi #####
    //file di salvataggio delle preferenze
    SharedPreferences sharedPrefs;
    //nome del giocatore
    EditTextPreference usernameEditText;


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
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());

        //si identifica la preference associata al nome del giocatore
        usernameEditText = findPreference("prefUsername");
        //si aggiorna il nome del giocatore
        updateUsername(usernameEditText);

    }//onCreatePreference

    private static void updateUsername(EditTextPreference editTextPreference) {
        //si rpeleva il contenuto del campo di testo editabile
        String username = editTextPreference.getText();
        //si verica se e' vuoto
        if(username.isEmpty()){
            //se vuoto si mostra la stringa informativa
           editTextPreference.setSummary(R.string.player_info);
        }//fi
        else {
            //se non e' vuoto si visualizza il contenuto
            editTextPreference.setSummary(username);
        }//esle
    }//updateUsername(EditTextPreference)

}//end GameSettingsFragment
