package com.example.wumpusworldgame.services;
//serie di import
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

/** class tupeWriter
 * questa classe e' utilizzata per realizzare un testo animato, che
 * viene visualizzato carattere per carattere, emulando il comportamento
 * di una macchina da scrivere
 */
public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {
    //##### attribuiti di classe #####

    //sequenza dei caratteri da visualizzare
    private CharSequence mText;
    //indice utilizzato per scorrere la sequenza di caratteri
    private int mIndex;
    //valore del delay di default, indica il ritardo che intercorre
    //tra la visualizzazione di un carattere e del successivo
    private long mDelay = 150; //in ms

    /** TypeWriter(Context): costruttore
     *
     * @param context
     */
    public TypeWriter(Context context) {
        //si richiama il costruttore della super classe
        super(context);
    }//TypeWriter(Context)

    /** TypeWriter(Context, AttributeSet): costruttore
     *
     * @param context
     * @param attrs
     */
    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }//TupeWriter(Contex, AttribureSet

    //
    private Handler mHandler = new Handler();

    /**
     *
     */
    private Runnable characterAdder = new Runnable() {

        /**
         *
         */
        @Override
        public void run() {
            //
            setText(mText.subSequence(0, mIndex++));

            //
            if (mIndex <= mText.length()) {
                //
                mHandler.postDelayed(characterAdder, mDelay);
            }
        }
    };

    /**
     *
     * @param txt
     */
    public void animateText(CharSequence txt) {
        mText = txt;
        mIndex = 0;

        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    /**
     *
     * @param m
     */
    public void setCharacterDelay(long m) {
        mDelay = m;
    }
}//end TypeWriter