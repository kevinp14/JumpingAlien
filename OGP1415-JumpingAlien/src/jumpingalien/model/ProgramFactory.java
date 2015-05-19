package jumpingalien.model;

import java.util.*;

import jumpingalien.model.expression.*;
import jumpingalien.model.expression.RandomE;
import jumpingalien.model.statement.*;
import jumpingalien.model.type.*;
import jumpingalien.part3.programs.*;


public class ProgramFactory implements IProgramFactory<Expression<?>, Statement, Type<?>, Program>{
	
	public ProgramFactory(){}
	
	@Override //TODO
	public Expression<?> createReadVariable(String variableName, Type<?> variableType,
			SourceLocation sourceLocation) {
		ReadVariable readVariable = new ReadVariable(variableName, variableType, sourceLocation);
		return readVariable;
	}

	@Override
	public Expression<DoubleType> createDoubleConstant(double value,
			SourceLocation sourceLocation) {
		DoubleConstant doubleConstant = new DoubleConstant(value, sourceLocation);
		return doubleConstant;
	}

	@Override
	public Expression<BooleanType> createTrue(SourceLocation sourceLocation) {
		BooleanE booleanE = new BooleanE(true, sourceLocation);
		return booleanE;
	}

	@Override
	public Expression<BooleanType> createFalse(SourceLocation sourceLocation) {
		BooleanE booleanE = new BooleanE(false, sourceLocation);
		return booleanE;
	}

	@Override //TODO
	public Expression<?> createNull(SourceLocation sourceLocation) {
		NullE nullE = new NullE(sourceLocation);
		return nullE;
	}

	
	@Override //TODO
	public Expression<?> createSelf(SourceLocation sourceLocation) {
		SelfE selfE = new SelfE(sourceLocation);
		return selfE;
	}

	@Override
	public Expression<DirectionType> createDirectionConstant(Direction value,
			SourceLocation sourceLocation) {
		DirectionConstant directionConstant = new DirectionConstant(value, sourceLocation);
		return directionConstant;
	}

	@Override
	public Expression<DoubleType> createAddition(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {		
		Addition addition = new Addition(left, right, sourceLocation);
		return addition;
	}

	@Override
	public Expression<DoubleType> createSubtraction(Expression<DoubleType> left,
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		Subtraction subtraction = new Subtraction(left, right, sourceLocation);
		return subtraction;
	}

	@Override
	public Expression<DoubleType> createMultiplication(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		Multiplication multiplication = new Multiplication(left, right, sourceLocation);
		return multiplication;
	}

	@Override
	public Expression<DoubleType> createDivision(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		Division division = new Division(left, right, sourceLocation);
		return division;
	}

	@Override
	public Expression<DoubleType> createSqrt(Expression<DoubleType> expr, 
			SourceLocation sourceLocation) {
		SqrtE sqrtE = new SqrtE(expr, sourceLocation);
		return sqrtE;
	}

	@Override
	public Expression<DoubleType> createRandom(Expression<DoubleType> maxValue, 
			SourceLocation sourceLocation) {
		RandomE random = new RandomE(maxValue, sourceLocation);
		return random;
	}

	@Override
	public Expression<BooleanType> createAnd(Expression<BooleanType> left, 
			Expression<BooleanType> right, SourceLocation sourceLocation) {
		AndE andE = new AndE(left, right, sourceLocation);
		return andE;
	}

	@Override
	public Expression<BooleanType> createOr(Expression<BooleanType> left, 
			Expression<BooleanType> right, SourceLocation sourceLocation) {
		OrE orE = new OrE(left, right, sourceLocation);
		return orE;
	}

	@Override
	public Expression<?> createNot(Expression<?> expr, SourceLocation sourceLocation) {
		NotE notE = new NotE(expr, sourceLocation);
		return notE;
	}

	@Override
	public Expression<BooleanType> createLessThan(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		LessThan lessThan = new LessThan(left, right, sourceLocation);
		return lessThan;
	}

	@Override
	public Expression<BooleanType> createLessThanOrEqualTo(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		LessThanOrEqual lessThanOrEqual = new LessThanOrEqual(left, right, sourceLocation);
		return lessThanOrEqual;
	}

	@Override
	public Expression<BooleanType> createGreaterThan(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		GreaterThan greaterThan = new GreaterThan(left, right, sourceLocation);
		return greaterThan;
	}

	@Override
	public Expression<BooleanType> createGreaterThanOrEqualTo(Expression<DoubleType> left, 
			Expression<DoubleType> right, SourceLocation sourceLocation) {
		GreaterThanOrEqual greaterThanOrEqual = new GreaterThanOrEqual(left, right, sourceLocation);
		return greaterThanOrEqual;
	}

	@Override
	public Expression<BooleanType> createEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		Equals equals = new Equals(left, right, sourceLocation);
		return equals;
	}

	@Override
	public Expression<BooleanType> createNotEquals(Expression<?> left, Expression<?> right,
			SourceLocation sourceLocation) {
		NotEquals notEquals = new NotEquals(left, right, sourceLocation);
		return notEquals;
	}

	@Override
	public Expression<DoubleType> createGetX(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		GetX getX = new GetX(expr, sourceLocation);
		return getX;
	}

	@Override
	public Expression<DoubleType> createGetY(Expression<GameObjectType> expr,
			SourceLocation sourceLocation) {
		GetY getY = new GetY(expr, sourceLocation);
		return getY;
	}

	@Override
	public Expression<DoubleType> createGetWidth(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		GetWidth getWidth = new GetWidth(expr, sourceLocation);
		return getWidth;
	}

	@Override
	public Expression<DoubleType> createGetHeight(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		GetHeight getHeight = new GetHeight(expr, sourceLocation);
		return getHeight;
	}

	@Override
	public Expression<DoubleType> createGetHitPoints(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		GetHP getHP = new GetHP(expr, sourceLocation);
		return getHP;
	}

	@Override
	public Expression<DoubleType> createGetTile(Expression<DoubleType> x, Expression<DoubleType> y,
			SourceLocation sourceLocation) {
		GetTile getTile = new GetTile(x, y, sourceLocation);
		return getTile;
	}

	//TODO 
	@Override
	public Expression<GameObjectType> createSearchObject(Expression<DoubleType> direction,
			SourceLocation sourceLocation) {
		SearchObject searchObject = new SearchObject(direction, sourceLocation);
		return searchObject;
	}

	@Override
	public Expression<BooleanType> createIsMazub(Expression<GameObjectType> expr, S
			ourceLocation sourceLocation) {
		IsMazub isMazub = new IsMazub(expr, sourceLocation);
		return isMazub;
	}

	@Override
	public Expression<BooleanType> createIsShark(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsShark isShark = new IsShark(expr, sourceLocation);
		return isShark;
	}

	@Override
	public Expression<BooleanType> createIsSlime(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsSlime isSlime = new IsSlime(expr, sourceLocation);
		return isSlime;
	}

	@Override
	public Expression<BooleanType> createIsPlant(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsPlant isPlant = new IsPlant(expr, sourceLocation);
		return isPlant;
	}

	@Override
	public Expression<BooleanType> createIsDead(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsDead isDead = new IsDead(expr, sourceLocation);
		return isDead;
	}

	//TODO
	@Override
	public Expression<BooleanType> createIsTerrain(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsTerrain isTerrain = new IsTerrain(expr, sourceLocation);
		return isTerrain;
	}

	@Override
	public Expression<BooleanType> createIsPassable(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsPassable isPassable = new IsPassable(expr, sourceLocation);
		return isPassable;
	}

	@Override
	public Expression<BooleanType> createIsWater(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsWater isWater = new IsWater(expr, sourceLocation);
		return isWater;
	}

	@Override
	public Expression<BooleanType> createIsMagma(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsMagma isMagma = new IsMagma(expr, sourceLocation);
		return isMagma;
	}

	@Override
	public Expression<BooleanType> createIsAir(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsAir isAir = new IsAir(expr, sourceLocation);
		return isAir;
	}

	@Override
	public Expression<BooleanType> createIsMoving(Expression<GameObjectType> expr, 
			Expression<DirectionType> direction, SourceLocation sourceLocation) {
		IsMoving isMoving = new IsMoving(expr, direction, sourceLocation);
		return isMoving;
	}

	@Override
	public Expression<BooleanType> createIsDucking(Expression<GameObjectType> expr, 
			SourceLocation sourceLocation) {
		IsDucking isDucking = new IsDucking(expr, sourceLocation);
		return isDucking;
	}

	@Override
	public Expression<BooleanType> createIsJumping(Expression<GameObjectType> expr,
			SourceLocation sourceLocation) {
		IsJumping isJumping =new IsJumping(expr, sourceLocation);
		return isJumping;
	}

	@Override
	public Statement createAssignment(String variableName, Type<?> variableType,
			Expression<DoubleType> value, SourceLocation sourceLocation) {
		Assignment assignment = new Assignment(variableName, variableType, value, sourceLocation);
		return assignment;
	}

	@Override
	public Statement createWhile(Expression<BooleanType> condition, Statement body,
			SourceLocation sourceLocation) {
		WhileS whileS = new WhileS(condition, body, sourceLocation);
		return whileS;
	}

	@Override //TODO
	public Statement createForEach(String variableName, Kind variableKind,
			Expression<BooleanType> where, Expression<DirectionType> sort, SortDirection sortDirection,
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
	public Statement createIf(Expression<BooleanType> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation) {
		IfS ifS = new IfS(condition, ifBody, elseBody, sourceLocation);
		return ifS;
	}

	@Override
	public Statement createPrint(Expression<DoubleType> value, SourceLocation sourceLocation) {
		PrintS printS = new PrintS(value, sourceLocation);
		return printS;
	}

	@Override
	public Statement createStartRun(Expression<DirectionType> direction, 
			SourceLocation sourceLocation) {
		StartRun startRun = new StartRun(direction, sourceLocation);
		return startRun;
	}

	@Override
	public Statement createStopRun(Expression<DirectionType> direction, 
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
	public Statement createWait(Expression<DoubleType> duration, SourceLocation sourceLocation) {
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
	public Type<?> getDoubleType() {
		DoubleType doubleType = new DoubleType(0.0);
		return doubleType;
	}

	@Override
	public Type<?> getBoolType() {
		BooleanType booleanType = new BooleanType(false);
		return booleanType;
	}

	@Override
	public Type<?> getGameObjectType() {
		GameObjectType gameObjectType = new GameObjectType(null);
		return gameObjectType;
	}

	@Override
	public Type<?> getDirectionType() {
		DirectionType directionType = new DirectionType(null);
		return directionType;
	}
	
	public Type<?> getIntervalType() {
		IntervalType intervalType = new IntervalType(null);
		return intervalType;
	}

	@Override
	public Program createProgram(Statement mainStatement, 
			Map<String, Type<?>> globalVariables) { //Map<String, T>?
		Program program = new Program(mainStatement, globalVariables);
		return program;
	}

}
