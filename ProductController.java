package com.anand.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.anand.product.model.Order;
import com.anand.product.model.Product;
import com.anand.product.repository.OrderRepository;
import com.anand.product.repository.ProductRepository;
import com.anand.product.service.CurrencyConversionService;
import com.anand.product.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class ProductController {
    Logger logger = LoggerFactory.getLogger(ProductController.class);

    Double s;
	@Autowired
	private PaypalService paypalService;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CurrencyConversionService currencyConversionService;

	
    @PostMapping("/order1") public Order saveData(@RequestBody Order order1) {
	  return paypalService.saveData(order1); }

	public static final String SUCCESS_URL = "/pay/success";
	public static final String CANCEL_URL = "/pay/cancel";

	@Autowired
	private ProductRepository productRepo;

	@GetMapping("/example")
	public String showExmapleView(Model model) {
		List<Product> productList = productRepo.findAll();
		model.addAttribute("products", productList);
		System.out.println("from the showExampleView()");
		return "example";

	}
	@PostMapping("/home")
	public String home(@RequestParam("totleprice") double totleprice, Model model) {
	    model.addAttribute("price", totleprice);
	    return "home";
	}

//	@PostMapping("/home")
//	public String home(Model model) {
//		double s1=18000.0;
//		model.addAttribute("price",s1);
//		return "home";
//	}

	@PostMapping("/pay")
	public String payment(@ModelAttribute("order") Order order) {
		try {
			System.out.print(order);

			Order order1 = new Order();
			double totalAmount = order.getPrice();
			s=order.getPrice();
			String currency = order.getCurrency();
			String paymentMethod = order.getMethod();
			String paymentIntent = order.getIntent();
			String transactionDescription = order.getDescription();
			order1.setCurrency(currency);
			order1.setDescription(transactionDescription);
			order1.setIntent(paymentIntent);
			order1.setMethod(paymentMethod);
			order1.setPrice(totalAmount);
			orderRepository.save(order1);
//				String cancelUrl = "https://yourwebsite.com/cancel";
//				String successUrl = "https://yourwebsite.com/success";
			double totalAmountInINR = currencyConversionService.convertToINR(totalAmount, currency);
			Payment payment = paypalService.createPayment(totalAmount, currency, paymentMethod, paymentIntent,
					transactionDescription, "http://localhost:8082" + CANCEL_URL,
					"http://localhost:8082" + SUCCESS_URL);
			for (Links link : payment.getLinks()) {
				System.out.println("Link : "+link);
				if (link.getRel().equals("approval_url")) {
					return "redirect:" + link.getHref();
				}
			}

		} catch (PayPalRESTException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	@GetMapping(value = CANCEL_URL)
	public String cancelPay() {
		return "cancel";
	}

	@GetMapping(value = SUCCESS_URL)
	public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			System.out.println(payment.toJSON());
			if (payment.getState().equals("approved")) {
				return "success";
			}
		} catch (PayPalRESTException e) {
			System.out.println(e.getMessage());
		}
		return "redirect:/";
	}
	@GetMapping(value = "abc")
	public String addPay(long id,Model model) {
		
		Product product=productRepo.findById(id).orElse(null);
		System.out.println(product.getTotleprice());
		model.addAttribute("price", product.getTotleprice());
		return "home";
	}
	
}