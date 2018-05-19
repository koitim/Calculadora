package br.edu.uni7.calculadora;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity implements View.OnClickListener {

  private SharedPreferences pref;
  private List<String> historico;
  private Expressao expressaoAtual;
  private boolean expressaoAtualConcluida;

  private TextView tv_exibe;
  private Button bt_historico;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.principal_layout);
    carregarHistorico();
    expressaoAtual          = new Expressao(this);
    expressaoAtualConcluida = false;
    inicializaComponentes();
  }

  private void inicializaComponentes() {
    tv_exibe             = (TextView) findViewById(R.id.tv_exibe);
    Button bt_0          = (Button) findViewById(R.id.bt_0);
    Button bt_1          = (Button) findViewById(R.id.bt_1);
    Button bt_2          = (Button) findViewById(R.id.bt_2);
    Button bt_3          = (Button) findViewById(R.id.bt_3);
    Button bt_4          = (Button) findViewById(R.id.bt_4);
    Button bt_5          = (Button) findViewById(R.id.bt_5);
    Button bt_6          = (Button) findViewById(R.id.bt_6);
    Button bt_7          = (Button) findViewById(R.id.bt_7);
    Button bt_8          = (Button) findViewById(R.id.bt_8);
    Button bt_9          = (Button) findViewById(R.id.bt_9);
    Button bt_virgula    = (Button) findViewById(R.id.bt_virgula);
    Button bt_igual      = (Button) findViewById(R.id.bt_igual);
    Button bt_soma       = (Button) findViewById(R.id.bt_soma);
    Button bt_subtrai    = (Button) findViewById(R.id.bt_subtrai);
    Button bt_multiplica = (Button) findViewById(R.id.bt_multiplica);
    Button bt_divide     = (Button) findViewById(R.id.bt_divide);
    Button bt_limpar     = (Button) findViewById(R.id.bt_limpar);
    bt_historico         = (Button) findViewById(R.id.bt_historico);
    bt_0.setOnClickListener(this);
    bt_1.setOnClickListener(this);
    bt_2.setOnClickListener(this);
    bt_3.setOnClickListener(this);
    bt_4.setOnClickListener(this);
    bt_5.setOnClickListener(this);
    bt_6.setOnClickListener(this);
    bt_7.setOnClickListener(this);
    bt_8.setOnClickListener(this);
    bt_9.setOnClickListener(this);
    bt_virgula.setOnClickListener(this);
    bt_igual.setOnClickListener(this);
    bt_soma.setOnClickListener(this);
    bt_subtrai.setOnClickListener(this);
    bt_multiplica.setOnClickListener(this);
    bt_divide.setOnClickListener(this);
    bt_limpar.setOnClickListener(this);
    bt_historico.setOnClickListener(this);
  }

  private void carregarHistorico() {
    pref = getApplicationContext().getSharedPreferences(getString(R.string.parametro_historico_armazenamento),0);
    historico = new ArrayList<>();
    int i = 0;
    String expressao = pref.getString(String.valueOf(i), getString(R.string.texto_nulo));
    while (!expressao.equals(getString(R.string.texto_nulo))) {
      historico.add(expressao);
      i++;
      expressao = pref.getString(String.valueOf(i), getString(R.string.texto_nulo));
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.bt_0:
      case R.id.bt_1:
      case R.id.bt_2:
      case R.id.bt_3:
      case R.id.bt_4:
      case R.id.bt_5:
      case R.id.bt_6:
      case R.id.bt_7:
      case R.id.bt_8:
      case R.id.bt_9:
        adicionaNumero(((Button) v).getText().toString());
        break;
      case R.id.bt_virgula:
        adicionaVirgula();
        break;
      case R.id.bt_igual:
        calculaResultado();
        break;
      case R.id.bt_soma:
      case R.id.bt_subtrai:
      case R.id.bt_multiplica:
      case R.id.bt_divide:
        adicionaOperador(((Button) v).getText().toString());
        break;
      case R.id.bt_historico:
        exibeHistorico();
        break;
      case R.id.bt_limpar:
        limpaTela();
        break;
      default:
        break;
    }
  }

  private void adicionaNumero(String operando) {
    verificaFinalizacaoExpressao();
    expressaoAtual.addOperando(operando);
    tv_exibe.setText(expressaoAtual.getExpressao());
  }

  private void adicionaVirgula() {
    verificaFinalizacaoExpressao();
    expressaoAtual.addVirgula();
    tv_exibe.setText(expressaoAtual.getExpressao());
  }

  private void calculaResultado() {
    try {
      String resultadoAtual = expressaoAtual.getExpressao() + " = " + expressaoAtual.getResultado();
      SharedPreferences.Editor editor = pref.edit();
      editor.putString(String.valueOf(historico.size()), resultadoAtual);
      editor.apply();
      historico.add(resultadoAtual);
      expressaoAtual = new Expressao(this);
      tv_exibe.setText(resultadoAtual);
      expressaoAtualConcluida = true;
    } catch (ExpressaoException e) {
      Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  private void adicionaOperador(String operador) {
    verificaFinalizacaoExpressao();
    expressaoAtual.addOperador(operador);
    tv_exibe.setText(expressaoAtual.getExpressao());
  }

  private void verificaFinalizacaoExpressao() {
    if (expressaoAtualConcluida) {
      finalizaExpressao();
    }
  }

  private void exibeHistorico() {
    Intent it = new Intent(this, Historico.class);
    it.putStringArrayListExtra(getString(R.string.parametro_historico), (ArrayList) historico);
    startActivity(it);
  }

  private void limpaTela() {
    finalizaExpressao();
    expressaoAtual = new Expressao(this);
  }

  private void finalizaExpressao() {
    tv_exibe.setText(getString(R.string.texto_nulo));
    expressaoAtualConcluida = false;
  }
}
