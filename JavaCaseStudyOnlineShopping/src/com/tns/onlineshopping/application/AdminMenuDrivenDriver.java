package com.tns.onlineshopping.application;

import java.util.Scanner;

import com.tns.onlineshopping.entities.Admin;
import com.tns.onlineshopping.entities.Order;
import com.tns.onlineshopping.entities.Product;
import com.tns.onlineshopping.entities.ProductQuantityPair;
import com.tns.onlineshopping.services.AdminService;
import com.tns.onlineshopping.services.OrderService;
import com.tns.onlineshopping.services.ProductService;
import com.tns.onlineshopping.services.SharedServices;

public class AdminMenuDrivenDriver implements Runnable {
    private static ProductService productService = SharedServices.productService;
    private static AdminService adminService = SharedServices.adminService;
    private static OrderService orderService = SharedServices.orderService;

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. View Products");
            System.out.println("4. Create Admin");
            System.out.println("5. View Admins");
            System.out.println("6. Update Order Status");
            System.out.println("7. View Orders");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    removeProduct(scanner);
                    break;
                case 3:
                    viewProducts();
                    break;
                case 4:
                    createAdmin(scanner);
                    break;
                case 5:
                    viewAdmins();
                    break;
                case 6:
                    updateOrderStatus(scanner);
                    break;
                case 7:
                    viewOrders();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void addProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Product Name: ");
        String name = scanner.next();
        System.out.print("Enter Product Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Stock Quantity: ");
        int stockQuantity = scanner.nextInt();

        Product product = new Product(productId, name, price, stockQuantity);
        productService.addProduct(product);
        System.out.println("Product added successfully!");
    }

    private void removeProduct(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();

        productService.removeProduct(productId);
        System.out.println("Product removed successfully!");
    }

    private void viewProducts() {
        System.out.println("Products:");
        for (Product product : productService.getProducts()) {
            System.out.println(product);
        }
    }

    private void createAdmin(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Email: ");
        String email = scanner.next();

        Admin admin = new Admin(userId, username, email);
        adminService.addAdmin(admin);
        System.out.println("Admin created successfully!");
    }

    private void viewAdmins() {
        System.out.println("Admins:");
        for (Admin admin : adminService.getAdmins()) {
            System.out.println("User ID: " + admin.getUserId() + ", Username: " + admin.getUsername() + ", Email: " + admin.getEmail());
        }
    }

    private void updateOrderStatus(Scanner scanner) {
        System.out.print("Enter Order ID: ");
        int orderId = scanner.nextInt();
        System.out.print("Enter new status (Pending/Completed/Delivered/Cancelled): ");
        String status = scanner.next();

        orderService.updateOrderStatus(orderId, status);
        System.out.println("Order status updated successfully!");
    }

    private void viewOrders() {
        System.out.println("Orders:");
        for (Order order : orderService.getOrders()) {
            System.out.println("Order ID: " + order.getOrderId() + ", Customer: " + order.getCustomer().getUsername() + ", Status: " + order.getStatus());
            for (ProductQuantityPair pair : order.getProducts()) {
                System.out.println("  Product: " + pair.getProduct().getName() + ", Quantity: " + pair.getQuantity());
            }
        }
    }
}
