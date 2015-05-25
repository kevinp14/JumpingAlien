package jumpingalien.part3.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jumpingalien.part3.programs.*;
import jumpingalien.model.Buzam;
import jumpingalien.model.SelfMadeDirection;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Program;
import jumpingalien.model.ProgramFactory;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.World;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.statement.Statement;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

/**
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 2.0
 */
public class Facade implements IFacadePart3 {
	
	public Facade() {}
	
	/**
	 * Create a new game world with the given parameters.
	 * 
	 * @param tileSize
	 *            Length (in pixels) of a side of each square tile in the world
	 * @param nbTilesX
	 *            Number of tiles in the horizontal direction
	 * @param nbTilesY
	 *            Number of tiles in the vertical direction
	 * @param visibleWindowWidth
	 *            Width of the visible window, in pixels
	 * @param visibleWindowHeight
	 *            Height of the visible window, in pixels
	 * @param targetTileX
	 *            Tile x-coordinate of the target tile of the created world
	 * @param targetTileY
	 *            Tile y-coordinate of the target tile of the created world
	 */
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY,
			int visibleWindowWidth, int visibleWindowHeight, int targetTileX,
			int targetTileY){
		World world = new World(tileSize, nbTilesX, nbTilesY, visibleWindowWidth,
				visibleWindowHeight, targetTileX, targetTileY);
		return world;
	}
	
	/**
	 * Returns the geological feature of the tile with its bottom left pixel at
	 * the given position.
	 * 
	 * @param world
	 *            The world containing the tile for which the
	 *            geological feature should be returned.
	 * 
	 * @param pixelX
	 *            The x-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * @param pixelY
	 *            The y-position of the pixel at the bottom left of the tile for
	 *            which the geological feature should be returned.
	 * 
	 * @return The type of the tile with the given bottom left pixel position,
	 *         where
	 *         <ul>
	 *         <li>the value 0 is returned for an <b>air</b> tile;</li>
	 *         <li>the value 1 is returned for a <b>solid ground</b> tile;</li>
	 *         <li>the value 2 is returned for a <b>water</b> tile;</li>
	 *         <li>the value 3 is returned for a <b>magma</b> tile.</li>
	 *         </ul>
	 * 
	 * @note This method must return its result in constant time.
	 * 
	 * @throw ModelException if the given position does not correspond to the
	 *        bottom left pixel of a tile.
	 */
	public int getGeologicalFeature(World world, int pixelX, int pixelY){
		return world.getGeologicalFeature(pixelX, pixelY);
	}
	
	/**
	 * Modify the geological type of a specific tile in the given world to a
	 * given type.
	 * 
	 * @param world
	 *            The world in which the geological type of a tile needs to be
	 *            modified
	 * @param tileX
	 *            The x-position x_T of the tile for which the type needs to be
	 *            modified
	 * @param tileY
	 *            The y-position y_T of the tile for which the type needs to be
	 *            modified
	 * @param tileType
	 *            The new type for the given tile, where
	 *            <ul>
	 *            <li>the value 0 is provided for an <b>air</b> tile;</li>
	 *            <li>the value 1 is provided for a <b>solid ground</b> tile;</li>
	 *            <li>the value 2 is provided for a <b>water</b> tile;</li>
	 *            <li>the value 3 is provided for a <b>magma</b> tile.</li>
	 *            </ul>
	 */
	public void setGeologicalFeature(World world, int tileX, int tileY, int tileType){
		world.setGeologicalFeature(tileX, tileY, tileType);
	}
	
	/**
	 * Returns the bottom left pixel coordinate of the tile at the given tile
	 * position.
	 * 
	 * @param world
	 *            The world from which to retrieve the tile.
	 * @param tileX
	 *            The x-position x_T of the tile
	 * @param tileY
	 *            The y-position y_T of the tile
	 * @return An array which contains the x-coordinate and y-coordinate of the
	 *         bottom left pixel of the given tile, in that order.
	 */
	public int[] getBottomLeftPixelOfTile(World world, int tileX, int tileY){
		return world.getBottomLeftPixelOfTile(tileX, tileY);
	}
	
	/**
	 * Returns the length of a square tile side in the given world.
	 * 
	 * @param world
	 *            The game world for which to retrieve the tile length
	 * 
	 * @return The length of a square tile side, expressed as a number of
	 *         pixels.
	 */
	public int getTileLength(World world){
		return world.getTileLength();
	}
	
	/**
	 * Returns the tile positions of all tiles within the given rectangular
	 * region.
	 * 
	 * @param world
	 *            The world from which the tile positions should be returned.
	 * @param pixelLeft
	 *            The x-coordinate of the left side of the rectangular region.
	 * @param pixelBottom
	 *            The y-coordinate of the bottom side of the rectangular region.
	 * @param pixelRight
	 *            The x-coordinate of the right side of the rectangular region.
	 * @param pixelTop
	 *            The y-coordinate of the top side of the rectangular region.
	 * 
	 * @return An array of tile positions, where each position (x_T, y_T) is
	 *         represented as an array of 2 elements, containing the horizontal
	 *         (x_T) and vertical (y_T) coordinate of a tile in that order.
	 *         The returned array is ordered from left to right,
	 *         bottom to top: all positions of the bottom row (ordered from
	 *         small to large x_T) precede the positions of the row above that.
	 * 
	 */
	public int[][] getTilePositionsIn(World world, int pixelLeft, int pixelBottom,
			int pixelRight, int pixelTop){
		return world.getTilePositionsIn(pixelLeft, pixelBottom, pixelRight, pixelTop);
	}
	
	/**
	 * Return the coordinates of the rectangular visible window that moves
	 * together with Mazub.
	 * 
	 * @return The pixel coordinates of the visible window, in the order
	 *         <b>left, bottom, right, top</b>.
	 */
	public int[] getVisibleWindow(World world){
		return world.getVisibleWindow();
	}
	
	/**
	 * Returns the size of the given game world, in number of pixels.
	 * 
	 * @param world
	 *            The world for which to return the size.
	 * @return The size of the game world, in pixels, as an array of two
	 *         elements: width (X) and height (Y), in that order.
	 */
	public int[] getWorldSizeInPixels(World world){
		return world.getWorldSize();
	}
	
	/**
	 * Starts the game that is played in the given world.
	 * After this method has been invoked, no further game objects will be added
	 * via {@link IFacadePart2#addPlant(World, Plant)},
	 * {@link IFacadePart2#addShark(World, Shark)},
	 * {@link IFacadePart2#addSlime(World, Slime)}, or
	 * {@link IFacadePart2#setMazub(World, Mazub)}), and no geological features
	 * will be changed via
	 * {@link IFacadePart2#setGeologicalFeature(World, int, int, int)}.
	 * 
	 * @param The
	 *            world for which to start the game.
	 */
	public void startGame(World world){
		world.startGame();
	}
	
	/**
	 * Advance the time for the world and all its objects by the given amount.
	 * 
	 * This method replaces {@link IFacadePart2#advanceTime(Mazub, double)}.
	 * 
	 * @param world
	 *            The world whose time needs to advance
	 * @param dt
	 *            The time interval (in seconds) by which to advance the given
	 *            world's time.
	 */
	public void advanceTime(World world, double dt) throws ModelException {
		try {
			world.advanceTime(dt);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("The given period of time dt is invalid!");
		}
	}
	
	/**
	 * Returns whether the game played in the given world has finished and the
	 * player has won. The player wins when Mazub has reached the target tile.
	 * 
	 * @param world
	 *            The world for which to check whether the player won
	 * @return true if the game is over and the player has won; false otherwise.
	 */
	public boolean didPlayerWin(World world){
		return world.didPlayerWin();
	}
	
	/**
	 * Returns whether the game, played in the given game world, is over.
	 * The game is over when Mazub has died, or has reached the target tile.
	 * 
	 * @param world
	 *            The world for which to check whether the game is over
	 * @return true if the game is over, false otherwise.
	 */
	public boolean isGameOver(World world){
		return world.isGameOver();
	}
	
	/**
	 * Creates a new slime, located at the provided pixel location (x, y).
	 * The returned slime should not belong to a world.
	 * 
	 * @param x
	 *            The x-coordinate of the slime's initial position
	 * @param y
	 *            The y-coordinate of the slime's initial position
	 * @param sprites
	 *            An array of sprites for the new slime
	 * @param school
	 *            The initial school to which the new slime belongs
	 * 
	 * @return A new slime, located at the provided location and part of the
	 *         given school. The returned slime should not belong to a world.
	 */
	public Slime createSlime(int pixelLeftX, int pixelBottomY, Sprite[] sprites, School school){
		Slime slime = new Slime(pixelLeftX, pixelBottomY, sprites, school, null);
		return slime;
	}
	
	/**
	 * Creates a new slime, located at the provided pixel location (x, y).
	 * The returned slime should not belong to a world.
	 * 
	 * @param x
	 *            The x-coordinate of the slime's initial position
	 * @param y
	 *            The y-coordinate of the slime's initial position
	 * @param sprites
	 *            An array of sprites for the new slime
	 * @param school
	 *            The initial school to which the new slime belongs
	 * @param program
	 *            The program that this slime should execute, or null if the
	 *            slime should follow its default behavior.
	 * 
	 * @return A new slime, located at the provided location and part of the
	 *         given school. The returned slime should not belong to a world.
	 */
	public Slime createSlimeWithProgram(int x, int y, Sprite[] sprites,
			School school, Program program) {
		Slime slime = new Slime(x, y, sprites, school, program);
		program.setGameObject(slime);
		return slime;
	}
	
	/**
	 * Add the given slime as a game object to the given world.
	 * 
	 * @param world
	 *            The world to which the slime should be added.
	 * @param slime
	 *            The slime that needs to be added to the world.
	 */
	public void addSlime(World world, Slime slime){
		world.addSlime(slime);
		slime.setWorld(world);
	}
	
	/**
	 * Returns all the slimes currently located in the given world.
	 * 
	 * @param world
	 *            The world for which to retrieve all slimes.
	 * @return All slimes that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each slime may only be returned once.
	 */
	public ArrayList<Slime> getSlimes(World world){
		return world.getSlimes();
	}
	
	/**
	 * Return the current sprite image for the given slime.
	 * 
	 * @param slime
	 *            The slime for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given slime, determined by its
	 *         orientation as defined in the assignment.
	 */
	public Sprite getCurrentSprite(Slime slime){
		return slime.getCurrentSprite();
	}
	
	/**
	 * Returns the current location of the given slime.
	 * 
	 * @param slime
	 *            The slime of which to find the location
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given slime's bottom left pixel in the world.
	 */
	public int[] getLocation(Slime slime){
		int[] location = new int[]{(int) slime.getPosition()[0], 
				(int) slime.getPosition()[1]};
		return location;
	}
	
	/**
	 * Returns the current school to which the given slime belongs.
	 * 
	 * @param slime
	 *            The slime for which to retrieve the school.
	 * 
	 * @return The current school of the given slime.
	 */
	public School getSchool(Slime slime){
		return slime.getSchool();
	}
	
	/**
	 * Creates a new slime school.
	 * 
	 * @return A new school for slimes, without any members.
	 */
	public School createSchool(){
		School school = new School();
		return school;
	}
	
	/**
	 * Creates a new shark, located at the provided pixel location (x, y).
	 * The returned shark should not belong to a world.
	 * 
	 * @param x
	 *            The x-coordinate of the shark's initial position
	 * @param y
	 *            The y-coordinate of the shark's initial position
	 * @param sprites
	 *            An array of sprites for the new shark
	 * 
	 * @return A new shark, located at the provided location. The returned shark
	 *         should not belong to a world.
	 */
	public Shark createShark(int pixelLeftX, int pixelBottomY, Sprite[] sprites){
		Shark shark = new Shark(pixelLeftX, pixelBottomY, sprites, null);
		return shark;
	}
	
	/**
	 * Creates a new shark, located at the provided pixel location (x, y).
	 * The returned shark should not belong to a world.
	 * 
	 * @param x
	 *            The x-coordinate of the shark's initial position
	 * @param y
	 *            The y-coordinate of the shark's initial position
	 * @param sprites
	 *            An array of sprites for the new shark
	 * @param program
	 *            The program that this shark should execute, or null if the
	 *            shark should follow its default behavior.
	 * 
	 * @return A new shark, located at the provided location. The returned shark
	 *         should not belong to a world.
	 */
	public Shark createSharkWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		Shark shark = new Shark(x, y, sprites, program);
		program.setGameObject(shark);
		return shark;
	}
	
	/**
	 * Add the given shark as a game object to the given world.
	 * 
	 * @param world
	 *            The world to which the shark should be added.
	 * @param shark
	 *            The shark that needs to be added to the world.
	 */
	public void addShark(World world, Shark shark){
		world.addShark(shark);
		shark.setWorld(world);
	}
	
	/**
	 * Returns all the sharks currently located in the given world.
	 * 
	 * @param world
	 *            The world for which to retrieve all sharks.
	 * @return All sharks that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each shark may only be returned once.
	 */
	public ArrayList<Shark> getSharks(World world){
		return world.getSharks();
	}
	
	/**
	 * Return the current sprite image for the given shark.
	 * 
	 * @param shark
	 *            The shark for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given shark, determined by its
	 *         orientation as defined in the assignment.
	 */
	public Sprite getCurrentSprite(Shark shark){
		return shark.getCurrentSprite();
	}
	
	/**
	 * Returns the current location of the given shark.
	 * 
	 * @param shark
	 *            The shark of which to find the location
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given shark's bottom left pixel in the world.
	 */
	public int[] getLocation(Shark shark){
		int[] location = new int[]{(int) shark.getPosition()[0], 
				(int) shark.getPosition()[1]};
		return location;
	}
	
	/**
	 * Creates a new plant, located at the provided pixel location (x, y).
	 * The returned plant should not belong to a world.
	 * 
	 * @param x
	 *            The x-coordinate of the plant's initial position
	 * @param y
	 *            The y-coordinate of the plant's initial position
	 * @param sprites
	 *            An array of sprites for the new plant
	 * 
	 * @return A new plant, located at the provided location. The returned plant
	 *         should not belong to a world.
	 */
	public Plant createPlant(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		Plant plant = new Plant(pixelLeftX, pixelBottomY, sprites, null);
		return plant;
	}
	
	/**
	 * /**
	 * Creates a new plant, located at the provided pixel location (x, y).
	 * The returned plant should not belong to a world.
	 * 
	 * @param x
	 *            The x-coordinate of the plant's initial position
	 * @param y
	 *            The y-coordinate of the plant's initial position
	 * @param sprites
	 *            An array of sprites for the new plant
	 * @param program
	 *            The program that this plant should execute, or null if the
	 *            plant should follow its default behavior.
	 * 
	 * @return A new plant, located at the provided location. The returned plant
	 *         should not belong to a world.
	 */
	public Plant createPlantWithProgram(int x, int y, Sprite[] sprites,
			Program program) {
		Plant plant = new Plant(x, y, sprites, program);
		program.setGameObject(plant);
		return plant;
	}
	
	/**
	 * Add the given plant as a game object to the given world.
	 * 
	 * @param world
	 *            The world to which the plant should be added.
	 * @param plant
	 *            The plant that needs to be added to the world.
	 */
	public void addPlant(World world, Plant plant){
		world.addPlant(plant);
		plant.setWorld(world);
	}
	
	/**
	 * Returns all the plants currently located in the given world.
	 * 
	 * @param world
	 *            The world for which to retrieve all plants.
	 * @return All plants that are located somewhere in the given world. There
	 *         are no restrictions on the type or order of the returned
	 *         collection, but each plant may only be returned once.
	 */
	public ArrayList<Plant> getPlants(World world){
		return world.getPlants();
	}
	
	/**
	 * Return the current sprite image for the given plant.
	 * 
	 * @param plant
	 *            The plant for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given plant, determined by its
	 *         orientation as defined in the assignment.
	 */
	public Sprite getCurrentSprite(Plant plant){
		return plant.getCurrentSprite();
	}
	
	/**
	 * Returns the current location of the given plant.
	 * 
	 * @param plant
	 *            The plant of which to find the location
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given plant's bottom left pixel in the world.
	 */
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
		Mazub alien = new Mazub(pixelLeftX, pixelBottomY, sprites, null);
		return alien;
	}
	
	/**
	 * Sets the given alien as the player's character in the given world.
	 * 
	 * @param world
	 *            The world for which to set the player's character.
	 * @param mazub
	 *            The alien to be set as the player's character.
	 */
	public void setMazub(World world, Mazub alien){
		world.setMazub(alien);
		alien.setWorld(world);
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
	public int[] getLocation(Mazub alien) throws ModelException {
		int[] location = new int[]{(int) alien.getPosition()[0], 
				(int) alien.getPosition()[1]};
		if (!alien.isValidPosition(location)) {
			throw new ModelException("This alien is on an invalid position!");
		}
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
		alien.startMoveHorizontally(SelfMadeDirection.LEFT);
	}
	
	/**
	 * Make the given alien move right.
	 * 
	 * @param alien
	 *            The alien that has to start moving right.
	 */
	public void startMoveRight(Mazub alien) {
		alien.startMoveHorizontally(SelfMadeDirection.RIGHT);
	}
	
	/**
	 * End the given alien's left move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving left.
	 */
	public void endMoveLeft(Mazub alien) {
		alien.endMoveHorizontally(SelfMadeDirection.LEFT);
	}
	
	/**
	 * End the given alien's right move.
	 * 
	 * @param alien
	 *            The alien that has to stop moving right.
	 */
	public void endMoveRight(Mazub alien) {
		alien.endMoveHorizontally(SelfMadeDirection.RIGHT);
	}
	
	/**
	 * Make the given alien duck.
	 * 
	 * @param alien
	 *            The alien that has to start ducking.
	 */
	public void startDuck(Mazub alien) throws ModelException {
		if (alien.isDucking())
			throw new ModelException("The alien is already ducking!");
		alien.startDuck();
	}
	
	/**
	 * End the given alien's ducking.
	 * 
	 * @param alien
	 *            The alien that has to stop ducking.
	 */
	public void endDuck(Mazub alien) throws ModelException {
		if (!alien.canEndDuck())
			throw new ModelException("The alien can not stop ducking here!");
		if (alien.canEndDuck())
			alien.endDuck();
	}
	
	/**
	 * Make the given alien jump.
	 * 
	 * @param alien
	 *            The alien that has to start jumping.
	 */
	public void startJump(Mazub alien) throws ModelException {
		if (!alien.isValidJumpingPosition(alien.getPosition()))
			throw new ModelException("The alien can not jump when not on solid terrain!");
		alien.startJump();
	}
	
	/**
	 * End the given alien's jump.
	 * 
	 * @param alien
	 *            The alien that has to stop jumping.
	 */ 
	public void endJump(Mazub alien) throws ModelException {
		if (!alien.isJumping())
			throw new ModelException("The alien has already stopped jumping");
		alien.endJump();
	}
	
	
	/**
	 * Returns the current number of hitpoints of the given alien.
	 */
	public int getNbHitPoints(Mazub alien){
		return alien.getNbHitPoints();
	}
	
	/**
	 * Returns whether the given alien is currently immune against enemies.
	 * 
	 * @param alien
	 *            The alien for which to retrieve the immunity status.
	 * @return True if the given alien is immune against other enemies (i.e.,
	 *         there are no interactions between the alien and enemy objects).
	 */
	public boolean isImmune(Mazub alien){
		return alien.isImmune();
	}
	
	/**
	 * Create a new instance of Buzam, at the specified location, and with the
	 * specified sprites.
	 * 
	 * @param pixelLeftX
	 *            The x location of the bottom left pixel of Buzam
	 * @param pixelBottomY
	 *            The y location of the bottom left pixel of Buzam
	 * @param sprites
	 *            The sprites to be used by Buzam (following the same structure
	 *            as the table for Mazub in the assignment).
	 */
	public Buzam createBuzam(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		Buzam buzam = new Buzam(pixelLeftX, pixelBottomY, sprites, null);
		return buzam;
	}

	/**
	 * Create a new instance of Buzam, at the specified location, and with the
	 * specified sprites.
	 * 
	 * @param pixelLeftX
	 *            The x location of the bottom left pixel of Buzam
	 * @param pixelBottomY
	 *            The y location of the bottom left pixel of Buzam
	 * @param sprites
	 *            The sprites to be used by Buzam (following the same structure
	 *            as the table for Mazub in the assignment).
	 * @param program
	 *            The program to execute, or null of Buzam should not execute a
	 *            program.
	 */
	public Buzam createBuzamWithProgram(int pixelLeftX, int pixelBottomY,
			Sprite[] sprites, Program program) {
		Buzam buzam = new Buzam(pixelLeftX, pixelBottomY, sprites, program);
		program.setGameObject(buzam);
		return buzam;
	}
	
	/**
	 * Add the given Buzam to the given world.
	 * 
	 * @param world
	 *            The world to which Buzam should be added.
	 * @param plant
	 *            The Buzam object that needs to be added to the world.
	 */
	public void addBuzam(World world, Buzam buzam) {
		world.setBuzam(buzam);
		buzam.setWorld(world);
	}

	/**
	 * Returns the current location of the given Buzam object.
	 * 
	 * @param alien
	 *            The alien of which to find the location
	 * @return An array, consisting of 2 integers {x, y}, that represents the
	 *         coordinates of the given alien's bottom left pixel in the world.
	 */
	public int[] getLocation(Buzam alien) {
		return alien.getPosition();
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
	public double[] getVelocity(Buzam alien) {
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
	public double[] getAcceleration(Buzam alien) {
		double[] acceleration = new double[] {alien.getHorizontalAcceleration(), 
				alien.getVerticalAcceleration()};
		return acceleration;
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
	public int[] getSize(Buzam alien) {
		Sprite sprite = alien.getCurrentSprite();
		int[] size = new int[] {sprite.getWidth(), sprite.getHeight()};
		return size;
	}

	/**
	 * Return the current sprite image for the given alien.
	 * 
	 * @param alien
	 *            The alien for which to get the current sprite image.
	 * 
	 * @return The current sprite image for the given alien. Buzam, the evil
	 *         twin of Mazub, follows the same rules as Mazub with respect to
	 *         its sprites.
	 */
	public Sprite getCurrentSprite(Buzam alien) {
		return alien.getCurrentSprite();
	}

	/**
	 * Returns the current number of hitpoints of the given alien.
	 */
	public int getNbHitPoints(Buzam alien) {
		return alien.getNbHitPoints();
	}

	/**
	 * Parse the given text into the program.
	 * Follow the instructions given in {@link ProgramParser}.
	 * 
	 * @param text
	 *            The text to parse
	 * 
	 * @return A {@link ParseOutcome}, namely
	 *         ParseOutcome.Success if parsing was successful, or
	 *         ParseOutcome.Failure if parsing was not successful.
	 */
	public ParseOutcome<?> parse(String text) {
		IProgramFactory<Expression, Statement, Type<?>, Program> factory = 
				new ProgramFactory();
		ProgramParser<Expression, Statement, Type<?>, Program> parser = new ProgramParser<>(factory);
		Optional<Program> parseResult = parser.parseString(text);
		if (parseResult.isPresent()){
			Program program =parseResult.get();
			ParseOutcome<?> result = ParseOutcome.success(program);
			return result;
		}
		else{
			List<String> errors = parser.getErrors();
			ParseOutcome<?> result = ParseOutcome.failure(errors);
			return result;
		}
	}

	/**
	 * Returns whether the given program is well-formed according to the rules
	 * in the assignment.
	 * 
	 * @param program
	 *            The program to check.
	 * @return true if the program is well-formed; false otherwise.
	 */
	public boolean isWellFormed(Program program) {
		return program.isWellFormed();
	}
}
