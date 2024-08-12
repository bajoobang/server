package bajoobang.bajoobang_spring.dto;

import bajoobang.bajoobang_spring.domain.Request;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestDTO {
    // 요청서 아이디
    private Long request_id;
    // 발품 기간
    private LocalDate date;
    // 발품 가격
    private int price;
    // 추가 요청
    private List<PlusRequestDTO> plus_list;

    // 매물 주소
    private String house_address;
    private int stair;


    public static RequestDTO toDTO(Request entity){

        return RequestDTO.builder()
                .request_id(entity.getRequestId())
                .date(entity.getRequestDate())
                .price(entity.getPriceRequest())
                .plus_list(PlusRequestListDTO.toDTO(entity.getPlusRequests()))
                .build();
    }
}
