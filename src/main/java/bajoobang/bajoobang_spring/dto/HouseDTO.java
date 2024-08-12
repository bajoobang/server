package bajoobang.bajoobang_spring.dto;

import bajoobang.bajoobang_spring.domain.LatLng;
import lombok.*;
import bajoobang.bajoobang_spring.domain.House;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HouseDTO {
    private Long house_id;
    private String content;
    private int money1;
    private int money2;
    private int stair;
    private int management;
    private String type;
    private int size;
    private LatLng latLng;

    public static HouseDTO toDTO(House entity){
        LatLng latLng = new LatLng(entity.getLatitude(), entity.getLongitude());

        return HouseDTO.builder()
                .house_id(entity.getHouseId())
                .content(entity.getContent())
                .money1(entity.getMoney1())
                .money2(entity.getMoney2())
                .stair(entity.getStair())
                .management(entity.getManagement())
                .size(entity.getSize())
                .latLng(latLng)
                .type(entity.getType())
                .build();
    }
}
