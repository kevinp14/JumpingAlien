package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class IntervalType extends Type<int[]>{

	public IntervalType(int[] value) {
		super(value);
	}
}
