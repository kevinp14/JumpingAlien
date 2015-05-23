package jumpingalien.model;

import java.util.*;

import jumpingalien.model.expression.*;
import jumpingalien.model.expression.RandomE;
import jumpingalien.model.statement.*;
import jumpingalien.model.type.*;
import jumpingalien.part3.programs.*;


public class ProgramFactory implements IProgramFactory<Expression, Statement, Type<?>, Program>{
	
	public ProgramFactory(){}
	
	@Override
	public Expression createReadVariable(String variableName, Type<?> variableType,
			SourceLocation sourceLocation) {
		ReadVariable readVariable = new ReadVariable(variableName, variableType, sourceLocation);
		return readVariable;
	}

	@Override
	public Expression createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		DoubleConstant doubleConstant = new DoubleConstant(value, sourceLocation);
		return doubleConstant;
	}

	@Override
	public Expression createTrue(SourceLocation sourceLocation) {
		BooleanE booleanE = new BooleanE(true, sourceLocation);
		return booleanE;
	}

	@Override
	public Expression createFalse(SourceLocation sourceLocation) {
		BooleanE booleanE = new BooleanE(false, sourceLocation);
		return booleanE;
	}

	@Override
	public Expression createNull(SourceLocation sourceLocation) {
		NullE nullE = new NullE(sourceLocation);
		return nullE;
	}

	
	@Override 
	public Expression createSelf(SourceLocation sourceLocation) {
		SelfE selfE = new SelfE(sourceLocation);
		return selfE;
	}

	@Override
	public Expression createDirectionConstant(Direction value,
			SourceLocation sourceLocation) {
		DirectionConstant directionConstant = new DirectionConstant(value, sourceLocation);
		return directionConstant;
	}

	@Override
	public Expression createAddition(Expression left, 
			Expression right, SourceLocation sourceLocation) {		
		Addition addition = new Addition(left, right, sourceLocation);
		return addition;
	}

	@Override
	public Expression createSubtraction(Expression left,
			Expression right, SourceLocation sourceLocation) {
		Subtraction subtraction = new Subtraction(left, right, sourceLocation);
		return subtraction;
	}

	@Override
	public Expression createMultiplication(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		Multiplication multiplication = new Multiplication(left, right, sourceLocation);
		return multiplication;
	}

	@Override
	public Expression createDivision(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		Division division = new Division(left, right, sourceLocation);
		return division;
	}

	@Override
	public Expression createSqrt(Expression expr, 
			SourceLocation sourceLocation) {
		SqrtE sqrtE = new SqrtE(expr, sourceLocation);
		return sqrtE;
	}

	@Override
	public Expression createRandom(Expression maxValue, 
			SourceLocation sourceLocation) {
		RandomE random = new RandomE(maxValue, sourceLocation);
		return random;
	}

	@Override
	public Expression createAnd(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		AndE andE = new AndE(left, right, sourceLocation);
		return andE;
	}

	@Override
	public Expression createOr(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		OrE orE = new OrE(left, right, sourceLocation);
		return orE;
	}

	@Override
	public Expression createNot(Expression expr, SourceLocation sourceLocation) {
		NotBoolean notBoolean = new NotBoolean(expr, sourceLocation);
		return notBoolean;
	}

	@Override
	public Expression createLessThan(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		LessThan lessThan = new LessThan(left, right, sourceLocation);
		return lessThan;
	}

	@Override
	public Expression createLessThanOrEqualTo(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		LessThanOrEqual lessThanOrEqual = new LessThanOrEqual(left, right, sourceLocation);
		return lessThanOrEqual;
	}

	@Override
	public Expression createGreaterThan(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		GreaterThan greaterThan = new GreaterThan(left, right, sourceLocation);
		return greaterThan;
	}

	@Override
	public Expression createGreaterThanOrEqualTo(Expression left, 
			Expression right, SourceLocation sourceLocation) {
		GreaterThanOrEqual greaterThanOrEqual = new GreaterThanOrEqual(left, right, sourceLocation);
		return greaterThanOrEqual;
	}

	@Override
	public Expression createEquals(Expression left, Expression right,
			SourceLocation sourceLocation) {
		Equals equals = new Equals(left, right, sourceLocation);
		return equals;
	}

	@Override
	public Expression createNotEquals(Expression left, Expression right,
			SourceLocation sourceLocation) {
		NotEquals notEquals = new NotEquals(left, right, sourceLocation);
		return notEquals;
	}

	@Override
	public Expression createGetX(Expression expr, 
			SourceLocation sourceLocation) {
		GetX getX = new GetX(expr, sourceLocation);
		return getX;
	}

	@Override
	public Expression createGetY(Expression expr,
			SourceLocation sourceLocation) {
		GetY getY = new GetY(expr, sourceLocation);
		return getY;
	}

	@Override
	public Expression createGetWidth(Expression expr, 
			SourceLocation sourceLocation) {
		GetWidth getWidth = new GetWidth(expr, sourceLocation);
		return getWidth;
	}

	@Override
	public Expression createGetHeight(Expression expr, 
			SourceLocation sourceLocation) {
		GetHeight getHeight = new GetHeight(expr, sourceLocation);
		return getHeight;
	}

	@Override
	public Expression createGetHitPoints(Expression expr, 
			SourceLocation sourceLocation) {
		GetHP getHP = new GetHP(expr, sourceLocation);
		return getHP;
	}

	@Override
	public Expression createGetTile(Expression x, Expression y,
			SourceLocation sourceLocation) {
		GetTile getTile = new GetTile(x, y, sourceLocation);
		return getTile;
	}

	@Override
	public Expression createSearchObject(Expression direction,
			SourceLocation sourceLocation) {
		SearchObject searchObject = new SearchObject(direction, sourceLocation);
		return searchObject;
	}

	@Override 
	public Expression createIsMazub(Expression expr, 
			SourceLocation sourceLocation) {
		IsMazub isMazub = new IsMazub(expr, sourceLocation);
		return isMazub;
	}

	@Override
	public Expression createIsShark(Expression expr, 
			SourceLocation sourceLocation) {
		IsShark isShark = new IsShark(expr, sourceLocation);
		return isShark;
	}

	@Override
	public Expression createIsSlime(Expression expr, 
			SourceLocation sourceLocation) {
		IsSlime isSlime = new IsSlime(expr, sourceLocation);
		return isSlime;
	}

	@Override
	public Expression createIsPlant(Expression expr, 
			SourceLocation sourceLocation) {
		IsPlant isPlant = new IsPlant(expr, sourceLocation);
		return isPlant;
	}

	@Override
	public Expression createIsDead(Expression expr, 
			SourceLocation sourceLocation) {
		IsDead isDead = new IsDead(expr, sourceLocation);
		return isDead;
	}

	@Override
	public Expression createIsTerrain(Expression expr, 
			SourceLocation sourceLocation) {
		IsTerrain isTerrain = new IsTerrain(expr, sourceLocation);
		return isTerrain;
	}

	@Override
	public Expression createIsPassable(Expression expr, 
			SourceLocation sourceLocation) {
		IsPassable isPassable = new IsPassable(expr, sourceLocation);
		return isPassable;
	}

	@Override
	public Expression createIsWater(Expression expr, 
			SourceLocation sourceLocation) {
		IsWater isWater = new IsWater(expr, sourceLocation);
		return isWater;
	}

	@Override
	public Expression createIsMagma(Expression expr, 
			SourceLocation sourceLocation) {
		IsMagma isMagma = new IsMagma(expr, sourceLocation);
		return isMagma;
	}

	@Override
	public Expression createIsAir(Expression expr, 
			SourceLocation sourceLocation) {
		IsAir isAir = new IsAir(expr, sourceLocation);
		return isAir;
	}

	@Override
	public Expression createIsMoving(Expression expr, Expression direction, 
			SourceLocation sourceLocation) {
		IsMoving isMoving = new IsMoving(expr, direction, sourceLocation);
		return isMoving;
	}

	@Override
	public Expression createIsDucking(Expression expr, 
			SourceLocation sourceLocation) {
		IsDucking isDucking = new IsDucking(expr, sourceLocation);
		return isDucking;
	}

	@Override
	public Expression createIsJumping(Expression expr,
			SourceLocation sourceLocation) {
		IsJumping isJumping =new IsJumping(expr, sourceLocation);
		return isJumping;
	}

	@Override
	public Statement createAssignment(String variableName, Type<?> variableType,
			Expression value, SourceLocation sourceLocation) {
		Assignment assignment = new Assignment(variableName, variableType, value, sourceLocation);
		return assignment;
	}

	@Override
	public Statement createWhile(Expression condition, Statement body,
			SourceLocation sourceLocation) {
		WhileS whileS = new WhileS(condition, body, sourceLocation);
		return whileS;
	}

	@Override //TODO
	public Statement createForEach(String variableName, Kind variableKind,
			Expression where, Expression sort, SortDirection sortDirection,
			Statement body, SourceLocation sourceLocation) {
		ForEach forEach = new ForEach(variableName, variableKind, where, sort, 
				sortDirection, body, sourceLocation);
		return forEach;
	}

	@Override//TODO
	public Statement createBreak(SourceLocation sourceLocation) {
		BreakS breakS = new BreakS(sourceLocation);
		return breakS;
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		IfS ifS = new IfS(condition, ifBody, elseBody, sourceLocation);
		return ifS;
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		PrintS printS = new PrintS(value, sourceLocation);
		return printS;
	}

	@Override
	public Statement createStartRun(Expression direction, 
			SourceLocation sourceLocation) {
		StartRun startRun = new StartRun(direction, sourceLocation);
		return startRun;
	}

	@Override
	public Statement createStopRun(Expression direction, 
			SourceLocation sourceLocation) {
		StopRun stopRun = new StopRun(direction, sourceLocation);
		return stopRun;
	}

	@Override
	public Statement createStartJump(SourceLocation sourceLocation) {
		StartJump startJump = new StartJump(sourceLocation);
		return startJump;
	}

	@Override
	public Statement createStopJump(SourceLocation sourceLocation) {
		StopJump stopJump = new StopJump(sourceLocation);
		return stopJump;
	}

	@Override
	public Statement createStartDuck(SourceLocation sourceLocation) {
		StartDuck startDuck = new StartDuck(sourceLocation);
		return startDuck;
	}

	@Override
	public Statement createStopDuck(SourceLocation sourceLocation) {
		StopDuck stopDuck = new StopDuck(sourceLocation);
		return stopDuck;
	}

	@Override
	public Statement createWait(Expression duration, SourceLocation sourceLocation) {
		WaitS waitS = new WaitS(duration, sourceLocation);
		return waitS;
	}

	@Override 
	public Statement createSkip(SourceLocation sourceLocation) {
		SkipS skipS = new SkipS(sourceLocation);
		return skipS;
	}

	@Override
	public Statement createSequence(List<Statement> statements, SourceLocation sourceLocation) {
		Sequence sequence = new Sequence(statements, sourceLocation);
		return sequence;
	}

	@Override
	public DoubleType getDoubleType() {
		DoubleType doubleType = new DoubleType(0.0);
		return doubleType;
	}

	@Override
	public BooleanType getBoolType() {
		BooleanType booleanType = new BooleanType(false);
		return booleanType;
	}

	@Override
	public GameObjectType getGameObjectType() {
		GameObjectType gameObjectType = new GameObjectType(null);
		return gameObjectType;
	}

	@Override
	public DirectionType getDirectionType() {
		DirectionType directionType = new DirectionType(null);
		return directionType;
	}

	@Override 
	public Program createProgram(Statement mainStatement, 
			Map<String, Type<?>> globalVariables) {
		Program program = new Program(mainStatement, globalVariables);
		return program;
	}

}
