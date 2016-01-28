package Patterns;

import com.badlogic.gdx.physics.box2d.Body;

public abstract interface Command {
	 abstract void execute(Body body);
}
