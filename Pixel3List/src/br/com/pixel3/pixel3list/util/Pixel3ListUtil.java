package br.com.pixel3.pixel3list.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Pixel3ListUtil {

	public static final String PREFERENCIAS_NOME = "Pixel3List" ;
	public static final String PREFERENCIAS_ID = "id";
	
	public static String cript(String dado) {
		try {
			MessageDigest msg = MessageDigest.getInstance("sha-256") ;
			byte[] digest = msg.digest(dado.getBytes()) ;
			String cript = new String(digest) ;
			return cript ;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException() ;
		}
	}
	
	public static boolean isMesmaData() {
		return true ;
	}
	
}
