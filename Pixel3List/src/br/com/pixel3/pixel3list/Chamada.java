package br.com.pixel3.pixel3list;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.pixel3.pixel3list.adapter.AlunoAdapter;
import br.com.pixel3.pixel3list.adapter.PeriodosAdapter;
import br.com.pixel3.pixel3list.dao.AlunoDAO;
import br.com.pixel3.pixel3list.dao.PresencaDAO;
import br.com.pixel3.pixel3list.dao.UsuarioDAO;
import br.com.pixel3.pixel3list.modelo.Aluno;
import br.com.pixel3.pixel3list.modelo.Presenca;
import br.com.pixel3.pixel3list.modelo.Usuario;
import br.com.pixel3.pixel3list.util.Pixel3ListUtil;

public class Chamada extends Activity implements OnClickListener {

	// Views
	private ListView alunos;
	private Spinner periodos;
	private TextView nomeProfessor;
	private TextView txtData ;
	private Button btnSalvar;
	private Button btnProximo;
	private Button btnAnterior;

	// Adapters
	private ArrayAdapter<Presenca> arrayAdapter;
	private PeriodosAdapter periodosAdapter ;

	// DAOs
	private UsuarioDAO usuarioDAO;
	private AlunoDAO alunoDAO;
	protected PresencaDAO presencaDAO;

	// Auxiliares
	private String periodo = "Manh�";
	private List<Aluno> listaAlunos;
	private List<Presenca> presencas ;
	private List<Long> datas;
	private Calendar dataAtual;
	protected int indiceData;
	private Usuario usuario;
	private Aluno alunoSelecionado ;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy") ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chamada) ;
		
		alunoDAO = new AlunoDAO(this) ;
		usuarioDAO = new UsuarioDAO(this) ;
		presencaDAO = new PresencaDAO(this) ;
		usuario = usuarioDAO.getUsuarioPorId(1L) ;
		
		alunos = (ListView) findViewById(R.id.alunos) ;
		btnAnterior = (Button) findViewById(R.id.btnAnterior) ;
		btnProximo = (Button) findViewById(R.id.btnProximo) ;
		btnSalvar = (Button) findViewById(R.id.btnSalvar) ;
		nomeProfessor = (TextView) findViewById(R.id.nomeProfessor) ;
		periodos = (Spinner) findViewById(R.id.periodos) ;
		txtData = (TextView) findViewById(R.id.txtData) ;

		datas = presencaDAO.qtdDatasDifetentes() ;
		Calendar c = Calendar.getInstance() ;
		zeraHoraDaData(c) ;
		datas.add(c.getTimeInMillis()) ;
		indiceData = datas.size()-1 ;
		dataAtual = c ;
		txtData.setText(sdf.format(c.getTimeInMillis()) + " - " + periodo) ;
		
		btnAnterior.setOnClickListener(this) ;
		btnProximo.setOnClickListener(this) ;
		btnSalvar.setOnClickListener(this) ;
		
		nomeProfessor.setText(usuario.getNome()) ;

		alunos.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				alunoSelecionado = listaAlunos.get(posicao) ;
				registerForContextMenu(view) ;
				return false;
			}
		}) ;
		
		periodosAdapter = new PeriodosAdapter(this) ;
		periodos.setAdapter(periodosAdapter) ;
		
		periodos.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapter, View view, int posicao, long id) {
				periodo = periodos.getSelectedItem().toString() ;
				
				zeraHoraDaData(dataAtual) ;
				carrega() ;				
			}

			public void onNothingSelected(AdapterView<?> adapter) {
			}
		}) ;
		
		carrega() ;
	}

	private void zeraHoraDaData(Calendar c) {
		c.set(Calendar.HOUR, 0) ;
		c.set(Calendar.MINUTE, 0) ;
		c.set(Calendar.SECOND, 0) ;
		c.set(Calendar.MILLISECOND, 0) ;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		carrega() ;
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		carrega() ;
	}
	
	public void carrega () {
		
		if ( datas.size() == 0 || indiceData >= datas.size()) {
			btnProximo.setEnabled(false) ;
		} else {
			btnProximo.setEnabled(true) ;
		}
		if ( indiceData > 0 ) {
			btnAnterior.setEnabled(true) ;
		} else {
			btnAnterior.setEnabled(false) ;
		}
		
		txtData.setText(sdf.format(dataAtual.getTime()) + " - " + periodo) ;
		
		listaAlunos = alunoDAO.getLista(periodo) ;
		
		presencas = new ArrayList<Presenca>() ;
		for (Aluno a : listaAlunos) {
			Presenca presenca = new Presenca();
			presenca.setAlunoId(a.getId()) ;
			presenca.setData(dataAtual.getTimeInMillis()) ;
			presenca.setPeriodo(periodo) ;
			presencas.add(presenca) ;
		}
		
		arrayAdapter = new AlunoAdapter(Chamada.this, android.R.layout.simple_list_item_1, listaAlunos, presencas) ;
		
		alunos.setAdapter(arrayAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem novoAluno = menu.add(0, 0, 1, "Cadastrar Alunos") ;
		novoAluno.setIcon(R.drawable.novo_aluno) ;

		MenuItem meusDados = menu.add(0, 2, 1, "Meus Dados") ;
		meusDados.setIcon(R.drawable.meus_dados) ;

		MenuItem enviarPresencas = menu.add(0, 1, 1, "Enviar Presen�as por Email") ;
		enviarPresencas.setIcon(R.drawable.email) ;
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			startActivity(new Intent(Chamada.this, FormAluno.class)) ;
			break;
		case 1:
			enviarEmail(usuario.getEmail(), "Presen�as", "Arquivo em formato maluco", "/sdcard/texto.csv");
			
			break;
		case 2:
			startActivity(new Intent(Chamada.this, MeusDados.class)) ;
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void enviarEmail(String emailDestino, String titulo, String texto, String arquivo) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { emailDestino });
		i.putExtra(Intent.EXTRA_SUBJECT, titulo);
		i.putExtra(Intent.EXTRA_TEXT, texto);
		if ( arquivo != null ) i.putExtra(Intent.EXTRA_STREAM, Uri.parse(arquivo));
		
		startActivity(Intent.createChooser(i, "Selecione a sua aplica��o favorita de email!"));
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		menu.add(0, 0, 0, "Excluir") ;
		menu.add(0, 1, 0, "Alterar") ;
		menu.add(0, 2, 0, "Enviar Presen�a") ;
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case 1:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Deseja remover o aluno ");
			builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					alunoDAO.remover(alunoSelecionado) ;
					carrega() ;
				}
			}) ;
			builder.setNegativeButton("N�o", null) ;
			AlertDialog dialog = builder.create();
			dialog.setTitle("Remover " + alunoSelecionado.getNome() + "?");
			dialog.show();
			carrega() ;
			break;
		case 2:
			SharedPreferences prefs = getSharedPreferences(Pixel3ListUtil.PREFERENCIAS_NOME, MODE_PRIVATE) ;
			Editor edit = prefs.edit() ;
			edit.putLong(Pixel3ListUtil.PREFERENCIAS_ID, 1) ;
			edit.commit() ;
			
			startActivity(new Intent(this, FormAluno.class)) ;
			break;
		case 3:
			enviarEmail(alunoSelecionado.getEmail(), "Presen�as", "Lista de Presen�as da escola " + usuario.getEscola(), null) ;
			Toast.makeText(this, "Presen�as enviadas com sucesso!", Toast.LENGTH_LONG).show() ;
			break;
		default:
			break;		
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		usuarioDAO.close();
		alunoDAO.close();
		presencaDAO.close();
	}

	public void onClick(View v) {
		if ( !datas.isEmpty() ) {
			if ( v == btnAnterior ) indiceData-- ;
			if ( v == btnProximo ) indiceData++ ;
			
			if ( indiceData < 0 ) {
				indiceData = 0 ;
			}
			if ( indiceData > datas.size() -1 ) {
				indiceData = datas.size()-1 ;
			}
			
			dataAtual.setTimeInMillis(datas.get(indiceData)) ;
			
			carrega() ;
		}
		
		if ( v == btnSalvar ) {
			presencaDAO.salvarTodos(presencas) ;
		}
	}
}