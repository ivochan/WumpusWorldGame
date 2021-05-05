package com.example.wumpusworldgame;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

public class GameInfoDialog extends DialogFragment {

        public static GameInfoDialog newInstance(int title) {

            GameInfoDialog frag = new GameInfoDialog();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.bat_idle)
                    .setTitle(title)
                    .create();
        }
    }

