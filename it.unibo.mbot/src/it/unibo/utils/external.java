package it.unibo.utils;
import java.io.IOException;

import it.unibo.qactors.akka.QActor;

public class external {
 
	public static void customExecute(QActor qa, String cmd) {
		try { 
//			System.out.println(" ***   customExecute dir= "  + qa.getPrologEngine().getCurrentDirectory() );
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) { e.printStackTrace(); } 
	}
	public static void customRunJar(QActor qa, String cmd) {
		try {
			Runtime.getRuntime().exec("java -jar " + cmd );
		} catch (IOException e) { e.printStackTrace(); }
	}
	public static void customRunJava(QActor qa, String cmd) {
		try {
			Runtime.getRuntime().exec("java " + cmd );
		} catch (IOException e) { e.printStackTrace(); }
	}
}
