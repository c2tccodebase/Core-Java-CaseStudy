package com.tns.onlineshopping.application;

import java.util.Map;
import java.util.Scanner;

import com.tns.onlineshopping.entities.Customer;
import com.tns.onlineshopping.entities.Order;
import com.tns.onlineshopping.entities.Product;
import com.tns.onlineshopping.entities.ProductQuantityPair;
import com.tns.onlineshopping.services.CustomerService;
import com.tns.onlineshopping.services.OrderService;
import com.tns.onlineshopping.services.ProductService;
import com.tns.onlineshopping.services.SharedServices;

public class CustomerMenuDrivenDriver implements Runnable {
	    private static ProductService productService = SharedServices.productService;
	    private static CustomerService customerService = SharedServices.customerService;
	    private static OrderService orderService = SharedServices.orderService;

	    @Override
	    public void run() {
	        Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("\nCustomer Menu:");
	            System.out.println("1. Register Customer");
	            System.out.println("2. View Products");
	            System.out.println("3. Add to Cart");
	            System.out.println("4. View Cart");
	            System.out.println("5. Place Order");
	            System.out.println("6. View Orders");
	            System.out.println("7. Exit");
	            System.out.print("Choose an option: ");
	            int choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                    registerCustomer(scanner);
	                    break;
	                case 2:
	                    viewProducts();
	                    break;
	                case 3:
	                    addToCart(scanner);
	                    break;
	                case 4:
	                    viewCart(scanner);
	                    break;
	                case 5:
	                    placeOrder(scanner);
	                    break;
	                case 6:
	                    viewOrders(scanner);
	                    break;
	                case 7:
	                    System.out.println("Exiting...");
	                    return;
	                default:
	                    System.out.println("Invalid choice! Please try again.");
	            }
	        }
	    }

	    private void registerCustomer(Scanner scanner) {
	        System.out.print("Enter User ID: ");
	        int userId = scanner.nextInt();
	        System.out.print("Enter Username: ");
	        String username = scanner.next();
	        System.out.print("Enter Email: ");
	        String email = scanner.next();
	        System.out.print("Enter Address: ");
	        String address = scanner.next();

	        Customer customer = new Customer(userId, username, email, address);
	        customerService.addCustomer(customer);
	        System.out.println("Customer registered successfully!");
	    }

	    private void viewProducts() {
	        System.out.println("Products:");
	        for (Product product : productService.getProducts()) {
	            System.out.println(product);
	        }
	    }

	    private void addToCart(Scanner scanner) {
	        System.out.print("Enter Customer ID: ");
	        int customerId = scanner.nextInt();
	        Customer customer = customerService.getCustomer(customerId);
	        if (customer == null) {
	            System.out.println("Customer not found!");
	            return;
	        }

	        System.out.print("Enter Product ID: ");
	        int productId = scanner.nextInt();
	        Product product = productService.getProductById(productId);
	        if (product == null) {
	            System.out.println("Product not found!");
	            return;
	        }

	        System.out.print("Enter Quantity: ");
	        int quantity = scanner.nextInt();

	        customer.getShoppingCart().addItem(product, quantity);
	        System.out.println("Product added to cart successfully!");
	    }

	    private void viewCart(Scanner scanner) {
	        System.out.print("Enter Customer ID: ");
	        int customerId = scanner.nextInt();
	        Customer customer = customerService.getCustomer(customerId);
	        if (customer == null) {
	            System.out.println("Customer not found!");
	            return;
	        }

	        System.out.println("Shopping Cart:");
	        for (Map.Entry<Product, Integer> entry : customer.getShoppingCart().getItems().entrySet()) {
	            Product product = entry.getKey();
	            int quantity = entry.getValue();
	            System.out.println("Product: " + product.getName() + ", Quantity: " + quantity);
	        }
	    }

	    private void placeOrder(Scanner scanner) {
	        System.out.print("Enter Customer ID: ");
	        int customerId = scanner.nextInt();
	        Customer customer = customerService.getCustomer(customerId);
	        if (customer == null) {
	            System.out.println("Customer not found!");
	            return;
	        }

	        Order order = new Order(customer);
	        for (Map.Entry<Product, Integer> entry : customer.getShoppingCart().getItems().entrySet()) {
	            Product product = entry.getKey();
	            int quantity = entry.getValue();
	            order.addProduct(product, quantity);
	        }

	        orderService.placeOrder(order);
	        customer.addOrder(order);
	        customer.getShoppingCart().clear();
	        System.out.println("Order placed successfully!");
	    }

	    private void viewOrders(Scanner scanner) {
	        System.out.print("Enter Customer ID: ");
	        int customerId = scanner.nextInt();
	        Customer customer = customerService.getCustomer(customerId);
	        if (customer == null) {
	            System.out.println("Customer not found!");
	            return;
	        }

	        System.out.println("Orders:");
	        for (Order order : customer.getOrders()) {
	            System.out.println("Order ID: " + order.getOrderId() + ", Status: " + order.getStatus());
	            for (ProductQuantityPair pair : order.getProducts()) {
	                System.out.println("  Product: " + pair.getProduct().getName() + ", Quantity: " + pair.getQuantity());
	            }
	        }
	    }
	
}
