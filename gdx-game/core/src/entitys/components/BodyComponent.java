/**
 * 
 */
package entitys.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * @author Julian Giebel
 *
 */
public class BodyComponent implements Component {

	public Body body;
	
	/**
	 * 
	 */
	public BodyComponent(Body _body) {
		this.body = _body;
		//body.setUserData(this);
	}

}
