import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Implementation of Perceptron  to learn classification of mammals and non-mammals
 * @author Scott Leung
 * takes in an input file for training data 
 */

public class Perceptron {

	public static void main(String[] args) throws IOException {
		double learning_rate = 0.1;
		int theta = 0;
		int iteration = 0;
		int max_iter = 100;
		int output;
		double[] weights = new double[5];
		double localError, globalError;

		ArrayList<Integer> x1 = new ArrayList<Integer>();
		ArrayList<Integer> x2 = new ArrayList<Integer>();
		ArrayList<Integer> x3 = new ArrayList<Integer>();
		ArrayList<Integer> x4 = new ArrayList<Integer>();
		ArrayList<Integer> outputs = new ArrayList<Integer>();

		FileReader fileReader = new FileReader(new File("input.txt"));
		BufferedReader br = new BufferedReader(fileReader);
		String line = br.readLine();

		//input variables read in from file and stored in Arraylists
		while ((line = br.readLine()) != null) {
			String[] tokens = line.split(",");
			x1.add(Integer.parseInt(tokens[1]));
			x2.add(Integer.parseInt(tokens[2]));
			x3.add(Integer.parseInt(tokens[3]));
			x4.add(Integer.parseInt(tokens[4]));
			outputs.add(Integer.parseInt(tokens[5]));

		}

		//weights
		weights[0] = Math.random();// w1
		weights[1] = Math.random();// w2
		weights[2] = Math.random();// w3
		weights[3] = Math.random();// w4
		weights[4] = Math.random();// bias

		//Perceptron learning part
		//some code here adapted from: https://www.youtube.com/watch?v=4aksMtJHWEQ
		do {
			iteration++;
			globalError = 0;
			for (int i = 0; i < x1.size(); i++) {
				output = calculateOutput(theta, weights, x1.get(i), x2.get(i), x3.get(i), x4.get(i));
				localError = outputs.get(i) - output;
				weights[0] += learning_rate * localError * x1.get(i);
				weights[1] += learning_rate * localError * x2.get(i);
				weights[2] += learning_rate * localError * x3.get(i);
				weights[3] += learning_rate * localError * x4.get(i);
				weights[4] += learning_rate * localError;
				globalError += (localError * localError);
				
			}
		} while (globalError != 0 && iteration <= max_iter);
		
		System.out.print("Boundary equaition: ");
		System.out.println(weights[0] + "*x1 + " + weights[1] + "*x2 + "+ weights[2] + "*x3 + "+ weights[3] + "*x4 + "+ weights[4] + "= " + theta);
		System.out.println();
		
		//for testing perceptron
		
		String continu = "y";

		do {
			Scanner in = new Scanner(System.in);
			String animalName = "";
			int y1 = -1, y2 = -1, y3 = -1, y4 = -1;
			System.out.print("Would you like to classify an animal? (y or n): ");
			continu = in.nextLine();
			if(continu.equalsIgnoreCase("y")){
				System.out.print("Enter animal's name: ");
				animalName = in.nextLine();
				System.out.print("Is the animal warm-blooded? (y or n): ");
				y1 = (in.nextLine().equalsIgnoreCase("y")) ? 1 : -1;
				System.out.print("Is the animal a vertabrate? (y or n): ");
				y2 = (in.nextLine().equalsIgnoreCase("y")) ? 1 : -1;
				System.out.print("Does the animal produce milk? (y or n): ");
				y3 = (in.nextLine().equalsIgnoreCase("y")) ? 1 : -1;
				System.out.print("Does the animal have hair? (y or n): ");
				y4 = (in.nextLine().equalsIgnoreCase("y")) ? 1 : -1;
				
				int mammal = calculateOutput(theta, weights, y1, y2, y3, y4);
				if(mammal == 1) {
					System.out.println("The " + animalName + " is a mammal");
				} else {
					System.out.println("The " + animalName + " is not a mammal");
				}			
			}
		} while (continu.equalsIgnoreCase("y"));
	}
	
	/**
	 * Calculates summation output based on current values 
	 * @param theta
	 * @param weights
	 * @param x1
	 * @param x2
	 * @param x3
	 * @param x4
	 * @return output 1 or 0
	 */
	private static int calculateOutput(int theta, double[] weights,	int x1, int x2, int x3, int x4) {
		int output = 0;
		double sum = x1 * weights[0] + x2 * weights[1] + x3 * weights[2] + x4 * weights[3] + weights[4];
		if(sum >= theta) {
			output = 1;
		}
		return output;
	}
	
	
}
