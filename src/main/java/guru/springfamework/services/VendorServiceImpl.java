package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDto;
import guru.springfamework.controllers.v1.VendorController;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDto)
                .map(vendorDTO -> {
                    vendorDTO.setVendor_url(getVendorUrl(id));
                    return vendorDTO;
                }).orElse(null);
    }

    @Override
    public VendorListDto getAllVendors() {
         List<VendorDTO> vendorDTOS = vendorRepository.findAll()
                 .stream()
                 .map(vendor -> {
                     VendorDTO vendorDTO = vendorMapper.vendorToVendorDto(vendor);
                     vendorDTO.setVendor_url(getVendorUrl(vendor.getId()));
                     return vendorDTO;
                 })
                 .collect(Collectors.toList());
         return new VendorListDto(vendorDTOS);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendorToSave = vendorMapper.vendorDtoToVendor(vendorDTO);
        vendorToSave.setId(id);

        return saveAndReturnDTO(vendorToSave);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    return saveAndReturnDTO(vendor);
                }).orElse(null);
    }

    @Override
    public void deleteVendorById(Long id) { vendorRepository.deleteById(id); }

    private String getVendorUrl(Long id) { return VendorController.BASE_URL + "/" + id; }
    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);
        VendorDTO returnDto = vendorMapper.vendorToVendorDto(savedVendor);
        returnDto.setVendor_url(getVendorUrl(savedVendor.getId()));
        return returnDto;
    }
}
