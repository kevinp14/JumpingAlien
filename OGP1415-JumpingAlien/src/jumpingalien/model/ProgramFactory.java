package jumpingalien.model;

import java.util.List;
import java.util.Map;

import jumpingalien.part3.programs.ParseOutcome;
import jumpingalien.part3.programs.ProgramParser;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.IProgramFactory.Kind;
import jumpingalien.part3.programs.IProgramFactory.SortDirection;

public class ProgramFactory<E, S, T, P> {

	public ProgramFactory() {
	}
	
	/* * Helper types * */

	/** Direction enum */
	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	/** Kind enum */
	public enum Kind {
		MAZUB, BUZAM, SLIME, SHARK, PLANT, TERRAIN, ANY
	}

	/** Sort direction enum **/
	public enum SortDirection {
		ASCENDING, DESCENDING
	}
	
	public ParseOutcome<?> parse(String text) {
		IProgramFactory<E, S, T, P> factory = new ProgramFactory<>();
		ProgramParser<E, S, T, P> parser = new ProgramParser<>(factory);
		Optional<P> parseResult = parser.parse(text.toCharArray());
	}

	/* * Expressions * */

	/**
	 * An expression that evaluates to the value of the variable with the given
	 * name and declared type
	 */
	public E createReadVariable(String variableName, T variableType,
			SourceLocation sourceLocation) {
		
	}

	/** An expression that evaluates to the given numeric value */
	E createDoubleConstant(double value, SourceLocation sourceLocation);

	/** An expression that evaluates to true */
	E createTrue(SourceLocation sourceLocation);

	/** An expression that evaluates to false */
	E createFalse(SourceLocation sourceLocation);

	/** An expression that evaluates to null */
	E createNull(SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the game object that is executing the
	 * program
	 */
	E createSelf(SourceLocation sourceLocation);

	/** An expression that evaluates to the given direction */
	E createDirectionConstant(Direction value, SourceLocation sourceLocation);

	/** An expression that evaluates to the sum of the given expressions */
	E createAddition(E left, E right, SourceLocation sourceLocation);

	/** An expression that evaluates to the difference of the given expressions */
	E createSubtraction(E left, E right, SourceLocation sourceLocation);

	/** An expression that evaluates to the product of the given expressions */
	E createMultiplication(E left, E right, SourceLocation sourceLocation);

	/** An expression that evaluates to the division of the given expressions */
	E createDivision(E left, E right, SourceLocation sourceLocation);

	/** An expression that evaluates to the square root of the given expressions */
	E createSqrt(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to a random value between 0 (inclusive) and
	 * the given maximum value (exclusive)
	 */
	E createRandom(E maxValue, SourceLocation sourceLocation);

	/** An expression that evaluates to the conjunction of the given expressions */
	E createAnd(E left, E right, SourceLocation sourceLocation);

	/** An expression that evaluates to the disjunction of the given expressions */
	E createOr(E left, E right, SourceLocation sourceLocation);

	/** An expression that evaluates to the negation of the given expression */
	E createNot(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the value of the left expression
	 * is less than the value of the right expression
	 */
	E createLessThan(E left, E right, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the value of the left expression
	 * is less than or equal to the value of the right expression
	 */
	E createLessThanOrEqualTo(E left, E right, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the value of the left expression
	 * is greater than the value of the right expression
	 */
	E createGreaterThan(E left, E right, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the value of the left expression
	 * is greater than or equal to the value of the right expression
	 */
	E createGreaterThanOrEqualTo(E left, E right, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the value of the left expression
	 * equals the value of the right expression
	 */
	E createEquals(E left, E right, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the value of the left expression
	 * does not equal the value of the right expression
	 */
	E createNotEquals(E left, E right, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the x-value of the object obtained from
	 * the given expression
	 */
	E createGetX(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the y-value of the object obtained from
	 * the given expression
	 */
	E createGetY(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the width of the object obtained from the
	 * given expression
	 */
	E createGetWidth(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the height of the object obtained from
	 * the given expression
	 */
	E createGetHeight(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the number of hitpoints of the object
	 * obtained from the given expression
	 */
	E createGetHitPoints(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the tile in which the pixel with
	 * coordinates (x, y) lies.
	 */
	E createGetTile(E x, E y, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to the first object that is encountered in
	 * the given direction
	 */
	E createSearchObject(E direction, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is a Mazub
	 */
	E createIsMazub(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is a Shark
	 */
	E createIsShark(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is a Slime
	 */
	E createIsSlime(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is a Plant
	 */
	E createIsPlant(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is dead
	 */
	E createIsDead(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is part of the terrain
	 */
	E createIsTerrain(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is passable
	 */
	E createIsPassable(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is water
	 */
	E createIsWater(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is magma
	 */
	E createIsMagma(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is air
	 */
	E createIsAir(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is moving
	 */
	E createIsMoving(E expr, E direction, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is ducking
	 */
	E createIsDucking(E expr, SourceLocation sourceLocation);

	/**
	 * An expression that evaluates to true if the object obtained from the
	 * given expression is jumping
	 */
	E createIsJumping(E expr, SourceLocation sourceLocation);

	/* * Statements * */

	/**
	 * A statement that changes the value of the variable with the given name
	 * and declared type to the value obtained from the given expression
	 */
	S createAssignment(String variableName, T variableType, E value,
			SourceLocation sourceLocation);

	/**
	 * A statement that executes the given body while the given condition
	 * evaluates to true
	 */
	S createWhile(E condition, S body, SourceLocation sourceLocation);

	/**
	 * A statement that executes the given body with the given variable set to
	 * all objects of the given kind for which the where-expression evaluates to
	 * true, sorted by the result of the given sort expression in the given
	 * direction. The where- and sort-expressions are optional, and can be null;
	 * */
	S createForEach(String variableName, Kind variableKind, E where, E sort,
			SortDirection sortDirection, S body, SourceLocation sourceLocation);

	/** A statement that terminates the currently executing loop */
	S createBreak(SourceLocation sourceLocation);

	/**
	 * A statement that executes the given ifBody if the condition evaluates to
	 * true, and the given elseBody otherwise.
	 * The elseBody is optional, and can be null.
	 */
	S createIf(E condition, S ifBody, S elseBody, SourceLocation sourceLocation);

	/** A statement that prints the value of the given expression */
	S createPrint(E value, SourceLocation sourceLocation);

	/**
	 * A statement that makes the object executing the program start moving in
	 * the given direction
	 */
	S createStartRun(E direction, SourceLocation sourceLocation);

	/**
	 * A statement that makes the object executing the program stop moving in
	 * the given direction
	 */
	S createStopRun(E direction, SourceLocation sourceLocation);

	/** A statement that makes the object executing the program start jumping */
	S createStartJump(SourceLocation sourceLocation);

	/** A statement that makes the object executing the program stop jumping */
	S createStopJump(SourceLocation sourceLocation);

	/** A statement that makes the object executing the program start ducking */
	S createStartDuck(SourceLocation sourceLocation);

	/** A statement that makes the object executing the program stop ducking */
	S createStopDuck(SourceLocation sourceLocation);

	/**
	 * A statement that suspends the execution of the program for the given
	 * duration
	 */
	S createWait(E duration, SourceLocation sourceLocation);

	/** A statement that does nothing */
	S createSkip(SourceLocation sourceLocation);

	/** A statement that executes of a list of statements subsequently */
	S createSequence(List<S> statements, SourceLocation sourceLocation);

	/* * Types * */

	/** The type of double values and variables */
	T getDoubleType();

	/** The type of boolean values and variables */
	T getBoolType();

	/** The type of game object values and variables */
	T getGameObjectType();

	/** The type of direction values and variables */
	T getDirectionType();

	/* * Program * */

	/**
	 * Create a program with the given main statement and variable declarations.
	 * The globalVariables map contains the type for each declared variable,
	 * with the name of the variable as the key.
	 */
	P createProgram(S mainStatement, Map<String, T> globalVariables);

}