package com.fss.emailsender.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@RooIntegrationTest(entity = Customer.class)
public class CustomerIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private CustomerDataOnDemand dod;

	@Test
    public void testCountCustomers() {
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", dod.getRandomCustomer());
        long count = Customer.countCustomers();
        Assert.assertTrue("Counter for 'Customer' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindCustomer() {
        Customer obj = dod.getRandomCustomer();
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Customer' failed to provide an identifier", id);
        obj = Customer.findCustomer(id);
        Assert.assertNotNull("Find method for 'Customer' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Customer' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllCustomers() {
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", dod.getRandomCustomer());
        long count = Customer.countCustomers();
        Assert.assertTrue("Too expensive to perform a find all test for 'Customer', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Customer> result = Customer.findAllCustomers();
        Assert.assertNotNull("Find all method for 'Customer' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Customer' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindCustomerEntries() {
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", dod.getRandomCustomer());
        long count = Customer.countCustomers();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Customer> result = Customer.findCustomerEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Customer' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Customer' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Customer obj = dod.getRandomCustomer();
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Customer' failed to provide an identifier", id);
        obj = Customer.findCustomer(id);
        Assert.assertNotNull("Find method for 'Customer' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyCustomer(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Customer' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        Customer obj = dod.getRandomCustomer();
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Customer' failed to provide an identifier", id);
        obj = Customer.findCustomer(id);
        boolean modified =  dod.modifyCustomer(obj);
        Integer currentVersion = obj.getVersion();
        Customer merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Customer' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", dod.getRandomCustomer());
        Customer obj = dod.getNewTransientCustomer(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Customer' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Customer' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Customer' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        Customer obj = dod.getRandomCustomer();
        Assert.assertNotNull("Data on demand for 'Customer' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Customer' failed to provide an identifier", id);
        obj = Customer.findCustomer(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Customer' with identifier '" + id + "'", Customer.findCustomer(id));
    }
}
