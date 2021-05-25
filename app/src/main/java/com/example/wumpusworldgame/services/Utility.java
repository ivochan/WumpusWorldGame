package com.example.wumpusworldgame.services;
//serie di import
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.preference.PreferenceManager;

import com.example.wumpusworldgame.R;
import java.util.Timer;
import java.util.TimerTask;
/** class  Utility
 * classe di utilita' che contiene una serie di metodi statici da
 * richiamare in entrambe le modalita' di gioco perche' comuni.
 */
public class Utility {

    /** metodo showLoadingScreen(Activity, LayoutInflater)
     * questo metodo realizza una schermata di caricamento
     * che viene mostrata appena si decide la modalita' di
     * gioc, prima che all'utente venga mostrata la mappa.
     * @param inflater
     * @param activity
     */
    public static void showLoadingScreen(Activity activity, LayoutInflater inflater){
        //##### implementazione della dialog #####

        //si inizializza una dialog
        Dialog customDialog;
        //si assegna alla dialog il layout
        View customView = inflater.inflate(R.layout.loading_screen_dialog, null);
        //si costruisce la dialog specificandone lo stile personalizzato
        customDialog = new Dialog(activity, R.style.CustomDialog);
        //si definisce la dimensione della finestra sul display
        customDialog.getWindow().setLayout(1080,2000);
        //si prelevano i valori stabiliti come attributi della finestra
        WindowManager.LayoutParams params = customDialog.getWindow().getAttributes();
        //si defisce l'ordinata della posizione della dialog sullo schermo
        params.y = 120;
        //si assegna il valore aggiornato come parametro della dialog
        customDialog.getWindow().setAttributes(params);

        //##### visualizzazione della dialog #####

        //si definisce il layout alla dialog attuale
        customDialog.setContentView(customView);
        //si visualizza la dialog
        customDialog.show();
        //si istanzia l'oggetto timer per defire il tempo in cui la finestra sara' visibile
        final Timer t = new Timer();
        //l'oggetto timer inizia la schedulazione dei processi attivi
        t.schedule(new TimerTask() {
            public void run() {
                //il task e' attivo quindi la dialog viene chiusa
                customDialog.dismiss();
                //viene fermato il thread timer
                t.cancel();
            }
        }, 2000);
    }//showLoadingScreen(Activity, LayoutInflater)

    /** metodo musicPlaying(MediaPlayer, Activity): void
     * questo metodo imposta la riproduzione della traccia musicale
     * scelta come colonna sonora di ogni activity, in base
     * al valore della preference relativa.
     * @param mp: MediaPlayer, riproduttore musicale;
     * @param activity: Activity, activity corrente per cui impostare
     *                la riproduzione della traccia audio.
     */
    public static void musicPlaying(MediaPlayer mp, Activity activity){
        //si prelevano le preferenze dell'activity ricevuta come parametro
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(activity);
        //si preleva la preference relativa alla traccia audio
        boolean music_on = sharedPrefs.getBoolean("prefSounds",true);
        //si controlla la preferenza
        if(music_on){
            //l'audio e' abilitato, allora si esegue la traccia musicale
            mp.start();
        }//fi
        else {
            //l'audio e' disabilitato, allora si stoppa la riproduzione musicale
            mp.stop();
        }//esle
    }//musicPlaying(MediaPlayer,Activity)

}// end class Utility
