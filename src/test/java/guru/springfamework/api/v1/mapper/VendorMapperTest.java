package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VendorMapperTest {

    public static final String VENDOR_NAME = "Joe Crab Shack";
    public static final String VENDOR_URL = "shop/vendors/32";
    public static final long ID = 1L;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Test
    void vendorToVendorDto() {
        // Given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(VENDOR_NAME);
        vendor.setVendor_url(VENDOR_URL);

        // When
        VendorDTO actualDto = vendorMapper.vendorToVendorDto(vendor);

        // Then
        assertNotNull(actualDto);
        assertEquals(VENDOR_NAME, actualDto.getName());
        assertEquals(VENDOR_URL, actualDto.getVendor_url());
    }
}