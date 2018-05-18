package br.edu.uni7.calculadora;


@SuppressWarnings("serial")
public class ExpressaoMalFormadaException extends ExpressaoException {

  public ExpressaoMalFormadaException() {
    super("Expressão mal formada.");
  }

}
