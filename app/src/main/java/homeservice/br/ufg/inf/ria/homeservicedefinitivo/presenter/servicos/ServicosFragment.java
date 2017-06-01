package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.servicos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.data.ServicoDAO;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.categorias.ListaCategoriasActivity;


public class ServicosFragment extends BaseFragment {

    private Categoria categoria;
    private List<Servico> listaServicos = new ArrayList<Servico>();
    private AdapterServicos adapter;
    ServicoDAO servicoDAO;

    private OnFragmentInteractionListener mListener;

    public ServicosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        categoria = EventBus.getDefault().removeStickyEvent(Categoria.class);

       return inflater.inflate(R.layout.fragment_servicos, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        initRecycler();
        limpaBD();
        popula();
        getServicos();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void initRecycler() {
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_servicos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdapterServicos(listaServicos, getContext());
        adapter.setActivity((ListaCategoriasActivity) getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void popula () {
        servicoDAO = new ServicoDAO(getActivity());
        for (int i = 1; i < 4;i++) {
            Servico servico = new Servico();
            servico.setId(i);
            servico.setNome("serviço" + i);
            servico.setDescricao("descricao" + i);
            servico.setPreco((double) i);
            servico.setCidade("cidade" + i);
            servico.setCategoria(this.categoria);
            servicoDAO.create(servico);
        }
    }

    private  void limpaBD() {
        servicoDAO = new ServicoDAO(getActivity());
        List<Servico> servicos = servicoDAO.getAll();
        for (Servico serv : servicos) {
            servicoDAO.delete(serv);
        }
    }

    private void getServicos() {
        servicoDAO = new ServicoDAO(getActivity());
        listaServicos = servicoDAO.getAll();
        adapter.setServicos(listaServicos);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }
}
