package com.ecommerce.fullcart.service.user;

import com.ecommerce.fullcart.dto.AddressDto;
import com.ecommerce.fullcart.entity.user.Address;
import com.ecommerce.fullcart.entity.user.District;
import com.ecommerce.fullcart.entity.user.Governorate;
import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.repository.user.AddressRepository;
import com.ecommerce.fullcart.repository.user.DistrictRepository;
import com.ecommerce.fullcart.repository.user.GovernorateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private AddressRepository addressRepository;
    private GovernorateRepository governorateRepository;
    private DistrictRepository districtRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository,
                          GovernorateRepository governorateRepository,
                          DistrictRepository districtRepository) {
        this.addressRepository = addressRepository;
        this.governorateRepository = governorateRepository;
        this.districtRepository = districtRepository;
    }

    public List<Governorate> findAllGovernorates() {
        return governorateRepository.findAll();
    }

    public List<District> findAllDistricts() {
        return districtRepository.findAll();
    }

    public List<District> findDistrictsByGovernorate(int id) {
        return districtRepository.findDistrictsByGovernorate_Id(id);
    }

    public Address save(AddressDto addressDto, User user) {
        District district = districtRepository.findById(addressDto.getDistrictId());
        Address address = new Address();
        address.setUser(user);
        address.setDistrict(district);
        address.setStreet(addressDto.getStreet());
        address.setBuilding(addressDto.getBuilding());
        address.setFloor(addressDto.getFloor());
        address.setApt(addressDto.getApt());
        address.setOther(addressDto.getOther());
        return addressRepository.save(address);
    }
}
