package jumpingalien.part2.tests;

import static jumpingalien.tests.util.TestUtils.doubleArray;
import static jumpingalien.tests.util.TestUtils.intArray;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import jumpingalien.model.*;
import jumpingalien.part2.facade.Facade;
import jumpingalien.part2.facade.IFacadePart2;
import jumpingalien.util.Sprite;
import jumpingalien.util.Util;

import org.junit.Test;

public class PartialFacadeTest {

	public static final int FEATURE_AIR = 0;
	public static final int FEATURE_SOLID = 1;
	public static final int FEATURE_WATER = 2;
	public static final int FEATURE_MAGMA = 3;

	@Test
	public void testGetBottomLeftPixelOfRandomTile() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(5, 4, 3, 1, 1, 1, 1);
		assertArrayEquals(intArray(15, 10),
				facade.getBottomLeftPixelOfTile(world, 3, 2));
	}

	@Test
	public void testZeroAccellerationOnGround() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);

		assertArrayEquals(doubleArray(0.0, 0.0), facade.getAcceleration(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testTilesInRegion() {
		IFacadePart2 facade = new Facade();

		World world = facade.createWorld(50, 3, 3, 1, 1, 1, 1);

		int[][] actualTiles = facade
				.getTilePositionsIn(world, 20, 20, 105, 105);
		int[][] expectedTiles = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 0, 1 },
				{ 1, 1 }, { 2, 1 }, { 0, 2 }, { 1, 2 }, { 2, 2 } };
		assertArrayEquals(expectedTiles, actualTiles);
	}

	@Test
	public void startMoveRightCorrect() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveRight(alien);
		facade.advanceTime(world, 0.1);

		// x_new [m] = 0 + 1 [m/s] * 0.1 [s] + 1/2 0.9 [m/s^2] * (0.1 [s])^2 =
		// 0.1045 [m] = 10.45 [cm], which falls into pixel (10, 0)

		assertArrayEquals(intArray(10, 499), facade.getLocation(alien));
	}

	@Test
	public void startMoveRightMaxSpeedAtRightTime() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);
		Mazub alien = facade.createMazub(0, 499, spriteArrayForSize(3, 3));
		facade.setMazub(world, alien);
		facade.startMoveRight(alien);
		// maximum speed reached after 20/9 seconds
		for (int i = 0; i < 100; i++) {
			facade.advanceTime(world, 0.2 / 9);
		}

		assertArrayEquals(doubleArray(3, 0), facade.getVelocity(alien),
				Util.DEFAULT_EPSILON);
	}

	@Test
	public void testWalkAnimationLastFrame() {
		IFacadePart2 facade = new Facade();

		// 2 vertical tiles, size 500px
		// ....
		// a...
		// XXXX
		// XXXX
		World world = facade.createWorld(500, 1, 2, 1, 1, 0, 1);
		facade.setGeologicalFeature(world, 0, 0, FEATURE_SOLID);

		int m = 10;
		Sprite[] sprites = spriteArrayForSize(3, 3, 10 + 2 * m);
		Mazub alien = facade.createMazub(0, 499, sprites);
		facade.setMazub(world, alien);

		facade.startMoveRight(alien);

		facade.advanceTime(world, 0.005);
		for (int i = 0; i < m; i++) {
			facade.advanceTime(world, 0.075);
		}

		assertEquals(sprites[8 + m], facade.getCurrentSprite(alien));
	}
	
	@Test
	public void testSetAndGetGeologicalFeature(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(5, 4, 3, 1, 1, 0, 1);
		world.setGeologicalFeature(0, 0, 1);
		assertEquals (world.getGeologicalFeature(0, 0), 1);
	}
	
	@Test
	public void testSetandGetWorld(){
		IFacadePart2 facade = new Facade();
		School school1 = facade.createSchool();
		School school2 = facade.createSchool();
		Slime slime = facade.createSlime(0, 0, spriteArrayForSize(3,3), school1);		
		slime.setSchool(school2);
		assertEquals(school2, slime.getSchool());
	}
	
	@Test
	public void testAddAndGetPlants(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(5, 4, 3, 1, 1, 0, 1);
		Plant plant = facade.createPlant(0, 0, spriteArrayForSize(3,3));
		facade.addPlant(world, plant);
		ArrayList<Plant> plants = new ArrayList<Plant>();
		plants.add(plant);
		assertEquals(world.getPlants(), plants);
	}
	
	@Test
	public void testGetVisibleWindow(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(5, 4, 3, 1, 1, 0, 1);
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(3,3));
		facade.setMazub(world, alien);
		int[] visibleWindow = new int[]{0,0,1,1};
		assertEquals(world.getVisibleWindow(), visibleWindow);
	}
	
	@Test
	public void testFallingWhenNotOnSolidGround(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(5, 4, 3, 1, 1, 0, 1);
		Mazub alien = facade.createMazub(0, 10, spriteArrayForSize(3,3));
		facade.setMazub(world, alien);
		world.advanceTime(0.1);
		assert (alien.getVerticalVelocity() != 0);
	}
	
	@Test
	public void testStartMoveHorizontallyMazub(){
		IFacadePart2 facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(3,3));
		facade.startMoveRight(alien);
		assert (alien.getHorizontalAcceleration() == 0.9);
	}
	
	@Test
	public void testEndMoveHorizontallyMazub(){
		IFacadePart2 facade = new Facade();
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(3,3));
		facade.startMoveRight(alien);
		facade.startMoveLeft(alien);
		facade.endMoveRight(alien);
		assert (alien.getHorizontalAcceleration() == -0.9);
	}
	
	@Test
	public void testGetAndSetMazub(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(5, 4, 3, 1, 1, 0, 1);
		Mazub alien = facade.createMazub(0, 0, spriteArrayForSize(3,3));
		facade.setMazub(world, alien);
		assertEquals(world.getMazub(), alien);
	}
	
	@Test 
	public void testGetTileLength(){
		IFacadePart2 facade = new Facade();
		World world = facade.createWorld(5, 4, 3, 1, 1, 0, 1);
		assert (world.getTileLength() == 4);
	}
}