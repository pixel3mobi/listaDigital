package br.com.pixel3.pixel3list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import br.com.pixel3.pixel3list.dao.UsuarioDAO;
import br.com.pixel3.pixel3list.modelo.Usuario;

public class Pixel3List extends Activity {

	private static final String TAG = "Pixel3List" ;
	private boolean d = true ;
	private UsuarioDAO dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        
        if (d) Log.d(TAG, "Width: " + width ) ;
        if (d) Log.d(TAG, "Height: " + height ) ;
		
        dao = new UsuarioDAO(this) ;
        Usuario usuarioAchado = dao.getUsuarioPorId(1L) ;
		if ( usuarioAchado.getId() == 0 ) {
			startActivity(new Intent(this, MeusDados.class)) ;
		} else {
			startActivity(new Intent(this, Principal.class)) ;
		}
		dao.close() ;
		finish() ;
	}
	
}