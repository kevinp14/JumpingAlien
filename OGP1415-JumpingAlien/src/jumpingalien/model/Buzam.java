package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;


//TODO: Klassendiagram maken en meenemen naar verdediging

/**
 * 
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 *
 */
public class Buzam extends Mazub {
	
	/**
	 * Initialize the Buzam alien at the given position in x- and y-direction with the given list of
	 * sprites.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the alien should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the alien should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the alien should look depending on its behaviour.
	 */
	public Buzam(double positionX, double positionY, Sprite[] spriteList) {
		super(positionX, positionY, spriteList);
		assert (isValidSpriteList(spriteList));
		this.changeNbHitPoints(400);
	}
}