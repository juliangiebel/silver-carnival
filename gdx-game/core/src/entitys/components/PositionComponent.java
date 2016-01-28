/**
 * 
 */
package entitys.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Julian Giebel
 *
 */
public class PositionComponent implements Component {

	public Vector2 position;
	
	/**
	 * 
	 */
	public PositionComponent(Vector2 _position) {
		this.position = _position;
	}

}
