package net.suthsc.site.controller;

import net.suthsc.constants.ApiConstants;
import net.suthsc.site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class CatalogController implements ApiConstants {

    private static final Logger LOGGER = Logger.getLogger(CatalogController.class.getSimpleName());

    private static final String GAMES_URL = "/games";

    private final ProductService productService;

    @Autowired
    public CatalogController(ProductService service) {
        productService = service;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("products", productService.list(1, 8));
        return "index";
    }

    @GetMapping("/catalog")
    public String catalog(@RequestParam(name="page", required = false, defaultValue = "1") String pageNumber,
                                       @RequestParam(name="size", required = false, defaultValue = "4") String pageSize,
                                       Model model) {
        model.addAttribute("products", productService.list(Integer.parseInt(pageNumber), Integer.parseInt(pageSize)));

        return "index";
    }

    @GetMapping(path = "/view/{guid}/")
    public String catalog(@PathVariable("guid") String guid, Model model) {
        model.addAttribute("product", productService.detailFor(guid));
        return "view";
    }
}
