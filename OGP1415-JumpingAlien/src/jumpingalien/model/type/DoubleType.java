package jumpingalien.model.type;

public class DoubleType extends Type<Double> {

	public DoubleType(Double value) {
		super(value);
	}
	
	public DoubleType(Integer value) {
		super(new Double(value));
	}
}
