/**
 * 
 */
package entitys_old;

import java.util.ArrayList;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
//obsolete! 
/**
 * @author Julian Giebel
 *
 *
 */
public final class UpdateEntitys {

	float fieldX, fieldY, fieldW, fieldH;
	ArrayList<Body> updaters;
	
	World world;
	
	/**
	 * @param fieldX
	 * @param fieldY
	 * @param fieldW
	 * @param fieldH
	 */
	public UpdateEntitys( float fieldW, float fieldH, World world) {
		this.fieldW = fieldW;
		this.fieldH = fieldH;
		this.world  = world;
		updaters = new ArrayList<Body>();
	}
	
	void addUpdateBody(Body body)
	{
		
		if(body != null) updaters.add(body);
		
	}
	
	void removeUpdateBody(Body body)
	{
		
		if(body == null || !updaters.contains(body) || updaters == null) return;
		updaters.remove(body);
		
	}
	
	void update()
	{
		
		
	}
	
	
	
}
