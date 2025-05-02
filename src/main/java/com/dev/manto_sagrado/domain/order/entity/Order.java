package com.dev.manto_sagrado.domain.order.entity;

import com.dev.manto_sagrado.domain.address.entity.Address;
import com.dev.manto_sagrado.domain.client.entity.Client;
import com.dev.manto_sagrado.domain.order.Enum.Payment;
import com.dev.manto_sagrado.domain.order.Enum.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "orders")
@Entity(name = "Order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "O preço total deve ser preenchido")
    private BigDecimal totalPrice;

    @NotNull(message = "O frete deve ser preenchido")
    private BigDecimal shippingCost;

    @NotNull(message = "O endereço deve ser preenchido")
    @ManyToOne @JoinColumn(name = "fk_address", nullable = false)
    private Address address;

    @NotNull(message = "O cliente deve ser preenchido")
    @ManyToOne @JoinColumn(name = "fk_client", nullable = false)
    private Client client;

    @NotNull(message = "O pagamento deve ser escolhido")
    private Payment payment;

    @NotNull(message = "O status deve ser preenchido")
    private Status status;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
