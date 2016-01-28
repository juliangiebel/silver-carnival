package entitys.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class VelocityComponent implements Component {

	public Vector2 velocity;
	public float walkSpeed;
	public float runSpeed;
	public boolean running;
	
	public float heading;//in Degree
	
	
	public VelocityComponent(float _walkSpeed,float _runSpeed) {
		this.velocity = new Vector2();
		this.walkSpeed = _walkSpeed;
		this.runSpeed = _runSpeed;
		running = false;
	}

}
