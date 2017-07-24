package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.servicos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseFragment;

public class ServicosFragment extends BaseFragment {

    private List<Servico> listaServicos = new ArrayList<Servico>();
    private AdapterServicos adapter;


    public ServicosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servicos, container, false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initRecycler();
//        SugarRecord.deleteAll(Servico.class);
        getServicos();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_servicos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterServicos(listaServicos, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL));
    }

    private void popula () {
        List<Categoria> listaCategoria = SugarRecord.listAll(Categoria.class);
        for (int x = 0 ; x < listaCategoria.size(); x++) {
            Categoria categoria = listaCategoria.get(x);
            for (int i = 1; i < 3;i++) {
                Servico servico = new Servico();
                servico.setNome("Serviço " + categoria.getNome() + " " + i);
                servico.setDescricao("Descricao " + categoria.getNome()+ " " + i);
                servico.setPreco((double) i);
                servico.setCidade("cidade" + i);
                servico.setCategoria(categoria);
                SugarRecord.save(servico);
            }
        }
    }

    private void getServicos() {
        showDialogWithMessage(getString(R.string.load_servicos));
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String nomeCategoria = sharedPref.getString("categoria", "HomeService");
        SugarContext.init(this.getContext());
        listaServicos = Select.from(Servico.class).where(Condition.prop("nome").like("%" + nomeCategoria+"%")).list();
        if(listaServicos.size() > 0) {
            adapter.setServicos(listaServicos);
            adapter.notifyDataSetChanged();
        }else  {
            popula();
            getServicos();
        }
        dismissDialog();
    }
}
