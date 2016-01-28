/**
 * 
 */
package physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Julian Giebel
 *
 */
public class Physics {

	private World world;

	/**
	 * @param world
	 */
	public Physics() {
		world = new World(new Vector2(0,0), true);
	}
	
	public World getWorld()
	{
		return this.world;
	}
	
	public void update(float a, int b, int c)
	{
		//1 / 60f, 6, 2
		world.step(a,b,c);
		
	}
	
	public void dispose()
	{
		this.world.dispose();
	}
	
	
	
}
