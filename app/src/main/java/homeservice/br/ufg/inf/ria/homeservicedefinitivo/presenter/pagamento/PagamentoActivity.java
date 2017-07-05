package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Cartao;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias.ListaCategoriasActivity;

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
        EventBus.getDefault().postSticky(endereco);
        EventBus.getDefault().postSticky(servico);
        TextView textView = (TextView) findViewById(R.id.label_valor);
        textView.setText("R$ "+servico.getPreco().toString() + "0");
    }

    public void realizaCompra(View view) {
        hideKeyboard();
        try {
            checkCampos();
        } catch (FormProblemException e) {
            showAlert(e.getMessage());
            return;
        }
        compra();
    }

    public void fechaCompra(View view) {
        Intent intent = new Intent(this, ListaCategoriasActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);

    }

    private void checkCampos() throws FormProblemException {
        String numeroCartao = getStringFromEdit(R.id.input_numero_cartao);
        String mes = getStringFromEdit(R.id.input_data_validade_mes);
        String ano = getStringFromEdit(R.id.input_data_validade_ano);
        String cvv = getStringFromEdit(R.id.input_cvv);
        if (numeroCartao.length()!= 16 ) {
            throw new FormProblemException(getString(R.string.error_numero_cartao));
        }
        if ("".equals(mes)) {
            throw new FormProblemException(getString(R.string.error_mes));
        }else {
            if(Integer.parseInt(mes) < 1 || Integer.parseInt(mes) > 12 ) {
                throw new FormProblemException(getString(R.string.error_mes));
            }
        }
        if ("".equals(ano)) {
            throw new FormProblemException(getString(R.string.error_ano));
        }else {
            if(ano.length() != 2) {
                throw new FormProblemException(getString(R.string.error_ano));
            }
        }
        if ("".equals(cvv)) {
            throw new FormProblemException(getString(R.string.error_cvv));
        }else {
            if (cvv.length() != 3) {
                throw new FormProblemException(getString(R.string.error_cvv));
            }
        }
    }

    private void compra() {
        String numeroCartao = getStringFromEdit(R.id.input_numero_cartao);
        String nomeCartao = getStringFromEdit(R.id.input_nome_cartao);
        String mes = getStringFromEdit(R.id.input_data_validade_mes);
        String ano = getStringFromEdit(R.id.input_data_validade_ano);
        String cvv = getStringFromEdit(R.id.input_cvv);

        cartao = new Cartao();
        cartao.setNumero(numeroCartao);
        cartao.setNome(nomeCartao);
        cartao.setMes(mes);
        cartao.setAno(ano);
        cartao.setCvv(cvv);
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
}
