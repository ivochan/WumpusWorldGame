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
    //##### attributi di classe #####

    //file di salvataggio delle preferenze
    SharedPreferences sharedPrefs;
    //campo editabile per il nome del giocatore
    EditTextPreference editUsername;
    //nome del giocatore
    String username;

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
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //##### nome del giocatore #####
        //si identifica la preference relativa al nome del giocatore
        editUsername = findPreference("prefUsername");
        //si preleva il nome inserito
        username = sharedPrefs.getString("prefUsername",null);
        //si verifica il contenuto
        if(username.isEmpty()){
            //la stringa e' nulla, percio' si visualizza il messaggio di info
            editUsername.setSummary(R.string.player_info);
        }//fi
        else {
            //e' stato inserito il nome percio' si visualizza nel campo summary
            editUsername.setSummary(username);
        }//else

        //##### #####


    }//onCreatePreference

}//end GameSettingsFragment
