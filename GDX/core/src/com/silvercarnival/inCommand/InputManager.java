/**
 * 
 */
package com.silvercarnival.inCommand;

import java.util.HashMap;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;

/**
 * @author Julian Giebel
 *
 */
public final class InputManager implements InputProcessor {

	HashMap<Integer,Short> _keys;
	
	HashMap<Integer,KeyInputCommand> keyInput;
	HashMap<Integer,TouchInputCommand> TouchInput;
	HashMap<Integer,MouseInputCommand> MouseInput;
	
	int curMouseHandle;
	
	Preferences pref;
	
	Boolean touched = false;
	int lastButton; 
	
	
	
	/**
	 * 
	 */
	public InputManager(Preferences _pref) {
		
		_keys = new HashMap<Integer, Short>();
		keyInput = new HashMap<Integer, KeyInputCommand>();
		TouchInput = new HashMap<Integer, TouchInputCommand>();
		MouseInput = new HashMap<Integer, MouseInputCommand>();
		
		curMouseHandle = 0; //if this is zero ignore mouse inputs;
		
		this.pref = _pref;
		if(!pref.contains("keys")) populateKeys();
		
		
	}
	
	private void populateKeys()
	{
		pref.putBoolean("keys", true);
		//TODO add default keys
		pref.putInteger(String.valueOf(Keys.W), Keys.W);
		pref.putInteger(String.valueOf(Keys.A), Keys.A);
		pref.putInteger(String.valueOf(Keys.S), Keys.S);
		pref.putInteger(String.valueOf(Keys.D), Keys.D);
		pref.putInteger(String.valueOf(Keys.ESCAPE), Keys.ESCAPE);
		pref.putInteger(String.valueOf(Keys.I), Keys.I);
		pref.putInteger(String.valueOf(Buttons.LEFT), Buttons.LEFT);
		pref.putInteger(String.valueOf(Keys.SHIFT_LEFT), Keys.SHIFT_LEFT);
		pref.flush();
	}
	
	public void addCommand(int keyID, KeyInputCommand command)
	{
		keyInput.put(keyID, command);
	}
	
	public void addCommand(int keyID, TouchInputCommand command)
	{
		TouchInput.put(keyID, command);
	}
	
	public void addCommand(int handle, MouseInputCommand command)
	{
		if(handle == 0)return;//TODO Throw exception "handle mustn't be set to Zero for Mouse Input " 
		MouseInput.put(handle, command);
	}
	
	public void removeCommand(int key)
	{
		keyInput.remove(key);
	}

	public void setMouseHandle(int handle)
	{
		curMouseHandle = handle;
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyDown(int)
	 */
	@Override
	public boolean keyDown(int keycode) {
		
		
		
		if(pref.contains(String.valueOf(keycode)))
		{
			int hkey = pref.getInteger(String.valueOf(keycode));
			
			if(keyInput.containsKey(hkey))
			{
				KeyInputCommand key = keyInput.get(hkey);
				if(key == null)
				{
					//TODO add error logging here "keyCommand <"+hkey+"> has been unexpectedly deleted!"
					keyInput.remove(hkey);
					return false;
				}else{
					key.keyDown(keycode);
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyUp(int)
	 */
	@Override
	public boolean keyUp(int keycode) {
		if(pref.contains(String.valueOf(keycode)))
		{
			int hkey = pref.getInteger(String.valueOf(keycode));
			if(keyInput.containsKey(hkey))
			{
				KeyInputCommand key = keyInput.get(hkey);
				if(key == null)
				{
					//TODO add error logging here "keyCommand <"+hkey+"> has been unexpectedly deleted!"
					keyInput.remove(hkey);
					return false;
				}else{
					key.keyUp(keycode);
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#keyTyped(char)
	 */
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDown(int, int, int, int)
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		lastButton = button;
		touched = true;
		
		if(pref.contains(String.valueOf(button)))
		{
			int hkey = pref.getInteger(String.valueOf(button));
			if(TouchInput.containsKey(hkey))
			{
				TouchInputCommand key = TouchInput.get(hkey);
				if(key == null)
				{
					//TODO add error logging here "TouchCommand <"+button+"> has been unexpectedly deleted!"
					TouchInput.remove(hkey);
					return false;
				}else{
					key.touchDown(screenX, screenY, pointer, button);
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchUp(int, int, int, int)
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		touched = false;
		
		if(pref.contains(String.valueOf(button)))
		{
			int hkey = pref.getInteger(String.valueOf(button));
			if(TouchInput.containsKey(hkey))
			{
				TouchInputCommand key = TouchInput.get(hkey);
				if(key == null)
				{
					//TODO add error logging here "TouchCommand <"+button+"> has been unexpectedly deleted!"
					TouchInput.remove(hkey);
					return false;
				}else{
					key.touchUp(screenX, screenY, pointer, button);
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#touchDragged(int, int, int)
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(touched)
		{
			if(pref.contains(String.valueOf(lastButton)))
			{
				int hkey = pref.getInteger(String.valueOf(lastButton));
				if(TouchInput.containsKey(hkey))
				{
					TouchInputCommand key = TouchInput.get(hkey);
					if(key == null)
					{
						//TODO add error logging here "TouchCommand <"+lastButton+"> has been unexpectedly deleted!"
						TouchInput.remove(hkey);
						return false;
					}else{
						key.touchDragged(screenX, screenY, pointer, pointer);
						return true;
					}
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#mouseMoved(int, int)
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		if(curMouseHandle != 0)
		{
			if(MouseInput.containsKey(curMouseHandle))
			{
				MouseInputCommand key = MouseInput.get(curMouseHandle);
				if(key == null)
				{
					//TODO add error logging here "MouseCommand <"+curMouseHandle+"> has been unexpectedly deleted!"
					MouseInput.remove(curMouseHandle);
					return false;
				}else{
					key.mouseMoved(screenX, screenY);
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		if(curMouseHandle != 0)
		{
			if(MouseInput.containsKey(curMouseHandle))
			{
				MouseInputCommand key = MouseInput.get(curMouseHandle);
				if(key == null)
				{
					//TODO add error logging here "MouseCommand <"+curMouseHandle+"> has been unexpectedly deleted!"
					MouseInput.remove(curMouseHandle);
					return false;
				}else{
					key.scrolled(amount);//TODO Add lastPosX and lastPosY to scrolled@MouseInputCommand
					return true;
				}
			}
		}
		return false;
	}

}
