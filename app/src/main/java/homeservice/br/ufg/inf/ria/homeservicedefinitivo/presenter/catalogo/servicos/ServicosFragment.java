package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.servicos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.SugarRecord;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.BaseFragment;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.CatalogoActivity;


public class ServicosFragment extends BaseFragment {

    private Categoria categoria;
    private List<Servico> listaServicos = new ArrayList<Servico>();
    private AdapterServicos adapter;

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
        adapter.setActivity((CatalogoActivity) getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void popula () {
        for (int i = 1; i < 5;i++) {
            Servico servico = new Servico();
            servico.setNome("Serviço " + this.categoria.getNome() + " " + i);
            servico.setDescricao("Descricao " + this.categoria.getNome()+ " " + i);
            servico.setPreco((double) i);
            servico.setCidade("cidade" + i);
            servico.setCategoria(this.categoria);
            SugarRecord.save(servico);
        }
    }

    private  void limpaBD() {
        SugarRecord.deleteAll(Servico.class);
    }

    private void getServicos() {
        showDialogWithMessage(getString(R.string.load_servicos));
        listaServicos = SugarRecord.listAll(Servico.class);
        adapter.setServicos(listaServicos);
        adapter.notifyDataSetChanged();
        dismissDialog();
    }
}
