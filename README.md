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

- gestire le voci del menu:

  - ~~avvio di una nuova partita~~;
  - risoluzione della partita corrente;
  - ~~schermata di informazioni sul gioco~~;
  - elenco dei punteggi;
  - tutorial;

- clip audio per la modalità wumpus;

- memorizzazione della matrice di gioco per la risoluzione;

- test del giocatore automatico;

  



