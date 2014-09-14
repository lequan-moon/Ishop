package com.nn.ishop.client.util;

import java.util.Comparator;

public class CollectionSorter{

  public static class StringComparatorAsc implements Comparator<String>
  {
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
  }
  public static class StringComparatorDesc implements Comparator<String>
  {
	public int compare(String o1, String o2) {
		return o2.compareTo(o1);
	}
  }

  public static class NumberComparatorAsc implements Comparator<Number>
  {
	public int compare(Number num1, Number num2) {
		return num1.intValue() < num2.intValue()?1:num1.intValue()==num2.intValue()?0:-1;
	}
  }
  public static class NumberComparatorDesc implements Comparator<Number>
  {
	public int compare(Number num1, Number num2) {
		return num2.intValue() < num1.intValue()?1:num1.intValue()==num2.intValue()?0:-1;
	}
  }
  
  public static class ObjectComparatorAsc implements Comparator<Object>
  {
	public int compare(Object o1, Object o2) {
		return o1.toString().compareTo(o2.toString());
	}
  }
  public static class ObjectComparatorDesc implements Comparator<Object>
  {
	public int compare(Object o1, Object o2) {
		return o2.toString().compareTo(o1.toString());
	}
  }
}