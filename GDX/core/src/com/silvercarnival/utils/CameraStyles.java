/**
 * 
 */
package com.silvercarnival.utils;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.silvercarnival.utils.Constants;

/**
 * @author Julian Giebel
 *
 */
public class CameraStyles {
	
	private CameraStyles()
	{
		
	}
	
	/**
	 * Checks the cameras viewport against a boundary.
	 * 
	 * @param camera the camera to be set inside the Boundary
	 * @param bx Boundary height to be checked against
	 * @param by Boundary width to be checked against
	 */
	public static void setToBoundary(Camera camera,float bx, float by)
	{
		float x = 0, y = 0;
		
		if(bx <= camera.viewportWidth|| by <= camera.viewportHeight) 
		{
			System.err.print("setToBoundary: bx and by mustn't be smaller than the viewport of camera\n");
			return;
		}
		
		if(0 > (x = camera.position.x - camera.viewportWidth/2));
		else if(bx < (x = camera.position.x + camera.viewportWidth/2)) x = x - bx;
		else x = 0;
		
		if(0 > (y = camera.position.y - camera.viewportHeight/2));
		else if(by < (y = camera.position.y + camera.viewportHeight/2))y = y - by;
		else y = 0;
		
		camera.position.set(camera.position.x - x, camera.position.y - y, camera.position.z);
		
		camera.update();
	}
	
	public static void lockOnTarget(Camera camera, Vector2 target)
	{
		Vector3 position = camera.position;
		position.x = target.x*Constants.PPM;
		position.y = target.y*Constants.PPM;
		camera.position.set(position);
		
		camera.update();
	}
	
	public static void lerpToTarget(Camera camera,Vector2 target,float lerp)
	{
		Vector3 position = camera.position;
		//a + (b-a) * lerp
		//b = target
		//a = current camera position
		position.x = camera.position.x +( target.x*Constants.PPM - camera.position.x) * lerp;
		position.y = camera.position.y +( target.y*Constants.PPM - camera.position.y) * lerp;
		camera.position.set(position);
		
		camera.update();
	}
	
	public static void lockAverageBetweenTargets(Camera camera, Vector2 targetA,Vector2 targetB)
	{
		Vector3 position = camera.position;
		position.x = (targetA.x + targetB.x )/2 *Constants.PPM;
		position.y = (targetA.y + targetB.y )/2 *Constants.PPM;
		camera.position.set(position);
		
		camera.update();
	}
	
	public static void lerpAverageBetweenTargets(Camera camera, Vector2 targetA,Vector2 targetB,float lerp)
	{
		float avgx = (targetA.x + targetB.x )/2 *Constants.PPM;
		float avgy = (targetA.y + targetB.y )/2 *Constants.PPM;
		Vector3 position = camera.position;
		position.x = camera.position.x +( avgx - camera.position.x) * lerp;
		position.y = camera.position.y +( avgy - camera.position.y) * lerp;
		camera.position.set(position);
		
		camera.update();
	}
	
	public static boolean searchFocalPoints(Camera camera, Array<Vector2> focalPoints, Vector2 target,float threshold)
	{
		//TODO Optimize for nearest not first to be smaller than threshold.
		for(Vector2 point : focalPoints)
		{
			
			if(target.dst(point) < threshold)
			{
				CameraStyles.lerpToTarget(camera,point,0.2f);
				return true;
			}
		}
		return false;
	}
}
