package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

public class VendorServiceImplTest {
    private static final String NAME_1 = "vendor_1";
    private static final Long ID_1 = 1L;
    private static final String NAME_2 = "vendor_2";
    private static final Long ID_2 = 2L;

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
    }

    @Test
    public void getVendorById() {
        // Given
        Vendor vendor1 = new Vendor();
        vendor1.setName(NAME_1);
        vendor1.setId(ID_1);
        given( vendorRepository.findById( anyLong() ) ).willReturn(Optional.of(vendor1));

        // WHen
        VendorDTO vendorDTO = vendorService.getVendorById(ID_1);

        // Then
        then( vendorRepository ).should( times(1) ).findById( anyLong() );
        assertThat( vendorDTO.getName() , is(equalTo(NAME_1)));
    }
}