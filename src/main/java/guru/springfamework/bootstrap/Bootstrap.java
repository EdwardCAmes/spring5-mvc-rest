package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCustomers();;

        loadCategories();

        loadVendors();
    }

    private void loadVendors() {
        Vendor vendor1 = new Vendor();
        vendor1.setName("Western Tasty Fruits, Ltd.");
        vendor1.setVendor_url("shop/vendors/674");
        vendorRepository.save(vendor1);

        Vendor vendor2 = new Vendor();
        vendor2.setName("Exotic Fruits Company");
        vendor2.setVendor_url("shop/vendors/32");
        vendorRepository.save(vendor2);

        Vendor vendor3 = new Vendor();
        vendor3.setName("Home Fruits");
        vendor3.setVendor_url("shop/vendors/502");
        vendorRepository.save(vendor3);

        Vendor vendor4 = new Vendor();
        vendor4.setName("Fun Fresh Fruits, Ltd.");
        vendor4.setVendor_url("shop/vendors/810");
        vendorRepository.save(vendor4);
    }

    private void loadCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");

        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        System.out.println("Data loaded = " + categoryRepository.count());
    }

    private void loadCustomers() {
        Customer customer1 = new Customer();
        customer1.setFirstName("Michael");
        customer1.setLastName("Weston");
        customer1.setId(1L);
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setFirstName("Sam");
        customer2.setLastName("Axe");
        customer2.setId(2L);
        customerRepository.save(customer2);

        System.out.println("Customers loaded: " + customerRepository.count());
    }
}
