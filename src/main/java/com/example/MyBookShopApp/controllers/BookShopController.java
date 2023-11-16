package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.entity.Book;
import com.example.MyBookShopApp.service.BookService;
import com.example.MyBookShopApp.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Controller
@RequestMapping("/books")
public class BookShopController {

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart(){
        return new ArrayList<>();
    }
    @ModelAttribute(name = "bookPost")
    public List<Book> bookPost(){
        return new ArrayList<>();
    }

    private final BookRepository bookRepository;

    private final PaymentsService paymentsService;

    private final BookService bookService;
    @Autowired
    public BookShopController(BookRepository bookRepository, PaymentsService paymentsService, BookService bookService) {
        this.bookRepository = bookRepository;
        this.paymentsService = paymentsService;
        this.bookService = bookService;
    }



    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model){
        if (cartContents == null || cartContents.equals("")){
            model.addAttribute("isCartEmpty",true);
        }else {
            model.addAttribute("isCartEmpty",false);
//            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) :
//                    cartContents;
//            cartContents = cartContents.endsWith("/") ? cartContents.substring(0,cartContents.length()-1) : cartContents;
            String[] cookiesSlugs = cartContents.split("/");



            Logger.getLogger(BookShopController.class.getSimpleName()).info("its cookies to else = " + cartContents);
            List<Book> bookFromCookiesSlugs = bookRepository.findBookBySlugIn(cookiesSlugs);
            model.addAttribute("bookCart",bookFromCookiesSlugs);
        }
        return "cart";
    }
    @GetMapping("/postponed")
    public String getPostponedBooks(@CookieValue(value = "paidContents",required = false) String postContents,
                                    Model model){
        if (postContents == null || postContents.equals("")){
            model.addAttribute("postIsEmpty",true);
        }else {
            model.addAttribute("postIsEmpty",false);
            String[] cookiesSlugs = postContents.split("/");
            List<Book> bookFromCookiesSlugs = bookService.findBookBySlugsIn(cookiesSlugs);
            model.addAttribute("bookPost",bookFromCookiesSlugs);

        }
        return "postponed";
    }


    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String handleRemoveBookFromCart(@PathVariable("slug") String slug,
                                           @CookieValue(value = "cartContents",
                                           required = false) String cartContents,
                                           HttpServletResponse response,
                                           Model model){
        if (cartContents != null && !cartContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents",String.join("/",cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty",false); 
        } else {
            model.addAttribute("isCartEmpty",true);
        }
        return "redirect:/books/cart";
        
    }
    @PostMapping("/changeBookStatus/post/remove/{slug}")
    public String handleRemoveBookFromPostponed(@PathVariable("slug") String slug,
                                           @CookieValue(value = "paidContents",
                                                   required = false) String paidContents,
                                           HttpServletResponse response,
                                           Model model){
        if (paidContents != null && !paidContents.equals("")){
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(paidContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("paidContents",String.join("/",cookieBooks));
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty",false);
        } else {
            model.addAttribute("isCartEmpty",true);
        }
        return "redirect:/books/postponed";

    }

    @PostMapping("/changeBookStatus/{slug}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @CookieValue(value = "cartContents",
    required = false) String cartContents, HttpServletResponse response, Model model){
        if (cartContents == null || cartContents.equals("")){
            Cookie cookie = new Cookie("cartContents",slug);
            cookie.setPath("/books");
            response.addCookie(cookie);

        Logger.getLogger(BookShopController.class.getSimpleName()).info("its cookies= " + cookie);
            model.addAttribute("isCartEmpty",false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents",stringJoiner.toString());
            cookie.setPath("/books");
             Logger.getLogger(BookShopController.class.getSimpleName()).info("its cookies to else = " + cookie);
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty",false);

            
        }


        return "redirect:/books/" + slug;
    }
    @PostMapping("/changeBookStatus/post/{slug}")
    public String handleChangeBookStatusPaid(@PathVariable("slug") String slug, @CookieValue(value = "paidContents",
            required = false) String paidContents,  HttpServletResponse response, Model model){
        if (paidContents == null || paidContents.equals("")){
            Cookie cookie = new Cookie("paidContents",slug);
            cookie.setPath("/books");
            response.addCookie(cookie);

            Logger.getLogger(BookShopController.class.getSimpleName()).info("its cookies= " + cookie);
            model.addAttribute("postIsEmpty",false);
        } else if (!paidContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(paidContents).add(slug);
            Cookie cookie = new Cookie("paidContents",stringJoiner.toString());
            cookie.setPath("/books");
            Logger.getLogger(BookShopController.class.getSimpleName()).info("its cookies to else = " + cookie);
            response.addCookie(cookie);
            model.addAttribute("postIsEmpty",false);


        }


        return "redirect:/books/" + slug;
    }

    @GetMapping("/pay")
    public RedirectView handlePay(@CookieValue(value = "cartContents", required = false) String cartContents) throws NoSuchAlgorithmException {

        cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
        cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) :
                cartContents;
        String[] cookieSlugs = cartContents.split("/");
        List<Book> booksFromCookieSlugs = bookRepository.findBookBySlugIn(cookieSlugs);
        String paymentUrl = paymentsService.getPaymentUrl(booksFromCookieSlugs);
        return new RedirectView(paymentUrl);
    }


}
