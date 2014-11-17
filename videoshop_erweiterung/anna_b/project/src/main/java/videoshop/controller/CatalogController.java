package videoshop.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.salespointframework.inventory.Inventory;
import org.salespointframework.inventory.InventoryItem;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.quantity.Units;
import org.salespointframework.time.BusinessTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import videoshop.model.Comment;
import videoshop.model.Disc;
import videoshop.model.Disc.DiscType;
import videoshop.model.VideoCatalog;

@Controller
class CatalogController {

	private final VideoCatalog videoCatalog;
	private final Inventory<InventoryItem> inventory;
	private final BusinessTime businessTime;

	// (｡◕‿◕｡)
	// Da wir nur ein Catalog.html-Template nutzen, aber dennoch den richtigen Titel auf der Seite haben wollen,
	// nutzen wir den MessageSourceAccessor um an die messsages.properties Werte zu kommen
	private final MessageSourceAccessor messageSourceAccessor;
	
	@Autowired
	public CatalogController(VideoCatalog videoCatalog, Inventory<InventoryItem> inventory, BusinessTime businessTime,
			MessageSource messageSource) {

		this.videoCatalog = videoCatalog;
		this.inventory = inventory;
		this.businessTime = businessTime;
		this.messageSourceAccessor = new MessageSourceAccessor(messageSource);
	}

	@RequestMapping("/dvdCatalog")
	public String dvdCatalog(ModelMap modelMap) {

		modelMap.addAttribute("catalog", videoCatalog.findByType(DiscType.DVD));
		modelMap.addAttribute("title", messageSourceAccessor.getMessage("catalog.dvd.title"));

		return "discCatalog";
	}
	
//Search START
	
	//save searchterm as pathvariable redirect to "dvdcatalog?search="+searchterm
	@RequestMapping(value = "/dvdCatalog", method = RequestMethod.POST)
	public String goToResultPageDvd(@RequestParam("searchTerm") String searchTerm, RedirectAttributes redirectAttrs)
	{		
		redirectAttrs.addAttribute("term", searchTerm);
		return "redirect:/dvdCatalog/{term}";
	}

	
	//show result
	@RequestMapping(value = "/dvdCatalog/{term}")
	public String showResultPageDvd(@PathVariable("term") String searchTerm, ModelMap modelMap, RedirectAttributes redirectAttrs)
	{		
		Collection<Disc> resultCatalog = new ArrayList<>();
		for(Disc disc : videoCatalog.findAll())
		{
			if(disc.getName().contains(searchTerm))
				{
					resultCatalog.add(disc);
				}		
		}
		
		modelMap.addAttribute("results", resultCatalog);
		
		return "discCatalog";
	}
//Search END
	
	@RequestMapping("/blurayCatalog")
	public String blurayCatalog(Model model) {

		model.addAttribute("catalog", videoCatalog.findByType(DiscType.BLURAY));
		model.addAttribute("title", messageSourceAccessor.getMessage("catalog.bluray.title"));

		return "discCatalog";
	}

	// (｡◕‿◕｡)
	// Befindet sich die angesurfte Url in der Form /foo/5 statt /foo?bar=5 so muss man @PathVariable benutzen
	// Lektüre: http://spring.io/blog/2009/03/08/rest-in-spring-3-mvc/
	@RequestMapping("/detail/{pid}")
	public String detail(@PathVariable("pid") Disc disc, Model model) {

		Optional<InventoryItem> item = inventory.findByProductIdentifier(disc.getIdentifier());
		Quantity quantity = item.map(InventoryItem::getQuantity).orElse(Units.ZERO);

		model.addAttribute("disc", disc);
		model.addAttribute("quantity", quantity);
		model.addAttribute("orderable", quantity.isGreaterThan(Units.ZERO));

		return "detail";
	}

	// (｡◕‿◕｡)
	// Der Katalog bzw die Datenbank "weiß" nicht, dass die Disc mit einem Kommentar versehen wurde,
	// deswegen wird die update-Methode aufgerufen
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public String comment(@RequestParam("pid") Disc disc, @RequestParam("comment") String comment,
			@RequestParam("rating") int rating) {

		disc.addComment(new Comment(comment, rating, businessTime.getTime()));
		videoCatalog.save(disc);

		return "redirect:detail/" + disc.getIdentifier();
	}
}
