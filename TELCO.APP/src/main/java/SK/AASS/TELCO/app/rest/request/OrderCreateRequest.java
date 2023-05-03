package SK.AASS.TELCO.app.rest.request;

import SK.AASS.TELCO.app.model.OrderProduct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    @NotBlank
    private String userEmail;

    @NotEmpty
    private List<OrderProduct> orderProducts;

}
