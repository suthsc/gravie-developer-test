package net.suthsc.site.controller;

import net.suthsc.constants.ApiConstants;
import net.suthsc.site.service.ProductService;
import net.suthsc.value.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class SearchController implements ApiConstants {

    private static final Logger LOGGER = Logger.getLogger(SearchController.class.getSimpleName());
    private static final String SEARCH_URL = "/search/";

    private final ProductService productService;

    @Autowired
    public SearchController(ProductService service) {
        productService = service;
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "query") String query,
                                      @RequestParam(name = "type", required = false, defaultValue = "game") String type,
                                      Model model) {

        List<Product> products = productService.search(query, type);
        model.addAttribute("products", products);
        return "search";
    }

}
