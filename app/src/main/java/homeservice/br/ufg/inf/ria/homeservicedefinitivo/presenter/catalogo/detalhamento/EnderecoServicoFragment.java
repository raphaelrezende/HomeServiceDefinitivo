package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;

public class EnderecoServicoFragment extends DialogFragment {

    private View view;
    private Button mButtonEndereco;
    private CreateDialogListener mListener;
    private Endereco endereco;
    private Servico servico;


    public EnderecoServicoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_endereco_servico, container, false);
        populaCidade(servico);
        mButtonEndereco = (Button) view.findViewById(R.id.botao_avancar_endereco);
        mButtonEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviaEndereco();
                if(endereco != null) {
                    EventBus.getDefault().postSticky(endereco);
                    mListener.onCreateDialog();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (CreateDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " deve implementar CreateEmailListener");
        }
    }

    public interface CreateDialogListener{
        public void onCreateDialog();
    }
    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    private void checkCEP() throws FormProblemException {
        EditText cep = (EditText) view.findViewById(R.id.input_cep);
        if(cep.getText().toString().length() != 8) {
            throw new FormProblemException(getString(R.string.error_cep));
        }
    }

    private void checkCampos() throws FormProblemException {
        EditText logradouro = (EditText) view.findViewById(R.id.input_logradouro);
        EditText bairro = (EditText) view.findViewById(R.id.input_bairro);
        EditText complemento = (EditText) view.findViewById(R.id.input_complemento);
        EditText observacoes = (EditText) view.findViewById(R.id.input_observacoes);
        EditText cep = (EditText) view.findViewById(R.id.input_cep);
        if("".equals(cep.getText().toString()) || "".equals(logradouro.getText().toString()) ||
                "".equals(bairro.getText().toString()) || "".equals(complemento.getText().toString())
                || "".equals(observacoes.getText().toString())) {
            throw new FormProblemException(getString(R.string.error_campos));
        }
    }

    private void populaCidade (Servico servico) {
        EditText editText = (EditText) view.findViewById(R.id.input_cidade);
        editText.setText(servico.getCidade());
    }

    private Endereco pegaEndereco() {
        Endereco ende = new Endereco();
        EditText cep = (EditText) view.findViewById(R.id.input_cep);
        ende.setCep(cep.getText().toString());
        EditText logradouro = (EditText) view.findViewById(R.id.input_logradouro);
        ende.setLogradouro(logradouro.getText().toString());
        EditText cidade = (EditText) view.findViewById(R.id.input_cidade);
        ende.setCidade(cidade.getText().toString());
        EditText bairro = (EditText) view.findViewById(R.id.input_bairro);
        ende.setBairro(bairro.getText().toString());
        EditText complemento = (EditText) view.findViewById(R.id.input_complemento);
        ende.setComplemento(complemento.getText().toString());
        EditText observacoes = (EditText) view.findViewById(R.id.input_observacoes);
        ende.setObservacoes(observacoes.getText().toString());

        SugarRecord.save(ende); // Salva endereco no banco
        ende = SugarRecord.last(Endereco.class);// Busca de novo para vir com id
        return ende;
    }

    public void enviaEndereco() {
        try {
            checkCampos();
            checkCEP();
        } catch (FormProblemException e) {
            Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        endereco = pegaEndereco();
    }


}
