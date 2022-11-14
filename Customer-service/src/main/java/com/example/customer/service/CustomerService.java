package com.example.customer.service;


import com.example.customer.exceptions.NoCustomerExistException;
import com.example.customer.model.dao.ICustomerDao;
import com.example.customer.model.entity.Customer;
import com.example.customer.model.entity.CustomerSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    ICustomerDao customerDao;

    //getCustomer
    public Customer getCustomer(String mobileNo){

        System.out.println("MOBILE NO "+mobileNo);
        Customer customer = customerDao.getCustomer(mobileNo);
        System.out.println(customer == null);
        if(customer == null)
            throw new NoCustomerExistException("No such customer exist with mobile no "+mobileNo);

        return customer;
    }


    //registerCustomer
    public Customer registerCustomer(Customer customer){

        // check already present
        Customer customerExisted = customerDao.getCustomer(customer.getMobile_no());

        //if present, update
        if(customerExisted != null)
            return customerDao.updateCustomer(customerExisted);

        return customerDao.addCustomer(customer);
    }

    //unregisterCustomer
    public Customer unRegisterCustomer(String mobileNo){

        Customer customer = customerDao.deleteCustomer(mobileNo);

        if(customer == null)
            throw new NoCustomerExistException("No such customer exist with mobile no "+mobileNo);

        return customer;
    }
}

