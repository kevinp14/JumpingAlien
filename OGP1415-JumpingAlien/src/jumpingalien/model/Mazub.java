package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;


// Klassendiagram maken en meenemen naar verdediging

/**
 * A class of Mazub aliens involving some maximums for the horizontal velocity depending on its current
 * actions, a list of sprites or images of the alien that are displayed depending on its current actions, 
 * the time the alien is moving horizontally or standing still, the last direction in which the alien
 * was moving and the secondary direction in which the alien is moving (only when performing multiple
 * movements at the same time), the number of hitpoints, methods to move the alien horizontally as 
 * well as vertically (jumping, falling and ducking), methods to inspect the behavior of the alien 
 * and a method to advance time and adapt the time depending characteristics based on the period of 
 * time passed.
 * 
 * @invar //TODO
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 8.0
 *
 */ //TODO: meeste @post veranderen in @effect en @invar bekijken
public class Mazub extends GameObject {
	private double maxRunningVelocity = 3;
	private double maxDuckingVelocity = 1;
	private double normalVerticalVelocity;
	private Sprite[] spriteList;
	private double timeMovingHorizontally;
	private double timeForcedDuck;
	private Direction secondaryDirection;
	
	/**
	 * Initialize the Mazub alien at the given position in x- and y-direction with the given list of
	 * sprites. Also sets the time moving horizontally to 0.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the alien should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the alien should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the alien should look depending on its behavior.
	 */
	public Mazub(double positionX, double positionY, Sprite[] spriteList) {
		super(positionX, positionY, spriteList);
		assert (isValidSpriteList(spriteList));
		this.spriteList = spriteList;
		this.setNormalHorizontalVelocity(1);
		this.setNormalHorizontalAcceleration(0.9);
		this.setMaxHorizontalVelocity(3);
		this.normalVerticalVelocity = 8;
	    this.timeMovingHorizontally = 0;
	    this.timeForcedDuck = 0;
	    this.changeNbHitPoints(100);
	}

	/**
	 * Return the current sprite image for the given alien.
	 * 
	 * @return 	The current sprite image for the given alien, determined by its
	 * 			state as defined in the assignment.
	 */
	@Basic 
	@Immutable
	@Override
	public Sprite getCurrentSprite() {
		if (this.isMovingHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking())
					return spriteList[7];
				if (this.isAirborne())
					return spriteList[5];
				else {
					if (((1000*this.timeMovingHorizontally) / 75) < 11)
						return spriteList[(int)(19 + ((1000*this.timeMovingHorizontally) / 75))];
					else
						this.timeMovingHorizontally = 0;
				}
			}
			else {
				if (this.isDucking())
					return spriteList[6];
				if (this.isAirborne())
					return spriteList[4];
				else {
					if (((1000*this.timeMovingHorizontally) / 75) < 11)
						return spriteList[(int) (8 + ((1000*this.timeMovingHorizontally) / 75))];
					else 
						this.timeMovingHorizontally = 0;
				}
			}
		}
		if (this.hasJustMovedHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking())
					return spriteList[7];
				else
					return spriteList[3];
			}
			else {
				if (this.isDucking())
					return spriteList[6];
				else
					return spriteList[2];
			}
		}
		else {
			if (this.isDucking())
				return spriteList[1];
			else
				return spriteList[0];
		}
	}
	
	/**
	 * @return	The maximum of the velocity of the alien while ducking in the x-direction.
	 * 
	 */
	@Basic
	@Immutable
	private double getMaxDuckingVelocity() {
		return this.maxDuckingVelocity;
	}
	
	/**
	 * @return	The secondary direction if the alien is moving in two directions at the same time.
	 * 
	 */
	private Direction getSecondaryDirection() {
		return this.secondaryDirection;
	}
	
	/**
	 * Set the secondary direction (if the alien is moving in two directions at the same time) to the 
	 * given direction.
	 * 
	 * @param direction
	 */
	private void setSecondaryDirection(Direction direction){
		this.secondaryDirection = direction;
	}
	
	/**
	 * @param 	dt
	 * 			The period of time dt for which the new period of time needs to be calculated.
	 * @return	The new period of time dt based on the current velocity and acceleration of the alien
	 * 			in this world. (used for accurate collision detection).
	 */
	private double getNewDt(double dt){
		double velocity = Math.sqrt(Math.pow((this.getHorizontalVelocity() 
				- this.getHorizontalAcceleration()), 2) + 
				Math.pow((this.getVerticalVelocity()), 2));
		double acceleration = Math.sqrt(Math.pow(this.getHorizontalAcceleration(), 2) + 
				Math.pow(this.getVerticalAcceleration(), 2));
		double newDt = 0.01 / (velocity + (acceleration * dt));
		if ((velocity + (acceleration * dt)) == 0)
			return 0.01;
		else {
			return newDt;
		}
	}
	
	/**
	 * Check whether the alien can jump at the given position or not.
	 * 
	 * @param	position
	 * @return	True if and only if the tile above the alien is passable.
	 */
	public boolean isValidJumpingPosition(int[] position) {
		int pixelsTouching = 0;
		for (int bottomPixel: this.getHorizontalPixels()) {
			if (this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(bottomPixel, 
					position[1])))
				pixelsTouching += 1;
		}
		return (pixelsTouching > 0);
	}
	
	/**
	 * Check whether the alien is jumping or not.
	 * 
	 * @return	True if and only if the alien's vertical velocity is bigger than 0.
	 */
	public boolean isJumping() {
		return (this.getVerticalVelocity() > 0);
	}
	
	/**
	 * Check whether the alien is in the air or not.
	 * 
	 * @return	True if and only if the alien's vertical velocity is not 0.
	 */
	private boolean isAirborne() {
		return (!(this.getVerticalVelocity() == 0));
	}
	
	/**
	 * Check whether the alien is ducking or not.
	 * 
	 * @return	True if and only if the alien's maximum horizontal velocity is equal to the maximum
	 * 			ducking velocity.
	 */
	public boolean isDucking() {
		return (this.getMaxHorizontalVelocity() == this.getMaxDuckingVelocity());
	}
	
	/**
	 * Check whether the alien can stand up straight again.
	 * 
	 * @return	True if and only if the tile at the height of where the alien's top perimeter would be 
	 * 			when ducking is passable.
	 */
	public boolean canEndDuck() {
		for (int horizontalPixel: this.getHorizontalPixels()) {
			if (this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
					horizontalPixel, this.getPosition()[1] + this.spriteList[0].getHeight())))
				return false;
		}
		return true;
	}
	
	/**
	 * Check whether the alien is touching the target tile or not.
	 * 
	 * @return	True if and only if the alien's tile is touching the target position's tile.
	 */
	protected boolean touchTargetTile() {
		return (((((this.getPosition()[0] + this.getCurrentSprite().getWidth()) 
				/ this.getWorld().getTileLength()) == this.getWorld().getTargetTile()[0])
				&& (((this.getPosition()[1] + this.getCurrentSprite().getWidth()) 
						/ this.getWorld().getTileLength()) == this.getWorld().getTargetTile()[1]))
						|| ((((this.getPosition()[0] + this.getCurrentSprite().getWidth()) 
								/ this.getWorld().getTileLength()) == this.getWorld().getTargetTile()[0])
								&& (((this.getPosition()[1] / this.getWorld().getTileLength())
										== this.getWorld().getTargetTile()[1]))));
	}
	

	/**
	 * Make the alien begin to jump (move in the positive y-direction).
	 */
	public void startJump() {
		if (this.isValidJumpingPosition(this.getPosition())) {
			this.setVerticalVelocity(this.normalVerticalVelocity);
			this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
		}
	}
	
	/**
	 * End the jumping of the alien.
	 */
	public void endJump() {
		if (this.getVerticalVelocity() > 0) {
			this.setVerticalVelocity(0);
		}
	}
	
	/**
	 * Make the alien begin to duck (move in the negative y-direction).
	 */
	public void startDuck() {
		this.setHorizontalAcceleration(0);
		this.setMaxHorizontalVelocity(this.getMaxDuckingVelocity());
	}
	
	/**
	 * End the ducking of the alien.
	 */
	public void endDuck() {
		this.setMaxHorizontalVelocity(this.maxRunningVelocity);
	}
	
	/**
	 * Make the alien begin to move in the x-direction to the left (negative x-direction) 
	 * or to the right (positive x-direction) depending on the given direction and set the secondary
	 * direction for if the alien would want to do two horizontal movements at the same time. Also 
	 * limit the velocity in the x-direction to its maximum.
	 * 
	 * @param	direction
	 * 			The given direction in which the alien has to move.
	 */
	public void startMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if (direction == Direction.RIGHT) {
			this.setLastDirection(Direction.RIGHT);
			if (this.isMovingLeft())
				this.setSecondaryDirection(Direction.LEFT);
			if (this.getHorizontalVelocity() <= this.getMaxHorizontalVelocity()) { 
				this.setHorizontalVelocity(this.getNormalHorizontalVelocity());
				this.setHorizontalAcceleration(this.getNormalHorizontalAcceleration());
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		else {
			this.setLastDirection(Direction.LEFT);
			if (this.isMovingRight())
				this.setSecondaryDirection(Direction.RIGHT);
			if (this.getHorizontalVelocity() >= -this.getMaxHorizontalVelocity()) { 
				this.setHorizontalVelocity(-this.getNormalHorizontalVelocity());
				this.setHorizontalAcceleration(-this.getNormalHorizontalAcceleration());
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			}
		}
	}

	/**
	 * Make the alien stop moving in the given horizontal direction.
	 *
	 * @param	direction
	 * 			The horizontal direction in which the alien was moving.
	 */
	@Override
	public void endMoveHorizontally(Direction direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0) 
				&& (!(this.getSecondaryDirection() == Direction.LEFT))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == Direction.RIGHT) && (this.getHorizontalVelocity() > 0) 
				&& (this.getSecondaryDirection() == Direction.LEFT)) {
			this.setHorizontalAcceleration(-this.getHorizontalAcceleration());
		}
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0)
				&& (!(this.getSecondaryDirection() == Direction.RIGHT))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == Direction.LEFT) && (this.getHorizontalVelocity() < 0) 
				&& (this.getSecondaryDirection() == Direction.RIGHT)) {
			this.setHorizontalAcceleration(-this.getHorizontalAcceleration());
		}
	}
	
	/**
	 * Return the new x-position in the game world of the alien after it moved horizontally. Also limit
	 * the alien's velocity to the maximum and stop the alien from moving if the tile to which it wants 
	 * to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the horizontal velocity is bigger than or equal to the maximum, the new horizontal 
	 * 			acceleration is set to 0 and the velocity to the maximum in the positive of negative 
	 * 			direction, depending on the direction the alien was going in.
	 * 			| if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity())
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	if (this.getHorizontalVelocity() < 0)
	 * 			|		(new this).setHorizontalVelocity(-this.getMaxHorizontalVelocity())
	 * 			|	else
	 * 			|		(new this).setHorizontalVelocity(this.getMaxHorizontalVelocity())
	 * @post	If the alien's x-position is the smaller than or equal to the minimum and the alien is 
	 * 			trying to move in the negative x-direction, the new horizontal velocity and acceleration 
	 * 			are set to 0 and the new x-position is set to the minimum.
	 * 			| if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the alien's x-position is the bigger than or equal to the maximum and the alien is 
	 * 			trying to move in the positive x-direction, the horizontal velocity and acceleration are
	 * 			set to 0 and the x-position is set to the maximum.
	 * 			| if ((this.getPosition()[0] >= (this.maxPositionX)) && 
	 * 			|		(this.getHorizontalVelocity() > 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the alien's horizontal velocity is smaller than 0 and the tile to the left of the
	 * 			alien is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0], this.getPosition()[1])))	
	 * 			|				&& (this.getHorizontalVelocity() < 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the alien's horizontal velocity is bigger than 0 and the tile to the right of the
	 * 			alien is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0] + this.getCurrentSprite().getWidth(), 
	 * 			|				this.getPosition()[1]))) && (this.getHorizontalVelocity() > 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	The new horizontal velocity is set to the sum of the current horizontal velocity and the 
	 * 			product of the current horizontal acceleration and dt.
	 * 			| (new this).setHorizontalVelocity(this.getHorizontalVelocity() + 
	 * 			|	this.getHorizontalAcceleration()*dt)
	 * @return	newPositionX
	 * 			The alien's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt 
	 * 			|	- this.getHorizontalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	private double horizontalMovement(double dt) throws IllegalArgumentException {
		if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity()) {
			this.setHorizontalAcceleration(0);
			if (this.getHorizontalVelocity() < 0) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} 
			else
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt 
				- this.getHorizontalAcceleration() * Math.pow(dt, 2) 
				+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}

	/**
	 * Return the new y-position in the game world of the alien after it moved vertically. Also
	 * stop the alien from moving if the tile to which he wants to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post	If the given y-position is the bigger than or equal to the maximum and the alien is 
	 * 			trying to move in the positive y-direction, the vertical velocity is set to 0 and the 
	 * 			acceleration is set to -10.
	 * 			| if ((this.getPosition()[1] >= this.maxPositionY) && 
	 * 			|		(this.getVerticalVelocity() > 0))
	 * 			|	(new this).setVerticalVelocity(0)
	 * @post 	If the alien's vertical velocity is smaller than 0 and the tile beneath the alien is not
	 * 			passable, the alien's new vertical acceleration and velocity are set to 0.
	 * 			| 		if ((this.getVerticalVelocity() < 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1]))))
	 * 			|			this.setVerticalAcceleration(0)
	 * 			|			this.setVerticalVelocity(0)
	 * @post 	If the alien's vertical velocity is bigger than 0 and the tile above the alien is not
	 * 			passable, the alien's new vertical velocity is set to 0.
	 * 			| 		if ((this.getVerticalVelocity() > 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight()))))
	 * 			|			this.setVerticalVelocity(0)
	 * @post	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| (new this).setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The alien's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */ 
	private double verticalMovement(double dt) throws IllegalArgumentException {
		this.setVerticalVelocity(this.getVerticalVelocity() + this.getVerticalAcceleration() * dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionY;
	}
		
	/**
	 * The actions the alien has to take when colliding with another game object.
	 * 
	 * @param newDt
	 * @param oldPosition
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		for (Plant plant: this.getWorld().getPlants()) {
			if (this.collidesWith(plant)) {
				if (this.getNbHitPoints() < 500)
					this.changeNbHitPoints(50);
			}
		}
		for (Shark shark: this.getWorld().getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWithTop(shark))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0);
				this.setPosition(oldPosition[0], oldPosition[1]);
				/*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
					}
				else {
					if (this.getTimeImmune() <= 0.60) 
						this.setTimeImmune(this.getTimeImmune() + newDt);
					else {
						this.makeVulnerable();
						this.setTimeImmune(0);
					}
				}
			}
		}
		for (Slime slime: this.getWorld().getSlimes()) {
			if ((this.collidesWith(slime)) && (!this.bottomCollidesWithTop(slime))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0);
				this.setPosition(oldPosition[0], oldPosition[1]);
				/*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
					}
				else {
					if (this.getTimeImmune() <= 0.60) 
						this.setTimeImmune(this.getTimeImmune() + newDt);
					else {
						this.makeVulnerable();
						this.setTimeImmune(0);
					}
				}
			}
		}
	}
	
	/**
	 * The actions the alien has to take when in a fluid.
	 * 
	 * @param newDt
	 */
	private void isInFluidActions(double newDt) {
		if (this.isInWater()) {
			if (this.getTimeInWater() >= 0.2) {
				this.changeNbHitPoints(-2);
				this.setTimeInWater(0);
			}
			else 
				this.setTimeInWater(this.getTimeInWater() + newDt);
		}
		else if (this.isInMagma()) {
			this.setTimeInMagma(this.getTimeInMagma() + newDt);
			if (!this.isImmuneForMagma()) {
				this.changeNbHitPoints(-50);
				this.makeImmuneForMagma();
			}
			else {
				if (this.getTimeImmuneForMagma() < 0.20) 
					this.setTimeImmuneForMagma(this.getTimeImmuneForMagma() + newDt);
				else {
					this.makeVulnerableForMagma();
					this.setTimeImmuneForMagma(0);
				}
			}
		}
		else {
			this.setTimeInWater(0);
			this.setTimeInMagma(0);
		}
	}
	
	/**
	 * Set the new position on the screen of the alien to new position in the game world, limit the
	 * alien's position to the minimum (in x-direction) and the maximum (both in x- and y-direction),
	 * block the alien's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally and the time not moving, increase the number of hitpoints if the alien 
	 * collides with a plant, block the alien's movement, decrease its hitpoints and make it immune
	 * if it collides with an enemy (only when it does not collide with its bottom perimeter), 
	 * decrease the number of hitpoints if the alien is in water or magma, and make it immune for 
	 * magma if it falls into magma.
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect //TODO
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		double sumDt = 0;
		while (sumDt < dt) {
			double newDt = this.getNewDt(dt);
			int[] oldPosition = this.getPosition();
			if (!this.isValidDt(newDt))
				throw new IllegalArgumentException("The given period of time dt is invalid!");
			if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
					|| (this.crossImpassableTop()) || (this.crossImpassableRight()))  {
				this.crossImpassableActions(oldPosition);
			}
			this.collidesWithActions(newDt, oldPosition);
			if (!this.isMovingHorizontally()) {
			    this.setTimeStalled(this.getTimeStalled() + newDt);
				this.timeMovingHorizontally = 0;
			}
			if (this.isMovingHorizontally()) {
				this.setTimeStalled(0);
				this.timeMovingHorizontally += newDt;
			}
			if ((this.isInWater()) || (this.isInMagma())) {
				this.isInFluidActions(newDt);
			}
			if ((this.canEndDuck()) && (this.timeForcedDuck > 0)) {
				this.endDuck();
				this.timeForcedDuck = 0;
			}
			if (!this.canEndDuck())
				this.timeForcedDuck += newDt;
			if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
					&& (!this.crossImpassableTop()) && (!this.crossImpassableRight())) {
				if (!this.touchImpassableBottom()) {
					this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
				}
				this.setPosition(oldPosition[0] + 100*this.horizontalMovement(newDt),
					oldPosition[1] + 100*this.verticalMovement(newDt));
			}
			sumDt += newDt;
		}
	}
}
