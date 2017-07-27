package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.drawer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Venda;


public class ComprasAdapter {

    private List<Venda> listaVendas;
    private Context context;

    public ComprasAdapter(List<Venda> listaVendas, Context context) {
        this.listaVendas = listaVendas;
        this.context = context;
    }

    public static class ComprasViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView dataCompra;
        TextView nomeServico;
        TextView precoServico;
        ImageView personPhoto;

        public ComprasViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            dataCompra = itemView.findViewById(R.id.label_data_compra);
            nomeServico = itemView.findViewById(R.id.label_nome_servico);
            precoServico = itemView.findViewById(R.id.label_preco_servico);
            personPhoto = itemView.findViewById(R.id.person_photo);
        }
    }
}
