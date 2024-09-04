package com.tns.onlineshopping.services;

public class SharedServices {
	
	    public static final ProductService productService = new ProductService();
	    public static final CustomerService customerService = new CustomerService();
	    public static final AdminService adminService = new AdminService();
	    public static final OrderService orderService = new OrderService(productService);
	}


