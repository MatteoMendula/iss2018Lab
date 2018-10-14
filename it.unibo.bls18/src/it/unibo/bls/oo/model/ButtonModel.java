package it.unibo.bls.oo.model;

import java.util.Observable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;

public class ButtonModel extends Observable implements IObservable, IObserver{
private boolean buttonState = false;
private String btnName;

//Factory methods
public static ButtonModel createButton( String btnName, IObserver obs ){
	 ButtonModel button = new ButtonModel(btnName);
	 button.addObserver(obs);
	 return button;
}
public static ButtonModel createButton( String btnName ){
	 ButtonModel button = new ButtonModel(btnName);
 	 return button;
}

	public ButtonModel(String btnName) {
		this.btnName = btnName;
	}
	@Override  //for IObservable
	public void addObserver(IObserver observer) {
		 super.addObserver(observer);		
	}
	
	@Override //for IObserver (called by the lower layer)
	public void update(Observable source, Object value) {
		System.out.println("ButtonModel updated"   );
		switchThestate();
	}

	protected void switchThestate() {
		buttonState = ! buttonState;
		this.setChanged();
		this.notifyObservers(btnName);				
	}
}
