import java.util.Arrays;
import java.util.Scanner;


public class MathHelper {
	
	
	public static int max(int[] x) {
		
		int y = 0;
		
		for (int i = 0; i < x.length; i++) {
			if (x[i] > y) {
				y = x[i];
			}
		}
		
		
		return y;
		
		
	}
	
	public static int min(int[] x) {
		
		int y = x[0]; 
		
		for (int i = 0; i < x.length; i++) {
			if (x[i] < y) {
				y = x[i];
			}
		}
		
		
		return y;
		
		
	}

	public static double average(double[] x) {
		
		double y = 0;
		
		for (int i = 0; i < x.length; i++) {
			y += x[i];
		}
		
		y /= x.length;
		
		
		return y;
		
		
	}
	
	public static double median(int[] x) {
		
		double y;
		Arrays.sort(x);
		
		int iter = (x.length - 1) / 2;
		
		
		int[][] nums = new int[iter + 1][];
		
		nums[0] = x;
		
		for (int i = 0; i < iter; i ++) {
			nums[i + 1] = new int[nums[i].length - 2];
			
			for (int j = 1; j < nums[i].length - 1; j++) {
				
				nums[i + 1][j - 1] = nums[i][j]; 
				
			}
			
			
		}
		
		int sum = 0;
		
		for (int k = 0; k < nums[nums.length - 1].length; k++) {
			sum += nums[nums.length - 1][k];
		}
		
		y = (float)sum / (float)nums[nums.length - 1].length;
		
		return y;
		
		
	}
	
	public static double stddev(double[] x) {
		
		double y;
		
		double diffSum = 0;
		
		double avg = (double)average(x);
		
		for (int i = 0; i < x.length; i++) {
			
			diffSum += (x[i] - avg) * (x[i] - avg);
			
		}
		
		double var = diffSum / x.length;
		
		y = Math.sqrt(var);
		
		
		return y;
		
		
	}
	
	public static int mode(int[] x) {
		
		int mode = 0;
		
		int modeCount = 1;
		
		
		for (int i = 0; i < x.length; i++) {
			int currentCount = 0;
			
			for (int j = 0; j < x.length; j++) {
				if (x[i] == x[j]) {
					currentCount++;
				}
			}
			
			if (currentCount > modeCount) {
				modeCount = currentCount;
				mode = x[i];
			}
		}
		
		
		
		
		return mode;
		
	}
	
	
	public static int[] inputArray(int len, Scanner in) {
		int[] inputArray = new int[len];
		
		System.out.println("Enter 7 integers");
		
		for (int i = 0; i < inputArray.length; i++) {
			System.out.println("Please enter integer at index " + i);
			inputArray[i] = in.nextInt();
		}
		
		
		return inputArray;
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter a number between 1 and 6 inclusive");
		int x = in.nextInt();
		
		if (!(x >= 1 && x <= 6)) {
			System.out.println("You must enter an int between 1 and 6 inclusive");
			System.exit(0);
		}
		else {
			
			int[] xArray = inputArray(7, in);
			
			System.out.println("Here is your output: ");
			
			if (x == 1) {
				System.out.println("Max: " + max(xArray));
			} else if (x == 2) {
				System.out.println("Min: " + min(xArray));
			} else if (x == 3) {
				System.out.println("Average: " + average(xArray));
			} else if (x == 4) {
				System.out.println("Median: " + median(xArray));
			} else if (x == 5) {
				System.out.println("Standard Deviation: " + stddev(xArray));
			} else if (x == 6) {
				System.out.println("Mode: " + mode(xArray));
			}
			
			System.exit(0);
			
			
			
			
		}
		*/
		
		
		
		
		

	}

}
