package SK.AASS.TELCO.app.rest.response;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInvoiceResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String tel;


    private String street;
    private Integer residenceNumber;
    private String city;
    private String postalCode;

    private Long orderId;
    private List<InvoiceOrderProducts> orderProducts = new ArrayList<>();
    private double sum;

}
