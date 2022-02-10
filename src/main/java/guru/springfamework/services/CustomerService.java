package guru.springfamework.services;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    CustomerDTO getCustomerByFirstName(String firstName);
    CustomerDTO getCustomerByLastName(String lastName);
    CustomerDTO createNewCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerByDto(Long id, CustomerDTO customerDTO);
    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
}
