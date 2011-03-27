package br.com.pixel3.pixel3list.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.pixel3.pixel3list.R;
import br.com.pixel3.pixel3list.modelo.Aluno;
import br.com.pixel3.pixel3list.modelo.Presenca;

public class AlunoAdapter extends ArrayAdapter<Presenca>{

	private final List<Aluno> alunos;
	private final Context context;
	private final List<Presenca> presencas;

	public AlunoAdapter(Context context, int textViewResourceId, List<Aluno> alunos, List<Presenca> presencas) {
		super(context, textViewResourceId);
		this.context = context;
		this.alunos = alunos;
		this.presencas = presencas;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
		View view = inflater.inflate(R.layout.item, null) ;
		
		Aluno aluno = alunos.get(position) ;
		
		ImageView fotoAluno = (ImageView) view.findViewById(R.id.fotoALuno) ;
		TextView nomeAluno = (TextView) view.findViewById(R.id.nomeAluno) ;
		CheckBox checkAluno = (CheckBox) view.findViewById(R.id.checkAluno) ;
		checkAluno.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				presencas.get(position).setPresenca(isChecked) ;
			}
		}) ;
		
		fotoAluno.setImageResource(R.drawable.pessoa) ;
		nomeAluno.setText(aluno.getNome()) ;
		
		return view ;
	}
	
	@Override
	public int getCount() {
		return presencas.size();
	}
	
	@Override
	public Presenca getItem(int position) {
		return presencas.get(position) ;
	}
	
	@Override
	public long getItemId(int position) {
		return presencas.get(position).getId() ;
	}
	
}
