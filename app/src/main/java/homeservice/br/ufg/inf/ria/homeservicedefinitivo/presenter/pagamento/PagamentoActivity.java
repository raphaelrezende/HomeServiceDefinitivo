package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Cartao;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.CatalogoActivity;

public class PagamentoActivity extends BaseActivity {

    private Endereco endereco;
    private Servico servico;
    private Cartao cartao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        endereco = EventBus.getDefault().removeStickyEvent(Endereco.class);
        servico = EventBus.getDefault().removeStickyEvent(Servico.class);
        TextView textView = (TextView) findViewById(R.id.label_valor);
        textView.setText("R$ "+servico.getPreco().toString() + "0");

    }

    public void realizaCompra(View view) {
        EditText numeroCartao = (EditText) findViewById(R.id.input_numero_cartao);
        EditText nomeCartao = (EditText) findViewById(R.id.input_nome_cartao);
        EditText mes = (EditText) findViewById(R.id.input_data_validade_mes);
        EditText ano = (EditText) findViewById(R.id.input_data_validade_ano);
        EditText cvv = (EditText) findViewById(R.id.input_cvv);
        cartao = new Cartao();
        cartao.setNumero(numeroCartao.getText().toString());
        cartao.setNome(nomeCartao.getText().toString());
        cartao.setMes(mes.getText().toString());
        cartao.setAno(ano.getText().toString());
        cartao.setCvv(cvv.getText().toString());
        SugarRecord.save(cartao);
        cartao = SugarRecord.last(Cartao.class);
        Venda venda = new Venda();
        venda.setServico(servico);
        venda.setEndereco(endereco);
        venda.setCartao(cartao);
        SugarRecord.save(venda);
        venda = SugarRecord.last(Venda.class);
        EventBus.getDefault().postSticky(venda);
        ConfirmacaoCompraFragment fragment = new ConfirmacaoCompraFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.show(fragmentTransaction,"Compra");
    }

    public void fechaCompra(View view) {
        Intent intent = new Intent(this, CatalogoActivity.class);
        finish();
        startActivity(intent);

    }

}
