package com.ontariotechu.sofe3980U;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BinaryController {

	@GetMapping("/")
	public String getCalculator(@RequestParam(name="operand1", required=false, defaultValue="") String operand1, Model model) {
		model.addAttribute("operand1", operand1);
		model.addAttribute("operand1Focused", operand1.length()>0);
        return "calculator";
	}
	
	@PostMapping("/")
	public String result(@RequestParam(name="operand1", required=false, defaultValue="") String operand1,
	@RequestParam(name="operator", required=false, defaultValue="") String operator ,
	@RequestParam(name="operand2", required=false, defaultValue="") String operand2, Model model) {
		model.addAttribute("operand1", operand1);
		model.addAttribute("operator", operator);
		model.addAttribute("operand2", operand2);
		Binary number1=new Binary (operand1);
		Binary number2=new Binary (operand2);
		switch(operator)
		{
			case "+":
				model.addAttribute("result", Binary.add(number1,number2).getValue());
				return "result";
			default:
				return "Error";
		}
	}

}