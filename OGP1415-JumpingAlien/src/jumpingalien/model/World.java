package jumpingalien.model;

import java.util.Collection;
import java.util.Hashtable;
import java.util.ArrayList;
import be.kuleuven.cs.som.annotate.Basic;

public class World {
	private int tileSize;
	private int nbTilesX;
	private int nbTilesY;
	private int visibleWindowWidth;
	private int visibleWindowHeight;
	private int targetTileX;
	private int targetTileY;
	private boolean won;
	private ArrayList<Plant> plants = new ArrayList<>();
	private ArrayList<Shark> sharks = new ArrayList<>();
	private	ArrayList<Slime> slimes = new ArrayList<>();
	private Mazub alien;
	private double dt;
	Hashtable<int[], Integer> tiles
	= new Hashtable<int[], Integer>();
	private GameState gameState;
	
	public World (int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		this.tileSize = tileSize;
		this.nbTilesX = nbTilesX;
		this.nbTilesY = nbTilesY;
		this.visibleWindowWidth = visibleWindowWidth;
		this.visibleWindowHeight = visibleWindowHeight;
		this.targetTileX = targetTileX;
		this.targetTileY = targetTileY;
		this.won = false;
		this.dt = 0;
		for (int x  = 0; x < nbTilesX; x++){
			for (int y = 0; y < nbTilesY; y++){
				this.tiles.put(new int[]{x*tileSize, y*tileSize, 0}, 0);
			}
		}
		this.gameState = GameState.INITIATED;
	}
	
	private boolean canAddMore(){
		int amountOfObjects = 0;
		if (plants != null){
			amountOfObjects += plants.size();
		}
		if  (sharks != null){
			amountOfObjects += sharks.size();
		}
		if (slimes != null){
			amountOfObjects += slimes.size();
		}
		if (amountOfObjects == 100){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void addPlant(Plant plant){
		if (this.canAddMore()){
			this.plants.add(plant);
		}
	}
	
	public void addShark(Shark shark){
		if (this.canAddMore()){
			this.sharks.add(shark);
		}
	}
	
	public void addSlime(Slime slime){
		if (this.canAddMore()){
			this.slimes.add(slime);
		}
	}
	
	//hoe zit het met die dt? zelf berekenen of meegegeven?
	public void advanceTime(double dt){
		this.alien.advanceTime(dt);
		for (Shark shark: sharks){
			shark.advanceTime(dt);
		}
		for (Slime slime: slimes){
			slime.advanceTime(dt);
		}
		for(Plant plant: plants){
			plant.advanceTime(dt);
		}
	}
	
	public int[] getBottomLeftPixelOfTile(int tileX, int tileY){
		int tilePositionX = (tileX%this.tileSize)*this.tileSize;
		int tilePositionY = (tileY%this.tileSize)*this.tileSize;
		int[] positionBottomLeftPixelOfTile = new int[]{tilePositionX, tilePositionY};
		return positionBottomLeftPixelOfTile;
	}
	
	private double getNextDt(double dt){
		double velocity = Math.pow((Math.pow(this.alien.getHorizontalVelocity(), 2) + 
				Math.pow(this.alien.getVerticalVelocity(),2)), 1/2);
		double acceleration = Math.pow((Math.pow(this.alien.getHorizontalAcceleration(), 2) + 
				Math.pow(this.alien.getVerticalAcceleration(),2)), 1/2);
		double newDt = 0.01 / (velocity + acceleration*dt);
		return newDt;
	}
	
	public boolean didPlayerWin(){
		if (this.won){
			return true;
		}
		else{
			return false;
		}
	}
	
	@Basic
	public int getGeologicalFeature(int pixelX, int pixelY){
		return this.tiles.get(this.getBottomLeftPixelOfTile(pixelX, pixelY));
	}
	
	@Basic
	public ArrayList<Plant> getPlants(){
		return this.plants;
	}
	
	@Basic
	public ArrayList<Shark> getSharks(){
		return this.sharks;
	}
	
	@Basic
	public ArrayList<Slime> getSlimes(){
		return this.slimes;
	}
	
	@Basic
	public int getTileLength(){
		return this.tileSize;
	}
	
	@Basic
	public int[][] getTilePositionsIn(int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		int dimension = 0;
		for (int x = (int)(pixelLeft/this.tileSize); x < pixelRight; x += this.tileSize ){
			for (int y = (int)(pixelBottom/this.tileSize); y < pixelTop; y += this.tileSize){
				dimension += 1;
			}
		}
		/*Hier moet nog iets voor van type, ik veronderstel int[]*/positions = new int[dimension][2];
		int placeInPositions = 0;
		for (int x = (int)(pixelLeft/this.tileSize); x < pixelRight; x += this.tileSize ){
			for (int y = (int)(pixelBottom/this.tileSize); y < pixelTop; y += this.tileSize){
				positions[placeInPositions][0] = x;
				positions[placeInPositions][1] = y;
				placeInPositions += 1;
			}
		}
		
		return positions ;
	}
	
	//Hier moeten links, onder, rechts en bovenste 'pixel' gegeven worden
	@Basic
	public int[] getVisibleWindow() {
		return new int[]{this.visibleWindowWidth, this.visibleWindowHeight};
	}
	
	@Basic
	public int[] getWorldSize(){
		int[] worldSize = new int[]{nbTilesX*this.tileSize, nbTilesY*this.tileSize};
		return worldSize;
	}
	
	public boolean isGameOver(){
		if (this.alien.getNbHitPoints() <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isNotPassable(int geologicalFeature){
		if (geologicalFeature == 1){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
	 *            <ul>
	 *            <li>the value 0 is provided for an <b>air</b> tile;</li>
	 *            <li>the value 1 is provided for a <b>solid ground</b> tile;</li>
	 *            <li>the value 2 is provided for a <b>water</b> tile;</li>
	 *            <li>the value 3 is provided for a <b>magma</b> tile.</li>
	 *            </ul>
	 */
	public void setGeologicalFeature(int tileX, int tileY, int tileType){
		this.tiles.put(this.getBottomLeftPixelOfTile(tileX, tileY),tileType);
	}
	
	public void setMazub(Mazub alien){
		this.alien = alien;
		this.alien.setWorld(this);
	}
	
	public void startGame(){
		this.gameState = GameState.STARTED;
	}

	
}
