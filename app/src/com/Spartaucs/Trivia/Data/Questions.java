package com.Spartaucs.Trivia.Data;

import java.util.ArrayList;

public class Questions {
	
	private int size;
	private String[] array;
	private ArrayList<Integer> used;
	
	public Questions(String[] array){
		size = Integer.parseInt(array[0]);
		this.array = array;
		used = new ArrayList<Integer>();
	}
	
	public int getSize(){
		return size;
	}
	
	public String[] getQuestion(){
	int z;
		
		if(used.size() == size){
			System.err.println("All questions are used! Program terminating.....");
			System.exit(0);
		}
		
		int temp;
		String[] str = new String[6];
		do{
			z = (int)((Math.random()*100));
			temp = ((z % size));
			//System.out.println(temp);
		}while(isUsed(temp));
		used.add(temp);
		str[0] = array[temp*6 + 1];
		str[1] = array[temp*6 + 2];
		str[2] = array[temp*6 + 3];
		str[3] = array[temp*6 + 4];
		str[4] = array[temp*6 + 5];
		str[5] = array[temp*6 + 6];
		
		return str;
	}
	
	public boolean isUsed(int input){
		 return used.contains(input);
	}
	
}
