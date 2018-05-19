package br.edu.uni7.calculadora;

import java.util.ArrayList;

class Expressao {
  private final int    OPERANDO  	   = 1;
  private final int    OPERADOR        = 2;
  private final Operador MULTIPLICACAO = new Operador("x");
  private final Operador DIVISAO       = new Operador("/");
  private final Operador SUBTRACAO     = new Operador("-");
  private final Operador SOMA          = new Operador("+");
  private final String VIRGULA   	   = ",";

  private int                 elementoAtual;
  private Operando            operando;
  private Operador            operador;
  private ArrayList<Elemento> expressao;

  Expressao() {
    elementoAtual = OPERANDO;
    operando      = new Operando();
    expressao     = new ArrayList<>();
    expressao.add(operando);
  }

  void addOperando(String i) {
    if (elementoAtual == OPERADOR) {
      elementoAtual = OPERANDO;
      operando      = new Operando();
      expressao.add(operando);
    }
    operando.add(i);
  }

  void addVirgula() {
    if (elementoAtual == OPERADOR) {
      elementoAtual = OPERANDO;
      operando      = new Operando();
      expressao.add(operando);
    } else {
      if (operando.toString().contains(VIRGULA)) {
        return;
      }
    }
    operando.add(VIRGULA);
  }

  void addOperador(String operador) {
    if (elementoAtual == OPERADOR) {
      if (operador.equals(this.operador.toString())) {
        return;
      }
      if (operador.equals(SUBTRACAO.toString())) {
        elementoAtual = OPERANDO;
        operando      = new Operando(operador);
        expressao.add(operando);
      } else {
        this.operador.setOperador(operador);
      }
    } else {
      if (operando.toString().equals("")) {
        if (operador.equals(SUBTRACAO.toString())) {
          operando.add(operador);
        }
      } else {
        if (!operando.toString().equals(SUBTRACAO.toString())) {
          elementoAtual = OPERADOR;
          this.operador = new Operador(operador);
          expressao.add(this.operador);
        }
      }
    }
  }

  String getResultado() throws ExpressaoException {
    ArrayList<Elemento> expressaoReduzida = new ArrayList<>();
    for (int i = 0; i < expressao.size(); i++) {
      Elemento e = expressao.get(i);
      if (e instanceof Operando) {
        expressaoReduzida.add(new Operando(e.toString()));
      } else {
        Operador o = (Operador) e;
        if (o.equals(MULTIPLICACAO) || o.equals(DIVISAO)) {
          if (i == (expressao.size() - 1)) {
            throw new ExpressaoMalFormadaException();
          }
          Operando operando1 = ((Operando) expressaoReduzida.get(expressaoReduzida.size() - 1));
          Operando operando2 = ((Operando) expressao.get(i + 1));
          double resultadoTemporario;
          if (o.equals(MULTIPLICACAO)) {
            resultadoTemporario = operando1.getValorNumerico() * operando2.getValorNumerico();
          } else {
            if (operando2.getValorNumerico() == 0) {
              throw new ExpressaoException("Não é permitido divisão por 0.");
            }
            resultadoTemporario = operando1.getValorNumerico() / operando2.getValorNumerico();
          }
          operando1.setOperando(resultadoTemporario);
          i++;
        } else {
          expressaoReduzida.add(new Operador(e.toString()));
        }
      }
    }
    double resultado = ((Operando) expressaoReduzida.get(0)).getValorNumerico();
    Operador op = null;
    for (int i = 1; i < expressaoReduzida.size(); i++) {
      Elemento e = expressaoReduzida.get(i);
      if (e instanceof Operador) {
        if (i == (expressaoReduzida.size() - 1)) {
          throw new ExpressaoMalFormadaException();
        }
        op = (Operador) e;
      } else {
        if (op.equals(SOMA)) {
          resultado = resultado + ((Operando) e).getValorNumerico();
        } else {
          resultado -= ((Operando) e).getValorNumerico();
        }
      }
    }
    String resultadoFinal = String.valueOf(resultado).replace('.', ',');
    int indiceVirgula = resultadoFinal.indexOf(',');
    String decimais = resultadoFinal.substring(indiceVirgula + 1);
    if (decimais.length() > 2) {
      decimais = decimais.substring(0, 2);
    }
    Integer decimaisInteger = Integer.valueOf(decimais);
    int decimaisInt = decimaisInteger.intValue();
    if (decimaisInt == 0) {
      resultadoFinal = resultadoFinal.substring(0,indiceVirgula);
    } else {
      resultadoFinal = resultadoFinal.substring(0,indiceVirgula + 3);
    }
    return resultadoFinal;
  }

  String getExpressao(){
    StringBuilder expressaoSB = new StringBuilder();
    for (int i = 0; i < expressao.size(); i++) {
      expressaoSB.append(expressao.get(i).toString());
    }
    return expressaoSB.toString();
  }

  private abstract class Elemento {

    private StringBuilder valor;

    private Elemento (String valor) {
      this.valor = new StringBuilder(valor);
    }

    private Elemento () {
      this.valor = new StringBuilder();
    }

    @Override
    public String toString() {
      return valor.toString();
    }

    StringBuilder getValor() {
      return valor;
    }

    void setValor(StringBuilder valor) {
      this.valor = valor;
    }

  }

  private class Operando extends Elemento {

    private double valorNumerico;

    private Operando() {
      super();
    }

    private Operando(String valor) {
      super(valor);
      calculaValorNumerico();
    }

    private void add(String i) {
      getValor().append(i);
      calculaValorNumerico();
    }

    private void calculaValorNumerico() {
      String valorString = getValor().toString();
      boolean inverterSinal = valorString.substring(0,0).equals(SUBTRACAO.toString());
      if (inverterSinal) {
        valorString = valorString.substring(1);
      }
      if (valorString.substring(0,1).equals(VIRGULA)) {
        valorString = "0" + valorString;
      }
      if (valorString.substring(valorString.indexOf(VIRGULA)+1).equals("")) {
        valorString = valorString + "0";
      }
      valorNumerico = Double.valueOf(valorString.replace(',', '.'));
      if (inverterSinal) {
        valorNumerico = valorNumerico * -1;
      }
    }

    @Override
    public String toString() {
      return super.toString();
    }

    private double getValorNumerico() {
      return valorNumerico;
    }

    private void setOperando(double operando) {
      setValor(new StringBuilder(String.valueOf(operando)));
      calculaValorNumerico();
    }
  }

  private class Operador extends Elemento {
    private Operador(String valor) {
      super(valor);
    }

    private void setOperador(String operador) {
      setValor(new StringBuilder(operador));
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (!(obj instanceof Operador)) {
        return false;
      }
      Operador operador = (Operador) obj;
      return getValor().toString().equals(operador.getValor().toString());
    }

    @Override
    public String toString() {
      return super.toString();
    }
  }
}
