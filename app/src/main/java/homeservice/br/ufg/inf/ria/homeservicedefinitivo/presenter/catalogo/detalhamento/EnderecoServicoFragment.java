package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;

public class EnderecoServicoFragment extends DialogFragment {


    public EnderecoServicoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_endereco_servico, container, false);
    }

}
