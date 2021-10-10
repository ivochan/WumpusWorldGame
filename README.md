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

- ~~scegliere clip audio per la modalità wumpus;~~
- inserire nella main activity il menu con:
  - le impostazioni di gioco, quali:
    - ~~abilitare i suoni~~
    - ~~scelta del nome del giocatore~~
      - ~~aggiornare il valore alla modifica~~
      - ~~controllare l'inserimento di caratteri non validi~~
    - importa dati di gioco:
      - ~~modifica del file dei punteggi~~
      - i~~controllo della validità del file importatoi~~
    - ~~esporta dati di gioco~~
    - ~~cancella dati di gioco~~
    - about
      - ~~feedback~~
      - ~~team~~
  - tutorial (due pagine)
    - ~~personaggi~~
    - ~~comandi~~
  - ~~informazioni di gioco~~
  - punteggi:
    - ~~record~~
    - ~~classifica~~
    - ~~attuale~~
    - ~~condivisione~~
- ~~gestire il tasto di navigazione -> in modo che punti all'activity precedente~~
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
- ~~effettuare le traduzioni in inglese;~~
- scegliere le clip audio per:
  - le activity secondarie;
  - suoni di gioco(ad esempio per la vittoria);
- ~~definire tema per il layout della alert dialog~~
- ~~inserire nome del giocatore dalle impostazioni~~
- creare l'activity che si occupa di visualizzare i punteggi di gioco:
  - ~~definire il layout~~
  - ~~visualizzare i contenuti~~
  - ~~visualizzare il punteggio più' alto~~
  - ~~ordinare in ordine decrescente i punteggi~~
  - ~~estrarre fino a dieci punteggi~~
  - ~~ogni punteggio e' preceduto dal nome scelto dal giocatore~~
  - ~~permettere la condivisione del punteggio piu' alto~~
  - ~~visualizzare il punteggio corrente~~;
  - ~~condividere il punteggio attuale;~~
- scrivere e leggere il file dei punteggi:
  - ~~aggiornandolo dopo ogni partita~~
  - ~~ordinando i dieci punteggi in ordine decrescente~~
- strutturare il menù delle schermate di gioco:
  - ~~nuova partita, (la modalità è quella a cui si sta giocando);~~
  - ~~risolvi partita (giocatore automatico);~~
  - ~~tutorial della modalità (con comandi di gioco)~~;
- ~~inserire le icone nella mappa di gioco;~~
- ~~visualizzare le icone nella mappa di esplorazione;~~
- ~~definizione del controller di gioco per entrambe le modalità;~~
- ~~realizzazione di una classe controller che comunichi con il backend per effettuare le azioni di gioco;~~
- ~~introdurre le stringhe che contengono le informazioni sullo stato della partita;~~
- la classe controller dovrà avere le seguenti funzionalità:
  - ~~metodo che preleva l'activity corrente per poter accedere alle risorse;~~
  - ~~metodo che preleva la mossa fatta dal giocatore;~~
  - ~~metodo che esegue la mossa scelta dal giocatore;~~
  - ~~funzione che impedisce di effettuare altre mosse al termine della partita;~~
  - ~~metodo che si occupa di tentare di colpire il nemico;~~
  - ~~metodo che verifica se si ha a disposizione un tentativo di colpire il nemico;~~
- sostituire i metodi deprecati;
- dark mode:
  - definire i colori principali
  - creare le dialog adeguate
  - creare uno sfondo per main, hero_side e wumpus_side activity;
- controllare proporzioni del layout sui dispositivi con schermo più piccolo;
- ~~togliere le iniziali del contenuto delle celle nella mappa;~~
- ~~verificare che ogni button della mappa abbia la stessa dimensione;~~
- ~~correggere bug sulla clip audio della mainActivity alla disattivazione degli effetti sonori~~;
- ~~scurire le icone e lo sfondo delle celle non visitate alla fine della partita;~~
- strutturare la dialog a fine partita con:

  - ~~effetto dissolvenza~~
  - ~~predisporre il condice per mostrare la dialog 1 s dopo aver terminato la partita~~
  - ~~messaggio sul risultato della partita( vinta o persa)~~
  - ~~alla chiusura mostrare la mappa di gioco con le celle non visitate~~
  - ~~mostrare la mappa di gioco completa nella suddetta dialog~~
  - alla scelta "condividi"
    - ~~condividere la mappa di gioco completa~~
    - ~~condividere il punteggio~~ 
    - ~~condividere l'attuale nome del giocatore, se specificato~~
    - condividere il link di download dell'app (github)
      - ~~normale~~
      - con href(hyperlink)
- ~~cambiare titolo delle schermate dei comandi di gioco~~
- ~~applicare lo stile personalizzato alla dialog del feedback~~
- ~~aggiungere i permessi di lettura e scrittura della memoria esterna~~
- ~~aggiungere il provider per la condivisione dei file nei dispositivi con sdk superiori alla 23~~
- Disegnare elementi grafici, quali:
  - icona PG
  - icone NPG
  - sfondi
  - icona app
- activity per la risoluzione automatica della partita:

  - test del giocatore automatico;
  - ~~memorizzazione della matrice di gioco per la risoluzione;~~
  - ~~schermata di risoluzione;~~
  - ~~definizione del layout;~~
  - ~~inserire un flag statico per gestire celle coperte e visitate nell'adapter~~
  - ~~creare un adapter distinto da quello delle classi di gioco interattive;~~
  - ~~inserire dei flag per gestire il tentativo di sparo, in modo da disambiguare le risorse con le classi di gioco interattivo;~~
  - ~~aggiornare il campo di testo della disponibilita' del colpo;~~





