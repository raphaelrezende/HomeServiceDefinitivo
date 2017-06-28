package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Cartao;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
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
        return view;
    }

    private void populaVenda() {
        Endereco endereco = venda.getEndereco();
        TextView labelCep = (TextView) view.findViewById(R.id.label_cep_venda);
        labelCep.setText("CEP: " + endereco.getCep());
        TextView labelLogradouro = (TextView) view.findViewById(R.id.label_logradouro_venda);
        labelLogradouro.setText("Logradouro: " + endereco.getLogradouro());
        TextView labelCidade = (TextView) view.findViewById(R.id.label_cidade_venda);
        labelCidade.setText("Cidade: " + endereco.getCidade());
        TextView labelBairro = (TextView) view.findViewById(R.id.label_bairro_venda);
        labelBairro.setText("Bairro: " + endereco.getBairro());
        TextView labelComplemento = (TextView) view.findViewById(R.id.label_complemento_venda);
        labelComplemento.setText("Complemento: " + endereco.getComplemento());
        TextView labelObservacoes = (TextView) view.findViewById(R.id.label_observacoes_venda);
        labelObservacoes.setText("Observações: " + endereco.getObservacoes());
        Cartao cartao = venda.getCartao();
        TextView labelNumeroCartao = (TextView) view.findViewById(R.id.label_numero_cartao_venda);
        labelNumeroCartao.setText("Número do cartão: ****-****-****-*" + cartao.getNumero().substring(13));
        TextView labelNomeCartao = (TextView) view.findViewById(R.id.label_nome_cartao_venda);
        labelNomeCartao.setText("Nome no cartão: " + cartao.getNome());
    }


}
