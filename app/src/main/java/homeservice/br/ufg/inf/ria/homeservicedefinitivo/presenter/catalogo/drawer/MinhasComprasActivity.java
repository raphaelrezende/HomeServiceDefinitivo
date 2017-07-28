package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orm.SugarContext;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.LinkedList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Usuario;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;

public class MinhasComprasActivity extends AppCompatActivity {

    private List<Venda> listaVendas;
    private ComprasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_compras);
        listaVendas = new LinkedList<>();
        SugarContext.init( this );
    }

    @Override
    protected void onStart() {
        super.onStart();
        initRecycler();
       // getCompras();
    }

    private void initRecycler() {
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ComprasAdapter(listaVendas, this);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String emailUsuario = sharedPref.getString("email", "HomeService");
        Usuario usuario = Select.from(Usuario.class).where(Condition.prop("email").like(emailUsuario)).list().get(0);
        listaVendas = Select.from(Venda.class).where(Condition.prop("usuario").like(usuario.getId())).list();
        System.out.println("tamanho " + listaVendas.size());
        adapter.setListaVendas(listaVendas);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    private void getCompras() {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String emailUsuario = sharedPref.getString("email", "HomeService");
        Usuario usuario = Select.from(Usuario.class).where(Condition.prop("email").like(emailUsuario)).list().get(0);
        listaVendas = Select.from(Venda.class).where(Condition.prop("usuario").like(usuario.getId())).list();
        System.out.println("tamanho " + listaVendas.size());
        adapter.setListaVendas(listaVendas);
        adapter.notifyDataSetChanged();
    }
}
