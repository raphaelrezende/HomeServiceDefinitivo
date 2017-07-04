package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.orm.SugarContext;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.avisos.AvisosFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias.ListaCategoriasFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento.EnderecoServicoFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento.ServicoDetalhadoFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.servicos.ServicosFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.pagamento.PagamentoActivity;

public class ListaCategoriasActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categorias);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListaCategoriasFragment fragment = new ListaCategoriasFragment();
        initView(fragment, R.id.container);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SugarContext.init(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            ListaCategoriasFragment fragment = new ListaCategoriasFragment();
            initView(fragment, R.id.container);
        } else if (id == R.id.nav_avisos) {
            AvisosFragment fragment = new AvisosFragment();
            initView(fragment, R.id.container);
        } else if (id == R.id.nav_compras) {

        } else if (id == R.id.nav_editar) {

        } else if (id == R.id.nav_sair) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
