package de.hsas.inf.gui.drawerexample3.ui.gallery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import de.hsas.inf.gui.drawerexample3.R;
import de.hsas.inf.gui.drawerexample3.databinding.FragmentGalleryBinding;

/*

*/
public class GalleryFragment extends Fragment {

    //Instanz von fragment_gallery.xml
    //Ermöglicht Zugriff auf die Views in der xml
    private FragmentGalleryBinding binding;


    private ImageView current; // Aktuell platzierte ImageView
    private ImageView [] imageViews; // Alle Image Views  (cell1, cell2, cell3)

    private Drawable empty;
    private Drawable [] drawables; // (color1, color2, color3)



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        //Erzeugen der Binding Instanz
        binding = FragmentGalleryBinding.inflate(inflater, container, false);

        // liefert die Wurzelansicht des aufgeblasenen Layouts
        // (z. B. das oberste LinearLayout oder ConstraintLayout im fragment_gallery.xml).
        View root = binding.getRoot();

        current = imageViews[0];
        imageViews = new ImageView[3];
        imageViews[0] = binding.cell1;
        imageViews[1] = binding.cell2;
        imageViews[2] = binding.cell3;

        //Drawables mit Hintergund laden
        empty = getResources().getDrawable(R.drawable.empty,null);
        drawables = new Drawable[3];
        drawables[0] = getResources().getDrawable(R.drawable.color1,null);
        drawables[1] = getResources().getDrawable(R.drawable.color2,null);
        drawables[2] = getResources().getDrawable(R.drawable.color3,null);


        // Beobachter fuer Feldnr. im ViewModel
        galleryViewModel.getFieldNo().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            // Wird aufgerufen, sobald Wert von fieldNo sich aendert
            @Override
            public void onChanged(Integer integer) {
                int fieldNo = integer;
                current = imageViews[fieldNo];
            }
        });

        galleryViewModel.getImgNo().observe(getViewLifecycleOwner(),new Observer<Integer>(){
            // Wird aufgerufen, sobald Wert von fieldNo sich aendert
            @Override
            public void onChanged(Integer integer) {
                int imgNo = integer;
                Drawable d = drawables[imgNo];
                current.setImageDrawable(d);
            }
        });

        //Next Button:
        binding.next.setOnClickListener(v->{

            // Felder werden wieder weiß gemacht
            for (ImageView view : imageViews) {
                view.setImageDrawable(empty);
            }
            galleryViewModel.diceFieldNo();
            galleryViewModel.diceImgNo();
        });

        //final TextView textView = binding.textGallery;
        //galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}