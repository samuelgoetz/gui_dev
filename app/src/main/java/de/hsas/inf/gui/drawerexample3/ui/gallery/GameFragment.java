package de.hsas.inf.gui.drawerexample3.ui.gallery;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import  de.hsas.inf.gui.drawerexample3.R;
// Falls du ViewBinding hast:

import de.hsas.inf.gui.drawerexample3.databinding.FragmentGameBinding;


public class GameFragment extends Fragment {

    private static final int[] DICE_DRAWABLES = {
            R.drawable.w_rfel_1_black, // Index 0 -> Wurf=1
            R.drawable.w_rfel_2_black, // Index 1 -> Wurf=2
            R.drawable.w_rfel_3_black,
            R.drawable.w_rfel_4_black,
            R.drawable.w_rfel_5_black,
            R.drawable.w_rfel_6_black
    };


    private FragmentGameBinding binding;
    private GameViewModel gameViewModel;

    // Falls du deine Felder in einem Array halten möchtest:
    private ImageView[] fieldViews;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Falls du ViewBinding nutzt
        binding = FragmentGameBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModel holen
        gameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        // Deine Felder "sammeln"
        fieldViews = new ImageView[] {
                binding.field0,
                binding.field1,
                binding.field2,
                binding.field3,
                binding.field4,
                binding.field5,
                binding.field6,
                binding.field7,
                binding.field8,
                binding.field9,
                binding.field10,
                binding.field11,
                binding.field12,
        };

        // LiveData beobachten
        gameViewModel.getPlayerPos().observe(getViewLifecycleOwner(), newPos -> {
            updateBoard();
        });
        gameViewModel.getAppPos().observe(getViewLifecycleOwner(), newPos -> {
            updateBoard();
        });

        gameViewModel.getPlayerDiceRoll().observe(getViewLifecycleOwner(), roll -> {
            // roll ist zwischen 1 und 6
            binding.diceGreen.setImageResource(DICE_DRAWABLES[roll - 1]);
        });

        gameViewModel.getAppDiceRoll().observe(getViewLifecycleOwner(), roll -> {
            binding.diceRed.setImageResource(DICE_DRAWABLES[roll - 1]);
        });

        // Klick‐Listener: Spieler würfelt
        binding.diceGreen.setOnClickListener(v -> {
            gameViewModel.rollPlayerDice();
            gameViewModel.rollAppDice();

            String winner = gameViewModel.checkWinner();
            if (winner != null) {
                Toast.makeText(getContext(), winner + " wins!", Toast.LENGTH_SHORT).show();
                gameViewModel.resetGame();
            }
        });

        // Initiales Board zeichnen
        updateBoard();
    }

    private void updateBoard() {
        // 1) Alle Felder auf "leer" setzen
        for (ImageView field : fieldViews) {
            field.setImageResource(R.drawable.ic_circle_black);
        }

        // 2) Spieler‐Position
        Integer pPos = gameViewModel.getPlayerPos().getValue();
        if (pPos != null && pPos >= 0 && pPos < fieldViews.length) {
            // HIER soll das grüne Feld gesetzt werden
            fieldViews[pPos].setImageResource(R.drawable.ludo_gamer_green);
        }

        // 3) App‐Position
        Integer aPos = gameViewModel.getAppPos().getValue();
        if (aPos != null && aPos >= 0 && aPos < fieldViews.length) {
            // Prüfen, ob Kollision:
            if (pPos != null && pPos.equals(aPos)) {
                // Einzelentwicklung -> zweifarbig
                fieldViews[aPos].setImageResource(R.drawable.ludo_gamers_red_green);
            } else {
                // Sonst: rote Figur
                fieldViews[aPos].setImageResource(R.drawable.ludo_gamer_red);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
