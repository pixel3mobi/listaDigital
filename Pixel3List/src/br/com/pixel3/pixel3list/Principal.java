package br.com.pixel3.pixel3list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.pixel3.pixel3list.dao.UsuarioDAO;

public class Principal extends Activity {

	private EditText editLogin;
	private EditText editSenha;
	private Button botaoLogin;
	private UsuarioDAO dao;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editLogin = (EditText) findViewById(R.id.editLogin) ;
        editSenha = (EditText) findViewById(R.id.editSenha) ;
        botaoLogin = (Button) findViewById(R.id.botaoLogin) ;
        
        botaoLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String login = editLogin.getEditableText().toString() ;
				String senha = editSenha.getEditableText().toString() ;
				
				dao = new UsuarioDAO(Principal.this) ;
				if ( dao.logar (login, senha) ) {
					startActivity(new Intent(Principal.this, Chamada.class)) ;
					finish() ;
				} else {
					editLogin.setText("") ;
					editSenha.setText("") ;
					Toast.makeText(Principal.this, "Usu�rio/Senha n�o confere!", Toast.LENGTH_LONG).show() ;
				}
				dao.close() ;
				
			}
		}) ;
    }
	
}
