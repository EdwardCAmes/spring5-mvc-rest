package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceImplIT {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VendorRepository vendorRepository;

    CustomerService customerService;

    @Before
    public void setup() throws Exception {
        System.out.println("Loading customer data");
        System.out.println(customerRepository.findAll().size());

        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() {
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo((updatedCustomer.getLastName())));
    }

    @Test
    public void patchCustomerUpdateLastName() {
        String updatedName = "UpdatedName";
        Long id = getCustomerIdValue();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        customerService.patchCustomer(id, customerDTO);

        Customer updatedCustomer = customerRepository.findById(id).get();

        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo((updatedCustomer.getFirstName())));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }
    private Long getCustomerIdValue() {
        List<Customer> customers = customerRepository.findAll();
        System.out.println("Customers found:  " + customers.size());
        // return first id
        return customers.get(0).getId();
    }
}
