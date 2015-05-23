package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;
import jumpingalien.model.GameObject;

/**
 * A class of game object types.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
@Value
public class GameObjectType extends Type<GameObject>{
	
	/**
	 * Initialize the game object type
	 * 
	 * @param	gameObject
	 * 			The game object type the game object should get.
	 */
	public GameObjectType(GameObject gameObject){
		super(gameObject);
	}

}
