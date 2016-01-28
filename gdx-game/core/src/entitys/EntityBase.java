/**
 * 
 */
package entitys;

import com.badlogic.gdx.physics.box2d.Body;

import Patterns.Command;

/**
 * @author Julian Giebel
 *
 */
public abstract class EntityBase {

	Body parent;
	static int IDcount; 
	int ID;
	
	public EntityBase(Body parent)
	{
		
		this.parent = parent;
		this.parent.setUserData(this);
		IDcount++;
		ID = IDcount;
		
		create();
	}
	
    abstract void create();
    //TODO Implement Event System inside it's own Package
    abstract void update(/*Instance of Event System: Event e*/);
    
    abstract void destroy(); //cleaning up
    
    int getID()
    {
    	return ID;
    }
	
}
