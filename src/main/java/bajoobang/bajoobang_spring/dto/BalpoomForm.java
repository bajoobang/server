package bajoobang.bajoobang_spring.dto;

import bajoobang.bajoobang_spring.domain.PlusRequest;
import bajoobang.bajoobang_spring.domain.Request;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BalpoomForm {

    // 발품 기간
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate requestDate;
    // 발품 가격
    private int priceRequest;
    // 추가 질문 사항.
    private List<PlusRequest> plusRequestList;
    // 추가 질문 사항.
    private List<String> plusAnswerList;

    private String address;
    private String status;

    // 수압
    private String powerWater = null;
    private String powerWash = null;
    private String powerShower = null;

    // 온수 시간
    private String timeWater1 = null;
    private String timeWater2 = null;
    private String timeWash1 = null;
    private String timeWash2 = null;
    private String timeShower1 = null;
    private String timeShower2 = null;

    // 채광
    private String lighting = null;

    // 곰팡이
    private String moldLiving = null;
    private String moldRest = null;
    private String moldVeranda = null;
    private String moldShoes = null;
    private String moldWindow = null;

    public static BalpoomForm toDTO(List<PlusRequest> plusRequests, Request requestInfo){
        return BalpoomForm.builder()
                .priceRequest(requestInfo.getPriceRequest())
                .requestDate(requestInfo.getRequestDate())
                .address(requestInfo.getAddress())
                .status(requestInfo.getStatus())
                .plusRequestList(plusRequests)
                .powerShower(requestInfo.getPowerShower())
                .powerWash(requestInfo.getPowerWash())
                .powerWater(requestInfo.getPowerWater())
                .timeShower1(requestInfo.getTimeShower1())
                .timeShower2(requestInfo.getTimeShower2())
                .timeWash1(requestInfo.getTimeWash1())
                .timeWash2(requestInfo.getTimeWash2())
                .timeWater1(requestInfo.getTimeWater1())
                .timeWater2(requestInfo.getTimeWater2())
                .lighting(requestInfo.getLighting())
                .moldLiving(requestInfo.getMoldLiving())
                .moldRest(requestInfo.getMoldRest())
                .moldVeranda(requestInfo.getMoldVeranda())
                .moldShoes(requestInfo.getMoldShoes())
                .moldWindow(requestInfo.getMoldWindow())
                .build();
    }
}
