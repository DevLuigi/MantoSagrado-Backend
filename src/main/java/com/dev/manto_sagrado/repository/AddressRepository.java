package com.dev.manto_sagrado.repository;

import com.dev.manto_sagrado.domain.address.Enum.AddressDefaultStatus;
import com.dev.manto_sagrado.domain.address.Enum.AddressType;
import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByClient(Client client);
    List<Address> findAllByClientAndType(Client client, AddressType type);
    Address findByClientAndDefaultAddress(Client client, AddressDefaultStatus defaultAddress);
    int deleteAllByClient(Client client);
}