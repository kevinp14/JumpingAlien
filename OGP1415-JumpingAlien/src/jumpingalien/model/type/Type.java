package jumpingalien.model.type;

/**
 * A class of types.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Type<T> {
	private T value;

	/**
	 * Initialize the type
	 * 
	 * @param	value
	 * 			The value the type should get.
	 */
	public Type(T value) {
		this.value = value;
	}

	/**
	 * @return	The value of the type.
	 * 
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Check whether the given object is of the same as this.
	 * 
	 * @return	True if and only if the given object is this.
	 */
	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}
}