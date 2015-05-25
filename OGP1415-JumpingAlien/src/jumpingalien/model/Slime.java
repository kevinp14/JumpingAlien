package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

/**
 * A class of slimes involving the normal and maximum for the horizontal velocity, the slime's school,
 * the time the slime is moving horizontally, the number of hitpoints, a method to change the school
 * in which the slime is, a method to create a random moving time and direction and a method 
 * to advance time and adapt the time depending characteristics based on the period of time passed.
 * 
 * 
 * @invar
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 8.0
 *
 */
public class Slime extends GameObject {
	private School school;
	private double timeMovingHorizontally;

	/**
	 * @param 	positionX
	 * 			The position in the x-direction where the slime should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the slime should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the slime should look depending on its behavior.
	 * @param	school
	 * 			The school to which the slime has to be assigned.
	 * @effect	The new last direction is set to a random direction.
	 * 			| this.setLastDirection(this.getRandomDirection())
	 * @effect	The new normal horizontal velocity is set to 0.
	 * 			| this.setNormalHorizontalVelocity(0)
	 * @effect	The new normal horizontal acceleration is set to 0.7.
	 * 			| this.setNormalHorizontalAcceleration(0.7)
	 * @effect	The new maximum horizontal velocity is set to 2.5.
	 * 			| this.setMaxHorizontalVelocity(2.5)
	 * @post	The new time moving horizontally is set to 0.
	 * 			| (new this).timeMovingHorizontally = 0
	 * @post	The new school is set to the given one.
	 * 			| (new this).school = school
	 * @effect	The number of hitpoints is increased with 100.
	 * 			| this.changeNbHitPoints(100)
	 */
	public Slime (int positionX, int positionY, Sprite[] spriteList, School school, Program program) {
		super(positionX, positionY, spriteList, program);
	    this.setLastDirection(this.getRandomDirection());
		this.setNormalHorizontalVelocity(0);
		this.setNormalHorizontalAcceleration(0.7);
		this.setMaxHorizontalVelocity(2.5);
	    this.timeMovingHorizontally = 0;
		this.school = school;
		this.changeNbHitPoints(100);
		this.programRunning = false;
	}
	
	/**
	 * @return The current school of the given slime.
	 * 
	 */
	@Basic
	public School getSchool(){
		return this.school;
	}
	
	/**
	 * @param 	school
	 * 			The school to which the slime's school has to be set.
	 * @effect	The slime is removed from its old school.
	 * 			| this.school.removeSlimeFromSchool(this)
	 * @post	The slime's new school is set to the given one.
	 * 			| (new this).school = school
	 * @effect	The slime is added to the given school.
	 * 			| school.addSlimeToSchool(this)
	 */
	public void setSchool(School school){
		this.school.removeSlimeFromSchool(this);
		this.school = school;
		school.addSlimeToSchool(this);
	}
	
	/**
	 * @return	A random time between 2 and 6 seconds during which the slime has to move.
	 * 
	 */
	private double getRandomMovingTime() {
		Random rand = new Random();
		int movingTime = rand.nextInt(60);
		if (movingTime > 20) {
			return (((double)movingTime) / 10);
		}
		else {
			return (((double)(movingTime + 20)) / 10);
		}
	}
	
	/**
	 * @return A random direction (left or right) for the slime to move in.
	 * 
	 */
	private SelfMadeDirection getRandomDirection(){
		Random rand = new Random();
	    int directionNumber = rand.nextInt(2);
	    if (directionNumber == 0) {
	    	return SelfMadeDirection.LEFT;
	    }
	    else {
	    	return SelfMadeDirection.RIGHT;
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
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	If the horizontal velocity is bigger than or equal to the maximum, the new horizontal 
	 * 			acceleration is set to 0 and the velocity to the maximum in the positive of negative 
	 * 			direction, depending on the direction the slime was going in.
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
			else {
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
			}
		}
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt 
				+ this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}
	
	/**
	 * @param	dt
	 * 			The time passed dt.
	 * @effect	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| this.setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The slime's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2
	 */
	private double verticalMovement(double dt) {
		this.setVerticalVelocity(this.getVerticalVelocity() + this.getVerticalAcceleration()*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
		return newPositionY;
	}
	
	/**
	 * @param	newDt
	 * 			The period of time on which collision has to be detected.
	 * @param	oldPosition
	 * 			The slime's old position.
	 * @effect	If the slime collides with another slime in its game world, its movement is blocked. If 
	 * 			the other slime is not is this slime's school, this slime loses one hitpoint per slime
	 * 			in the other slime's school and every other slime in the other slime's school gains one
	 * 			hitpoint. 
	 * 			If this slimes school is smaller than the other slime's school, this slime
	 * 			is added to the other slime's school and all slimes in the other slime's school give
	 * 			one hitpoints to this slime. 
	 * 			If this slimes school is larger than the other slime's school, the other slime
	 * 			is added to this slime's school and all slimes in this slime's school give
	 * 			one hitpoints to the other slime. 
	 * 			| for (Slime slime: this.getWorld().getSlimes())
	 * 			|	if (this.collidesWith(slime))
	 * 			|		this.collisionBlockMovement(slime, oldPosition, newDt)
	 * 			|		if (!(this.getSchool() == slime.getSchool()))
	 * 			|			for (Slime otherSlime: this.getSchool().getSlimes())
	 * 			|				if (!(otherSlime == this))
	 * 			|					this.changeNbHitPoints(-1)
	 * 			|					otherSlime.changeNbHitPoints(1)
	 * 			|			if (this.getSchool().getSize() < slime.getSchool().getSize())
	 * 			|				this.setSchool(slime.getSchool())
	 * 			|				for (Slime otherSlime: slime.getSchool().getSlimes())
	 * 			|					if (!(otherSlime == this))
	 * 			|						otherSlime.changeNbHitPoints(-1)
	 * 			|						this.changeNbHitPoints(1)
	 * 			|			else
	 * 			|				slime.setSchool(this.getSchool())
	 * 			|				for (Slime otherSlime: this.getSchool().getSlimes())
	 * 			|					if (!(otherSlime == slime))
	 * 			|						otherSlime.changeNbHitPoints(-1)
	 * 			|						slime.changeNbHitPoints(1)
	 * @effect	If the slime collides with a shark in its game world, its movement is blocked if it
	 * 			is trying to move in the direction in which it collided, and it loses 50 hitpoints.
	 * 			| for (Shark shark: this.getWorld().getSharks())
	 * 			|	if (this.collidesWith(shark)) 
	 * 			|		this.collisionBlockMovement(shark, oldPosition, newDt)
	 * 			|		if ((!this.bottomCollidesWith(shark)) && (!this.isImmune()))
	 * 			|			this.changeNbHitPoints(-50)
	 * 			|			for (Slime slime: this.getSchool().getSlimes())
	 * 			|				if (!(slime == this))
	 * 			|					slime.changeNbHitPoints(-1);
	 * @effect	If the slime collides with the alien in its game world, its movement is blocked if it
	 * 			is trying to move in the direction in which it collided, and it loses 50 hitpoints.
	 * 			| if (this.collidesWith(alien))
	 * 			|		this.collisionBlockMovement(alien, oldPosition, newDt)
	 * 			|		if ((!this.bottomCollidesWith(alien)) && (!this.isImmune()))
	 * 			|			this.changeNbHitPoints(-50)
	 * 			|			for (Slime slime: this.getSchool().getSlimes())
	 * 			|				if (!(slime == this))
	 * 			|					slime.changeNbHitPoints(-1)
	 * @effect	If the slime collides with buzam, its movement is blocked if it is trying to move in 
	 * 			the direction in which it collided, and it loses 50 hitpoints if it didn't fall on 
	 * 			top of bazum.
	 * 			| if (this.collidesWith(bazum)) 
	 * 			|	this.collisionBlockMovement(bazum, oldPosition, newDt)
	 * 			|	if ((!this.bottomCollidesWith(bazum)) && (!this.isImmune()))
	 * 			|		this.changeNbHitPoints(-50)
	 * 			|		for (Slime slime: this.getSchool().getSlimes())
	 * 			|			if (!(slime == this))
	 * 			|				slime.changeNbHitPoints(-1)
	 */
	private void collidesWithActions(double newDt, int[] oldPosition) {
		if (this.getWorld() != null){
			for (Slime slime: this.getWorld().getSlimes()) {
				if (this.collidesWith(slime)) {
					this.collisionBlockMovement(slime, oldPosition, newDt);
					if (!(this.getSchool() == slime.getSchool())) {
						for (Slime otherSlime: this.getSchool().getSlimes()) {
							if (!(otherSlime == this)) {
								this.changeNbHitPoints(-1);
								otherSlime.changeNbHitPoints(1);
							}
						}
						if (this.getSchool().getSize() < slime.getSchool().getSize()) {
							this.setSchool(slime.getSchool());
							for (Slime otherSlime: slime.getSchool().getSlimes()) {
								if (!(otherSlime == this)) {
									otherSlime.changeNbHitPoints(-1);
									this.changeNbHitPoints(1);
								}
							}
						}
						else {
							slime.setSchool(this.getSchool());
							for (Slime otherSlime: this.getSchool().getSlimes()) {
								if (!(otherSlime == slime)) {
									otherSlime.changeNbHitPoints(-1);
									slime.changeNbHitPoints(1);
								}
							}
						}
					}
				}
			}
			for (Shark shark: this.getWorld().getSharks()) {
				if (this.collidesWith(shark)) {
					this.collisionBlockMovement(shark, oldPosition, newDt);
					if (!this.bottomCollidesWith(shark)) {
						if (!this.isImmune()) {
							this.changeNbHitPoints(-50);
							for (Slime slime: this.getSchool().getSlimes()) {
								if (!(slime == this)) {
									slime.changeNbHitPoints(-1);
								}
							}
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
						for (Slime slime: this.getSchool().getSlimes()) {
							if (!(slime == this)) {
								slime.changeNbHitPoints(-1);
							}
						}
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
			Buzam buzam = this.getWorld().getBuzam();
			if (buzam != null){
				if (this.collidesWith(buzam)) {
					this.collisionBlockMovement(buzam, oldPosition, newDt);
					if (!this.bottomCollidesWith(buzam)) {
						if (!this.isImmune()) {
							this.changeNbHitPoints(-50);
							for (Slime slime: this.getSchool().getSlimes()) {
								if (!(slime == this)) {
									slime.changeNbHitPoints(-1);
								}
							}
							this.makeImmune();
						}
						else {
							if (Util.fuzzyLessThanOrEqualTo(this.getTimeImmune(), 0.60)) {
								this.setTimeImmune(this.getTimeImmune() + newDt);
							}
							else {
								this.makeVulnerable();
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * @param	newDt
	 * 			The period of time with which the time is advanced.
	 * @effect	If the slime is in water, its hitpoints are reduced with 2 every 0.2 seconds.
	 * 			| if ((this.isInWater()) && (this.getTimeInWater() >= 0.2)) 
	 * 			|		this.changeNbHitPoints(-2)
	 * @effect	If the slime is in magma, its hitpoints are reduced with 50 upon contact and every 
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
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect	If the slime is already moving horizontally for more than a random moving time, all
	 * 			ongoing movements are ended and it starts moving in another random direction.
	 * 			|if (this.timeMovingHorizontally >= movingTime)
	 * 			|	this.endMoveHorizontally(this.getLastDirection())
	 * 			|	this.startMoveHorizontally(this.getRandomDirection(), 
	 * 			|		this.getNormalHorizontalVelocity(), this.getNormalHorizontalAcceleration())
	 * @effect	If the slime is trying to cross an impassable tile, take the corresponding actions.
	 * 			| if ((this.crossImpassableBottom()) || (this.crossImpassableLeft()) 
	 * 			|	|| (this.crossImpassableTop()) || (this.crossImpassableRight()))
	 * 			|		this.crossImpassableActions(oldPosition)
	 * @effect	If the slime is in a fluid, the corresponding actions are taken.
	 * 			| if ((this.isInWater()) || (this.isInMagma())) 
	 * 			|	this.isInFluidActions(newDt)
	 * @effect	If the slime is not standing on an impassable tile, its vertical acceleration is set
	 * 			to the normal one.
	 * 			| if (!this.touchImpassableBottom())
	 * 			|	this.setVerticalAcceleration(this.getNormalVerticalAcceleration())
	 * @effect	If the slime is not trying to cross an impassable tile, its position is increased
	 * 			by 100 times the horizontal/vertical movement.
	 * 			| if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
	 * 			|	&& (!this.crossImpassableTop()) && (!this.crossImpassableRight()))
	 * 			|		this.setPosition(oldPosition[0] + 100*this.horizontalMovement(newDt),
	 * 			|			oldPosition[1] + 100*this.verticalMovement(newDt))
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (!this.isValidDt(dt)) {
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		}
		if (this.getProgram() == null){
			double sumDt = 0;
			double movingTime = this.getRandomMovingTime();
			while (!Util.fuzzyGreaterThanOrEqualTo(sumDt, dt)) {
				double newDt = this.getNewDt(dt);
				int[] oldPosition = this.getPosition();
				double[] oldPositionAsDouble = this.getPositionAsDouble();
				if (Util.fuzzyGreaterThanOrEqualTo(this.timeMovingHorizontally, movingTime)) {
					this.timeMovingHorizontally = 0;
					this.endMoveHorizontally(this.getLastDirection());
					this.startMoveHorizontally(this.getRandomDirection(), this.getNormalHorizontalVelocity(),
							this.getNormalHorizontalAcceleration());
				}
				if (!Util.fuzzyGreaterThanOrEqualTo(this.timeMovingHorizontally, movingTime)) {
					this.timeMovingHorizontally += newDt;
				}
				if ((this.crossImpassableLeft()) || (this.crossImpassableBottom()) 
						|| (this.crossImpassableRight())) {
					this.crossImpassableActions(oldPosition);
				}
				if ((this.isInWater()) || (this.isInMagma())) {
					this.isInFluidActions(dt);
				}
				this.collidesWithActions(newDt, oldPosition);
				if ((this.isInWater()) || (this.isInMagma())) {
					this.isInFluidActions(newDt);
				}
				if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
						&& (!this.crossImpassableTop()) && (!this.crossImpassableRight())) {
					if (!this.touchImpassableBottom()) {
						this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
					}
					this.setPosition(oldPositionAsDouble[0] + 100*this.horizontalMovement(newDt),
						oldPositionAsDouble[1] + 100*this.verticalMovement(newDt));
				}
				sumDt += newDt;
			}
		}
		if (this.programRunning){
			double sumDt = 0;
			while (!Util.fuzzyGreaterThanOrEqualTo(sumDt, dt)) {
				double newDt = this.getNewDt(dt);
				int[] oldPosition = this.getPosition();
				double[] oldPositionAsDouble = this.getPositionAsDouble();
				if ((this.crossImpassableLeft()) || (this.crossImpassableBottom()) 
						|| (this.crossImpassableRight())) {
					this.crossImpassableActions(oldPosition);
				}
				if ((this.isInWater()) || (this.isInMagma())) {
					this.isInFluidActions(dt);
				}
				this.collidesWithActions(newDt, oldPosition);
				if ((this.isInWater()) || (this.isInMagma())) {
					this.isInFluidActions(newDt);
				}
				if ((!this.crossImpassableBottom()) && (!this.crossImpassableLeft())
						&& (!this.crossImpassableTop()) && (!this.crossImpassableRight())) {
					if (!this.touchImpassableBottom()) {
						this.setVerticalAcceleration(this.getNormalVerticalAcceleration());
					}
					this.setPosition(oldPositionAsDouble[0] + 100*this.horizontalMovement(newDt),
						oldPositionAsDouble[1] + 100*this.verticalMovement(newDt));
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
