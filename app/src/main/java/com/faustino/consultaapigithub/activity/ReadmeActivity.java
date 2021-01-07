package com.faustino.consultaapigithub.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.faustino.consultaapigithub.R;
import com.faustino.consultaapigithub.utils.CarregaLoading;

public class ReadmeActivity extends AppCompatActivity {

    private WebView webView;
    private Context context = this;
    private CarregaLoading carregaLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        carregaLoading = new CarregaLoading(context);
        carregaLoading.carregaLoading();

        Intent i = getIntent();
        String autor =(String) i.getSerializableExtra("autor");
        String nomeRepositorio =(String) i.getSerializableExtra("nomeRepositorio");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nomeRepositorio);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left);

        inicializaComponentes();
        carregarWebView(autor, nomeRepositorio);
    }

    private void inicializaComponentes() {
        webView = findViewById(R.id.webview);
    }

    private void carregarWebView(String autor, String nomeRepositorio) {
        String url = "https://github.com/"+autor+"/"+nomeRepositorio+"/blob/master/README.md";
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100)
                    carregaLoading.fechaDialogLoading();
                }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
