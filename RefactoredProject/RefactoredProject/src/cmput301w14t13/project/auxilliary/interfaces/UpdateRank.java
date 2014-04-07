package cmput301w14t13.project.auxilliary.interfaces;
/**
 * This is the rank object used by RankedHierarchicalActivity
 * to determine an activities rank
 * 
 * Used by CommentTree to determine which list to update 
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
