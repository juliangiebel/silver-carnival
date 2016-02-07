package com.silvercarnival.inCommand;

public abstract interface TouchInputCommand {

	abstract void touchDown(int x, int y, int pointer, int button);
	abstract void touchUp(int x, int y, int pointer, int button);
	abstract void touchDragged(int x, int y, int pointer, int button);
	
}
