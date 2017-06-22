package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.FormProblemException;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;

public class EnderecoServicoFragment extends DialogFragment {

    private View view;
    private Button mButtonEndereco;
    private CreateDialogListener mListener;

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
        EditText editText = (EditText) getDialog().findViewById(R.id.input_cep);
        String cep = editText.getText().toString();
        if(cep.length() != 8) {
            throw new FormProblemException(getString(R.string.error_cep));
        }
    }

    private void populaCidade (Servico servico) {
        EditText editText = (EditText) view.findViewById(R.id.input_cidade);
        editText.setText(servico.getCidade());
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
