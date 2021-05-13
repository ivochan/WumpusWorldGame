package com.example.wumpusworldgame.services;
//serie di import
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.wumpusworldgame.R;

/** class  Utility
 * classe di utilita' che contiene una serie di metodi statici da
 * richiamare in entrambe le modalita' di gioco perche' comuni.
 */
public class Utility {

    /** metodo showLoadingScreen(): void
     * questo metodo visualizza la schermata di caricamento
     * prima dell'avvio della sessione di gioco
     * utilizzando il layout definito per il Toast
     * @param context
     * @param loading_layout
     */
    public static void showLoadingScreen(Context context, View loading_layout){
        //si crea il toast
        Toast loading_toast = new Toast(context);
        //si imposta la sua disposizione nella finestra
        loading_toast.setGravity(Gravity.CENTER, 0, 0);
        //si imposta il tempo per cui dovra' essere visualizzato
        loading_toast.setDuration(Toast.LENGTH_SHORT);
        loading_toast.setView(loading_layout);
        loading_toast.show();
    }//showLoadingScreen(Context, View)

}// end class Utility
