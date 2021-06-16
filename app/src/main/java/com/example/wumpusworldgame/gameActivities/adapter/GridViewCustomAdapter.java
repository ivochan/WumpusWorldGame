package com.example.wumpusworldgame.gameActivities.adapter;
//serie di import
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.example.wumpusworldgame.R;
import com.example.wumpusworldgame.gameActivities.HeroSide;
import game.session.configuration.Starter;
/** class GridViewCustomAdapter
 * questa classe serve per visualizzare i dati nella
 * GridView che ha consentito di realizzare la mappa nella schermata di gioco
 */
public class GridViewCustomAdapter extends BaseAdapter {
    //##### attributi di classe #####

    //arrayList di stringhe che contiene gli elementi della mappa di gioco
    private ArrayList<String> game_items;
    //arrayList di stringhe, in cui ogni stringa descrive il contenuto della cella della mappa di esplorazione
    private ArrayList<String> items;
    //activivy in cui verra' utilizzato
    private static Activity mActivity;
    //oggetto layoutInflater per unire questo componente al layout dell'activity
    private static LayoutInflater inflater = null;
    //intero che indica la modalita' di gioco
    private static int game_mode=0;

    /** GridViewCustomAdapter(Activity, ArrayList): costruttore
     * permette di istanziare l'oggetto gridViewCustomAdapter
     * @param activity: Activity, activity in cui verra' utilizzato l'adapter
     * @param tempArrayList: ArrayList<String>, oggetto che contiene gli items
     */
    public GridViewCustomAdapter(Activity activity, ArrayList<String> tempArrayList, ArrayList<String> tempGameArrayList) {
        //si preleva l'activity
        mActivity = activity;
        //si verifica il valore dell'intero
        if(mActivity instanceof HeroSide){
            //ci si trova nella modalita' di gioco Eroe
            game_mode = 0;
        }//fi
        else {
            //ci si trova nella modalita' di gioco Wumpus
            game_mode = 1;
        }//esle
        //si prelevano gli oggetti della mappa di gioco
        game_items = tempGameArrayList;
        //si prelevano gli oggetti da visualizzare
        items = tempArrayList;
        //si utilizza l'inflater
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
     * @return o: Object.
     */
    @Override
    public final Object getItem(int position) {
        //variabile ausiliaria
        Object o = items.get(position);
        //si restituisce l'oggetto
        return o;
    }//getItem(int)

    /** metodo getView(int, View, ViewGroup): View
     * questo metodo consente di visualizzare il contenuto nell'oggetto GridView
     * aggiungendo al suo interno ogni oggetto Button
     * @param position
     * @param convertView
     * @param parent
     * @return v: View;
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //si istanzia un oggetto View
        View v;
        //si utilizza l'oggetto inflater per aggiungere il layout
        //di ogni oggetto da inserire nella griglia
        v = inflater.inflate(R.layout.grid_item, null);
        //si inserisce un oggetto di tipo button
        Button tv = v.findViewById(R.id.button);
        //altezza del pulsante
        tv.setHeight(62);
        //larghezza del pulsante
        tv.setWidth(86);
        //si preleva il contenuto dell'item che si trova nella posizione
        //fornita dall'indice ricevuto come parametro
        String cell_type = items.get(position);
        //si seleziona l'immagine di ogni pulsante
        int icon = setButtonIcon(cell_type);
        //si imposta l'immagine di ogni pulsante
        tv.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        //padding
        tv.setPadding(0, 8, 0, 0);
        //la partita e' terminata
        //si deve aggiornare la mappa segnalando le celle ancora coperte
        if(!Starter.getGameStart()) {
            //si preleva il contenuto della cella della mappa di gioco
            //che si trova nella stessa posizione di quella nella mappa di esplorazione
            String game_cell_type = game_items.get(position);
            //prendo il contenuto della cella
            if(cell_type.equals(" ")){
                //se vuoto e' una cella coperta
                //si preleva l'icona
                int covered_icon = setButtonIcon(game_cell_type);
                //si verifica la versione dell'sdk
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //le icone delle caselle coperte vengono visualizzate come nere
                    tv.setCompoundDrawableTintList(ColorStateList.valueOf(Color.BLACK));
                    //sfondo delle caselle coperte
                    tv.setBackgroundResource(R.drawable.covered_grid_item_specs);
                    //si imposta l'immagine di ogni pulsante
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, covered_icon, 0, 0);
                }//fi
                else {
                    //per le versioni inferiori dell'sdk le celle sono nere
                    tv.setBackgroundResource(R.color.dark_brown_700);
                }//else
            }//fi cella coperta
        }//fi partita terminata
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

    /** metodo setButtonIcon(String, int): int
     * questo metodo assegna ad ogni button della mappa di gioco un'icona
     * in base alla modalita' di gioco assegnata come parametro di classe
     * @param cell_type: String, contenuto della cella di gioco;
     * @return icon: int, codice identificativo dell'icona del button.
     */
    private int setButtonIcon(String cell_type){
        //variabile ausiliaria che indica il codice dell'icona
        int icon = 0;
        //switch case sul contenuto della singola cella della mappa
            switch (cell_type){
                //PG
                case "P":
                    if(game_mode==0) {
                        //hero side
                        icon = R.drawable.hero_pg;
                    }//fi
                    else {
                        //wumpus side
                        icon = R.drawable.wumpus_pg;
                    }//else
                    break;
                //ENEMY
                case "E":
                    if(game_mode==0) {
                        //hero side
                        icon = R.drawable.wumpus_pg;
                    }//fi
                    else {
                        //wumpus side
                        icon = R.drawable.hero_pg;
                    }//else
                    break;
                //DANGER
                case "D":
                    if(game_mode==0){
                        //hero_side : fossa/pozzo
                        icon = R.drawable.pit_hero_danger;
                    }//fi
                    else{
                        //wumpus_side: trappola
                        icon = R.drawable.trap_wumpus_danger;
                    }//else
                    break;
                //AWARD
                case "A":
                    if(game_mode==0){
                        //hero_side : tesoro
                        icon = R.drawable.chest_hero_award;
                    }//fi
                    else {
                        //wumpus_side: uscita
                        icon = R.drawable.exit_wumpus_award;
                    }//esle
                    break;
                //FORBIDDEN
                case "F":
                   icon = R.drawable.mangrove;
                    break;
                //SAFE--> OBSERVED
                case "O" :
                    icon = R.drawable.foot_path;
                    break;
                default:
                    break;
            }//end switch
        return icon;
    }//setButtonIcon(String, int)

    /** metodo getCurrentActivity
     * quesot metodo restituisce l'activity in cui e' stato
     * utlizzato l'adapter, cioe' quella cui verra' visualizzata
     * la griglia.
     * @return mActivity: Activity
     */
    public static Activity getCurrentActivity() {
        //restituisce l'activity corrente
        return mActivity;
    }//getCurrentActivity()

}//end GridViewCustomAdapter