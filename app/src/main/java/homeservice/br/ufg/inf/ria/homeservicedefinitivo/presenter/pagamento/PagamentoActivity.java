package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.badoualy.stepperindicator.StepperIndicator;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Cartao;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Usuario;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Endereco;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.FormProblemException;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias.ListaCategoriasActivity;

public class PagamentoActivity extends BaseActivity {

    private Servico servico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        SugarContext.init(this);
        TextView textView = (TextView) findViewById(R.id.label_valor);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        servico = recuperaServico(sharedPref.getString("servico", "HomeService"));
        textView.setText("R$ "+servico.getPreco().toString() + "0");
        StepperIndicator indicator = (StepperIndicator) findViewById(R.id.indicator);
        indicator.setCurrentStep(2);
    }

    private Servico recuperaServico(String nomeServico){
        List<Servico> listaServicos = Select.from(Servico.class).where(Condition.prop("nome").like(nomeServico)).list();
        Servico servico = listaServicos.get(0);
        return servico;
    }

    public void realizaCompra(View view) {
        hideKeyboard();
        try {
            checkCampos();
        } catch (FormProblemException e) {
            showAlert(e.getMessage());
            return;
        }
        StepperIndicator indicator = (StepperIndicator) findViewById(R.id.indicator);
        indicator.setCurrentStep(4);
        realizaCompra();
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

    private void realizaCompra() {
        SugarRecord.deleteAll(Venda.class);
        String numeroCartao = getStringFromEdit(R.id.input_numero_cartao);
        String nomeCartao = getStringFromEdit(R.id.input_nome_cartao);
        String mes = getStringFromEdit(R.id.input_data_validade_mes);
        String ano = getStringFromEdit(R.id.input_data_validade_ano);
        String cvv = getStringFromEdit(R.id.input_cvv);

        Cartao cartao = new Cartao();
        cartao.setNumero(numeroCartao);
        cartao.setNome(nomeCartao);
        cartao.setMes(mes);
        cartao.setAno(ano);
        cartao.setCvv(cvv);
        SugarRecord.save(cartao);
        cartao = SugarRecord.last(Cartao.class);

        Venda venda = new Venda();
        venda.setServico(servico);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Endereco endereco = Select.from(Endereco.class).where(Condition.prop("id").like(sharedPref.getLong("endereco",0))).list().get(0);
        venda.setEndereco(endereco);
        venda.setCartao(cartao);

        String emailUsuario = sharedPref.getString("email", "HomeService");
        Usuario usuario =  Select.from(Usuario.class).where(Condition.prop("email").like(emailUsuario)).list().get(0);
        venda.setUsuario(usuario);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy/HH:mm:ss");
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        venda.setDataHora(data_completa);

        SugarRecord.save(venda);
        venda = SugarRecord.last(Venda.class);

        EventBus.getDefault().postSticky(venda);
        ConfirmacaoCompraFragment fragment = new ConfirmacaoCompraFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.show(fragmentTransaction,"Compra");

    }
}
