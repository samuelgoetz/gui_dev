package de.hsas.inf.gui.drawerexample3.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private MutableLiveData<Integer> fieldNo;
    private MutableLiveData<Integer> imgNo;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        fieldNo = new MutableLiveData<>();
        imgNo = new MutableLiveData<>();
        fieldNo.setValue(0);
        imgNo.setValue(0);
       // mText.setValue("This is gallery fragment");
    }

    // Mini-Spiellogik. Sie haelt nur die Positions- und Werte-Information
    //(Welche Platznr. und welche Bildnr.)
    public void diceFieldNo() {
        Random r = new Random();
        int f = r.nextInt(3);
        fieldNo.setValue(f);
    }

    public void diceImgNo() {
        Random r = new Random();
        int f = r.nextInt(3);
        imgNo.setValue(f);
    }


    public LiveData<Integer> getFieldNo() { return  fieldNo;}
    public LiveData<Integer> getImgNo() {return imgNo; }
    public LiveData<String> getText() {
        return mText;
    }
}