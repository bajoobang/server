package bajoobang.bajoobang_spring.pay.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PayRequest {
    private String url;
    private Map<String, String> map;
}