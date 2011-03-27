package br.com.pixel3.pixel3list.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.pixel3.pixel3list.modelo.Presenca;

public class PresencaDAO extends SQLiteOpenHelper {

	private static final String TABELA = "Presenca";
	private static final int VERSAO = 1;
	private static final String[] COLS = {"id", "alunoId", "periodo", "data", "presenca"} ;

	public PresencaDAO(Context context) {
		super(context, TABELA, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABELA +
		"(id INTEGER PRIMARY KEY," +
		" alunoId Integer," +
		" periodo TEXT," +
		" data Integer," +
		" presenca Integer" +
		");" ;
		db.execSQL(sql) ;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABELA);
		this.onCreate(db);
	}

	public List<Long> qtdDatasDifetentes() {
		String sql = "SELECT DISTINCT data FROM " + TABELA + " ;" ;
		Cursor c = getWritableDatabase().rawQuery(sql, null) ;
		
		List<Long> datas = new ArrayList<Long>() ;
		
		while(c.moveToNext()) {
			datas.add(c.getLong(0)) ;
		}
		
		return datas ;
	}
	
	public void salvarTodos(List<Presenca> presencas) {
		for (Presenca presenca : presencas) {
			if ( presenca.getId() == null ) {
				ContentValues values = new ContentValues() ;
				values.put("periodo", presenca.getPeriodo()) ;
				values.put("data", presenca.getData()) ;
				values.put("alunoId", presenca.getAlunoId()) ;
				values.put("presenca", presenca.isPresenca()) ;
				long id = getWritableDatabase().insert(TABELA, null, values) ;
				presenca.setId(id) ;
			}
		}
	}
	
	public List<Presenca> getPresencasPorData(Long data) {
		List<Presenca> presencas = new ArrayList<Presenca>() ;
		
		Cursor c = getWritableDatabase().query(TABELA, COLS, "data=", new String[]{data.toString()}, null, null, "data") ;
		
		while ( c.moveToNext()) {
			Presenca p = new Presenca() ;
			p.setId(c.getLong(0)) ;
			p.setAlunoId(c.getLong(1)) ;
			p.setPeriodo(c.getString(2)) ;
			p.setData(c.getLong(3)) ;
			p.setPresenca(c.getInt(4)==1?true:false) ;
			
			presencas.add(p) ;
		}
		
		return presencas ;
	}
	
}
