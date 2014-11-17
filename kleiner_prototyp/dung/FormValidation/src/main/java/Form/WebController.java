package Form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebController {


    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String Anzeige(Person person) {
        return "form";
    }

    @RequestMapping(value="/ergebnis", method=RequestMethod.POST)
  
    public String Validated(ModelMap model, @RequestParam ("names") String name,@RequestParam ("ages") int age,@RequestParam ("addresss") String address ) {
        model.addAttribute("names",name);
        model.addAttribute("ages", age);
        model.addAttribute("addresss", address);
    	return "ergebnis";
    }

}