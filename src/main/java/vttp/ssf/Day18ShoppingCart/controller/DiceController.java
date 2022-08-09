package vttp.ssf.Day18ShoppingCart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vttp.ssf.Day18ShoppingCart.services.DiceService;

@Controller
@RequestMapping("/roll")
public class DiceController {
    
    @Autowired
    private DiceService diceSvc;

    @GetMapping
    public String roll(Model model) {
        List<Integer> result = diceSvc.roll(5);
        model.addAttribute("dices", result);
        return "roll";
    }
    
}
