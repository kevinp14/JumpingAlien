package jumpingalien.model;

import java.util.Random;

public class Expression {
	
	private Object x;
	
	public Expression(Object variable) {
		super();
		this.x = variable;
	}
	
	public double constant(double c) {
		return c;
	}
	
	public Expression expressionBrackets(Expression[] expression) {
		return expression[0];
	}
	
	public double random(double rangeEnd) {
		Random rand = new Random();
		return ((int)rangeEnd) * rand.nextDouble();
	}
	
	public Object sum(Object y) {
		return (this.x + y);
	}
	
	public int getX() {
		return 
	}
	
	
	
}