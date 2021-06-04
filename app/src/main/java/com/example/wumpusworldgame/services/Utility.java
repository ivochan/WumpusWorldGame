package com.example.wumpusworldgame.services;
//serie di import
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.preference.PreferenceManager;
import com.example.wumpusworldgame.R;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/** class  Utility
 * classe di utilita' che contiene una serie di metodi statici da
 * richiamare in entrambe le modalita' di gioco perche' comuni.
 * //TODO revisionare tutti i metodi e scrivere la documentazione
 */
public class Utility {
    //##### attributi di classe #####

    //email dello sviluppatore
    public final static String SUPPORT_EMAIL = "ivochan17@gmail.com";

    /** metodo showLoadingScreen(Activity, LayoutInflater)
     * questo metodo realizza una schermata di caricamento
     * che viene mostrata appena si decide la modalita' di
     * gioco, prima che all'utente venga mostrata la mappa.
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
        }, 1500);
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
            mp.pause();
        }//esle
    }//musicPlaying(MediaPlayer,Activity)

    /** metodo collectDeviceInfo(): String
     * questo metodo raccoglie tutte le informazioni sul dispositivo in
     * uso che verranno poi inviate allo sviluppatore per il feedback.
     * @param screen_size: String, la dimensione del display del dispositivo
     *                   in uso, e' ricevuta come parametro perche' per
     *                   calcolarla e' necessario essere all'interno di
     *                   un'activity.
     * @return info: String, stringa che contiene tutte le informazioni
     *              tecniche che riguardano il dispositivo su cui sta
     *              girando l'applicazione.
     */
    public static String collectDeviceInfo(String screen_size){
        //variabile ausiliaria
        String info = null;
        //stringa che identifica l'inizio delle specifiche del dispositivo
        String start = "###      DEVICE INFO      ###\n\n";
        //marca del dispositivo
        String brand = Build.BRAND.toString();
        //modello del dispositivo
        String model = Build.MODEL.toString();
        //chipset del dispositivo
        String hardware = Build.HARDWARE.toString();
        //processore del dispositivo
        String cpu = Build.CPU_ABI.toString();
        //versione del sistema operativo
        String os = Build.VERSION.BASE_OS.toString();
        String os1 = os.replace(brand,"");
        String os2 = os1.replace(model,"");
        String os3 = os2.replace("//","");
        //display del dispositivo
        String display = Build.DISPLAY.toString();
        String display1 = display.replace(model,"");
        //memoria interna del dispositivo
        String memory = getMemoryCapacity();
        //memoria RAM del dispositivo
        String ram = getTotalRAM();
        //stringa che identifica la fine del corpo di testo riservato alle informazioni
        //del dispositivo e l'inizio dello spazio in cui l'utente puo' scrivere il messaggio
        String end = "###       MESSAGE     ###\n\n";
        //stringa da restituire, che concatena tutte le precedenti
        info = start+ "Model:  "+brand+" "+model+
                "\nHardware:  "+hardware+"\nCPU:  "+cpu+
                "\nBase OS:  "+os3+
                "\nDisplay:  "+display1+
                "\nScreen size:  "+screen_size+" \""+
                "\nMemory:  "+memory+
                "\nRAM:  "+ram+
                "\n\n"+end;
        //si restituisce la stringa contenente tutte le info sul dispositivo
        return info;
    }//collectDeviceInfo()

    /** metodo getMemoryCapacity(): String
     * metodo che si occupa di calcolare la capacita' della memoria
     * interna del dispositivo in uso.
     * @return memory: String, stringa che contiene le informazioni sulla
     *              memoria del dispositivo occupata e totale, secondo
     *              il seguente formato <spazio_occupato>GB / <spazio_totale>GB
     */
    private static String getMemoryCapacity(){
        //variabile ausiliaria
        String memory;
        //si preleva il path della memoria del dispositivo
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        //memoria totale
        //la parte di memoria che non viene contata e' una partizione riservata al sistema
        long totalBytes = (stat.getBlockSizeLong()*stat.getBlockCountLong());
        //conversione da byte a gb
        String internalTotalSpace = byteConversion(totalBytes);
        //memoria disponibile
        long availableBytes = stat.getBlockSizeLong()*stat.getAvailableBlocksLong();
        //conversione da byte in gb
        String internalAvailableSpace = byteConversion(availableBytes);
        //memoria utilizzata
        long bytesUsed = totalBytes - availableBytes;
        //conversione da byte a gb
        String internalUsedSpace = byteConversion(bytesUsed);
        //si compone la stringa da restituire
        memory = ""+internalAvailableSpace+"/"+internalTotalSpace;
        //si restituisce
        return memory;
    }//getMemoryCapacity()

    /** metodo byteConversion(long): String
     * questo metodo converte il valore ricevuto il byte
     * in un0altra unita' di misura in base al parametro size
     * @param size
     * @return String
     */
    public static String byteConversion(long size){
        //tabella dei valori
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;
        //conversione
        if (size <  Kb)
            return floatForm(size) + " byte";
        else if (size >= Kb && size < Mb)
            return floatForm((double)size / Kb) + " KB";
        else if (size >= Mb && size < Gb)
            return floatForm((double)size / Mb) + " MB";
        else if (size >= Gb && size < Tb)
            return floatForm((double)size / Gb) + " GB";
        else if (size >= Tb && size < Pb)
            return floatForm((double)size / Tb) + " TB";
        else if (size >= Pb && size < Eb)
            return floatForm((double)size / Pb) + " PB";
        else if (size >= Eb)
            return floatForm((double)size / Eb) + " EB";
        else
            return "";
    }//byteConversion(long)

    /** metodo floatForm(double): String
     * questo metodo restiuisce la stringa che rappresenta
     * il numero double convertito in forma decimale
     * @param d
     * @return
     */
    public static String floatForm (double d)    {
        //
        return new DecimalFormat("#.##").format(d);
    }//floatForm(double)

    /** metodo getTotalRAM(): String
     *
     * @return
     */
    public static String getTotalRAM() {
        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();
            totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;
            double mb = totRam / 1024.0;
            double gb = totRam / 1048576.0;
            double tb = totRam / 1073741824.0;

            if (tb > 1) {
                lastValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = twoDecimalForm.format(mb).concat(" MB");
            } else {
                lastValue = twoDecimalForm.format(totRam).concat(" KB");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }
        return lastValue;
    }//getTotalRAM()


}// end class Utility
