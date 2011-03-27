package br.com.pixel3.pixel3list.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.pixel3.pixel3list.modelo.Usuario;

public class UsuarioDAO extends SQLiteOpenHelper {

	private static final int VERSAO = 1;
	private static final String TABELA = "Usuario";
	private static final String[] COLS = {"id", "nome", "login", "email", "senha", "escola", "sexo"} ;

	public UsuarioDAO(Context ctx) {
		super(ctx, TABELA, null, VERSAO) ;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA +
		"(id INTEGER PRIMARY KEY," +
		" login TEXT UNIQUE NOT NULL," +
		" nome TEXT UNIQUE NOT NULL," +
		" email TEXT NOT NULL," +
		" senha TEXT NOT NULL," +
		" escola TEXT," +
		" sexo TEXT" +
		");" ;
		db.execSQL(sql) ;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABELA);
		this.onCreate(db);
	}
	
	public void salvarOuAtualizar(Usuario usuario) {
		ContentValues valores = new ContentValues() ;
		valores.put("nome", usuario.getNome()) ;
		valores.put("login", usuario.getLogin()) ;
		valores.put("senha", usuario.getSenha()) ;
		valores.put("email", usuario.getEmail()) ;
		valores.put("escola", usuario.getEscola()) ;
		valores.put("sexo", usuario.getSexo()) ;

		if ( usuario.getId() == 0 ) {
			getWritableDatabase().insert(TABELA, null, valores) ;
		} else {
			valores.put("id", usuario.getId()) ;
			getWritableDatabase().update(TABELA, valores, "id=?", new String[]{usuario.getId().toString()}) ;
		}
	}

	public Usuario getUsuarioPorId(Long id) {
		Usuario u = new Usuario() ;
		u.setId(new Long(0)) ;
		u.setLogin("") ;
		u.setSenha("") ;
		
		Cursor c = null ;

		try {
			c = getWritableDatabase().query(TABELA, COLS, "id=?", new String[]{id.toString()}, null, null, null) ;

			while(c.moveToNext()) {
				u.setId(c.getLong(0)) ;
				u.setNome(c.getString(1)) ;
				u.setLogin(c.getString(2)) ;
				u.setEmail(c.getString(3)) ;
				u.setSenha(c.getString(4)) ;
				u.setEscola(c.getString(5)) ;
				u.setSexo(c.getString(6)) ;
			}
		} finally {
			if ( c != null ) c.close();
		}

		return u;
	}

	public boolean logar(String login, String senha) {
		Usuario achado = getUsuarioPorId(1L) ;
		return achado.getLogin().equals(login) && achado.getSenha().equals(senha);
	}
	
}