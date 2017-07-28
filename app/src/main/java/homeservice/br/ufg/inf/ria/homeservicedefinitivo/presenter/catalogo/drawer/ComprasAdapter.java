package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;


public class ComprasAdapter extends RecyclerView.Adapter<ComprasAdapter.ComprasViewHolder>{

    private List<Venda> listaVendas;
    private Context context;

    public ComprasAdapter(List<Venda> listaVendas, Context context) {
        this.listaVendas = listaVendas;
        this.context = context;
    }

    @Override
    public ComprasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compra_linha,parent,false);
        ComprasViewHolder cvh = new ComprasViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(ComprasViewHolder holder, int position) {
        holder.dataCompra.setText("Data e hora: " + listaVendas.get(position).getDataHora());
        holder.nomeServico.setText("Servico: " +listaVendas.get(position).getServico().getNome());
        holder.precoServico.setText("Valor: R$" + listaVendas.get(position).getServico().getPreco());
    }

    @Override
    public int getItemCount() {
        return listaVendas.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public List<Venda> getListaVendas() {
        return listaVendas;
    }

    public void setListaVendas(List<Venda> listaVendas) {
        this.listaVendas = listaVendas;
    }

    public static class ComprasViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView dataCompra;
        TextView nomeServico;
        TextView precoServico;

        ComprasViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            dataCompra = itemView.findViewById(R.id.label_data_compra);
            nomeServico = itemView.findViewById(R.id.label_nome_servico);
            precoServico = itemView.findViewById(R.id.label_preco_servico);
        }
    }
}
