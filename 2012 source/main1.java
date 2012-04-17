

public class main1{
	public static IntStack stackx = new IntStack(56);
	public static IntStack stacky = new IntStack(56);
	
	public static int[] array = new int[0];
	
	public static void main(String args[]){
		
		test1();
		
		test2();
		
		test3();
		
	}
	
	public static void cleanStack(){
		int[] xVals = stackx.getArray();
		int[] yVals = stacky.getArray();
		
		int xsize = stackx.size();
		int ysize = stacky.size();

		for(int i = 0; i < xsize-1; i++)
		{
			for(int j = i+1; j < xsize; j++){
				if((xVals[i] == xVals[j]) && (yVals[i] == yVals[j])){
					for(int k = 0; k < xsize; k++){
						xVals[i + k] = xVals[j + k];
						yVals[i + k] = yVals[j + k];
						xsize--;
					}
					i = 0;
				}
			}
		}
		
		for(int i = 1; i < ysize; i++)
		{
			if(xVals[i] == 0 && yVals[i] == 0){
				xsize = i-1;
				break;
			}
		}
		if(xVals[xsize] == 0 && yVals[xsize] == 0)
			xsize--;
		

		stackx.setArray(xVals, xsize);
		stacky.setArray(yVals, xsize);	
	  }


		// 	  public static int[] splice(int[] arr, int sindex, int eindex){
		// for(int i = 0; i < arr.length-eindex; i++){
		// 	System.out.println("----> " + arr[eindex + i]);
		// 	arr[sindex + i + 1] = arr[eindex + i];
		// }
		// /*for(int i = 0; i < (esindex-sindex); i++){
		// 	arr[arr.size() - i - 1] = 0;
		// }*/
		// return slice(arr, 0, arr.length-eindex);
		// 	  }
		// 
		// 	  public static int[] slice(int[] arr, int sindex, int eindex){
		// int[] newarr = new int[eindex-sindex];
		// System.out.println("SLICE BEFORE: " + printArr(arr) + " " + sindex + " " + eindex);
		// for(int i = 0; i < eindex-sindex; i++){
		// 	newarr[i] = arr[sindex+i];
		// } 
		// System.out.println("SLICE AFTER: " + printArr(newarr) + "\n");
		// return newarr;
		// 	  }
	
	  public static String printArr(int[] arr, int size)
	{
		String s = "";
		for(int i= 0; i <= size; i++)
			s += arr[i] + ", ";
		return s;
	}
	
	public static void test1(){
		stackx.push(0);
		stacky.push(0);
		
		stackx.push(1);
		stacky.push(0);
		
		stackx.push(2);
		stacky.push(0);
		
		stackx.push(3);
		stacky.push(0);
		
		stackx.push(3);
		stacky.push(1);
		
		stackx.push(2);
		stacky.push(1);
		
		stackx.push(2);
		stacky.push(0);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(2);
		stacky.push(2);
		
		stackx.push(1);
		stacky.push(2);
		
		stackx.push(1);
		stacky.push(1);
		
		stackx.push(1);
		stacky.push(0);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(2);
		stacky.push(1);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(0);
		stacky.push(1);
		
		stackx.push(0);
		stacky.push(0);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(0);
		stacky.push(2);
		
		stackx.push(1);
		stacky.push(2);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(0);
		stacky.push(3);
		
		stackx.push(1);
		stacky.push(3);
		
		stackx.push(2);
		stacky.push(3);
		
		stackx.push(3);
		stacky.push(3);
		
		stackx.push(3);
		stacky.push(2);
		
		stackx.push(4);
		stacky.push(2);
		
		stackx.push(4);
		stacky.push(1);
		
		stackx.push(4);
		stacky.push(0);
		
		stackx.push(5);
		stacky.push(0);
		
		stackx.push(5);
		stacky.push(1);
		
		stackx.push(5);
		stacky.push(2);
		
		stackx.push(5);
		stacky.push(3);
		
		stackx.push(5);
		stacky.push(4);
		
		stackx.push(5);
		stacky.push(5);
		
		stackx.push(5);
		stacky.push(6);
		
		stackx.push(4);
		stacky.push(6);
		
		stackx.push(4);
		stacky.push(5);
		
		stackx.push(5);
		stacky.push(5);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(4);
		stacky.push(4);
		
		stackx.push(5);
		stacky.push(4);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(3);
		stacky.push(4);
		
		stackx.push(3);
		stacky.push(5);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(2);
		stacky.push(4);
		
		stackx.push(2);
		stacky.push(5);
		
		stackx.pop();
		stacky.pop();
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(3);
		stacky.push(3);
		
		stackx.push(3);
		stacky.push(4);
		
		
		System.out.println("test start");
		
		System.out.println(printArr(stackx.getArray(), stackx.size()));
		System.out.println(printArr(stacky.getArray(), stacky.size()) + "\n");
		
		cleanStack();
		
		System.out.println(printArr(stackx.getArray(), stackx.size()));
		System.out.println(printArr(stacky.getArray(), stacky.size()));
		
		stackx.reset();
		stacky.reset();
		
	}

	public static void test2(){
		stackx.push(0);
		stacky.push(0);
		
		stackx.push(1);
		stacky.push(0);
		
		stackx.push(2);
		stacky.push(0);
		
		stackx.push(2);
		stacky.push(1);
		
		stackx.push(3);
		stacky.push(1);
		
		stackx.push(3);
		stacky.push(0);
		
		stackx.push(4);
		stacky.push(0);
		
		stackx.push(5);
		stacky.push(0);
		
		stackx.push(5);
		stacky.push(1);
		
		stackx.push(4);
		stacky.push(1);
		
		stackx.push(3);
		stacky.push(1);
		
		stackx.push(3);
		stacky.push(2);
		
		stackx.push(2);
		stacky.push(2);
		
		stackx.push(2);
		stacky.push(1);
		
		stackx.push(1);
		stacky.push(1);
		
		stackx.push(1);
		stacky.push(0);
		
		stackx.pop();
		stacky.pop();
		
		stackx.push(1);
		stacky.push(2);
		
		stackx.push(2);
		stacky.push(2);
		
		stackx.push(2);
		stacky.push(3);
		
		stackx.push(1);
		stacky.push(3);
		
		stackx.push(1);
		stacky.push(2);
		
		stackx.push(0);
		stacky.push(2);
		
		stackx.push(0);
		stacky.push(1);
		
		System.out.println("test start");
		
		System.out.println(printArr(stackx.getArray(), stackx.size()));
		System.out.println(printArr(stacky.getArray(), stacky.size()) + "\n");
		
		
		cleanStack();
		
		System.out.println(printArr(stackx.getArray(), stackx.size()));
		System.out.println(printArr(stacky.getArray(), stacky.size()));
		
		stackx.reset();
		stacky.reset();
		
	}

	public static void test3(){
		stackx.push(0);
		stacky.push(0);
		
		stackx.push(1);
		stacky.push(0);
		
		stackx.push(2);
		stacky.push(0);
		
		stackx.push(3);
		stacky.push(0);
		
		stackx.push(3);
		stacky.push(1);
		
		
		System.out.println("test start");
		
		System.out.println(printArr(stackx.getArray(), stackx.size()));
		System.out.println(printArr(stacky.getArray(), stacky.size()) + "\n");
		
		cleanStack();
		
		System.out.println(printArr(stackx.getArray(), stackx.size()));
		System.out.println(printArr(stacky.getArray(), stacky.size()));
		
		stackx.reset();
		stacky.reset();
		
	}
}