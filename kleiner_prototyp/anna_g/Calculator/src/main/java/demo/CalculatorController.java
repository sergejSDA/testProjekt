package demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@EnableAutoConfiguration
@Controller
public class CalculatorController {
	@RequestMapping(value="/calculator", method=RequestMethod.GET)
    public String Show(ModelMap modelMap) {
        return "calculator";
    }

    @RequestMapping(value="/result", method=RequestMethod.POST)
  
    public String Number (ModelMap model, @RequestParam ("firstNumber") float firstNumber, @RequestParam ("secondNumber") float secondNumber) {
        model.addAttribute("firstNumber",firstNumber);
        model.addAttribute("secondNumber", secondNumber);
        Number resultEnd = new Number();
        resultEnd.setFirstNumber(firstNumber);
        resultEnd.setSecondNumber(secondNumber);
        
        model.addAttribute("result", resultEnd.getResult());
    	return "result";
    }

}
