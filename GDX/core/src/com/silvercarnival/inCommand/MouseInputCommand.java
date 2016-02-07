package com.silvercarnival.inCommand;

public abstract interface MouseInputCommand {

	abstract void mouseMoved(int x, int y);
	abstract void scrolled(int ammount);
	
}
