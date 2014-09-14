package test;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] a = new int[]{1,2,3,4,5};
		int[] b = new int[]{2,3,6,0,5};
		
		for(int i:a){
			int ret = -1;
			for(int j:b){
				if(j==i){
					ret = i;
				}
			}
			if(ret == -1 ){
				System.out.println("Number in array a which is not present in array b " + i);
			}
		}

	}

}
