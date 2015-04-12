package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import jumpingalien.util.Sprite;

public class Plant {
	private double positionX;
	private double positionY;
	private Sprite[] spriteList;
	
	public Plant(int positionX, int positionY, Sprite[] spriteList){
		this.positionX = (double)positionX/100d;
		this.positionY = (double)positionY/100d;
		this.spriteList = spriteList;
	}
	
	@Basic
	public double[] getPosition() {
		double[] position = new double[]{this.positionX, this.positionY};
		return position;
	}
	
	public void advanceTime(double dt){
		
	}
}
