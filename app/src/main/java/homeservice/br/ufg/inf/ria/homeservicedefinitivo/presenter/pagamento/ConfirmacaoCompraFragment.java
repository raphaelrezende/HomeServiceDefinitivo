package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Cartao;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Usuario;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;

public class ConfirmacaoCompraFragment extends DialogFragment {

    private View view;

    private Venda venda;

    public ConfirmacaoCompraFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_confirmacao_compra, container, false);
        venda = EventBus.getDefault().removeStickyEvent(Venda.class);
        populaVenda();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String nomeUsuario = preferences.getString("nome","HomeService");
        String emailUsuario = preferences.getString("email", "HomeService");
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        editor.putString("email", emailUsuario);
        editor.putString("nome", nomeUsuario);
        editor.apply();
        return view;
    }

    private void populaVenda() {
        Endereco endereco = venda.getEndereco();
        TextView labelCep = (TextView) view.findViewById(R.id.label_cep_venda);
        labelCep.setText("CEP: " + endereco.getCep());
        TextView labelComplemento = (TextView) view.findViewById(R.id.label_complemento_venda);
        labelComplemento.setText("Complemento: " + endereco.getComplemento());
        TextView labelDataHora = (TextView) view.findViewById(R.id.label_data_hora_venda);
        labelDataHora.setText("Data/Hora: " + venda.getDataHora());
        Cartao cartao = venda.getCartao();
        TextView labelNumeroCartao = (TextView) view.findViewById(R.id.label_numero_cartao_venda);
        labelNumeroCartao.setText("Número do cartão: ****-****-****-" + cartao.getNumero().substring(12));
        TextView labelNomeCartao = (TextView) view.findViewById(R.id.label_nome_cartao_venda);
        labelNomeCartao.setText("Nome no cartão: " + cartao.getNome());
        Usuario usuario = venda.getUsuario();
        TextView labelNomeUsuario =  view.findViewById(R.id.label_nome_usuario_venda);
        labelNomeUsuario.setText("Nome do cliente: " + usuario.getNome());
        TextView labelEmailUsuario =  view.findViewById(R.id.label_email_usuario_venda);
        labelEmailUsuario.setText("E-mail do cliente: " + usuario.getEmail());
        TextView labelNomeServico =  view.findViewById(R.id.label_nome_servico);
        labelNomeServico.setText("Serviço contratado: " + venda.getServico().getNome());
    }


}
