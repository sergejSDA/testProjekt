package Form;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WebController {


    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String Show() {
        return "form";
    }

    @RequestMapping(value="/ergebnis", method=RequestMethod.POST)
  
    public String Calc(ModelMap model, @RequestParam ("zahl1") int zahl1,@RequestParam ("zahl2") int zahl2 ) {
        model.addAttribute("zahl1",zahl1);
        model.addAttribute("zahl2", zahl2);
        int zahl3 = zahl1 % zahl2;
        model.addAttribute("zahl3", zahl3);
    	return "ergebnis";
    }

}