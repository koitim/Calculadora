package br.edu.uni7.calculadora;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class ExpressoesAdapter extends RecyclerView.Adapter<ExpressoesAdapter.ViewHolder>{

  private List<String> expressoes;

  public ExpressoesAdapter(List<String> expressoes) {
    this.expressoes = expressoes;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.expressao_row_item, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    viewHolder.setTextView(expressoes.get(position));
  }

  @Override
  public int getItemCount() {
    return expressoes.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvExpressao;

    public ViewHolder(View v) {
      super(v);
      tvExpressao = (TextView) v.findViewById(R.id.tv_expressao_item);
    }

    public void setTextView(String expressao) {
      tvExpressao.setText(expressao);
    }
  }
}
