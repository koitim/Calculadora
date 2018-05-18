package br.edu.uni7.calculadora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class Historico extends AppCompatActivity {

  private List<String> expressoes;
  private RecyclerView rvExpressoes;
  private ExpressoesAdapter eAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.expressao_layout);
    carregahistorico();
    rvExpressoes = (RecyclerView) findViewById(R.id.rv_historico);
    rvExpressoes.addItemDecoration(new SpacesItemDecoration(2));
    rvExpressoes.setHasFixedSize(true);

    int scrollPosition = 0;
    if (rvExpressoes.getLayoutManager() != null) {
      scrollPosition = ((LinearLayoutManager) rvExpressoes.getLayoutManager())
          .findFirstCompletelyVisibleItemPosition();
    }

    rvExpressoes.setLayoutManager(new LinearLayoutManager(this));
    rvExpressoes.scrollToPosition(scrollPosition);
    rvExpressoes.setItemAnimator(new DefaultItemAnimator());

    eAdapter = new ExpressoesAdapter(expressoes);
    rvExpressoes.setAdapter(eAdapter);
  }

  private void carregahistorico() {
    Intent it = getIntent();
    expressoes = it.getStringArrayListExtra("historico");
  }
}
