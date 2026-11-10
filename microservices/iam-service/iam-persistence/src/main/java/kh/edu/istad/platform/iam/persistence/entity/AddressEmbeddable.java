package kh.edu.istad.platform.iam.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEmbeddable {

    @Column(name = "address_street_address")
    private String streetAddress;

    @Column(name = "address_locality", length = 100)
    private String locality;

    @Column(name = "address_region", length = 100)
    private String region;

    @Column(name = "address_postal_code", length = 20)
    private String postalCode;

    @Column(name = "address_country", length = 2)
    private String country;
}
