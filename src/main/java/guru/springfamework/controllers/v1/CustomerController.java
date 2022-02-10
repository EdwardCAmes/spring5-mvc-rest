package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDto;
import guru.springfamework.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDto> getAllCustomers() {
        return new ResponseEntity<CustomerListDto>(
                new CustomerListDto(customerService.getAllCustomers()), HttpStatus.OK );
    }
    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<CustomerDTO> getCustomerByFirstName(@PathVariable String firstName) {
        return new ResponseEntity<CustomerDTO>(
                customerService.getCustomerByFirstName(firstName), HttpStatus.OK );
    }
}
