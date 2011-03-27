package br.com.pixel3.pixel3list.adapter;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class PeriodosAdapter implements SpinnerAdapter {

	private List<String> lista = Arrays.asList("Manh�", "Tarde", "Noite") ;
	private final Context ctx;
	
	public PeriodosAdapter(Context ctx) {
		this.ctx = ctx;
	}
	
	public int getCount() {
		return lista.size();
	}

	public Object getItem(int posicao) {
		return lista.get(posicao);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(ctx) ;
		tv.setText(lista.get(position)) ;
		tv.setTextColor(Color.BLACK) ;
		return tv;
	}

	public int getViewTypeCount() {
		return 0;
	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isEmpty() {
		return false;
	}

	public void registerDataSetObserver(DataSetObserver observer) {
	}

	public void unregisterDataSetObserver(DataSetObserver observer) {
	}

	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView tv = new TextView(ctx) ;
		tv.setText(lista.get(position)) ;
		tv.setTextColor(Color.BLACK) ;
		tv.setTextSize(30) ;
		tv.setPadding(10, 15, 0, 15) ;
		return tv;
	}

}
