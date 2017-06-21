package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.avisos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisosFragment extends Fragment {


    public AvisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_avisos, container, false);
    }

}
