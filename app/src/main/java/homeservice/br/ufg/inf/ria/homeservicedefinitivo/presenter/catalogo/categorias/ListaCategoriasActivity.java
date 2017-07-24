package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer.AvisosFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer.EditaUsuarioActivity;

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        setUsernameDrawer(navigationView);
    }

    // Coloca o nome e email do usuario no drawer
    private void setUsernameDrawer(NavigationView navigationView) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String nomeUsuario = sharedPref.getString("nome","HomeService");
        String emailUsuario = sharedPref.getString("email", "HomeService");
        TextView textViewNome = navigationView.getHeaderView(0).findViewById(R.id.label_drawer_nome);
        TextView textViewEmail = navigationView.getHeaderView(0).findViewById(R.id.label_drawer_email);
        textViewNome.setText(nomeUsuario);
        textViewEmail.setText(emailUsuario);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            super.onBackPressed();
        }
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (id == R.id.nav_inicio) {
            ListaCategoriasFragment fragment = new ListaCategoriasFragment();
            initView(fragment, R.id.container);
        } else if (id == R.id.nav_avisos) {
            AvisosFragment fragment = new AvisosFragment();
            initView(fragment, R.id.container);
        } else if (id == R.id.nav_compras) {

        } else if (id == R.id.nav_editar) {
            Intent intent = new Intent(this, EditaUsuarioActivity.class);
            startActivity(intent);
            MenuItem inicio = navigationView.getMenu().findItem(R.id.nav_inicio);
            inicio.setChecked(true);
            onNavigationItemSelected(inicio);
        } else if (id == R.id.nav_sair) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
