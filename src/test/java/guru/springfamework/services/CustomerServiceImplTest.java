package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {
    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "Blogs";
    public static final long ID = 1L;

    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
    }

    @Test
    void getAllCustomers() {
        // Given
        List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        // Then
        assertEquals(3, customerDTOS.size());
    }

    @Test
    void getCustomerByFirstName() {
        // Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        when( customerRepository.findByFirstName( anyString() )).thenReturn(customer);

        // When
        CustomerDTO customerDTO = customerService.getCustomerByFirstName(FIRST_NAME);

        // Then
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    void getCustomerByLastName() {
        // Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        when( customerRepository.findByLastName( anyString() )).thenReturn(customer);

        // When
        CustomerDTO customerDTO = customerService.getCustomerByLastName(LAST_NAME);

        // Then
        assertEquals(ID, customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }

    @Test
    void createNewCustomer() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1L);

        when(customerRepository.save( any(Customer.class) ) ).thenReturn(savedCustomer);

        // When
        CustomerDTO savedDto = customerService.createNewCustomer( customerDTO );

        // Then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
    }
}