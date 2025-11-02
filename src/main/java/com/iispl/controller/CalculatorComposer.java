package com.iispl.controller;


import java.util.Map;

import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CalculatorComposer extends SelectorComposer<Window> {

	@Wire
	Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0;

	@Wire
	Button add, sub, mult, div;
	
	@Wire
	Textbox resultBox;
	
	Map<String, String> operandsMap = Map.of("add", "+",
											"sub", "-",
											"mult", "X",
											"div", "/");

	double num1 = 0, num2 = 0;
	String temp = "", operand = "";
//	
//	@Listen("onClick=#clear")
//	public void clear() {
//		num1 = 0;
//		num2 = 0;
//		temp = "";
//		resultBox.setText("");
//		alert("cleared..");
//	}

	@Listen("onClick=button")
	public void calculate(Event e) {

		resultBox.setDisabled(true);

		String id = e.getTarget().getId();
		if (id.equals("clear")) {
			num1 = 0;
			num2 = 0;
			temp = "";
			resultBox.setText("");
			alert("cleared..");
			return;
		}
		
		if (Character.isDigit(id.charAt(0))) {
			int num = Integer.parseInt(id);
			if (operand.isBlank()) {
				num1 = num1 * 10 + num;
				resultBox.setValue(num1+"");
			} else {
				num2 = num2 * 10 + num;		
				resultBox.setValue(temp + num2 );
			}
		} else if(id.equals("calculate")) {
			double rs = operation(operand, num1, num2);
			resultBox.setValue(resultBox.getText() + " = " + rs );
			
		}
		else {		
			operand = id;	
			resultBox.setText(num1 + " " + operandsMap.get(operand) + " ");
			temp = resultBox.getText();
		}
	}

	public double operation(String operand, double num1, double num2) {
		double result = 0;
		switch (operand) {
		case "add":
			return num1 + num2;
		case "sub":
			return num1 - num2;
		case "mult":
			return num1 * num2;
		case "div":
			if (num2 == 0 && num1 != 0)
				alert("zero can't devide");
			else
				return num1 / num2;
		default:
			alert("Invalid choice");
		}
		return 0;
	}
	
	
}