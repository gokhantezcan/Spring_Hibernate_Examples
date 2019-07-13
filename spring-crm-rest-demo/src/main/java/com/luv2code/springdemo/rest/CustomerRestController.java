package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// post api customer create new customer
	//get api customer read a list of customers
	//get api/customers read single customer
	//put api/ customers( id update existing customer
	// delete api customer id delete
	
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/customers")
	public List<Customer> getCustomers()
	{
		return customerService.getCustomers();
	}
	
	@GetMapping("/customer/{customerId}")
	public Customer getCustomer(@PathVariable int customerId)
	{
		Customer theCustomer = customerService.getCustomer(customerId);
		// if customer was null, jackson would return empty json body
		
		if(theCustomer == null)
		{
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
		
		return theCustomer;
	}
	
	// @RequestBody to access the request body as a pojo. bu ksımı api uygulamasından yapmaya calıstı.
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer)
	{
		// also just in case the pass an id in json ... set is to 0 thjis is force  a save of new item .. instead update saveorupodate
		theCustomer.setId(0);
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer)
	{
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId)
	{
		Customer tempCustomer = customerService.getCustomer(customerId);
		
		if(tempCustomer == null)
		{
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}
		
		customerService.deleteCustomer(customerId);
		return "Deleted customer id - " + customerId;
	}
	
}






