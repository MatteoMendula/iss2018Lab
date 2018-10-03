package it.unibo.bls.oo.model;

import java.util.Observable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;

public class ButtonModel extends Observable implements IObservable, IObserver{
private boolean buttonState = false;

//Factory method
public static ButtonModel createButton( IObserver obs ){
	 ButtonModel button = new ButtonModel();
	 button.addObserver(obs);
	 return button;
}

	@Override  //for IObservable
	public void addObserver(IObserver observer) {
		 super.addObserver(observer);		
	}
	
	@Override //for IObserver (called by the lower layer)
	public void update(Observable source, Object value) {
		System.out.println("ButtonObserverModel updated"   );
		switchThestate();
	}

	protected void switchThestate() {
		buttonState = ! buttonState;
		this.setChanged();
		this.notifyObservers("pressed");				
	}
}
