package it.unibo.bls.oo.model;

import java.util.Observable;
import it.unibo.bls.interfaces.IObservable;
import it.unibo.bls.interfaces.IObserver;

public class ButtonObserverModel extends Observable implements IObservable, IObserver{
private boolean buttonState = false;

//Factory method
public static ButtonObserverModel createButton( IObserver obs ){
	 ButtonObserverModel button = new ButtonObserverModel();
	 button.addObserver(obs);
	 return button;
}

	@Override
	public void addObserver(IObserver observer) {
		 super.addObserver(observer);		
	}
	
	protected void switchThestate() {
		buttonState = ! buttonState;
		this.setChanged();
		this.notifyObservers("pressed");		
		
	}


	@Override
	public void update(Observable source, Object value) {
		System.out.println("ButtonObserverModel updated"   );
		switchThestate();
	}
}
