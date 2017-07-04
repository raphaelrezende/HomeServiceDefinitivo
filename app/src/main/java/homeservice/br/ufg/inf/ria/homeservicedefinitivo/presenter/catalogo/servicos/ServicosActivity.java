package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.servicos;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;

public class ServicosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        ServicosFragment fragment = new ServicosFragment();
        initView(fragment, R.id.container_servicos);

    }

  }
