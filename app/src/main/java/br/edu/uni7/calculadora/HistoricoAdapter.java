package br.edu.uni7.calculadora;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder>{

  private List<String> historico;

  public HistoricoAdapter(List<String> historico) {
    this.historico = historico;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
    View v = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.historico_row_item, viewGroup, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    viewHolder.setTextView(historico.get(position));
  }

  @Override
  public int getItemCount() {
    return historico.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvHistorico;

    public ViewHolder(View v) {
      super(v);
      tvHistorico = (TextView) v.findViewById(R.id.tv_historico_item);
    }

    public void setTextView(String expressao) {
      tvHistorico.setText(expressao);
    }
  }
}
