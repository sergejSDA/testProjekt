package demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalcController {

    @RequestMapping(value="/form", method=RequestMethod.GET)
    public String Anzeige() {
        return "form";
    }

    @RequestMapping(value="/ergebnis", method=RequestMethod.POST)
    public String Validated(ModelMap model, @RequestParam ("zahl1") int zahl1,@RequestParam ("zahl2") int zahl2,@RequestParam ("ergebnis") int ergebnis ) {
        model.addAttribute("zahl1",zahl1);
        model.addAttribute("zahl2", zahl2);
        model.addAttribute("ergebnis", ergebnis);
    	return "ergebnis";
    }
}
