package com.example.wumpusworldgame.gameActivities;
import com.example.wumpusworldgame.gameController.GameController;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
//serie di import
public class GameSessionCheckThread extends  Thread{

    //##### attributi di classe #####
    private Lock l;
    private Condition c;

    private int id=s_id++;
    private static int s_id=1;

    //costruttore
    public GameSessionCheckThread(Lock l, Condition c) {
        //assegnamento dei parametri
        this.l=l;
        this.c=c;
    }


    @Override
    public void run() {
            try {
                l.lock();
                while(!HeroSide.endGame()) {
                    c.await();
                }

                GameController.endGameSession("c");
                Thread.sleep(500);

            } catch(Exception e) {
                e.printStackTrace();
                System.exit(0);
            } finally {
                l.unlock();
            }
            System.out.println("è stato premuto il pulsante. Thread "+id+" ha reagito!");
    }//run

}//end
