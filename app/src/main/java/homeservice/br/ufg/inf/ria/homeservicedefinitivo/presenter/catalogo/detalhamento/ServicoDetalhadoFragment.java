package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;

public class ServicoDetalhadoFragment extends BaseFragment {

    private Servico servico;

    private CreateEnderecoListener mListener;
    private View view;
    private Button mButtonEndereco;

    public ServicoDetalhadoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        servico = EventBus.getDefault().removeStickyEvent(Servico.class);

        view = inflater.inflate(R.layout.fragment_servico_detalhado, container, false);

        mButtonEndereco = (Button) view.findViewById(R.id.botaoAvancar);
        mButtonEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreateEndereco(servico);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        iniciaCampos();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CreateEnderecoListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " deve implementar CreateEmailListener");
        }
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

    public interface CreateEnderecoListener {
        public void onCreateEndereco(Servico servico);
    }

}
