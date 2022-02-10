package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDto;
import guru.springfamework.domain.Customer;
import guru.springfamework.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest extends AbstractRestControllerTest {
    public static final String FIRST_NAME = "Joe";
    public static final String LAST_NAME = "Blogs";
    public static final long ID = 1L;

    @Mock
    CustomerService customerService;

    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAllCustomers() throws Exception {
        // Given
        List<CustomerDTO> customerDTOS = Arrays.asList(new CustomerDTO(), new CustomerDTO(), new CustomerDTO());
        when(customerService.getAllCustomers()).thenReturn(customerDTOS);

        // When
        // Then
        mockMvc.perform( get("/api/v1/customers").contentType(MediaType.APPLICATION_JSON) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.customers", hasSize(3)));
    }

    @Test
    public void getGetByFirstName() throws Exception {
        // Given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        when( customerService.getCustomerByFirstName( anyString() )).thenReturn(customer);

        // When
        // Then
        mockMvc.perform( get("/api/v1/customers/firstName/Joe").contentType(MediaType.APPLICATION_JSON) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.firstName", equalTo(FIRST_NAME)));
    }

    @Test
    void createNewCustomer() throws Exception {
        // Given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customer.getFirstName());
        returnedDto.setLastName(customer.getLastName());

        when( customerService.createNewCustomer(customer) ).thenReturn(returnedDto);

        // When
        // Then
        mockMvc.perform(
                post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString(customer) ) )
                .andExpect( status().isCreated() )
                .andExpect( jsonPath("$.firstName", equalTo("Fred")));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnedDto = new CustomerDTO();
        returnedDto.setFirstName(customer.getFirstName());
        returnedDto.setLastName(customer.getLastName());

        when( customerService.saveCustomerByDto(anyLong(), any(CustomerDTO.class) ) ).thenReturn(returnedDto);

        // When
        // Then
        mockMvc.perform(
                        put("/api/v1/customers/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content( asJsonString(customer) ) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.firstName", equalTo("Fred")))
                .andExpect( jsonPath("$.lastName", equalTo("Flintstone")));
    }
}