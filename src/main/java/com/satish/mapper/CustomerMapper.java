package com.satish.mapper;

import com.satish.dto.AddressDto;
import com.satish.dto.CustomerDto;
import com.satish.entity.Address;
import com.satish.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDto, Customer>{

    @Mapping(source = "addresses", target = "addresses", qualifiedByName = "addressToAddressDto")
    CustomerDto toDto(Customer entity);

    @Named("addressToAddressDto")
    @Mapping(target = "id", source = "id")
    default AddressDto addressToAddressDto(Address address){
        if(address == null){
            return null;
        }
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setCity(address.getCity());
        dto.setZipCode(address.getZipCode());
        return dto;
    }
}
