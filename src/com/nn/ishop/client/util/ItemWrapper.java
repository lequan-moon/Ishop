package com.nn.ishop.client.util;

import java.io.Serializable;
import java.util.Comparator;


public class ItemWrapper implements Identifiable, Serializable {
	private static final long serialVersionUID = 5659511233746022125L;
	private String id;
    private String label;
	public String value;

    /**
     * Creates a new ItemWrapper object.
     *
     * @param itemId id of ItemWrapper
     * @param itemLabel label of ItemWrapper
     */
    public ItemWrapper(String itemId, String itemLabel) {
        id = itemId;
        label = itemLabel;
    }
    
    /**
     * Creates a new ItemWrapper object. Default constructor.
     */
    public ItemWrapper() 
    {
    }

    /**
     * set id for this object
     *
     * @param itemId id of ItemWrapper
     */
    public void setId(String itemId) {
        id = itemId;
    }

    /**
     * get id of ItemWrapper
     *
     * @return id id of ItemWrapper
     */
    public String getId() {
        return id;
    }

    /**
     * override toString of object
     *
     * @return label String represents information about ItamWrapper
     */
    public String toString() {
        return label;
    }

	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValue(String value) {
		//nothing
		
	}
	/**
	 * Sorts two given entity by names as ascending 
	 */
	public static Comparator<ItemWrapper> nameComparatorAsd = new Comparator<ItemWrapper>(){
		public int compare(ItemWrapper entity1, ItemWrapper entity2){
			String name1 = "";
			String name2 = "";
			try {
				name1 = entity1.toString().toLowerCase();
				name2 = entity2.toString().toLowerCase();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(name1 == null) name1 = "";
			if(name2 == null) name2 = "";
			return name1.compareTo(name2);
		};
	};
	/**
	 * Sorts two given entity by names as descending
	 */
	public static Comparator<ItemWrapper> nameComparatorDes = new Comparator<ItemWrapper>(){
		public int compare(ItemWrapper entity1, ItemWrapper entity2){
			String name1 = null;
			String name2 = null;
			
			try {
				name1 = entity1.toString().toLowerCase();
				name2 = entity2.toString().toLowerCase();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(name1 == null) name1 = "";
			if(name2 == null) name2 = "";
			return name2.compareTo(name1);
		};
	};
	
}
