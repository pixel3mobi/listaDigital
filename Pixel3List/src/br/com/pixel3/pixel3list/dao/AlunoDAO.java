package br.com.pixel3.pixel3list.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.pixel3.pixel3list.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

	private static final int VERSAO = 1;
	private static final String TABELA = "Aluno";
	private static final String[] COLS = {"id", "nome", "email", "sexo", "periodos"} ;

	public AlunoDAO(Context context) {
		super(context, TABELA, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA +
		"(id INTEGER PRIMARY KEY," +
		" nome TEXT NOT NULL," +
		" email TEXT," +
		" sexo TEXT," +
		" periodos TEXT" +
		");" ;
		db.execSQL(sql) ;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABELA);
		this.onCreate(db);
	}
	
	public List<Aluno> getLista (String periodo) {
		List<Aluno> alunos = new ArrayList<Aluno>() ;
		
		Cursor c = null ;
		try {
			c = getWritableDatabase().query(TABELA, COLS, "periodos like ?", new String[]{"%" + periodo + "%"}, null, null, null) ;
			
			if ( c.getCount() == 0 ) return alunos ;

			while(c.moveToNext()) {
				
				Aluno a = new Aluno () ;
				a.setId(c.getLong(0)) ;
				a.setNome(c.getString(1)) ;
				a.setEmail(c.getString(2)) ;
				a.setSexo(c.getString(3)) ;
				a.setPeriodos(c.getString(4)) ;
				
				alunos.add(a) ;
				
			}
		} finally {
			if ( c != null ) c.close() ;
		}
		
		return alunos ;
	}

	public void salvarOuAlterar(Aluno aluno) {
		ContentValues valores = new ContentValues() ;
		valores.put("email", aluno.getEmail()) ;
		valores.put("nome", aluno.getNome()) ;
		valores.put("sexo", aluno.getSexo()) ;
		valores.put("periodos", aluno.getPeriodos()) ;
		
		long id = 0 ;
		if ( aluno.getId() == null ) {
			id = getWritableDatabase().insert(TABELA, null, valores) ;
		} else {
			valores.put("id", aluno.getId()) ;
			id = getWritableDatabase().update(TABELA, valores, "id=?", new String[]{aluno.getId().toString()}) ;
		}
		
		aluno.setId(id) ;
	}

	public void remover(Aluno a) {
		getWritableDatabase().delete(TABELA, "id=?", new String[]{a.getId().toString()}) ;
	}

}
