package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.detalhamento;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;

public class ServicoDetalhadoFragment extends BaseFragment {

    private Servico servico;

    public ServicoDetalhadoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        servico = EventBus.getDefault().removeStickyEvent(Servico.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_servico_detalhado, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        iniciaCampos();

    }

    private void iniciaCampos() {
        TextView nameView = (TextView) getView().findViewById(R.id.tituloServicoDetalhado);
        nameView.setText(servico.getNome());
        TextView descriptionView = (TextView) getView().findViewById(R.id.descricaoServicoDetalhado);
        descriptionView.setText(servico.getDescricao());
        TextView cidadeView = (TextView) getView().findViewById(R.id.cidadeServicoDetalhado);
        cidadeView.setText(servico.getCidade());
        TextView precoView = (TextView) getView().findViewById(R.id.precoServicoDetalhado);
        precoView.setText("R$ "+servico.getPreco().toString()+"0");
    }
}
