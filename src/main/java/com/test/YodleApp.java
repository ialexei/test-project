package com.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Yodle Challenge
 * The program reads the text file from main/resources and computes 
 * the max sum as required in the problem.
 *
 */
public class YodleApp {
	
	public long computeSum(String fileName) {
		
		long maxSum = 0;
		int rowIndex = 0;
		int currentIndex = 0;
		ClassLoader classLoader = getClass().getClassLoader();
		try (InputStream is = classLoader.getResourceAsStream(fileName)) {
			
			try (Scanner scanner = new Scanner(is)) {
	
				while (scanner.hasNextLine()) {
					
					String line = scanner.nextLine();
					System.out.println(line);
					
					long[] row = parseLine(line);
					if (rowIndex == 0) {
						
						maxSum = row[0];
					}else{
					
						if (row.length-1 < rowIndex) {
							throw new IllegalArgumentException();
						}
					
						currentIndex = currentIndex+(row[currentIndex] > row[currentIndex+1] ? 0 : 1);
						maxSum += row[currentIndex];
					}
					
					rowIndex++;
				}
	
				scanner.close();
			
			}
		}catch(IOException e) {
			throw new IllegalStateException(e);
		}
		
		return maxSum;
	}
	
	private long[] parseLine(String line) {
		
		String []strNumbers = line.split(" ");
		long[] numbers = new long[strNumbers.length];
		int index = 0;
		for(String strNumber : strNumbers) {
			numbers[index++] = Long.parseLong(strNumber);
		}
		
		return numbers;
	}
	
	public static void main(String[] args) {
		
		System.out.println("Max sum : "+new YodleApp().computeSum("triangle.txt"));
		System.out.println("Max sum : "+new YodleApp().computeSum("triangle2.txt"));
		System.exit(0);
	}
}
