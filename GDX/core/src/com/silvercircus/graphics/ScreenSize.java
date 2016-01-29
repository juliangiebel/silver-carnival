/**
 * 
 */
package com.silvercircus.graphics;

import com.badlogic.gdx.Gdx;

/**
 * @author Julian Giebel
 *
 */
public final class ScreenSize {

	private static final int defHeight = 480;
	
	private static final int defWidth  = 720;
	
	/**
	 * 
	 */
	private ScreenSize() {
		
	}
	
	public static int getWidth()
	{
		if(Gdx.graphics != null) return Gdx.graphics.getWidth();
		return defWidth;
	}
	
	public static int getHeight()
	{
		if(Gdx.graphics != null) return Gdx.graphics.getHeight();
		return defHeight;
	}

}
