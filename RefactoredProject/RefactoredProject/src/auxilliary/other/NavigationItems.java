package auxilliary.other;


/**
 * Uses Enums to represent The Navigational numbers that the 
 * Dropdown menu for sorting return
 * Used in CommentTree for switch cases in sorting
 * @author http://stackoverflow.com/questions/8157755/how-to-convert-enum-value-to-int
 *
 */

public enum NavigationItems {
	Relevant(0),
	ProximityToMe(1),
	ProximityToLocation(2),
	Pictures(3),
	Newest(4),
	Oldest(5);
	
	
    private final int value;
    private NavigationItems(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
 
    private static NavigationItems[] allValues = values();
    public static NavigationItems fromOrdinal(int n) {return allValues[n];}
}