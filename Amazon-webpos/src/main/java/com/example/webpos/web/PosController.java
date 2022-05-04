package com.example.webpos.web;

import javax.servlet.http.HttpSession;

import com.example.webpos.biz.PosService;
import com.example.webpos.model.Cart;
import com.example.webpos.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PosController {

    private PosService posService;

    @Autowired
    private HttpSession session;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    private void saveCart(Cart cart) {
        session.setAttribute("cart", cart);
    }

    private Cart getCart(){
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            this.saveCart(cart);
        }
        return cart;
    }
    
    @GetMapping("/")
    public String pos(Model model) {
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }

    @GetMapping("/add")
    public String add(@RequestParam(name = "pid") String pid,Model model){
        posService.add(getCart(),pid, 1);
        saveCart(getCart());
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam(name = "pid") String pid,
    @RequestParam(name = "quantity") int quantity, Model model){
        posService.modify(getCart(),pid, quantity);
        saveCart(getCart());
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam(name = "pid") String pid,Model model){
        posService.remove(getCart(),pid);
        saveCart(getCart());
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }

    @GetMapping("/empty")
    public String empty(Model model){
        posService.empty(getCart());
        saveCart(getCart());
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", getCart());
        return "index";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        model.addAttribute("checkoutAmount", posService.checkout(getCart()));
        saveCart(getCart());
        return "paid";
    }
}
