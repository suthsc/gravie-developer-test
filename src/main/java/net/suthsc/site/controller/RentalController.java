package net.suthsc.site.controller;

import net.suthsc.site.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RentalController {

    private final ProductService productService;

    @Autowired
    public RentalController(ProductService service) {
        productService = service;
    }

    @GetMapping("/rent/{guid}/")
    public String rentGame(@PathVariable("guid") String guid, Model model) {
        model.addAttribute("product", productService.detailFor(guid));
        return "rent";
    }

}
