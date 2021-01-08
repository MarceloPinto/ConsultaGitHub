package com.faustino.consultaapigithub.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faustino.consultaapigithub.R;
import com.faustino.consultaapigithub.adapter.ListarRepositoriosAdapter;
import com.faustino.consultaapigithub.model.Item;
import com.faustino.consultaapigithub.service.ControlaRequisicoes;
import com.faustino.consultaapigithub.service.RespostaRequisicoes;
import com.faustino.consultaapigithub.utils.CarregaLoading;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RespostaRequisicoes {

    private RespostaRequisicoes respostaRequisicoes = this;
    private Context context = this;

    private RecyclerView recyclerView;
    private EditText editText;
    private LinearLayout layoutVazio;
    private CarregaLoading carregaLoading;
    private int page = 1;
    private String language;

    private List<Item> listRepo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Repositórios");
        toolbar.setNavigationIcon(R.drawable.ic_hamburguer);

        inicializaComponentes();

        editText.addTextChangedListener(new TextWatcher() {
                    @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                    Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
                    Runnable workRunnable;
                    @Override public void afterTextChanged(Editable s) {
                        handler.removeCallbacks(workRunnable);
                        workRunnable = () -> doSmth(s.toString());
                        handler.postDelayed(workRunnable, 300 /*delay*/);
                    }

                    private final void doSmth(String str) {
                        language = str;
                        listRepo.clear();
                        if ("".equals(str) || str.length() == 1) {
                            recyclerView.setVisibility(View.GONE);
                            layoutVazio.setVisibility(View.VISIBLE);
                        }else{
                            recuperaDados(str);
                        }
                    }
                }
        );
    }

    private void inicializaComponentes() {
        editText = findViewById(R.id.edttxt_language);
        recyclerView = findViewById(R.id.recycler);
        layoutVazio = findViewById(R.id.layout_vazio);

        editText.setHintTextColor(getResources().getColor(R.color.white));
        editText.setHint("BUSCA POR LINGUAGEM");
    }

    private void recuperaDados(String language) {
        carregaLoading = new CarregaLoading(context);
        carregaLoading.carregaLoading();
        listRepo = new ArrayList<>();
        ControlaRequisicoes controlaRequisicoes = new ControlaRequisicoes(context, respostaRequisicoes);
        controlaRequisicoes.retornaRepositorios(language, page);
    }

    @Override
    public void onReceiveRepositorios(List<Item> listRepositorios) {
        carregaLoading.fechaDialogLoading();
        int posicaoDoScroll = listRepo.size();
        listRepo.addAll(listRepositorios);

        if (listRepositorios.size() > 0) {
            page++;

            recyclerView.setAdapter(new ListarRepositoriosAdapter(listRepo, this, respostaRequisicoes, language, page));
            RecyclerView.LayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);

            recyclerView.scrollToPosition(posicaoDoScroll-3);

            recyclerView.setVisibility(View.VISIBLE);
            layoutVazio.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            layoutVazio.setVisibility(View.VISIBLE);
        }
    }

    public static void loadMore(Context mContext, RespostaRequisicoes mRespostaRequisicoes, String language, int page){
        ControlaRequisicoes controlaRequisicoes = new ControlaRequisicoes(mContext, mRespostaRequisicoes);
        controlaRequisicoes.retornaRepositorios(language, page);
    }

}
