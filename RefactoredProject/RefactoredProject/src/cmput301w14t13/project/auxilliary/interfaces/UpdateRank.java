package cmput301w14t13.project.auxilliary.interfaces;

/**
 * @author  mjnichol
 */
public class UpdateRank {
	
	/**
	 * @uml.property  name="rank"
	 */
	private int rank;
	
	public UpdateRank(int n)
	{
		rank = n;
	}
	
	/**
	 * @return
	 * @uml.property  name="rank"
	 */
	public int getRank(){
		return rank;
	}

}
