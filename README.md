# Wumpus World Game

## Introduzione

Questo progetto rappresenta l'implementazione, in **Android** e **Java**, del videogioco conosciuto con il nome di "**Hunt the Wumpus**", realizzato nel 1972 da Gregory Yob.

Si tratta di un gioco di avventura che si svolge in un labirinto, le cui caratteristiche verranno generate in maniera casuale, strutturato come una composizione di stanze, comunicanti se adiacenti.

Il giocatore interpreta il ruolo di un *cacciatore*, che durante l'esplorazione del labirinto, dovrà sopravvivere alle insidie presenti, come il pozzo e individuare la tana del mostro, il *Wumpus*. Questo sarà possibile sfruttando le tracce disseminate per il labirinto, ovvero il cattivo odore emanato dal mostro, che giunge sino alle celle attigue alla sua tana (nel gioco originale si trattava di macchie di sangue).

La sola possibilità che si ha di sfuggire al mostro è quella di ucciderlo scoccando una freccia da una qualsiasi stanza adiacente a quella in cui è nascosto.

Inoltre, bisogna evitare le stanze con i pozzi, in cui si corre il rischio di caderci dentro, attigue a quelle in cui giunge la brezza (nel gioco originale era presente il muschio). Un ulteriore rischio, nel gioco di Yob, è costituito dai super-pipistrelli, che possono catturare il giocatore e rilasciarlo in una stanza differente, scelta in maniera casuale.



## Struttura del progetto

Il codice utilizzato per implementare il back-end questa versione del gioco è stato scritto in Java.

Per quanto riguarda, la parte grafica dell'applicazione, ovvero l'interfaccia utente, è stata realizzata in Android.

In questa versione del gioco è stata prevista, per il giocatore, la possibilità di interpretare, indifferentemente, il ruolo del **Wumpus** o del cacciatore, a cui ci si riferisce come **Avventuriero**.



## TODO

- scegliere clip audio per la modalità wumpus;
- memorizzazione della matrice di gioco per la risoluzione;
- test del giocatore automatico;
- inserire nella main activity il menu con:
  - le impostazioni di gioco, quali:
    - abilitare i suoni
    - scelta del nome del giocatore
    - punteggi:
      - importa dati di gioco
      - esporta dati di gioco
    - feedback
  - tutorial
  - informazioni di gioco
- gestire il tasto di navigazione -> in modo che punti all'activity precedente
- rendere a schermo intero la schermata di caricamento della partita;
- fare in modo che all'avvio di una nuova partita si mantenga la modalità di gioco scelta in precedenza, ovvero se il pg sarà l'avventuriero oppure il wumpus
- ~~togliere l'autosize al testo animato della schermata iniziale~~
- ~~rallentare la velocità di scorrimento del testo animato ed inserire una scroll bar~~
- ~~inserire una la scroll bar che scorra automaticamente fino all'ultimo carattere del testo animato visualizzato;~~
- allineare il valore del punteggio con l'etichetta che indica il rispettivo campo, nella schermata di gioco
- spostare la box dei messaggi sopra la matrice di gioco e dargli una forma rettangolare
- fare una ricerca sulla grandezza dello schermo, in pollici, dei dispositivi più comuni.
- azione di invio di un feedback;
- disabilita/abilita traccia audio per ogni activity;
- layout del testo nella game info activity;
- richiesta permessi all'utente per l'utilizzo dell'app;
- tradurre il testo della game info activity in inglese
- 



