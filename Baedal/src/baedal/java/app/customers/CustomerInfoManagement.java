package baedal.java.app.customers;

import baedal.java.app.common.Management;

public class CustomerInfoManagement extends Management {
	private static String id;
	private static Customer customer;
	
	public CustomerInfoManagement(String id) {
		this.id = id;
		this.customer = customerDAO.showProfile(id);
		
		
	}

}
