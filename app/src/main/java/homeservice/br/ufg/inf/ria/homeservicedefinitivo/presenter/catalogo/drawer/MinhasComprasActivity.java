package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;

public class MinhasComprasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_compras);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecycler();
    }

    private void initRecycler() {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
    }
}
