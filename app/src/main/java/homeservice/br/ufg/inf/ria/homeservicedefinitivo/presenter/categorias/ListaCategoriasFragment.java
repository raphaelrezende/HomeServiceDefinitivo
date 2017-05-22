package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.categorias;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.data.CategoriaDAO;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;


public class ListaCategoriasFragment extends BaseFragment {
    private List<Categoria> categoriaList;
    private AdapterCategoria adapter;
    CategoriaDAO categoriaDAO;


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
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterCategoria(categoriaList, getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void getCategorias() {
        showDialogWithMessage(getString(R.string.load_categorias));
        categoriaDAO = new CategoriaDAO(getActivity());
        categoriaList = categoriaDAO.getAll();
        adapter.setCategorias(categoriaList);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }

    private void popula() {
        categoriaDAO = new CategoriaDAO(getActivity());
        for (int i = 2; i < 5;i++) {
            Categoria categoria = new Categoria();
            categoria.setId(i);
            categoria.setNome("categoria" + i);
            categoria.setDescricao("descricao" + i);
            categoriaDAO.create(categoria);
        }
    }

    private  void limpaBD() {
        categoriaDAO = new CategoriaDAO(getActivity());
        List<Categoria> categorias = categoriaDAO.getAll();
        for (Categoria cat : categorias) {
            categoriaDAO.delete(cat);
        }
    }
}