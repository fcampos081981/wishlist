package com.cleanarch.wishlist.infrastructure.persistence.postgresql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "wishlist")
@Access(AccessType.FIELD)
@Getter
@Setter
public class WishlistEntityPostgresql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id", nullable = false, unique = true)
    private String customerId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "wishlist_product", joinColumns = @JoinColumn(name = "wishlist_id"))
    @Column(name = "product_id")
    private Set<String> productIds;

}
