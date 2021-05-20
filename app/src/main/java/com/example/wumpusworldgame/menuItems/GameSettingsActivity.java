package com.example.wumpusworldgame.menuItems;
//serie di import
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import com.example.wumpusworldgame.R;
/** class GameSettingsActivity
 * questa classe contiene una serie di impostazioni,
 * modificabili dall'utente.
 */
public class GameSettingsActivity extends AppCompatActivity {
    //##### attributi di classe #####

    /** metodo onCreate(Bunde): void
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //si richiama il metodo della super classe
        super.onCreate(savedInstanceState);
        //si imposta il layout
        setContentView(R.layout.game_settings_activity);
        //
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new GameSettingsFragment())
                    .commit();
        }//fi
        //
        ActionBar actionBar = getSupportActionBar();
        //
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }//fi
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
        //mp.start();
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
    public void onBackPressed(){
       //si richiama il metodo della super classe
        super.onBackPressed();
    }//onBackPressed()

   //##### inner class #####

    /** class GameSettingsFragments
     * questo fragmente implementa, effettivamente, la serie di impostazioni
     */
    public static class GameSettingsFragment extends PreferenceFragmentCompat {
        /** metodo onCreatePreferences(Bundle, String)
         * @param savedInstanceState
         * @param rootKey
         */
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.game_settings_root_preferences, rootKey);
        }//onCreate(Bundle, String)

    }//end GameSettingsFragment

}//end GameSettingsActivity