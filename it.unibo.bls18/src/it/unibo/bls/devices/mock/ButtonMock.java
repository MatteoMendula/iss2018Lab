package it.unibo.bls.devices.mock;

import java.util.Observable;
import it.unibo.bls.interfaces.IButtonObservable;
import it.unibo.bls.interfaces.IObserver;

public class ButtonMock extends Observable implements IButtonObservable{
private boolean buttonState = false;

//Factory method
public static ButtonMock createButton( IObserver obs ){
	 ButtonMock button = new ButtonMock();
	 button.addObserver(obs);
	 return button;
}

	@Override
	public void addObserver(IObserver observer) {
		 super.addObserver(observer);		
	}
	
	//Used for testing purposed of for M2M interaction
	public void press() {
 		System.out.println("ButtonMock press"   );
		buttonState = ! buttonState;
		this.setChanged();
		this.notifyObservers("pressed");
	}
}
