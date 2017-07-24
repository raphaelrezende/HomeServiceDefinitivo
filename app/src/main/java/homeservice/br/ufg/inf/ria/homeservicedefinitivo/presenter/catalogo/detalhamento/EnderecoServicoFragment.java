package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.orm.SugarRecord;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;

public class EnderecoServicoFragment extends BaseFragment {

    private View view;
    private CreateDialogListener mListener;

    public EnderecoServicoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_endereco_servico, container, false);
        final Button mButtonEndereco = (Button) view.findViewById(R.id.botao_avancar_endereco);
        mButtonEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkCampos();
                    checkCEP();
                } catch (FormProblemException e) {
                    Toast toast = Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                salvaEndereco();
                mListener.onCreateDialog();
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

    private void salvaEndereco() {
        Endereco endereco = new Endereco();
        EditText cep = (EditText) view.findViewById(R.id.input_cep);
        endereco.setCep(cep.getText().toString());
        EditText logradouro = (EditText) view.findViewById(R.id.input_logradouro);
        endereco.setLogradouro(logradouro.getText().toString());
        EditText cidade = (EditText) view.findViewById(R.id.input_cidade);
        endereco.setCidade(cidade.getText().toString());
        EditText bairro = (EditText) view.findViewById(R.id.input_bairro);
        endereco.setBairro(bairro.getText().toString());
        EditText complemento = (EditText) view.findViewById(R.id.input_complemento);
        endereco.setComplemento(complemento.getText().toString());
        EditText observacoes = (EditText) view.findViewById(R.id.input_observacoes);
        endereco.setObservacoes(observacoes.getText().toString());
        Long idEndereco = SugarRecord.save(endereco); // coloca o id do endereco no preferences
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("endereco",idEndereco);
        editor.apply();

    }
}
