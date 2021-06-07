package com.example.wumpusworldgame.adapters;
//serie di import
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameActivities.HeroSide;
/** class GridViewCustomAdapter
 * questa classe serve per visualizzare i dati nella
 * GridView che ha consentito di realizzare la mappa nella schermata di gioco
 */
public class GridViewCustomAdapter extends BaseAdapter {
    //##### attributi di classe #####

    //arrayList di stringhe
    //ogni stringa e' un'etichetta che descrive il contenuto della cella della mappa di gioco
    ArrayList<String> items;
    //activivy in cui verra' utilizzato
    static Activity mActivity;
    //oggetto layoutInflater per unire questo componente al layout dell'activity
    private static LayoutInflater inflater = null;

    int game_mode=0;

    /** GridViewCustomAdapter(Activity, ArrayList): costruttore
     * permette di istanziare l'oggetto gridViewCustomAdapter
     * @param activity: Activity, activity in cui verra' utilizzato l'adapter
     * @param tempArrayList: ArrayList<String>, oggetto che contiene gli items
     */
    public GridViewCustomAdapter(Activity activity, ArrayList<String> tempArrayList) {
        //si preleva l'activity
        mActivity = activity;

        if(mActivity instanceof HeroSide){
            game_mode =0;
        }
        else {
            game_mode=1;
        }
        //si prelevano gli oggetti da visualizzare
        items = tempArrayList;
        //si utilizza l'inflater
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }//end GridViewCustomAdapter(Activity, ArrayList)

    //##### metodi #####

    /** metodo getCount(): int
     * fornisce il numero di oggetti dell'arrayList
     * @return number_of_items: int, numero di oggetti da visualizzare nel layout
     */
    @Override
    public final int getCount() {
        //variabile ausiliaria per la lunghezza dell'arrayList
        int number_of_items = items.size();
        //si restituisce il numero di oggetti contenuti nell'arrayList
        return number_of_items;
    }//getCount()

    /** metodo getItem(int): Object
     * questo metodo restituisce l'oggetto contenuto nell'array list
     * nella posizione fornita come parametro
     * @param position: int, indice posizionale dell'oggetto che si vuole venga restituito.
     * @return
     */
    @Override
    public final Object getItem(int position) {
        //variabile ausiliaria
        Object o = items.get(position);
        //si restituisce l'oggetto
        return o;
    }//getItem(int)

    /** metodo getView(int, View, ViewGroup): View
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //si istanzia un oggetto View
        View v = null;
        //si utilizza l'oggetto inflater per aggiungere il layout
        //di ogni oggetto da inserire nella griglia
        v = inflater.inflate(R.layout.grid_item, null);
        //si inserisce un oggetto di tipo button
        Button tv = (Button) v.findViewById(R.id.button);
        //si preleva il contenuto dell'item che si trova nella posizione
        //fornita dall'indice ricevuto come parametro
        String cell_type = items.get(position);
        //il testo del button viene assegnato prelevando il contenuto dell'item
        tv.setText(cell_type);
        //si seleziona l'immagine di ogni pulsante
        int icon = setButtonIcon(cell_type);
        //si imposta l'immagine di ogni pulsante
        tv.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        tv.setPadding(0,4,0,4);
        //si restituisce il singolo oggetto View
        return v;
    }//getView(int, View,ViewGroup)

    /** metodo getItemId(int): long
     * questo metodo restiuisce il codice identificativo dell'oggetto
     * che si trova nella posizione ricevuta come parametro
     * @param position: int, indice posizionale dell'oggetto di iteresse;
     * @return position: int;
     */
    @Override
    public final long getItemId(int position) {
        //il codice id coincide con la posizione in cui si trova l'oggetto
        return position;
    }//getItemId(int)

    /**
     *
     * @param cell_type
     * @return
     */
    private int setButtonIcon(String cell_type){
        //variabile ausiliaria che indica il codice dell'icona
        int icon = 0;
        //switch case sul contenuto della singola cella della mappa
            switch (cell_type){
                case "P":
                    if(game_mode==0) {
                        icon = R.drawable.hero_pg;
                    }
                    else {
                        icon = R.drawable.wumpus_pg;
                    }

                    break;
                case "E":
                    icon = R.drawable.wumpus_pg;
                    break;
                default:
                    break;
            }

        return icon;

    }//setButtonIcon

}//end GridViewCustomAdapter