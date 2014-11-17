package demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonController {
	
	private static final String IS_AJAX_HEADER = "X-Requested-With=XMLHttpRequest";
	private final Person person;

	@Autowired
	public PersonController(Person person) {
		this.person = person;
	}

	@RequestMapping("/")
	public String index() {
		return "redirect:/person";
	}

	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public String guestBook(Model model) {

		model.addAttribute("entries", person.findAll());
		return "person";
	}

	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public String addEntry(@RequestParam("name") String name, @RequestParam("lastname") String lastname, @RequestParam("text") String text) {

		person.save(new PersonEntry(name,lastname, text));
		return "redirect:/person";
	}

	@RequestMapping(value = "/person", method = RequestMethod.POST, headers = IS_AJAX_HEADER)
	public String addEntry(@RequestParam("name") String name,@RequestParam("lastname") String lastname, @RequestParam("text") String text, Model model) {

		model.addAttribute("entry", person.save(new PersonEntry(name,lastname, text)));
		model.addAttribute("index", person.count());
		return "person :: entry";
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
	public String removeEntry(@PathVariable Long id) {
		person.delete(id);
		return "redirect:/person";
	}


	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE, headers = IS_AJAX_HEADER)
	public HttpEntity<?> removeEntryJS(@PathVariable Long id) {

		Optional<PersonEntry> entry = person.findOne(id);

		if (!entry.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		person.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
