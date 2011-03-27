package br.com.pixel3.pixel3list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ToggleButton;
import br.com.pixel3.pixel3list.dao.AlunoDAO;
import br.com.pixel3.pixel3list.modelo.Aluno;

public class FormAluno extends Activity {

	private Button btnAdicionar;
	private CheckBox checkNoite;
	private CheckBox checkTarde;
	private CheckBox checkManha;
	private ToggleButton toggleSexo;
	private EditText editEmail;
	private EditText editNome;
	protected Aluno aluno;
	private AlunoDAO dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formaluno) ;

		dao = new AlunoDAO(this) ;
		
		editNome = (EditText) findViewById(R.id.editNome) ;
		editEmail = (EditText) findViewById(R.id.editEmail) ;
		toggleSexo = (ToggleButton) findViewById(R.id.toggleSexo) ;
		checkManha = (CheckBox) findViewById(R.id.checkManha) ;
		checkTarde = (CheckBox) findViewById(R.id.checkTarde) ;
		checkNoite = (CheckBox) findViewById(R.id.checkNoite) ;
		btnAdicionar = (Button) findViewById(R.id.btnAdicionar) ;
		
		btnAdicionar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				aluno = new Aluno() ;
				aluno.setNome(editNome.getEditableText().toString()) ;
				aluno.setEmail(editEmail.getEditableText().toString()) ;
				aluno.setSexo(toggleSexo.getText().toString()) ;
				
				StringBuilder periodos = new StringBuilder() ;
				if ( checkManha.isChecked() ) periodos.append(checkManha.getText() + ";");
				if ( checkTarde.isChecked() ) periodos.append(checkTarde.getText() + ";");
				if ( checkNoite.isChecked() ) periodos.append(checkNoite.getText() + ";");
				aluno.setPeriodos(periodos.toString()) ;
				
				dao.salvarOuAlterar(aluno) ;
				dao.close() ;
				
				finish() ;
			}
		}) ;
		
	}
	
}
