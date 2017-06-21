package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.servicos;

/**
 * Created by raphael on 23/05/17.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias.ListaCategoriasActivity;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.detalhamento.ServicoDetalhadoFragment;

/**
 * Created by raphael on 18/05/17.
 */

public class AdapterServicos extends RecyclerView.Adapter<AdapterServicos.ServicoViewHolder> {

    /**
     * List of servicos
     */
    private List<Servico> servicos;

    private ListaCategoriasActivity activity;

    /**
     * The application context
     */
    private Context context;

    public AdapterServicos(List<Servico> servicos, Context context){
        this.servicos = servicos;
        this.context = context;
    }

    @Override
    public AdapterServicos.ServicoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.servicos_linha, viewGroup, false);
        AdapterServicos.ServicoViewHolder viewHolder = new AdapterServicos.ServicoViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterServicos.ServicoViewHolder holder, int position) {
        final Servico servico = servicos.get(position);
        holder.nameView.setText(servico.getNome());
        holder.descriptionView.setText(servico.getDescricao());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreDescricao(servico);
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }

    public List<Servico> getservicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public static class ServicoViewHolder
            extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView descriptionView;

        ServicoViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.label_servicos_title);
            descriptionView = (TextView)itemView.findViewById(R.id.label_servicos_desc);
        }
    }

    private void abreDescricao(Servico servico) {
        ServicoDetalhadoFragment fragment = new ServicoDetalhadoFragment();
        EventBus.getDefault().postSticky(servico);
        activity.initView(fragment);

    }
    public ListaCategoriasActivity getActivity() {
        return activity;
    }

    public void setActivity(ListaCategoriasActivity activity) {
        this.activity = activity;
    }

}

