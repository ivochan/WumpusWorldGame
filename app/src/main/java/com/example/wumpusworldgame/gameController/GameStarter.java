package com.example.wumpusworldgame.gameController;

public class GameStarter {

    private static OnGameStartChangeListener listener;

    private static boolean flag;

    public GameStarter(boolean flag){
        this.flag=flag;
    }

    public void setOnGameStartChangeListener(OnGameStartChangeListener listener){
        this.listener = listener;
    }

    public static boolean getGameStart(){
        return flag;
    }

    public static void setGameStart(boolean value){
        flag = value;
        if(listener != null) {
            listener.onGameStartChanged(value);
        }
    }


    public interface OnGameStartChangeListener {
        public void onGameStartChanged(boolean newValue);
    }

}
