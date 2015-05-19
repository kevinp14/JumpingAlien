package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class DoubleType extends Type<Double> {

	public DoubleType(Double value) {
		super(value);
	}
	
	public DoubleType(Integer value) {
		super(new Double(value));
	}
}
