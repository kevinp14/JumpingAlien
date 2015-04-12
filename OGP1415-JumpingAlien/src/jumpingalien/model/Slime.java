package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Slime extends Mazub{
	private double positionX;
	private double positionY;
	private Sprite[] spriteList;
	private School school;
	private int timeMoving;
	private Direction lastDirection;

	public Slime (int positionX, int positionY, Sprite[] spriteList) {
		this.positionX = (double)positionX/100d;
		this.positionY = (double)positionY/100d;
		this.spriteList = spriteList;
		this.school = school;
		this.timeMoving = 0;
		this.lastDirection = Direction.STALLED;
	}
	
	@Basic
	public double[] getPosition() {
		double[] position = new double[]{this.positionX, this.positionY};
		return position;
	}
	
	@Basic
	public School getSchool(){
		return this.school;
	}
	
	public void setSchool(School school){
		this.school = school;
	}
	
	public void advanceTime(double dt){
		
	}
	
}
