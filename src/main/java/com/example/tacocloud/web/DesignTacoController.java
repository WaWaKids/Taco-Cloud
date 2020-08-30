package com.example.tacocloud.web;

import com.example.tacocloud.Ingredient;
import com.example.tacocloud.Ingredient.Type;
import com.example.tacocloud.Order;
import com.example.tacocloud.Taco;
import com.example.tacocloud.data.IngredientRepository;
import com.example.tacocloud.data.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

//a controller for "/design" page

@Controller
@RequestMapping("/design")
@SessionAttributes("order") //keeps all the objects under the "order" session
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
    private final TacoRepository designRepo;

    @Autowired
    public DesignTacoController (IngredientRepository ingredientRepo, TacoRepository designRepo) {

        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")//annotation that ensures that "order" object will be created
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @GetMapping("/design")
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i)); //adding all the list of ingredients from repos to
                                                                   //list
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {                                  //filtering all the ingredients by type to the groups,
            model.addAttribute(type.toString().toLowerCase(),      //then sending them to html file
                    filterByType(ingredients, type));
        }

        return "design";
    }

    @PostMapping("/design")
    public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
        if (!errors.hasErrors()) { //return the "design" page if has errors
            return "design";       //if not, saves the order and redirects to "orders/current" page
        }

        Taco saved = designRepo.save(design);
        order.addDesign(saved);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(toList());
    }

}