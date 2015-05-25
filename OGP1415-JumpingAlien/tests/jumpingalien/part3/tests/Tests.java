package jumpingalien.part3.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jumpingalien.model.School;
import jumpingalien.model.World;
import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.Shark;
import jumpingalien.model.Slime;
import jumpingalien.model.Program;
import jumpingalien.model.ProgramFactory;
import jumpingalien.model.expression.*;
import jumpingalien.model.statement.*;
import jumpingalien.model.type.*;
import jumpingalien.part3.facade.Facade;
import jumpingalien.part3.facade.IFacadePart3;
import jumpingalien.part3.programs.IProgramFactory;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import static jumpingalien.tests.util.TestUtils.spriteArrayForSize;
import static org.junit.Assert.assertEquals;

public class Tests{
	private IFacadePart3 facade = new Facade();
	private IProgramFactory<Expression, Statement, Type<?>, Program> programFactory = 
			new ProgramFactory();
	private Mazub mazub = facade.createMazub(0, 0, spriteArrayForSize(3,3));
	private SkipS testStatement = new SkipS(null);
	private Map<String, Type<?>> testVariables = new HashMap<>();
	private Program program = this.programFactory.createProgram(testStatement, testVariables);
	private World world = new World(1, 1, 1,1, 1, 1,1);
	private Plant plant = facade.createPlant(0, 0, spriteArrayForSize(3,3));
	private Shark shark = facade.createShark(0, 0, spriteArrayForSize(3,3));
	private School school = new School();
	private Slime slime = facade.createSlime(0, 0, spriteArrayForSize(3,3), school);
	private SelfE selfExpr = (SelfE) programFactory.createSelf(null);
	
	//EXPRESSIONS
	
	@Test
	public void testDoubleNullAddition(){
		DoubleConstant left = (DoubleConstant) programFactory.createDoubleConstant(1, null);
		NullE right = (NullE) programFactory.createNull(null);
		Addition addition = (Addition) programFactory.createAddition(left, right, null);
		assertEquals(1.0, addition.evaluate(program));
	}
	
	@Test
	public void testSubtractionSqrt(){
		DoubleConstant left = (DoubleConstant) programFactory.createDoubleConstant(4, null);
		DoubleConstant right = (DoubleConstant) programFactory.createDoubleConstant(2, null);
		Subtraction subtraction = (Subtraction) programFactory.createSubtraction(left, right, null);
		SqrtE sqrt = (SqrtE) programFactory.createSqrt(left, null);
		assertEquals(2.0, subtraction.evaluate(program));
		assertEquals(2.0, sqrt.evaluate(program));
	}
	
	@Test
	public void testAndOr(){
		BooleanE left = (BooleanE) programFactory.createTrue(null);
		BooleanE right = (BooleanE) programFactory.createFalse(null);
		AndE and = (AndE) programFactory.createAnd(left, right, null);
		OrE or = (OrE) programFactory.createOr(left, right, null);
		assertEquals(false, and.evaluate(program));
		assertEquals(true, or.evaluate(program));
	}
	
	@Test
	public void testMultiplicationDivision(){
		DoubleConstant left = (DoubleConstant) programFactory.createDoubleConstant(4, null);
		DoubleConstant right = (DoubleConstant) programFactory.createDoubleConstant(2, null);
		Division division = (Division) programFactory.createDivision(left, right, null);
		Multiplication multiplication = (Multiplication) programFactory.createMultiplication(left, 
				right, null);
		assertEquals(2.0, division.evaluate(program));
		assertEquals(8.0, multiplication.evaluate(program));
	}
	
	@Test
	public void testGetHeight(){
		program.setGameObject(mazub);
		GetHeight getHeight = (GetHeight) programFactory.createGetHeight(selfExpr, null);
		double height = (double)facade.getSize(mazub)[1];
		assertEquals(height, getHeight.evaluate(program));
	}
	
	@Test
	public void testGetHP(){
		program.setGameObject(mazub);
		GetHP getHP = (GetHP) programFactory.createGetHitPoints(selfExpr, null);
		double hp = (double)facade.getNbHitPoints(mazub);
		assertEquals(hp, getHP.evaluate(program));
	}
	
	@Test
	public void testGetWidth(){
		program.setGameObject(mazub);
		GetWidth getWidth = (GetWidth) programFactory.createGetWidth(selfExpr, null);
		double width = (double)facade.getSize(mazub)[0];
		assertEquals(width, getWidth.evaluate(program));
	}
	
	@Test
	public void testGetXY(){
		program.setGameObject(mazub);
		GetX getX = (GetX) programFactory.createGetX(selfExpr, null);
		GetY getY = (GetY) programFactory.createGetY(selfExpr, null);
		double x = (double)facade.getLocation(mazub)[0];
		double y = (double)facade.getLocation(mazub)[1];
		assertEquals(x, getX.evaluate(program));
		assertEquals(y, getY.evaluate(program));
	}
	
	@Test
	public void testBooleanDoubleExpressions(){
		DoubleConstant left = (DoubleConstant) programFactory.createDoubleConstant(2, null);
		DoubleConstant right = (DoubleConstant) programFactory.createDoubleConstant(2, null);
		Equals equals = (Equals) programFactory.createEquals(left, right, null);
		GreaterThan greaterThan = (GreaterThan) programFactory.createGreaterThan(left, right, null);
		GreaterThanOrEqual greaterThanOrEqual= (GreaterThanOrEqual) 
				programFactory.createGreaterThanOrEqualTo(left, right, null);
		LessThan lessThan = (LessThan) programFactory.createLessThan(left, right, null);
		LessThanOrEqual lessThanOrEqual = (LessThanOrEqual) 
				programFactory.createLessThanOrEqualTo(left, right, null);
		NotEquals notEquals = (NotEquals) programFactory.createNotEquals(left, right, null);
		assertEquals(false, notEquals.evaluate(program));
		assertEquals(true, equals.evaluate(program));
		assertEquals(false, greaterThan.evaluate(program));
		assertEquals(true, greaterThanOrEqual.evaluate(program));
		assertEquals(false, lessThan.evaluate(program));
		assertEquals(true, lessThanOrEqual.evaluate(program));
	}
	
	@Test
	public void testIsDead(){
		program.setGameObject(mazub);
		IsDead isDead = (IsDead) programFactory.createIsDead(selfExpr, null);
		assert(!(boolean)isDead.evaluate(program));
	}
	
	@Test
	public void testIsAir(){
		DoubleConstant air = (DoubleConstant) programFactory.createDoubleConstant(0, null);
		IsAir isAir = (IsAir) programFactory.createIsAir(air, null);
		assert((boolean)isAir.evaluate(program));
	}
	
	@Test
	public void testIsTerrain(){
		DoubleConstant terrain = (DoubleConstant) programFactory.createDoubleConstant(1, null);
		IsTerrain isTerrain = (IsTerrain) programFactory.createIsTerrain(terrain, null);
		assert((boolean)isTerrain.evaluate(program));
	}
	
	@Test
	public void testIsWater(){
		DoubleConstant water = (DoubleConstant) programFactory.createDoubleConstant(2, null);
		IsWater isWater = (IsWater) programFactory.createIsWater(water, null);
		assert((boolean)isWater.evaluate(program));
	}
	
	@Test
	public void testIsMagma(){
		DoubleConstant magma = (DoubleConstant) programFactory.createDoubleConstant(3, null);
		IsMagma isMagma = (IsMagma) programFactory.createIsMagma(magma, null);
		assert((boolean)isMagma.evaluate(program));
	}
	
	@Test
	public void testIsMazub(){
		program.setGameObject(mazub);
		IsMazub isMazub = (IsMazub) programFactory.createIsMazub(selfExpr, null);
		assert((boolean)isMazub.evaluate(program));
	}
	
	@Test 
	public void testIsPlant(){
		program.setGameObject(plant);
		IsPlant isPlant = (IsPlant) programFactory.createIsPlant(selfExpr, null);
		assert((boolean)isPlant.evaluate(program));
	}
	
	@Test
	public void testIsShark(){
		program.setGameObject(shark);
		IsShark isShark = (IsShark) programFactory.createIsShark(selfExpr, null);
		assert((boolean)isShark.evaluate(program));
	}
	
	@Test
	public void testIsSlime(){
		program.setGameObject(slime);
		IsSlime isSlime = (IsSlime) programFactory.createIsSlime(selfExpr, null);
		assert((boolean)isSlime.evaluate(program));
	}

	//STATEMENTS
	
	@Test
	public void testStartDuck(){
		StartDuck startDuck = (StartDuck) programFactory.createStartDuck(null);
		program.setGameObject(mazub);
		startDuck.execute(program, null);
		assert(mazub.isDucking());
	}
	
	@Test 
	public void testStartJump(){
		StartJump startJump = (StartJump) programFactory.createStartJump(null);
		mazub.setWorld(world);
		program.setGameObject(mazub);
		startJump.execute(program, null);
		assert(mazub.isJumping());
	}
	
	@Test
	public void testStopDuck(){
		StartDuck startDuck = (StartDuck) programFactory.createStartDuck(null);
		StopDuck stopDuck = (StopDuck) programFactory.createStopDuck(null);
		program.setGameObject(mazub);
		startDuck.execute(program, null);
		stopDuck.execute(program, null);
		assert(!mazub.isDucking());
	}
	
	@Test
	public void testStopJump(){
		StartJump startJump = (StartJump) programFactory.createStartJump(null);
		StopJump stopJump = (StopJump) programFactory.createStopJump(null);
		mazub.setWorld(world);
		program.setGameObject(mazub);
		startJump.execute(program, null);
		stopJump.execute(program, null);
		assert(!mazub.isJumping());
	}
	
	@Test
	public void testStopRun(){
		DirectionConstant direction = (DirectionConstant) programFactory.createDirectionConstant(
				Direction.RIGHT, null);
		StartRun startRun = (StartRun) programFactory.createStartRun(direction, null);
		StopRun stopRun = (StopRun) programFactory.createStopRun(direction, null);
		program.setGameObject(mazub);
		startRun.execute(program, null);
		stopRun.execute(program, null);
		assert(mazub.getHorizontalVelocity() == 0);
	}
	
	@Test
	public void testSequenceAndAssignment(){
		BooleanType testType = (BooleanType) programFactory.getBoolType();
		testVariables.put("testnaam", testType);
		testVariables.put("testnaam1", testType);
		BooleanE booleanE = (BooleanE) programFactory.createTrue(null);
		Assignment assignment = (Assignment) programFactory.createAssignment("testnaam", 
				testType, booleanE, null);
		Assignment assignment1 = (Assignment) programFactory.createAssignment("testnaam1", 
				testType, booleanE, null);
		List<Statement> statements = new ArrayList<Statement>();
		statements.add(assignment);
		statements.add(assignment1);
		Sequence localStatement = (Sequence) programFactory.createSequence(statements, null);
		Program localProgram = new Program(localStatement, testVariables);
		localProgram.run();
		assertEquals(true, localProgram.getObjectByName("testnaam", testType));
		assertEquals(true, localProgram.getObjectByName("testnaam1", testType));
	}
	
	@Test
	public void testIfAndStartRunAndSkip(){
		DirectionConstant direction = (DirectionConstant) programFactory.createDirectionConstant(
				Direction.RIGHT, null);
		StartRun startRun = (StartRun) programFactory.createStartRun(direction, null);
		SkipS skip = (SkipS) programFactory.createSkip(null);
		IsMoving isMoving = (IsMoving) programFactory.createIsMoving(selfExpr, direction, null);
		IfS ifS = (IfS) programFactory.createIf(isMoving, startRun, skip, null);
		program.setGameObject(mazub);
		ifS.execute(program, null);
		assert(mazub.getHorizontalVelocity() > 0);
	}
	
	@Test
	public void testWhile(){
		DirectionConstant direction = (DirectionConstant) programFactory.createDirectionConstant(
				Direction.RIGHT, null);
		program.setGameObject(mazub);
		StartRun startRun = (StartRun) programFactory.createStartRun(direction, null);
		StopRun stopRun = (StopRun) programFactory.createStopRun(direction, null);
		IsMoving isMoving = (IsMoving) programFactory.createIsMoving(selfExpr, direction, null);
		WhileS whileS = (WhileS) programFactory.createWhile(isMoving, stopRun, null);
		startRun.execute(program, null);
		whileS.execute(program, null);
		assert(!mazub.isMovingHorizontally());
	}
	
	//TYPES
	
	@Test
	public void testBooleanType(){
		BooleanType booleanType = (BooleanType) programFactory.getBoolType();
		assert(booleanType.getValue() instanceof Boolean);
	}
	
	@Test
	public void testDirectionType(){
		DirectionType directionType = (DirectionType) programFactory.getDirectionType();
		assert(directionType.getValue() instanceof Direction);
	}
	
	@Test
	public void testDoubleType(){
		DoubleType doubleType = (DoubleType) programFactory.getDoubleType();
		assert(doubleType.getValue() instanceof Double);
	}
	
	@Test
	public void testGameObjectType(){
		GameObjectType gameObjectType = (GameObjectType) programFactory.getGameObjectType();
		assert(gameObjectType.getValue() instanceof GameObject);
	}
}
