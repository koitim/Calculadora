package br.edu.uni7.calculadora;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class Historico extends AppCompatActivity {

  private List<String> historico;
  private RecyclerView rvHistorico;
  private HistoricoAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.historico_layout);
    carregahistorico();
    rvHistorico = (RecyclerView) findViewById(R.id.rv_historico);
    rvHistorico.addItemDecoration(new SpacesItemDecoration(2));
    rvHistorico.setHasFixedSize(true);

    int scrollPosition = 0;
    if (rvHistorico.getLayoutManager() != null) {
      scrollPosition = ((LinearLayoutManager) rvHistorico.getLayoutManager())
          .findFirstCompletelyVisibleItemPosition();
    }

    rvHistorico.setLayoutManager(new LinearLayoutManager(this));
    rvHistorico.scrollToPosition(scrollPosition);
    rvHistorico.setItemAnimator(new DefaultItemAnimator());

    mAdapter = new HistoricoAdapter(historico);
    rvHistorico.setAdapter(mAdapter);
  }

  private void carregahistorico() {
    Intent it = getIntent();
    historico = it.getStringArrayListExtra("historico");
  }
}
