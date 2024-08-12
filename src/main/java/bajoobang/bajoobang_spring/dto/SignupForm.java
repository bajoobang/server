package bajoobang.bajoobang_spring.dto;

import lombok.Data;

@Data
public class SignupForm {
    private String name;
    private String email;
    private String pw;
    private String address;
    private double latitude;
    private double longitude;
}
