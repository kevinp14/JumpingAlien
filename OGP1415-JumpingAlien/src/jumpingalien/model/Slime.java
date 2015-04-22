package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Slime extends GameObject {
	private Sprite[] spriteList;
	private double normalHorizontalVelocity;
	private double normalHorizontalAcceleration = 0.7;
	private double maxHorizontalVelocity = 2.5;
	private School school;
	private double timeMovingHorizontally;
	private int hitPoints = 100;

	public Slime (int positionX, int positionY, Sprite[] spriteList, School school) {
		super(positionX, positionY);
		this.spriteList = spriteList;
		this.school = school;
		this.timeMovingHorizontally = 0;
		this.setLastDirection(Direction.STALLED);
	}
	
	public Sprite getCurrentSprite() {
		
	}
	
	@Basic
	public School getSchool(){
		return this.school;
	}
	
	public void setSchool(School newSchool){
		this.school.removeSlimeFromSchool(this);
		this.school = newSchool;
		newSchool.addSlimeToSchool(this);
	}
	
	private boolean isMoving() {
		if (this.timeMovingHorizontally > 0)
			return true;
		else
			return false;
	}
	
	protected double horizontalMovement(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt))
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		if ((this.getPosition()[0] <= 0) && (this.getHorizontalVelocity() < 0)) {
			this.setHorizontalVelocity(0);
		}
		if ((this.getPosition()[0] >= this.getMaxPosition()[0]) && (this.getHorizontalVelocity() > 0)) {
			this.setHorizontalVelocity(0);
		}
		else {
			if (this.isMoving()) {
				if (!(this.timeMovingHorizontally < 50)) {
					if (this.isMovingLeft())
						this.setHorizontalVelocity(0.5);
					if (this.isMovingRight())
						this.setHorizontalVelocity(-0.5);
				}
			}
			else {
				// Hoe een random richting kiezen om in te bewegen?
			}
		}
		double newPositionX = this.getHorizontalVelocity() * dt;
		return newPositionX;
	}
	
	public void advanceTime(double dt) throws IllegalArgumentException {
		if (! isValidDt(dt)) 
			throw new IllegalArgumentException("The given period of time dt is invalid!");
		this.setPosition(this.getPosition()[0] + (int)(100 * this.horizontalMovement(dt)),
				this.getPosition()[1]);
		if (this.getPosition()[0] < 0)
			this.setPosition(0, this.getPosition()[1]);
		if (this.getPosition()[0] > this.getMaxPosition()[0])
			this.setPosition(this.getMaxPosition()[0], this.getPosition()[1]);
		if (this.getPosition()[1] < 0)
			this.setPosition(this.getPosition()[0], 0);
		if (getPosition()[1] > getMaxPosition()[1])
			this.setPosition(this.getPosition()[0], this.getMaxPosition()[1]);
		if (!(this.getHorizontalVelocity() == 0)) {
			this.timeMovingHorizontally += 1;
		}
		if (/*botsing*/)
			this.setNbHitPoints(-50);
		if (/*in water*/)
			this.setNbHitPoints((int)(-2 * (dt % (20))));
		if (/*in lava*/)
			this.setNbHitPoints((int)(-50 *((dt + 20) % (20))));
	}
	
}
