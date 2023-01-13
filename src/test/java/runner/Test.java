package runner;

import java.util.HashSet;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//3,9,10,27,38,43,82
		int [] Input1= {3,27,38,43};
		int [] Input2 = {9,10,82};
		//int [] output = null;
		HashSet<Integer> newvalue = new HashSet<Integer>();
		
		for (int i = 0; i<Input1.length-1;i++) {
			for (int j =i+1;j<Input1.length-1;j++) {
				if(Input1[i]<Input2[j]) {
					newvalue.add(Input1[i]);
				}
				
			}
		}
		System.out.println(newvalue);
	}

}
