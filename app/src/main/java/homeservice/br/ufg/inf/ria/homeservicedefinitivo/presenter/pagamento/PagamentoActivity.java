package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;

public class PagamentoActivity extends BaseActivity {

    private Endereco endereco;
    private Servico servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
       // endereco = EventBus.getDefault().removeStickyEvent(Endereco.class);
        servico = EventBus.getDefault().removeStickyEvent(Servico.class);
        TextView textView = (TextView) findViewById(R.id.label_valor);
        textView.setText("R$ "+servico.getPreco().toString());
    }

    public void confirmaCompra(View view) {

    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
