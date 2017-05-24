package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.categorias;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.servicos.ServicosFragment;

/**
 * Created by raphael on 18/05/17.
 */

public class AdapterCategoria extends RecyclerView.Adapter<AdapterCategoria.CategoriaViewHolder> {

    /**
     * List of categorias
     */
    private List<Categoria> categorias;

    /**
     * The application context
     */
    private Context context;

    public AdapterCategoria(List<Categoria> categorias, Context context){
        this.categorias = categorias;
        this.context = context;
    }

    @Override
    public AdapterCategoria.CategoriaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categorias_linha, viewGroup, false);
        AdapterCategoria.CategoriaViewHolder viewHolder = new AdapterCategoria.CategoriaViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoriaViewHolder holder, int position) {
        final Categoria categoria = categorias.get(position);
        holder.nameView.setText(categoria.getNome());
        holder.descriptionView.setText(categoria.getDescricao());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                posta(categoria);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public List<Categoria> getcategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public static class CategoriaViewHolder
            extends RecyclerView.ViewHolder {
        TextView nameView;
        TextView descriptionView;

        CategoriaViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.label_categoria_title);
            descriptionView = (TextView)itemView.findViewById(R.id.label_categoria_desc);
        }
    }
//
//    private void posta(Categoria categoria) {
//        ServicosFragment fragment = new ServicosFragment();
//        EventBus.getDefault().postSticky(categoria);
//        ListaCategoriasActivity cat = (ListaCategoriasActivity) context;
//        cat.initView(fragment);
//        }

}
