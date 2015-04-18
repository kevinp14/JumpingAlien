package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Plant extends Mazub {
	private double positionX;
	private double positionY;
	private int hitPoints = 1;
	private Sprite[] spriteList;
	private double timeMoving;
	private double timeStalled;
	private Direction lastDirection;
	
	public Plant(int positionX, int positionY, int hitPoints, Sprite[] spriteList){
		this.positionX = (double)positionX/100d;
		this.positionY = (double)positionY/100d;
		this.hitPoints = hitPoints;
		this.spriteList = spriteList;
		this.timeMoving = 0;
		this.timeStalled = 0;
		this.lastDirection = Direction.STALLED;
	}
	
	@Basic
	public double[] getPosition() {
		double[] position = new double[]{this.positionX, this.positionY};
		return position;
	}
	
	@Basic
	private void setPosition(double positionX, double positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public int getHitPoints() {
		return this.hitPoints;
	}
	
	private void setHitPoints(int hitPointsDifference) {
		this.hitPoints += hitPointsDifference;
	}
	
	public void advanceTime(double dt) {
			
	}
}