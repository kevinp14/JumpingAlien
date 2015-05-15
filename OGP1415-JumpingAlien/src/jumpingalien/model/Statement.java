package jumpingalien.model;

import jumpingalien.model.Expression;

import java.util.List;

public class Statement {
	
	private Expression e;
	private GameObject o;
	private Kind K;
	
	public Statement(Expression exp, GameObject object, Kind kind) {
		super();
		this.e = exp;
		this.o = object;
		this.K = kind;
	}
	
//	/* * Helper types * */
//
//	/** Direction enum */
//	public enum Direction {
//		LEFT, RIGHT, UP, DOWN
//	}

	/** Kind enum */
	public enum Kind {
		MAZUB, BUZAM, SLIME, SHARK, PLANT, TERRAIN, ANY
	}

	/** Sort direction enum **/
	public enum SortDirection {
		ASCENDING, DESCENDING
	}
	
	public void whileDo() {
		while (this.e) {
		}
	}
	
	public void forEachDo(Expression x, SortDirection sortDirection) {
	}
	
//	public void breakHere() {
//		break;
//	}
	
	public void ifThenElse() {
		if (this.e)
	}
	
	public void print() {
		System.out.println(this.e);
	}
	
	public void sequence(List<Statement> sequence) {
		for (Statement seq: sequence) {
			sequence.remove(seq);
		}
	}
	
	public void startRun(Direction direction) {
		this.o.startMoveHorizontally(direction, this.o.getNormalHorizontalVelocity(),
				this.o.getNormalHorizontalAcceleration());
	}
	
	public void stopRun(Direction direction) {
		this.o.endMoveHorizontally(direction);
	}
	
	public void startJump(Direction direction) {
		if (this.o == this.o.getWorld().getBuzam()) {
			Buzam alien = (Buzam)this.o;
			alien.startJump();
		}
		if (this.o == this.o.getWorld().getMazub()) {
			Mazub alien = (Mazub)this.o;
			alien.startJump();
		}
		if (this.o.getWorld().getSharks().contains(this.o)) {
			Shark shark = (Shark)this.o;
			shark.startMoveVertically(direction);
		}
	}
	
	public void endJump() {
		if (this.o == this.o.getWorld().getBuzam()) {
			Buzam alien = (Buzam)this.o;
			alien.endJump();
		}
		if (this.o == this.o.getWorld().getMazub()) {
			Mazub alien = (Mazub)this.o;
			alien.endJump();
		}
		if (this.o.getWorld().getSharks().contains(this.o)) {
			Shark shark = (Shark)this.o;
			shark.endMoveVertically();
		}
	}
	
	public void startDuck() {
		if (this.o == this.o.getWorld().getBuzam()) {
			Buzam alien = (Buzam)this.o;
			alien.startDuck();
		}
		if (this.o == this.o.getWorld().getMazub()) {
			Mazub alien = (Mazub)this.o;
			alien.startDuck();
		}
	}
	
	public void endDuck() {
		if (this.o == this.o.getWorld().getBuzam()) {
			Buzam alien = (Buzam)this.o;
			alien.endDuck();
		}
		if (this.o == this.o.getWorld().getMazub()) {
			Mazub alien = (Mazub)this.o;
			alien.endDuck();
		}
	}
	
	public void waitFor(double time) {
		this.wait((long)time);
	}
	
	public void skip() {
		this.waitFor(0.001);
	}

}