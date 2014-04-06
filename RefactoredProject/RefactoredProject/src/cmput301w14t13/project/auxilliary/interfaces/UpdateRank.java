package cmput301w14t13.project.auxilliary.interfaces;
/**
 * This is the rank object used by RankedHierarchicalActivity
 * to determine an activities rank
 * 
 * @author nsd
 *
 */
public class UpdateRank {
	

	private int rank;
	
	public UpdateRank(int n)
	{
		rank = n;
	}
	
	public int getRank(){
		return rank;
	}

}
