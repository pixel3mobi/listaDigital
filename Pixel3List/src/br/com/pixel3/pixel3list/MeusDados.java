package br.com.pixel3.pixel3list;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;
import br.com.pixel3.pixel3list.dao.UsuarioDAO;
import br.com.pixel3.pixel3list.modelo.Usuario;

public class MeusDados extends Activity {
	
	private Button btnSalvar;
	private UsuarioDAO dao;
	private Usuario usuario;
	private EditText editEscola;
	private EditText editEmail;
	private ToggleButton toggleSexo;
	private EditText editSenha;
	private EditText editNome;
	private EditText editLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meusdados) ;

		
		editLogin = (EditText) findViewById(R.id.editLogin) ;
		editNome = (EditText) findViewById(R.id.editNome) ;
		editSenha = (EditText) findViewById(R.id.editSenha) ;
		toggleSexo = (ToggleButton) findViewById(R.id.toggleSexo) ;
		editEmail = (EditText) findViewById(R.id.editEmail) ;
		editEscola = (EditText) findViewById(R.id.editEscola) ;
		btnSalvar = (Button) findViewById(R.id.btnSalvar) ;
		
		dao = new UsuarioDAO (MeusDados.this) ;
		usuario = dao.getUsuarioPorId(1L) ;
		dao.close() ;
		if ( usuario.getId() == 0 ) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			StringBuilder sb = new StringBuilder() ;
			sb.append("CONTRATO DE LICEN�A DE USU�RIO FINAL COMPLEMENTAR PARA  APLICATIVOS PIXEL� ('EULA Complementar')" +
			"\n\nIMPORTANTE: LEIA COM ATEN��O � Este aplicativo ('PixelList'), inclusive toda documenta��o 'on-line' ou " +
			"eletr�nica ('Componentes do Aplicativo') est�o sujeitos aos termos e condi��es do contrato sob o qual " +
			"voc� aceitou a instala��o descrita abaixo (cada um deles um 'Contrato de Licen�a de Usu�rio Final' ou 'EULA') e aos termos e condi��es deste EULA Complementar. " +
			"\n\nAO INSTALAR, COPIAR OU DE OUTRO MODO USAR OS COMPONENTES DO APLICATIVO, VOC� EST� CONCORDANDO EM VINCULAR-SE AOS TERMOS E CONDI��ES DO EULA DO " +
			"APLICATIVO APLIC�VEL E DESTA EULA COMPLEMENTAR. SE VOC� N�O CONCORDAR COM ESTES TERMOS E CONDI��ES, N�O INSTALE, COPIE OU USE APLICATIVO.'" +
			"\n\n* A PIXEL� MOBI ret�m todos os direitos, titularidade e interesses relacionados aos Componentes do aplicativo. Todos os direitos que n�o estejam expressamente concedidos s�o reservados � Pixel� Mobi." +
			"\n\n* A Pixel� Mobi se ausenta de qualquer responsabilidade por incompatibilidade de hardware ou por mau uso por parte do usu�rio." +
			"\n\n* Este aplicativo � destribuido de forma grauita, por tanto n�o nos comprometemos com atualiza��es e manuten��o do mesmo." ) ;
			
			builder.setMessage(sb.toString());
			builder.setNeutralButton("OK", null);
			AlertDialog dialog = builder.create();
			dialog.setTitle("EULA");
			dialog.show();
		}

		if ( usuario.getId() != 0 ) {
			editLogin.setText(usuario.getLogin()) ;
			editNome.setText(usuario.getNome()) ;
			editSenha.setText(usuario.getSenha()) ;
			toggleSexo.setText(usuario.getSexo()) ;
			editEmail.setText(usuario.getEmail()) ;
			editEscola.setText(usuario.getEscola()) ;
			btnSalvar.setText("Alterar") ;
		}
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				usuario.setLogin(editLogin.getEditableText().toString()) ;
				usuario.setSenha(editSenha.getEditableText().toString()) ;
				usuario.setSexo(toggleSexo.getText().toString()) ;
				usuario.setEmail(editEmail.getEditableText().toString()) ;
				usuario.setEscola(editEscola.getEditableText().toString()) ;
				usuario.setNome(editNome.getEditableText().toString()) ;

				dao = new UsuarioDAO(MeusDados.this) ;
				dao.salvarOuAtualizar(usuario) ;
				dao.close() ;
				
				startActivity(new Intent(MeusDados.this, Chamada.class)) ;
				finish() ;
			}
		}) ;
	}
	
}
