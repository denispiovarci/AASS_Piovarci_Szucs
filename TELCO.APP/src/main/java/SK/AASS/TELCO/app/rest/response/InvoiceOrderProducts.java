package SK.AASS.TELCO.app.rest.response;

import SK.AASS.TELCO.app.config.ProductType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceOrderProducts {

        private String productName;
        private ProductType productType;
        private int amount;
        private float price;
}
