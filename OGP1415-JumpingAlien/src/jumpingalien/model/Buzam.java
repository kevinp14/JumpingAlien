package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import jumpingalien.util.*;


//TODO: Klassendiagram maken en meenemen naar verdediging

/**
 * A class of Buzam aliens involving some maximums for the horizontal velocity depending on its current
 * actions, a list of sprites or images of the alien that are displayed depending on its current actions, 
 * the time the alien is moving horizontally or standing still, the last direction in which the alien
 * was moving and the secondary direction in which the alien is moving (only when performing multiple
 * movements at the same time), the number of hitpoints, methods to move the alien horizontally as 
 * well as vertically (jumping, falling and ducking), methods to inspect the behavior of the alien 
 * and a method to advance time and adapt the time depending characteristics based on the period of 
 * time passed.
 * 
 * @invar	The direction in which a game object is ordered to move in must be a valid one.
 * 			| isValidMovingDirection(direction)
 * @invar	The normal/initial velocity will never be smaller than 1.
 * 			| this.getNormalHorizontalVelocity() >= 1
 * @invar	The maximum horizontal velocity will never be smaller than the normal/initial velocity
 * 			| this.getMaximumHorizontalVelocity() >= this.getNormalHorizontalVelocity()
 * @invar	The absolute value of the horizontal acceleration will always be bigger than 0.
 * 			| Math.abs(this.getHorizontalAcceleration()) > 0
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 *
 */
public class Buzam extends GameObject {
	private Sprite[] spriteList;
	private double timeMovingHorizontally;
	private double timeForcedDuck;
	
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
	 * @effect	The new normal horizontal velocity is set to 1.
	 * 			| this.setNormalHorizontalVelocity(1)
	 * @effect	The new normal horizontal acceleration is set to 0.9.
	 * 			| this.setNormalHorizontalAcceleration(0.9)
	 * @effect	The new maximum horizontal velocity is set to 3.
	 * 			| this.setMaxHorizontalVelocity(3)
	 * @effect	The new normal vertical velocity is set to 8.
	 * 			| setNormalVerticalVelocity(8)
	 * @post	The new time moving horizontally is set to 0.
	 * 			| (new this).timeMovingHorizontally = 0
	 * @post	The new time forced ducking is set to 0.
	 * 			| (new this).timeForcedDuck = 0
	 * @effect	The number of hitpoints is increased with 100.
	 * 			| this.changeNbHitPoints(100)
	 */
	public Buzam(double positionX, double positionY, Sprite[] spriteList, Program program) {
		super(positionX, positionY, spriteList, program);
		assert (isValidSpriteList(spriteList));
		this.spriteList = spriteList;
		this.setNormalHorizontalVelocity(1);
		this.setNormalHorizontalAcceleration(0.9);
		this.setMaxHorizontalVelocity(3);
		this.setNormalVerticalVelocity(8);
	    this.timeMovingHorizontally = 0;
	    this.timeForcedDuck = 0;
	    this.changeNbHitPoints(100);
	}

	/**
	 * @pre	The alien's sprite list should be valid.
	 * 		| isValidSpriteList(this.spriteList)
	 * @post	The returned sprite should be in the list.
	 * 			| spriteList[x] with x >= 0 and x <= spriteList.length
	 * @return 	The current sprite image for the alien, based on its current actions.
	 */
	@Basic 
	@Immutable
	@Override
	public Sprite getCurrentSprite() {
		assert (this.isValidSpriteList(this.spriteList));
		if (this.isMovingHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking()) {
					return spriteList[7];
				}
				if (this.isAirborne()) {
					return spriteList[5];
				}
				else {
					if (!Util.fuzzyGreaterThanOrEqualTo(((1000*this.timeMovingHorizontally) / 75)
							,11)) {
						return spriteList[(int)(19 + ((1000*this.timeMovingHorizontally) / 75))];
					}
					else {
						this.timeMovingHorizontally = 0;
					}
				}
			}
			else {
				if (this.isDucking()) {
					return spriteList[6];
				}
				if (this.isAirborne()) {
					return spriteList[4];
				}
				else {
					if (!Util.fuzzyGreaterThanOrEqualTo(((1000*this.timeMovingHorizontally) / 75)
							,11)) {
						return spriteList[(int) (8 + ((1000*this.timeMovingHorizontally) / 75))];
					}
					else {
						this.timeMovingHorizontally = 0;
					}
				}
			}
		}
		if (this.hasJustMovedHorizontally()) {
			if (this.isMovingLeft()) {
				if (this.isDucking()) {
					return spriteList[7];
				}
				else {
					return spriteList[3];
				}
			}
			else {
				if (this.isDucking()) {
					return spriteList[6];
				}
				else {
					return spriteList[2];
				}
			}
		}
		else {
			if (this.isDucking()) {
				return spriteList[1];
			}
			else {
				return spriteList[0];
			}
		}
	}
	
	/**
	 * @param 	dt
	 * 			The period of time dt for which the new period of time needs to be calculated.
	 * @return	The new period of time dt based on the current velocity and acceleration of the alien
	 * 			in this world. (used for accurate collision detection).
	 */
	private double getNewDt(double dt) {
		double velocity = Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2) + 
				Math.pow(this.getVerticalVelocity(), 2));
		double acceleration = Math.sqrt(Math.pow(this.getHorizontalAcceleration(), 2) + 
				Math.pow(this.getVerticalAcceleration(), 2));
		double newDt = 0.01 / (velocity + (acceleration * dt));
		if (Util.fuzzyEquals((velocity + (acceleration * dt)), 0)) {
			return 0.01;
		}
		else { 
			return newDt;
		}
	}
	
	/**
	 * Check whether the alien can jump at the given position or not.
	 * 
	 * @param	position
	 * @return	True if and only if the tile beneath the alien is not passable.
	 */
	public boolean isValidJumpingPosition(int[] position) {
		for (int horizontalPixel: this.getHorizontalPixels()) {
			if (this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(horizontalPixel, 
					position[1]))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check whether the alien is airborne or not.
	 * 
	 * @return	True if and only if the alien's vertical velocity is not 0.
	 */
	private boolean isAirborne() {
		return (!(Util.fuzzyEquals(this.getVerticalVelocity(), 0)));
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
					horizontalPixel, this.getPosition()[1] + this.spriteList[0].getHeight()))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Make the alien begin to move in the x-direction to the left (negative x-direction) 
	 * or to the right (positive x-direction) depending on the given direction and set the secondary
	 * direction for if the alien would want to do two horizontal movements at the same time. Also 
	 * limit the velocity in the x-direction to its maximum.
	 * 
	 * @param	direction
	 * 			The given direction in which the alien has to move.
	 * @pre	The given direction should be valid.
	 * 		| isValidMovingDirection(direction)
	 * @post	The new last direction should be valid.
	 * 			| isValidMovingDirection((new this).getLastDirection())
	 * @post	The new secondary direction should be valid.
	 * 			| isValidMovingDirection((new this).getSecondaryDirection())
	 */
	public void startMoveHorizontally(SelfMadeDirection direction) {
		assert (isValidMovingDirection(direction));
		if (direction == SelfMadeDirection.RIGHT) {
			this.setLastDirection(SelfMadeDirection.RIGHT);
			if (Util.fuzzyLessThanOrEqualTo(this.getHorizontalVelocity(), 
					this.getMaxHorizontalVelocity())) {
				this.setHorizontalVelocity(this.getNormalHorizontalVelocity());
				this.setHorizontalAcceleration(this.getNormalHorizontalAcceleration());
			}
			else {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		else {
			this.setLastDirection(SelfMadeDirection.LEFT);
			if (Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(),
					(-this.getMaxHorizontalVelocity()))) {
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
	 * @pre	The given direction should be valid.
	 * 		| isValidMovingDirection(direction)
	 */
	@Override
	public void endMoveHorizontally(SelfMadeDirection direction) {
		assert (isValidMovingDirection(direction));
		if ((direction == SelfMadeDirection.RIGHT) 
				&& (!Util.fuzzyLessThanOrEqualTo(this.getHorizontalVelocity(), 0))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == SelfMadeDirection.RIGHT) 
				&& (!Util.fuzzyLessThanOrEqualTo(this.getHorizontalVelocity(), 0))) {
			this.setHorizontalAcceleration(-this.getHorizontalAcceleration());
		}
		if ((direction == SelfMadeDirection.LEFT) 
				&& (!Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(), 0))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((direction == SelfMadeDirection.LEFT) 
				&& (!Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(), 0))) {
			this.setHorizontalAcceleration(-this.getHorizontalAcceleration());
		}
	}
	
	/**
	 * Return the new x-position in the game world of the alien after it moved horizontally. Also limit
	 * the alien's velocity to the maximum.
	 * 
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	If the horizontal velocity is bigger than or equal to the maximum, the new horizontal 
	 * 			acceleration is set to 0 and the velocity to the maximum in the positive of negative 
	 * 			direction, depending on the direction the alien was going in.
	 * 			| if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity())
	 * 			|	this.setHorizontalAcceleration(0)
	 * 			|	if (this.getHorizontalVelocity() < 0)
	 * 			|		this.setHorizontalVelocity(-this.getMaxHorizontalVelocity())
	 * 			|	else
	 * 			|		this.setHorizontalVelocity(this.getMaxHorizontalVelocity())
	 * @effect	The new horizontal velocity is set to the sum of the current horizontal velocity and the 
	 * 			product of the current horizontal acceleration and dt.
	 * 			| this.setHorizontalVelocity(this.getHorizontalVelocity() + 
	 * 			|	this.getHorizontalAcceleration() * dt)
	 * @return	newPositionX
	 * 			The alien's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt 
	 * 			|	- this.getHorizontalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2
	 */
	private double horizontalMovement(double dt) {
		if (Util.fuzzyGreaterThanOrEqualTo(Math.abs(this.getHorizontalVelocity()),
				this.getMaxHorizontalVelocity())) {
			this.setHorizontalAcceleration(0);
			if (!Util.fuzzyGreaterThanOrEqualTo(this.getHorizontalVelocity(), 0)) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} 
			else
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt 
				+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}

	/**
	 * Return the new y-position in the game world of the alien after it moved vertically.
	 * 
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| this.setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The alien's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
	 */
	private double verticalMovement(double dt) {
		this.setVerticalVelocity(this.getVerticalVelocity() + this.getVerticalAcceleration() * dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				+ this.getVerticalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionY;
	}
		
	/**
	 * The actions the alien has to take when colliding with another game object.
	 * 
	 * @param	newDt
	 * 			The period of time on which collision has to be detected.
	 * @param	oldPosition
	 * 			The alien's old position.
	 * @effect	If the alien collides with a plant in its game world and its current number of 
	 * 			hitpoints is smaller than 500, it has to gain 50 hitpoints.
	 * 			| for (Plant plant: this.getWorld().getPlants())
	 * 			|	if ((this.collidesWith(plant)) && (!plant.isDead()) && (this.getNbHitPoints() < 500))
	 * 			|			this.changeNbHitPoints(50)
	 * 			|			plant.changeNbHitPoints(-1)
	 * @effect	If the alien collides with a shark in its game world, its movement is blocked if it
	 * 			is trying to move in the direction in which it collided, and it loses 50 hitpoints if
	 * 			it didn't fall on top of the shark.
	 * 			| for (Shark shark: this.getWorld().getSharks())
	 * 			|	if (this.collidesWith(shark)) 
	 * 			|		this.collisionBlockMovement(shark, oldPosition, newDt)
	 * 			|		if ((!this.bottomCollidesWith(shark)) && (!this.isImmune()))
	 * 			|			this.changeNbHitPoints(-50)
	 * @effect	If the alien collides with a slime in its game world, its movement is blocked if it
	 * 			is trying to move in the direction in which it collided, and it loses 50 hitpoints if
	 * 			it didn't fall on top of the slime.
	 * 			| for (Slime slime: this.getWorld().getSlimes())
	 * 			|	if (this.collidesWith(slime)) 
	 * 			|		this.collisionBlockMovement(slime, oldPosition, newDt)
	 * 			|		if ((!this.bottomCollidesWith(slime)) && (!this.isImmune()))
	 * 			|			this.changeNbHitPoints(-50)
	 * @effect	If buzam collides with mazub, its movement is blocked if it is trying to move in 
	 * 			the direction in which it collided, and it loses 50 hitpoints if it didn't fall on 
	 * 			top of mazub.
	 * 			| if (this.collidesWith(alien)) 
	 * 			|	this.collisionBlockMovement(alien, oldPosition, newDt)
	 * 			|	if ((!this.bottomCollidesWith(alien)) && (!this.isImmune()))
	 * 			|		this.changeNbHitPoints(-50)
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		for (Plant plant: this.getWorld().getPlants()) {
			if ((this.collidesWith(plant)) && (!plant.isDead()) && (this.getNbHitPoints() < 500)) {
					this.changeNbHitPoints(50);
					plant.changeNbHitPoints(-1);
			}
		}
		for (Shark shark: this.getWorld().getSharks()) {
			if (this.collidesWith(shark)) {
				this.collisionBlockMovement(shark, oldPosition, newDt);
				if (!this.bottomCollidesWith(shark)) {
					if (!this.isImmune()) {
						this.changeNbHitPoints(-50);
						this.makeImmune();
						}
					else {
						if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
							this.setTimeImmune(this.getTimeImmune() + newDt);
						}
						else {
							this.makeVulnerable();
							this.setTimeImmune(0);
						}
					}
				}
			}
		}
		for (Slime slime: this.getWorld().getSlimes()) {
			if (this.collidesWith(slime)) {
				this.collisionBlockMovement(slime, oldPosition, newDt);
				if (!this.bottomCollidesWith(slime)) {
					if (!this.isImmune()) {
						this.changeNbHitPoints(-50);
						this.makeImmune();
						}
					else {
						if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
							this.setTimeImmune(this.getTimeImmune() + newDt);
						}
						else {
							this.makeVulnerable();
							this.setTimeImmune(0);
						}
					}
				}
			}
		}
		Mazub alien = this.getWorld().getMazub();
		if (this.collidesWith(alien)) {
			this.collisionBlockMovement(alien, oldPosition, newDt);
			if (!this.bottomCollidesWith(alien)) {
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
					}
				else {
					if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
						this.setTimeImmune(this.getTimeImmune() + newDt);
					}
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
	 * @param	newDt
	 * 			The period of time with which the time is advanced.
	 * @effect	If the alien is in water, its hitpoints are reduced with 2 every 0.2 seconds.
	 * 			| if ((this.isInWater()) && (this.getTimeInWater() >= 0.2)) 
	 * 			|		this.changeNbHitPoints(-2)
	 * @effect	If the alien is in magma, its hitpoints are reduced with 50 upon contact and every 
	 * 			0.2 seconds. However, the alien can not lose more than 50 hitpoints every 0.2 seconds.
	 * 			| if (this.isInMagma()) 
	 * 			|		this.changeNbHitPoints(-50)
	 */
	private void isInFluidActions(double newDt) {
		if (this.isInWater()) {
			if (Util.fuzzyGreaterThanOrEqualTo(this.getTimeInWater(), 0.2)) {
				this.changeNbHitPoints(-2);
				this.setTimeInWater(0);
			}
			else {
				this.setTimeInWater(this.getTimeInWater() + newDt);
			}
		}
		else if (this.isInMagma()) {
			if (Util.fuzzyGreaterThanOrEqualTo(this.getTimeInMagma(), 0.2)) {
				this.changeNbHitPoints(-50);
				this.setTimeInMagma(0);
			}
			else {
				this.setTimeInMagma(this.getTimeInMagma() + newDt);
			}
		}
		else {
			this.setTimeInWater(0);
			this.setTimeInMagma(0);
		}
	}
	
	/**
	 * Set the new position on the screen of the alien to new position in the game world,
	 * block the alien's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally and the time not moving, take the right actions when colliding with an enemy,
	 * when trying to cross an impassable tile and when in a fluid.
	 * 
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect	If the alien is trying to cross an impassable tile, take the corresponding actions.
	 * 			| if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
	 * 			|	|| (this.crossImpassableTop()) || (this.crossImpassableRight()))
	 * 			|		this.crossImpassableActions(oldPosition)
	 * @effect	If the alien is not moving horizontally the time stalled is increased with newDt.
	 * 			| if (!this.isMovingHorizontally()) 
	 * 			|	this.setTimeStalled(this.getTimeStalled() + newDt)
	 * @effect	If the alien is moving horizontally the time moving horizontally is increased with newDt.
	 * 			| if (this.isMovingHorizontally()) 
	 * 			|	this.timeMovingHorizontally += newDt
	 * @effect	If the alien is in a fluid, the corresponding actions are taken.
	 * 			| if ((this.isInWater()) || (this.isInMagma())) 
	 * 			|	this.isInFluidActions(newDt)
	 * @effect	If the alien can end its ducking and it was forced to duck, its ducking is ended.
	 * 			| if ((this.canEndDuck()) && (this.timeForcedDuck > 0))
	 * 			|	this.endDuck()
	 * @effect	If the alien is not standing on an impassable tile, its vertical acceleration is set
	 * 			to the normal one.
	 * 			| if (!this.touchImpassableBottom())
	 * 			|	this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 * @effect	If the alien is not trying to cross an impassable tile, its position is increased
	 * 			by 100 times the horizontal/vertical movement.
	 * 			| if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
	 * 			|	&& (!this.crossImpassableTop()) && (!this.crossImpassableRight()))
	 * 			|		this.setPosition(oldPosition[0] + 100*this.horizontalMovement(newDt),
	 * 			|			oldPosition[1] + 100*this.verticalMovement(newDt))
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if ((this.getProgram() == null) || (this.programRunning == true)){
			if (!this.isValidDt(dt)) {
				throw new IllegalArgumentException("The given period of time dt is invalid!");
			}
			double sumDt = 0;
			while (!Util.fuzzyGreaterThanOrEqualTo(sumDt, dt)) {
				double newDt = this.getNewDt(dt);
				int[] oldPosition = this.getPosition();
				double[] oldPositionAsDouble = this.getPositionAsDouble();
				if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
						|| (this.crossImpassableTop()) || (this.crossImpassableRight()))  {
					this.crossImpassableActions(oldPosition);
				}
				this.collidesWithActions(newDt, oldPosition);
				if ((this.isInWater()) || (this.isInMagma())) {
					this.isInFluidActions(newDt);
				}
				if (!this.isMovingHorizontally()) {
				    this.setTimeStalled(this.getTimeStalled() + newDt);
					this.timeMovingHorizontally = 0;
				}
				if (this.isMovingHorizontally()) {
					this.setTimeStalled(0);
					this.timeMovingHorizontally += newDt;
				}
				if ((this.canEndDuck()) && (!Util.fuzzyLessThanOrEqualTo(this.timeForcedDuck, 0))) {
					this.endDuck();
					this.timeForcedDuck = 0;
				}
				if (!this.canEndDuck()) {
					this.timeForcedDuck += newDt;
				}
				if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
						&& (!this.crossImpassableTop()) && (!this.crossImpassableRight())) {
					if (!this.touchImpassableBottom()) {
						this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
					}
					this.setPosition(oldPositionAsDouble[0] + 100 * this.horizontalMovement(newDt),
							oldPositionAsDouble[1] + 100 * this.verticalMovement(newDt));
				}
				sumDt += newDt;
			}
		}
		else{
			Thread t = new Thread(this.getProgram());
			t.start();
			this.programRunning = true;
		}
	}
}
