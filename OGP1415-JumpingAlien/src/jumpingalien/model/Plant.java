package jumpingalien.model;

import java.util.Random;

import jumpingalien.util.Sprite;

/**
 * A class of plants involving the normal and maximum for the horizontal velocity, the time the plant 
 * is moving horizontally, the number of hitpoints, a method to create a random direction and a method 
 * to advance time and adapt the time depending characteristics based on the period of time passed.
 * 
 * @invar 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 5.0
 *
 */
public class Plant extends GameObject {
	
	private Direction nextDirection;
	private double timeMovingHorizontally;
	
	/**
	 * @param 	positionX
	 * 			The position in the x-direction where the plant should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the plant should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the plant should look depending on its behaviour.
	 * @effect	The new last direction is set to a random direction.
	 * 			| this.setLastDirection(this.getRandomDirection())
	 * @effect	The new normal horizontal velocity is set to 0.5.
	 * 			| this.setNormalHorizontalVelocity(0.5)
	 * @post	The new next direction is set to stalled.
	 * 			| (new this).nextDirection = Direction.STALLED
	 * @post	The new time moving horizontally is set to 0.
	 * 			| (new this).timeMovingHorizontally = 0
	 * @effect	The number of hitpoints is increased with 1.
	 * 			| this.changeNbHitPoints(1)
	 */
	public Plant(int positionX, int positionY, Sprite[] spriteList, Program program) {
		super(positionX, positionY, spriteList, program);
		this.setLastDirection(this.getRandomDirection());
		this.setNormalHorizontalVelocity(0.5);
	    this.nextDirection = Direction.STALLED;
	    this.timeMovingHorizontally = 0;
	    this.changeNbHitPoints(1);
	}
	
	/**
	 * @return A random direction (left or right) for the plant to move in.
	 * 
	 */
	private Direction getRandomDirection(){
		Random rand = new Random();
	    int directionNumber = rand.nextInt(2);
	    if (directionNumber == 0)
	    	return Direction.LEFT;
	    else
	    	return Direction.RIGHT;
	}
	
	/**
	 * @param 	dt
	 * 			The period of time dt for which the new period of time needs to be calculated.
	 * @return	The new period of time dt based on the current velocity and acceleration of the alien
	 * 			in this world. (used for accurate collision detection).
	 * 
	 */
	private double getNewDt(double dt) {
		double velocity = Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2) + 
				Math.pow(this.getVerticalVelocity(), 2));
		double acceleration = Math.sqrt(Math.pow(this.getHorizontalAcceleration(), 2) + 
				Math.pow(this.getVerticalAcceleration(), 2));
		double newDt = 0.01 / (velocity + (acceleration * dt));
		if ((velocity + (acceleration * dt)) == 0)
			return 0.01;
		else 
			return newDt;
	}
	
	/**
	 * @return The next direction in which the plant has to move.
	 * 
	 */
	protected Direction getNextDirection() {
		return this.nextDirection;
	}
	
	/**
	 * @param 	direction
	 * 			The direction to which the next direction has to be set.
	 */
	protected void setNextDirection(Direction direction){
		this.nextDirection = direction;
	}
	
	/**
	 * @param	direction
	 * 			The direction in which the plant has to move.
	 * @pre	The given direction should be valid.
	 * 		| isValidMovingDirection(direction)
	 * @post	The new last direction should be valid.
	 * 			| isValidMovingDirection((new this).getLastDirection())
	 * @post	The new next direction (always the opposite of the last direction) should be valid.
	 * 			| isValidMovingDirection((new this).getNextDirection())
	 */
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT) {
			this.setLastDirection(Direction.RIGHT);
			this.setNextDirection(Direction.LEFT);
			this.setHorizontalVelocity(this.getNormalHorizontalVelocity());
		}
		else {
			this.setLastDirection(Direction.LEFT);
			this.setNextDirection(Direction.RIGHT);
			this.setHorizontalVelocity(-this.getNormalHorizontalVelocity());
		}
	}
	
	/**
	 * @param	direction
	 * 			The horizontal direction in which the plant was moving.
	 * @pre	The given direction should be valid.
	 * 		| isValidMovingDirection(direction)
	 */
	@Override
	public void endMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0))
			this.setHorizontalVelocity(0);
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0))
			this.setHorizontalVelocity(0);
	}
	
	/**
	 * @param	dt
	 * 			The time passed dt.
	 * @return	newPositionX
	 * 			The plant's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt
	 */
	private double horizontalMovement(double dt) {
		double newPositionX = this.getHorizontalVelocity() * dt;
		return newPositionX;
	}
	
	/**
	 * @param	newDt
	 * 			The period of time on which collision has to be detected.
	 * @param	oldPosition
	 * 			The plant's old position.
	 * @effect	If the plant collides with an alien (Mazub or Buzam) in it's game world and the alien's 
	 * 			current number of hitpoints is smaller than 500, it loses 1 hitpoint.
	 * 			| if (((this.collidesWith(alien)) && (alien.getNbHitPoints() < 500))
	 * 			|	|| ((this.collidesWith(buzam)) && (buzam.getNbHitPoints() < 500)))
	 * 			|		this.changeNbHitPoints(-1)
	 * @effect	If the plant collides with another plant in the game world, its movement is blocked.
	 * 			| for (Plant plant: this.getWorld().getPlants())
	 * 			|	if (this.collidesWith(plant))
	 * 			|		this.collisionBlockMovement(plant, oldPosition, newDt)
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		Mazub alien = this.getWorld().getMazub();
//		Buzam buzam = this.getWorld().getBuzam();
		if (((this.collidesWith(alien)) && (alien.getNbHitPoints() < 500)))
//				|| ((this.collidesWith(buzam)) && (buzam.getNbHitPoints() < 500)))
			this.changeNbHitPoints(-1);
		for (Plant plant: this.getWorld().getPlants()) {
			if (this.collidesWith(plant))
				this.collisionBlockMovement(plant, oldPosition, newDt);
		}
	}
	
	/**
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	If the plant is already moving horizontally for more than 0.5s, all ongoing movements
	 * 			are ended and it starts moving into the next (opposite) direction.
	 * 			|if (this.timeMovingHorizontally >= 0.50)
	 * 			|	this.endMoveHorizontally(this.getLastDirection())
	 * 			|	this.startMoveHorizontally(this.getNextDirection())
	 * @effect	If the plant is trying to cross an impassable tile, take the corresponding actions.
	 * 			| if ((this.crossImpassableLeft()) || (this.crossImpassableRight()))
	 * 			|		this.crossImpassableActions(oldPosition)
	 * @effect	If the plant is not trying to cross an impassable tile, its x-position is increased
	 * 			by 100 times the horizontal movement.
	 * 			| if ((!this.crossImpassableLeft()) && (!this.crossImpassableRight()))
	 * 			|		this.setPosition(oldPosition[0] + 100*this.horizontalMovement(newDt),
	 * 			|			oldPosition[1])
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (!this.isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		double sumDt = 0;
		while (sumDt < dt) {
			double newDt = this.getNewDt(dt);
			int[] oldPosition = this.getPosition();
			if (this.timeMovingHorizontally >= 0.50) {
				this.timeMovingHorizontally = 0;
				this.endMoveHorizontally(this.getLastDirection());
				this.startMoveHorizontally(this.getNextDirection());
			}
			else if (this.timeMovingHorizontally < 0.50) {
				this.timeMovingHorizontally += newDt;
			}
			if ((this.crossImpassableLeft()) || (this.crossImpassableRight()))  {
				this.crossImpassableActions(oldPosition);
			}
			this.collidesWithActions(newDt, oldPosition);
			if ((!this.crossImpassableLeft()) && (!this.crossImpassableRight())) {
				this.setPosition(oldPosition[0] + (100 * this.horizontalMovement(
						newDt)), oldPosition[1]);
			}
			sumDt += newDt;
		}
	}
}