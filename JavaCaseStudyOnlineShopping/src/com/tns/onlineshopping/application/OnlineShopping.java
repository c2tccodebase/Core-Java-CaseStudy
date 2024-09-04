package com.tns.onlineshopping.application;

public class OnlineShopping {

	public static void main(String[] args) {
		Thread adminThread = new Thread(new AdminMenuDrivenDriver());
		Thread customerThread = new Thread(new CustomerMenuDrivenDriver());

		adminThread.start();
		customerThread.start();
	}
}
