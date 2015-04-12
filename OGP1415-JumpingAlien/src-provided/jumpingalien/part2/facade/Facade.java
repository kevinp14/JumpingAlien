package jumpingalien.part2.facade;

import java.util.Collection;

import jumpingalien.model.Direction;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

/**
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 2.0
 */
public class Facade implements IFacadePart2 {
	
	public Facade() {}
	
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		World world = new World(tileSize, nbTilesX, nbTilesY, visibleWindowWidth,
				visibleWindowHeight, targetTileX, targetTileY);
		return world;
	}
	
	public int getGeologicalFeature(World world, int pixelX, int pixelY){
		return world.getGeologicalFeature(pixelX, pixelY);
	}
	
	public void setGeologicalFeature(World world, int tileX, int tileY, int tileType){
		world.setGeologicalFeature(tileX, tileY, tileType);
	}
	
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY){
		
	}
	
	public int getTileLength(World world){
		
	}
	
	public int[][] getTilePositionsIn(World world, int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		
	}
	
	public int[] getVisibleWindow(World world){
		
	}
	
	public int[] getWorldSizeInPixels(World world){
		
	}
	
	public void startGame(World world){
		
	}
	
	/**
	 * Advance the state of the given alien by the given time period.
	 * 
	 * @param alien
	 *            The alien whose time has to be advanced.
	 * @param dt
	 *            The time interval (in seconds) by which to advance the given
	 *            alien's time.
	 */
	public void advanceTime(World world, double dt) throws ModelException {
		try {
			world.advanceTime(dt);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("The given period of time dt is invalid!");
		}
	}
	
	public boolean didPlayerWin(World world){
		return world.didPlayerWin();
	}
	
	public boolean isGameOver(World world){
		
	}
	
	public Slime createSlime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, School school){
		Slime slime = new Slime(pixelLeftX, pixelBottomY, sprites, school);
		return slime;
	}
	
	public void addSlime(World world, Slime slime){
		world.addSlime(slime);
	}
	
	public Collection<Slime> getSlimes(World world){
		
	}
	
	public Sprite getCurrentSprite(Slime slime){
		return slime.getCurrentSprite();
	}
	
	public int[] getLocation(Slime slime){
		int[] location = new int[]{(int) slime.getPosition()[0], 
				(int) slime.getPosition()[1]};
		return location;
	}
	
	public School createSchool(){
		School school = new School();
		return school;
	}
	
	public School getSchool(Slime slime){
		return slime.getSchool();
	}
	
	public Shark createShark(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		Shark shark = new Shark(pixelLeftX, pixelBottomY, sprites);
		return shark;
	}
	
	public void addShark(World world, Shark shark){
		world.addShark(shark);
	}
	
	public Collection<Shark> getSharks(World world){
		
	}
	
	public Sprite getCurrentSprite(Shark shark){
		return shark.getCurrentSprite();
	}
	
	public int[] getLocation(Shark shark){
		int[] location = new int[]{(int) shark.getPosition()[0], 
				(int) shark.getPosition()[1]};
		return location;
	}
	
	public Plant createPlant(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		Plant plant = new Plant(pixelLeftX, pixelBottomY, sprites);
		return plant;
	}
	
	public void addPlant(World world, Plant plant){
		world.addPlant(plant);
	}
	
	public Collection<Plant> getPlants(World world){
		
	}
	
	public Sprite getCurrentSprite(Plant plant){
		return plant.getCurrentSprite();
	}
	
	public int[] getLocation(Plant plant){
		int[] location = new int[]{(int) plant.getPosition()[0], 
				(int) plant.getPosition()[1]};
		return location;
	}
	
	/**
	 * Create an instance of Mazub.
	 * 
	 * @param pixelLeftX
	 *            The x-location of Mazub's bottom left pixel.
	 * @param pixelBottomY
	 *            The y-location of Mazub's bottom left pixel.
	 * @param sprites
	 *            The array of sprite images for Mazub.
	 * 
	 * @return
	 */
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		Mazub alien = new Mazub(pixelLeftX, pixelBottomY, sprites);
		return alien;
	}
	
	public void setMazub(World world, Mazub alien){
		
	}
	
	/**
	 * Return the current sprite image for the given alien.
	 * 
	 * @param alien
	 *            The alien for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given alien, determined by its
	 *         state as defined in the assignment.
	 */
	public Sprite getCurrentSprite(Mazub alien) {
		return alien.getCurrentSprite();
	}
	
	/**
	 * Return the current location of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the location.
	 * 
	 * @return an array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given alien's bottom left pixel in the world.
	 */
	public int[] getLocation(Mazub alien){
		int[] location = new int[]{(int) alien.getPosition()[0], 
				(int) alien.getPosition()[1]};
		return location;
	}
	
	/**
	 * Return the current size of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the size.
	 * 
	 * @return An array, consisting of 2 integers {w, h}, that represents the
	 *         current width and height of the given alien, in number of pixels.
	 */
	public int[] getSize(Mazub alien) {
		Sprite sprite = alien.getCurrentSprite();
		int[] size = new int[] {sprite.getWidth(), sprite.getHeight()};
		return size;
	}
	
	/**
	 * Return the current velocity (in m/s) of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the velocity.
	 * 
	 * @return an array, consisting of 2 doubles {vx, vy}, that represents the
	 *         horizontal and vertical components of the given alien's current
	 *         velocity, in units of m/s.
	 */
	public double [] getVelocity(Mazub alien) {
		double[] velocity = new double[] {alien.getHorizontalVelocity(),
				alien.getVerticalVelocity()};
		return velocity;
	}
	
	/**
	 * Return the current acceleration (in m/s^2) of the given alien.
	 * 
	 * @param alien
	 *            The alien of which to get the acceleration.
	 * 
	 * @return an array, consisting of 2 doubles {ax, ay}, that represents the
	 *         horizontal and vertical components of the given alien's current
	 *         acceleration, in units of m/s^2.
	 */
	public double[] getAcceleration(Mazub alien) {
		double[] acceleration = new double[] {alien.getHorizontalAcceleration(), 
				alien.getVerticalAcceleration()};
		return acceleration;
	}

	/**
	 * Make the given alien move left.
	 * 
	 * @param alien
	 *            The alien that has to start moving left.
	 */
	public void startMoveLeft(Mazub alien) {
		alien.startMoveHorizontally(Direction.LEFT);
	}
	
	/**
	 * Make the given alien move right.
	 * 
	 * @param alien
	 *            The alien that has to start moving right.
	 */
	public void startMoveRight(Mazub alien) {
		alien.startMoveHorizontally(Direction.RIGHT);
	}
	
	/**
	 * End the given alien's left move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving left.
	 */
	public void endMoveLeft(Mazub alien) {
		alien.endMoveHorizontally(Direction.LEFT);
	}
	
	/**
	 * End the given alien's right move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving right.
	 */
	public void endMoveRight(Mazub alien) {
		alien.endMoveHorizontally(Direction.RIGHT);
	}
	
	/**
	 * Make the given alien duck.
	 * 
	 * @param alien
	 *            The alien that has to start ducking.
	 */
	public void startDuck(Mazub alien){
		alien.startDuck();
	}
	
	/**
	 * End the given alien's ducking.
	 * 
	 * @param alien
	 *            The alien that has to stop ducking.
	 */
	public void endDuck(Mazub alien) throws ModelException {
		if (!alien.isDucking())
			throw new ModelException("The alien was not ducking!");
		alien.endDuck();
	}
	
	/**
	 * Make the given alien jump.
	 * 
	 * @param alien
	 *            The alien that has to start jumping.
	 */
	public void startJump(Mazub alien) throws ModelException { 
		if (alien.isJumping())
			throw new ModelException("The alien can not jump while in the air!");
		alien.startJump();}
	
	/**
	 * End the given alien's jump.
	 * 
	 * @param alien
	 *            The alien that has to stop jumping.
	 */
	public void endJump(Mazub alien) throws ModelException {
		if (!alien.isJumping())
			throw new ModelException("The alien has already stopped jumping!");
		alien.endJump();
	}
	
	public int getNbHitPoints(Mazub alien){
		return alien.getNbHitPoints();
	}
	
	public boolean isImmune(Mazub alien){
		
	}
}
