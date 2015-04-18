package jumpingalien.model;

import java.util.Collection;

public class School {
	private Collection<Slime> slimes;
	
	public School(){
		this.slimes = null;
	}
	
	public void addSlimeToSchool(Slime slime){
		this.slimes.add(slime);
	}
	
	public void removeSlimeFromSchool(Slime slime){
		this.slimes.remove(slime);
	}
}