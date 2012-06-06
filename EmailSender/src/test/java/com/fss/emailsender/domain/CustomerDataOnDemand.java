package com.fss.emailsender.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Component
@Configurable
@RooDataOnDemand(entity = Customer.class)
public class CustomerDataOnDemand {

	private Random rnd = new SecureRandom();

	private List<Customer> data;

	public Customer getNewTransientCustomer(int index) {
        Customer obj = new Customer();
        setName(obj, index);
        return obj;
    }

	public void setName(Customer obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }

	public Customer getSpecificCustomer(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Customer obj = data.get(index);
        Long id = obj.getId();
        return Customer.findCustomer(id);
    }

	public Customer getRandomCustomer() {
        init();
        Customer obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Customer.findCustomer(id);
    }

	public boolean modifyCustomer(Customer obj) {
        return false;
    }

	public void init() {
        int from = 0;
        int to = 10;
        data = Customer.findCustomerEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Customer' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Customer>();
        for (int i = 0; i < 10; i++) {
            Customer obj = getNewTransientCustomer(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
