package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;

public class ServicoDetalhadoFragment extends BaseFragment {

    private CreateEnderecoListener mListener;


    public ServicoDetalhadoFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_servico_detalhado, container, false);

        final Button mButtonEndereco = (Button) view.findViewById(R.id.botaoAvancar);
        mButtonEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCreateEndereco();
                mButtonEndereco.setVisibility(View.GONE);
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
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String nomeServico = sharedPref.getString("servico", "HomeService");
        SugarContext.init(this.getContext());
        List<Servico> listaServicos = Select.from(Servico.class).where(Condition.prop("nome").like(nomeServico)).list();
        Servico servico = listaServicos.get(0);
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
        public void onCreateEndereco();
    }

}
