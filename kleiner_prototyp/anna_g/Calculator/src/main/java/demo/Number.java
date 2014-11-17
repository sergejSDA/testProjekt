package demo;

public class Number {
	private float firstNumber;
	private float secondNumber;
	private float result;
	
	
	public float getFirstNumber (){
		return firstNumber;
	}
	public void setFirstNumber (float firstNumber){
		this.firstNumber = firstNumber;
	}
	
	public float getSecondNumber(){
		return secondNumber;
	}
	
	public void setSecondNumber (float secondNumber){
		this.secondNumber = secondNumber;
	}
	
	public float getResult (){
		result = firstNumber + secondNumber;
		return result;
	}

}
