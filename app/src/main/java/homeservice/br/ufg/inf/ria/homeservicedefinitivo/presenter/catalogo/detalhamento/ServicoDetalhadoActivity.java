package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento.PagamentoActivity;

public class ServicoDetalhadoActivity extends BaseActivity implements ServicoDetalhadoFragment.CreateEnderecoListener, EnderecoServicoFragment.CreateDialogListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servico_detalhado);

        ServicoDetalhadoFragment fragment = new ServicoDetalhadoFragment();
        initView(fragment, R.id.container_servico_detalhado);
    }

    @Override
    public void onCreateEndereco(Servico servico) {
        EnderecoServicoFragment fragment = new EnderecoServicoFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setServico(servico);
        fragment.show(fragmentTransaction,"Dialog");
    }

    @Override
    public void onCreateDialog() {
        Intent intent = new Intent(this, PagamentoActivity.class);
        startActivity(intent);

    }
}
