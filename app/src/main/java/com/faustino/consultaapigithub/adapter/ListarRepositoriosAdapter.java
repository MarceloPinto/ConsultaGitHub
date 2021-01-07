package com.faustino.consultaapigithub.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.faustino.consultaapigithub.R;
import com.faustino.consultaapigithub.activity.MainActivity;
import com.faustino.consultaapigithub.activity.ReadmeActivity;
import com.faustino.consultaapigithub.model.Item;
import com.faustino.consultaapigithub.service.RespostaRequisicoes;

import java.util.List;

public class ListarRepositoriosAdapter extends RecyclerView.Adapter {

    private List<Item> listRepositorios;
    private Context context;
    private RespostaRequisicoes respostaRequisicoes;
    private String language;
    private int page;

    public ListarRepositoriosAdapter(List<Item> listRepositorios, Context context, RespostaRequisicoes respostaRequisicoes, String language, int page) {
        this.listRepositorios = listRepositorios;
        this.context = context;
        this.respostaRequisicoes = respostaRequisicoes;
        this.language = language;
        this.page = page;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_listar_repositorio_adapter, parent, false);
        ListarRepositoriosViewHolder holder = new ListarRepositoriosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ListarRepositoriosViewHolder holder = (ListarRepositoriosViewHolder) viewHolder;
        final Item repositorio = listRepositorios.get(position);

        Glide.with(context)
                .load(repositorio.getOwner().getAvatar_url())
                .centerInside()
                .into(holder.imgAvatar);

        String descricao = repositorio.getDescription();
        if (descricao.length() > 40) {
            descricao = descricao.substring(0, 37);
        }
        descricao += "...";

        holder.txtvwNome.setText(repositorio.getName());
        holder.txtvwDescricao.setText(descricao);
        holder.txtvwStars.setText(String.valueOf(repositorio.getStargazers_count()));
        holder.txtvwForks.setText(String.valueOf(repositorio.getForks()));
        holder.txtvwNomeAutor.setText(repositorio.getOwner().getLogin());

        holder.txtvwStars.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_star, 0, 0, 0);
        holder.txtvwForks.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fork, 0, 0, 0);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadmeActivity.class);
                intent.putExtra("autor", repositorio.getOwner().getLogin());
                intent.putExtra("nomeRepositorio", repositorio.getName());
                context.startActivity(intent);
            }
        });

        if (listRepositorios.size()-1 == position){
            MainActivity.loadMore(context, respostaRequisicoes, language, page);
        }
    }

    @Override
    public int getItemCount() {
        return listRepositorios.size();
    }

    public static class ListarRepositoriosViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        com.makeramen.roundedimageview.RoundedImageView imgAvatar;
        TextView txtvwNome;
        TextView txtvwDescricao;
        TextView txtvwStars;
        TextView txtvwForks;
        TextView txtvwNomeAutor;

        public ListarRepositoriosViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            txtvwNome = itemView.findViewById(R.id.txt_nome);
            txtvwDescricao = itemView.findViewById(R.id.txt_descricao);
            txtvwStars = itemView.findViewById(R.id.txt_Stars);
            txtvwForks = itemView.findViewById(R.id.txt_forks);
            txtvwNomeAutor = itemView.findViewById(R.id.txt_nome_autor);
        }
    }
}
