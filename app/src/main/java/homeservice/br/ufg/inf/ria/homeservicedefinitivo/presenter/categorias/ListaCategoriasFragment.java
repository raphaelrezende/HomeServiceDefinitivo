package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.categorias;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.SugarRecord;

import java.util.LinkedList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;


public class ListaCategoriasFragment extends BaseFragment {

    private List<Categoria> categoriaList;
    private AdapterCategoria adapter;

    public ListaCategoriasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_categorias, container, false);
        categoriaList = new LinkedList<>();
        //setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initRecycler();
        limpaBD();
        popula();
        getCategorias();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_categorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterCategoria(categoriaList, getContext());
        adapter.setActivity((ListaCategoriasActivity) getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void getCategorias() {
        showDialogWithMessage(getString(R.string.load_categorias));
        categoriaList = SugarRecord.listAll(Categoria.class);
        adapter.setCategorias(categoriaList);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    private void popula() {
        for (int i = 1; i < 4;i++) {
            Categoria categoria = new Categoria();
            categoria.setId((long) i);
            categoria.setNome("categoria" + i);
            categoria.setDescricao("descricao" + i);
            SugarRecord.save(categoria);
        }
    }

    private  void limpaBD() {
        SugarRecord.deleteAll(Categoria.class);
    }
}