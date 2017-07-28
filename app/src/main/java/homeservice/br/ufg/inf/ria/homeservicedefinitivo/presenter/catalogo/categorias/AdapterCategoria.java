package homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.categorias;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.R;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.presenter.catalogo.servicos.ServicosActivity;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posta(categoria);
            }
        });
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

    public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView nameView;

        CategoriaViewHolder(View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.label_categoria_title);
        }
    }

    private void posta(Categoria categoria) {
        Intent intent = new Intent(this.context, ServicosActivity.class);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("categoria", categoria.getNome());
        editor.apply();
        context.startActivity(intent);
    }
}
