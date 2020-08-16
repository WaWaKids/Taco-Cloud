package com.example.tacocloud.web;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import com.example.tacocloud.Ingredient;
import com.example.tacocloud.Ingredient.Type;
import com.example.tacocloud.Taco;

@Slf4j
@Controller
public class DesignTacoController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
        );

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @GetMapping("/design")
    public String showDesignForm(Model model) {
        model.addAttribute("design", new Taco());
        return "design";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(toList());
    }

}