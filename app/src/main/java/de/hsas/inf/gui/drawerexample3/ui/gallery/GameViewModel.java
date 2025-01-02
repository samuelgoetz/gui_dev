package de.hsas.inf.gui.drawerexample3.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class GameViewModel extends ViewModel {

    private final MutableLiveData<Integer> playerPos = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> appPos = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> playerDiceRoll = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> appDiceRoll = new MutableLiveData<>(0);

    // Beispielhaftes Limit, bis wohin gelaufen werden muss:
    private static final int FINISH_POS = 8;
    private final Random random = new Random();

    public GameViewModel() {
        // Optional: Initialwerte setzen, falls nötig
        playerPos.setValue(0);
        appPos.setValue(0);
    }

    // Getter für die LiveData
    public LiveData<Integer> getPlayerPos() {
        return playerPos;
    }

    public LiveData<Integer> getAppPos() {
        return appPos;
    }

    public LiveData<Integer> getPlayerDiceRoll() {
        return playerDiceRoll;
    }

    public LiveData<Integer> getAppDiceRoll() {
        return appDiceRoll;
    }

    // Spieler würfelt
    public void rollPlayerDice() {
        int roll = random.nextInt(6) + 1; // 1..6
        playerDiceRoll.setValue(roll);

        // Position updaten
        Integer currentPos = playerPos.getValue();
        if (currentPos == null) currentPos = 0;
        int newPos = currentPos + roll;
        if (newPos > FINISH_POS) {
            newPos = FINISH_POS; // oder nur FINISH_POS begrenzen
        }
        playerPos.setValue(newPos);
    }

    // App würfelt
    public void rollAppDice() {
        int roll = random.nextInt(6) + 1;
        appDiceRoll.setValue(roll);

        // Position updaten
        Integer currentPos = appPos.getValue();
        if (currentPos == null) currentPos = 0;
        int newPos = currentPos + roll;
        if (newPos > FINISH_POS) {
            newPos = FINISH_POS;
        }
        appPos.setValue(newPos);
    }

    // Beispiel: prüfen, ob jemand gewonnen hat
    public String checkWinner() {
        if (playerPos.getValue() != null && playerPos.getValue() == FINISH_POS) {
            return "PLAYER";
        }
        if (appPos.getValue() != null && appPos.getValue() == FINISH_POS) {
            return "APP";
        }
        return null; // kein Gewinner
    }

    // Zurücksetzen des Spiels
    public void resetGame() {
        playerPos.setValue(0);
        appPos.setValue(0);
        playerDiceRoll.setValue(0);
        appDiceRoll.setValue(0);
    }
}
