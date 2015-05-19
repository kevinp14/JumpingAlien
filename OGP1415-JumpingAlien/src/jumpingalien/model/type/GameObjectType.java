package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;
import jumpingalien.model.GameObject;

@Value
public class GameObjectType extends Type<GameObject>{
	
	public GameObjectType(GameObject gameObject){
		super(gameObject);
	}

}
