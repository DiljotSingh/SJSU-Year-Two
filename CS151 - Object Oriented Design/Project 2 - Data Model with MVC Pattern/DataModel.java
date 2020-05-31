import java.util.*;

/**
 * A Data Model that stores a list of Integers and is Observable
 * @author Diljot Singh
 *
 */

public class DataModel extends Observable
{

	private ArrayList<Integer> 		data; 	//List of all Integers in this DataModel
	
  
	/**
	 * Constructs a Model with an empty list of Integers 
	 */
	public DataModel()
	{
		this.data = new ArrayList<Integer>();
	}
	
	
	/**
	 * Adds a Integer value to the list of all Integers
	 * @param value the Integer to add
	 */
	public void addValue(Integer value)
	{
		data.add(value);
		
		//Notifies all Observers (views) of the change
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Sets a specified index's element to a new value
	 * @param index the index of the element to overwrite
	 * @param value the new value to be stored at that index
	 */
	public void setValue(int index, Integer value)
	{
		data.set(index, value);
		
		//Notifies all Observers (views) of the change
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Gets this model's list of data
	 * @return data
	 */
	public ArrayList<Integer> getModel()
	{
		return this.data;
	}
}