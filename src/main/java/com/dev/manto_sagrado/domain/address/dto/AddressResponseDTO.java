package com.dev.manto_sagrado.domain.address.dto;

import com.dev.manto_sagrado.domain.address.Enum.AddressDefaultStatus;
import com.dev.manto_sagrado.domain.address.Enum.AddressType;
import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.entity.Client;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseDTO {
    private long id;
    private String identification;
    private AddressType type;
    private String streetAddress;
    private int number;
    private String complement;
    private String cep;
    private String neighborhood;
    private String city;
    private String uf;
    private Client client;
    private AddressDefaultStatus defaultAddress;

    public static AddressResponseDTO fromAddress(Address address) {
        return AddressResponseDTO.builder()
                .id(address.getId())
                .identification(address.getIdentification())
                .type(address.getType())
                .streetAddress(address.getStreetAddress())
                .number(address.getNumber())
                .complement(address.getComplement())
                .cep(address.getCep())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .uf(address.getUf())
                .client(address.getClient())
                .defaultAddress(address.getDefaultAddress())
                .build();
    }
}
