package com.example.wumpusworldgame.services;
//serie di import
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import com.example.wumpusworldgame.R;
import java.util.Timer;
import java.util.TimerTask;
/** class  Utility
 * classe di utilita' che contiene una serie di metodi statici da
 * richiamare in entrambe le modalita' di gioco perche' comuni.
 */
public class Utility {

    /** metodo showLoadingScreen(Activity, LayoutInflater)
     *
     * @param inflater
     * @param activity
     */
    public static void showLoadingScreen(Activity activity, LayoutInflater inflater){
        //
        Dialog customDialog;
        View customView = inflater.inflate(R.layout.loading_screen_dialog, null);

        // Build the dialog
        customDialog = new Dialog(activity, R.style.CustomDialog);
        customDialog.getWindow().setLayout(1080,2000);
        WindowManager.LayoutParams params = customDialog.getWindow().getAttributes(); // change this to your dialog.

        params.y = 120; // Here is the param to set your dialog position. Same with params.x
        customDialog.getWindow().setAttributes(params);

        customDialog.setContentView(customView);
        customDialog.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                customDialog.dismiss(); // when the task active then close the dialog
                t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 2000);

    }
}// end class Utility
