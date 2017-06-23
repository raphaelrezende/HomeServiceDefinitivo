package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;

public class PagamentoActivity extends BaseActivity {

    private Endereco endereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        endereco = EventBus.getDefault().removeStickyEvent(Endereco.class);
        TextView edit = (TextView) findViewById(R.id.label_endereco);
        edit.setText(endereco.getCep());
    }
}
