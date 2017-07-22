package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvisosFragment extends BaseFragment {

    private View view;


    public AvisosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_avisos, container, false);
        ListView listView;
        String[] avisos = {"aviso1", "aviso2", "aviso3", "aviso4"};
        listView = (ListView) view.findViewById(R.id.list_avisos);
        ArrayAdapter<String> array = new ArrayAdapter<>(this.getContext(),android.R.layout.simple_list_item_1, avisos);
        listView.setAdapter(array);
        return view;
    }

}
