package demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class Testcontroller {

	@RequestMapping(value="/eingabe", method=RequestMethod.GET)
    public String Anzeige() {
        return "eingabe";
    }

    @RequestMapping(value="/ergebnis", method=RequestMethod.POST)
    public String Begrüßung(ModelMap model, @RequestParam ("names") String name) {
        model.addAttribute("names",name);
        return "ergebnis";
    }

}