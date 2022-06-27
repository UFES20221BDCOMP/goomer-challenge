package br.com.ufes.bd1.goomer.dto;

import br.com.ufes.bd1.goomer.model.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class AddressDto {

    @NotBlank(message = "address line must be provided")
    @JsonProperty("address_line")
    private String addressLine;

    @NotBlank(message = "neighborhood must be provided")
    @JsonProperty("neighborhood")
    private String neighborhood;

    @NotBlank(message = "city must be provided")
    @JsonProperty("city")
    private String city;

    @NotBlank(message = "state must be provided")
    @JsonProperty("state")
    private String state;

    @NotBlank(message = "zip code must be provided")
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
