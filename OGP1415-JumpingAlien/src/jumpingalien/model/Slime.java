package jumpingalien.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

/**
 * A class of slimes involving the normal and maximum for the horizontal velocity, the slime's school,
 * the time the slime is moving horizontally, the number of hitpoints, a method to change the school
 * in which the slime is, a method to create a random moving time and direction and a method 
 * to advance time and adapt the time depending characteristics based on the period of time passed.
 * 
 * 
 * @invar //TODO
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mari�n (Tweede fase ingenieurswetenschappen)
 * @version 5.0
 *
 */
public class Slime extends GameObject {
	private double normalHorizontalVelocity;
	private double normalHorizontalAcceleration;
	private double maxHorizontalVelocity;
	private School school;
	private double timeMovingHorizontally;

	/**
	 * Initialize the slime at the given position in x- and y-direction with the given list of
	 * sprites. Also sets the time moving horizontally to 0.
	 * 
	 * @param 	positionX
	 * 			The position in the x-direction where the slime should be.
	 * @param 	positionY
	 * 			The position in the y-direction where the slime should be.
	 * @param 	spriteList
	 * 			The list of sprites displaying how the slime should look depending on its behavior.
	 * @param	school
	 * 			The school to which the slime has to be assigned.
	 */
	public Slime (int positionX, int positionY, Sprite[] spriteList, School school) {
		super(positionX, positionY, spriteList);
		this.normalHorizontalVelocity = 0;
		this.normalHorizontalAcceleration = 0.7;
		this.maxHorizontalVelocity = 2.5;
	    this.timeMovingHorizontally = 0;
		this.school = school;
		this.changeNbHitPoints(100);
	}
	
	/**
	 * Returns the current school to which the given slime belongs.
	 * 
	 * @param slime
	 *            The slime for which to retrieve the school.
	 * 
	 * @return The current school of the given slime.
	 */
	@Basic
	public School getSchool(){
		return this.school;
	}
	
	/**
	 * Set the slime's school to the given school.
	 * 
	 * @param 	school
	 * 			The school to which the slime's school has to be set.
	 */
	public void setSchool(School school){
		this.school.removeSlimeFromSchool(this);
		this.school = school;
		school.addSlimeToSchool(this);
	}
	
	/**
	 * @return	A random time between 2 and 6 seconds during which the slime has to move.
	 */
	private int getRandomMovingTime() {
		Random rand = new Random();
		int movingTime = rand.nextInt(60);
		if (movingTime > 20)
			return movingTime;
		else
			return (movingTime + 20);
	}
	
	/**
	 * @return A random direction (left or right) for the slime to move in.
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
	 * Return the new x-position in the game world of the slime after it moved horizontally. Also
	 * stop the slime from moving if the tile to which it wants to move is not passable.
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
	 * @post	If the slime's x-position is the smaller than or equal to the minimum and the slime is 
	 * 			trying to move in the negative x-direction, the new horizontal velocity and acceleration 
	 * 			are set to 0 and the new x-position is set to the minimum.
	 * 			| if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the slime's x-position is the bigger than or equal to the maximum and the slime is 
	 * 			trying to move in the positive x-direction, the horizontal velocity and acceleration are
	 * 			set to 0 and the x-position is set to the maximum.
	 * 			| if ((this.getPosition()[0] >= (this.maxPositionX)) && 
	 * 			|		(this.getHorizontalVelocity() > 0)) 
	 * 			|	(new this).setHorizontalAcceleration(0) 
	 * 			|	(new this).setHorizontalVelocity(0)
	 * @post	If the slime's horizontal velocity is smaller than 0 and the tile to the left of the
	 * 			slime is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0], this.getPosition()[1])))	
	 * 			|				&& (this.getHorizontalVelocity() < 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the slime's horizontal velocity is bigger than 0 and the tile to the right of the
	 * 			slime is not passable, the new horizontal acceleration and velocity are set to 0.
	 * 			| 		if ((this.world.isNotPassable(this.world.getGeologicalFeature(
	 * 			|				this.getPosition()[0] + this.getCurrentSprite().getWidth(), 
	 * 			|				this.getPosition()[1]))) && (this.getHorizontalVelocity() > 0)) 
	 * 			|			(new this).setHorizontalAcceleration(0)
	 * 			|			(new this).setHorizontalVelocity(0)
	 * @post	If the slime is already moving horizontally for more than a random moving time, the 
	 * 			ongoing movements are ended and it starts moving into the next random direction.
	 * 			| if (this.isMovingHorizontally()) 
	 * 			|	int movingTime = this.getRandomMovingTime()
	 * 			|	if (!(this.timeMovingHorizontally < movingTime))
	 * 			|		this.endMoveHorizontally(this.getLastDirection())
	 * 			|		this.startMoveHorizontally(this.getRandomDirection())
	 * @post	If the slime is not moving horizontally already, it starts moving into a random direction
	 * 			(left or right).
	 * 			| if (!this.isMovingHorizontally())
	 * 			|	this.startMoveHorizontally(this.getRandomDirection())
	 * @return	newPositionX
	 * 			The slime's new x-position after horizontal movement.
	 * 			| newPositionX = this.getHorizontalVelocity() * dt
	 */
	private double horizontalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if (Math.abs(this.getHorizontalVelocity()) >= this.getMaxHorizontalVelocity()) {
			this.setHorizontalAcceleration(0);
			if (this.getHorizontalVelocity() < 0) {
				this.setHorizontalVelocity(-this.getMaxHorizontalVelocity());
			} else
				this.setHorizontalVelocity(this.getMaxHorizontalVelocity());
		}
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0); 
			this.setHorizontalVelocity(0);
		}
		if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0], this.getPosition()[1])))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth(), this.getPosition()[1]))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
		}
		if (this.isMovingHorizontally()) {
			int movingTime = this.getRandomMovingTime();
			if (!(this.timeMovingHorizontally <= movingTime))
				this.endMoveHorizontally(this.getLastDirection());
				this.startMoveHorizontally(this.getRandomDirection());
		}
		if (!this.isMovingHorizontally())
			this.startMoveHorizontally(this.getRandomDirection());
		this.setHorizontalVelocity(this.getHorizontalVelocity() + this.getHorizontalAcceleration() * dt);
		double newPositionX = this.getHorizontalVelocity() * dt - 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) + 
				this.getHorizontalAcceleration() * Math.pow(dt, 2) / 2;
		return newPositionX;
	}
	
	/**
	 * Return the new y-position in the game world of the slime after it moved vertically. Also
	 * stop the slime from moving if the tile to which he wants to move is not passable.
	 * @param	dt
	 * 			The time passed dt.
	 * @post 	If the slime's vertical velocity is smaller than 0 and the tile beneath the slime is not
	 * 			passable, the slime's new vertical acceleration and velocity are set to 0.
	 * 			| 		if ((this.getVerticalVelocity() < 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1]))))
	 * 			|			this.setVerticalAcceleration(0)
	 * 			|			this.setVerticalVelocity(0)
	 * @post 	If the slime's vertical velocity is bigger than 0 and the tile above the slime is not
	 * 			passable, the slime's new vertical velocity is set to 0.
	 * 			| 		if ((this.getVerticalVelocity() > 0) && (this.world.isNotPassable(
	 * 			|				this.world.getGeologicalFeature((int)this.getPosition()[0], 
	 * 			|				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight()))))
	 * 			|			this.setVerticalVelocity(0)
	 * @post	The new vertical velocity is set to the sum of the current vertical velocity and the 
	 * 			product of the current vertical acceleration and dt.
	 * 			| (new this).setVerticalVelocity(this.getVerticalVelocity() + 
	 * 			|	this.getVerticalAcceleration()*dt)
	 * @return	newPositionY
	 * 			The slime's new y-position after vertical movement.
	 * 			| newPositionY = this.getVerticalVelocity() * dt 
	 * 			|	- this.getVerticalAcceleration() * Math.pow(dt, 2)
	 * 			|	+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	private double verticalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getVerticalVelocity() < 0) && (this.getWorld().isNotPassable(
				this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setVerticalAcceleration(0);
			this.setVerticalVelocity(0);
		}
		if ((this.getVerticalVelocity() > 0) && (this.getWorld().isNotPassable(
				this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setVerticalVelocity(0);
		}
		this.setVerticalVelocity(this.getVerticalVelocity() + this.normalVerticalAcceleration*dt);
		double newPositionY = this.getVerticalVelocity() * dt 
				- this.getVerticalAcceleration() * Math.pow(dt, 2)
				+ this.getVerticalAcceleration() * Math.pow(dt, 2)/2;
		return newPositionY;
	}
	
	/**
	 * Set the new position on the screen of the slime to new position in the game world, limit the
	 * slime's position to the minimum (in x-direction) and the maximum (in x-direction), block the 
	 * slime's movement if it wants to move to a tile that is not passable, adapt the time
	 * moving horizontally, block the slime's movement and change the slime's school if it collides
	 * with another slime, block the slime's movement, decrease its hitpoints and make it immune
	 * if it collides with an enemy (only when it does not collide with its bottom perimeter), 
	 * decrease the number of hitpoints if the slime is in water or magma, and make it immune for 
	 * magma if it falls into magma.
	 * @param 	dt
	 * 			The time passed dt.
	 * @effect //TODO
	 * @throws	IllegalArgumentException
	 * 			| !isValidDt(dt)
	 */
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt)) 
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition(this.getPosition()[0] + (int)(100 * this.horizontalMovement(dt)),
				this.getPosition()[1] + (int)(100 * this.verticalMovement(dt)));
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setPosition(0, this.getPosition()[1]);
		}
		if ((this.getPosition()[0] >= (this.getMaxPosition()[0])) && 
				(this.getHorizontalVelocity() > 0)) {
			this.setPosition(this.getMaxPosition()[0], this.getPosition()[1]);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0], this.getPosition()[1])))
				&& (this.getHorizontalVelocity() < 0)) {
			this.setPosition(this.getWorld().getBottomLeftPixelOfTile(this.getPosition()[0], 
					this.getPosition()[1])[0] + this.getWorld().getTileLength(), this.getPosition()[1]);
		}
		if ((this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature(
				this.getPosition()[0] + this.getCurrentSprite().getWidth(), this.getPosition()[1]))) 
				&& (this.getHorizontalVelocity() > 0)) {
			this.setPosition(this.getWorld().getBottomLeftPixelOfTile(this.getPosition()[0], 
					this.getPosition()[1])[0] - this.getWorld().getTileLength(), this.getPosition()[1]);
		}
		if ((this.getVerticalVelocity() < 0) && (this.getWorld().isNotPassable(
				this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
						(int)this.getPosition()[1])))) {
			this.setPosition(this.getPosition()[0], 
					this.getWorld().getBottomLeftPixelOfTile((int)this.getPosition()[0], 
					(int)this.getPosition()[1])[1] + this.getWorld().getTileLength());
		}			
		if ((this.getVerticalVelocity() > 0) && 
				(this.getWorld().isNotPassable(this.getWorld().getGeologicalFeature((int)this.getPosition()[0], 
				(int)this.getPosition()[1] + this.getCurrentSprite().getHeight())))) {
			this.setPosition(this.getPosition()[0], 
					this.getWorld().getBottomLeftPixelOfTile((int)this.getPosition()[0],
					(int)this.getPosition()[1])[1] - this.getWorld().getTileLength());
		}
		if (!(this.getHorizontalVelocity() == 0)) {
			this.timeMovingHorizontally += 1;
		}
		for (Slime slime: this.getWorld().getSlimes()) {
			if (this.collidesWith(slime)) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				if (!(this.getSchool() == slime.getSchool())) {
					for (Slime otherSlime: this.getSchool().getSlimes()) {
						if (!(otherSlime == this)) {
							this.changeNbHitPoints(-1);
							otherSlime.changeNbHitPoints(1);
						}
					}
					this.setSchool(slime.getSchool());
					for (Slime otherSlime: slime.getSchool().getSlimes()) {
						if (!(otherSlime == this)) {
							otherSlime.changeNbHitPoints(-1);
							this.changeNbHitPoints(1);
						}
					}
				}
			}
		}
		for (Shark shark: this.getWorld().getSharks()) {
			if ((this.collidesWith(shark)) && (!this.bottomCollidesWithTopOfObject(shark))) {
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0); /*Is dit goed om beweging te blokkeren? Want in de opgave
				staat: "Properties of the ongoing movement of the colliding game, e.g. direction, velocity 
				and acceleration, may not change directly as a result of the collision."*/
				if (!this.isImmune()) {
					this.changeNbHitPoints(-50);
					this.makeImmune();
				}
				else
					this.timeImmune += 1;
			}
		}
		if ((this.collidesWith(this.getWorld().getMazub())) 
				&& (!this.bottomCollidesWithTopOfObject(this.getWorld().getMazub()))) {
			this.setHorizontalAcceleration(0);
			this.setHorizontalVelocity(0);
			if (!this.isImmune()) {
				this.changeNbHitPoints(-50);
				for (Slime slime: this.getSchool().getSlimes()) {
					if (!(slime == this))
						slime.changeNbHitPoints(-1);
				}
				this.makeImmune();
			}
			else
				this.timeImmune += 1;
		}
		if (this.isInWater())
			this.changeNbHitPoints((int)(-2 * (dt / (0.2))));
		if (this.isInMagma()) {
			if (!this.isImmuneForMagma()) {
				this.changeNbHitPoints((int)(-50 *((dt + 1) / (0.2))));
				this.makeImmuneForMagma();
			}
			else
				this.timeImmuneForMagma += 1;
		}
	}
}