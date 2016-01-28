/**
 * 
 */
package entitys.components;

import com.badlogic.ashley.core.Component;

/**
 * @author Julian Giebel
 * @desc Holds all possible inputs
 *
 */
public class InputComponent implements Component {

	public Boolean action, up, down, left, right;
	
	/**
	 * 
	 */
	public InputComponent() {
		action = false;
		up = false;
		down = false;
		left = false;
		right = false;
		
	}

}
