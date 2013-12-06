package com.Spartaucs.Trivia.Data;


public class QuestionManager {
	
	int size;
	int count;
	Questions[] questions;
	
	public QuestionManager(int size, String[] easyArray, String[] hardArray){
		
		count = 0;
		questions = new Questions[size];
		questions[0] = new Questions(easyArray);
		questions[1] = new Questions(hardArray);
	}
	
	public void countIncrement(){
		if( count == 2){
			count = 0;
		}else{
			count++;
		}
	}
	
	public String[] getQuestion(){
		
		countIncrement();
		
		if(count == 2 ){
			return questions[1].getQuestion();
		}else{
			return questions[0].getQuestion();
		}
	}
	
}
