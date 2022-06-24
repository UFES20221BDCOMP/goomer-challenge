package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDto {

    @JsonProperty("address_line")
    private String addressLine;

    @JsonProperty("neighborhood")
    private String neighborhood;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("zip_code")
    private String zipCode;


    public Address toEntity() {
        Address address = new Address();
        address.setAddressLine(addressLine);
        address.setNeighborhood(neighborhood);
        address.setCity(city);
        address.setState(state);
        address.setZipCode(zipCode);
        return address;
    }

    public static AddressDto fromEntity(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.addressLine = address.getAddressLine();
        addressDto.neighborhood = address.getNeighborhood();
        addressDto.city = address.getCity();
        addressDto.state = address.getState();
        addressDto.zipCode = address.getZipCode();
        return addressDto;
    }
}
