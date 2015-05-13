package jumpingalien.model;

import java.util.ArrayList;

/**
 * A class of schools involving its size, methods to add and remove a slime and a method to return all
 * slimes in this school.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 2.0
 *
 */
public class School {
	private ArrayList<Slime> slimes = new ArrayList<>();
	
	public School() {}
	
	/**
	 * @param	slime
	 * 			The slime that has to be added to the school.
	 */
	protected void addSlimeToSchool(Slime slime){
		this.slimes.add(slime);
	}
	
	/**
	 * @param	slime
	 * 			The slime that has to be removed from the school.
	 */
	protected void removeSlimeFromSchool(Slime slime){
		this.slimes.remove(slime);
	}
	
	/**
	 * @return	An array list containing the slimes in this school.
	 * 
	 */
	protected ArrayList<Slime> getSlimes() {
		return this.slimes;
	}
	
	/**
	 * @return	The size of this school.
	 * 
	 */
	protected int getSize() {
		return this.slimes.size();
	}
}