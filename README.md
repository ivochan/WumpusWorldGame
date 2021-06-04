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
    - ~~abilitare i suoni~~
    - ~~scelta del nome del giocatore~~
    - importa dati di gioco
    - esporta dati di gioco
    - about
      - ~~feedback~~
      - ~~team~~
  - ~~tutorial~~
  - ~~informazioni di gioco~~
  - punteggi:
    - record
    - classifica
    - attuale
    - ~~condivisione~~
- gestire il tasto di navigazione -> in modo che punti all'activity precedente
- ~~rendere a schermo intero la schermata di caricamento della partita~~;
- fare in modo che all'avvio venga ripristinato lo stato in cui è stata interrotta la partita precedente, alla chiusura dell'applicazione;
- ~~togliere l'autosize al testo animato della schermata iniziale~~
- ~~rallentare la velocità di scorrimento del testo animato ed inserire una scroll bar~~
- ~~inserire una la scroll bar che scorra automaticamente fino all'ultimo carattere del testo animato visualizzato;~~
- ~~allineare il valore del punteggio con l'etichetta che indica il rispettivo campo, nella schermata di gioco~~
- ~~spostare la box dei messaggi sopra la matrice di gioco e dargli una forma rettangolare;~~
- ~~azione di invio di un feedback;~~
- ~~scelta del gestore mail per inviare il feedback;~~
- ~~disabilita/abilita traccia audio per ogni activity;~~
- ~~sistemare layout del testo nella game info activity;~~
- effettuare le traduzioni in inglese;
- scegliere le clip audio per le activity secondarie;
- ~~definire tema per il layout della alert dialog~~
- ~~inserire nome del giocatore dalle impostazioni~~
- creare l'activity che si occupa di visualizzare i punteggi di gioco:
  - ~~definire il layout~~
  - visualizzare i contenuti
  - ~~visualizzare il punteggio più' alto~~
  - ordinare in ordine decrescente i punteggi
  - mantenere fino a dieci punteggi
  - ogni punteggio e' preceduto dal nome scelto dal giocatore
  - ~~permettere la condivisione del punteggio piu' alto~~
  - ~~visualizzare il punteggio corrente~~;
  - ~~condividere il punteggio attuale;~~
- esportare il file dei punteggi ed il nome attuale del giocatore (dati di gioco)
- importare il file dei punteggi ed il nome attuale del giocatore (dati di gioco)
- scrivere e leggere il file dei punteggi:
  - aggiornandolo dopo ogni partita
  - ordinando i dieci punteggi in ordine decrescente
  - tenendo memorizzato l'ultimo nome del giocatore
- strutturare il menù delle schermate di gioco:
  - definire un layout per entrambe le modalità,
  - implementare le seguenti voci:
    - ~~nuova partita, (la modalità è quella a cui si sta giocando);~~
    - risolvi partita (giocatore automatico);
    - tutorial dei comandi di gioco;
    - punteggi;
- 



