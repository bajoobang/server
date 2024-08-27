package bajoobang.bajoobang_spring.controller;

import bajoobang.bajoobang_spring.domain.Member;
import bajoobang.bajoobang_spring.domain.Request;
import bajoobang.bajoobang_spring.dto.RequestDTO;
import bajoobang.bajoobang_spring.dto.RequestIdForm;
import bajoobang.bajoobang_spring.pay.PayInfoDto;
import bajoobang.bajoobang_spring.pay.response.BaseResponse;
import bajoobang.bajoobang_spring.pay.response.PayReadyResDto;
import bajoobang.bajoobang_spring.repository.HouseRepository;
import bajoobang.bajoobang_spring.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import bajoobang.bajoobang_spring.domain.House;
import bajoobang.bajoobang_spring.dto.BalpoomForm;
import bajoobang.bajoobang_spring.repository.MemberRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final HouseRepository houseRepository;
    private final RequestService requestService;
    private final HouseService houseService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final AlarmService alarmService;
    private final KakaoPayService kakaoPayService;


    // 요청서 작성 완료 시.
    @PostMapping("/request-form")
    public ResponseEntity<?> requestForm(@RequestPart("jsonData") RequestDTO requestDTO,
                              @RequestPart("date") String date,
                              @RequestPart("price") int price,
                              HttpServletRequest request,
                              @RequestParam Long house_id,
                              @RequestPart("address") String address,
                              HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        log.info("---------------");
        log.info("house id: " + house_id);
        if (session != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            // 세션에서 멤버를 꺼내오기
            Member member = (Member) session.getAttribute("loginMember");
            // 퀴리 파라미터로 매물 가져오기
            House house = houseRepository.findByHouseId(house_id);
            // 새로운 요청서 저장
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsingDate = LocalDate.parse(date, formatter);
            // + 상태값 저장해주는 거 해줘야함.
            requestDTO.setDate(parsingDate); // 급한대로 그냥 이렇게 처리ㅋ
            requestDTO.setPrice(price);
            Request newRequest = requestService.saveRequest(requestDTO, member, house, address);

            // 주어진 house의 위도와 경도로부터 가까운 회원 20명 검색
            List<Member> nearbyMembers = memberRepository.findTop20MembersByDistance(house.getLatitude(), house.getLongitude());
            nearbyMembers.forEach(m -> log.info("Member Address: " + m.getAddress()));
//  여기다
            List<Member> alarmMembers = memberService.findMembersByTravelTime(member.getId(), nearbyMembers, house.getLatitude(), house.getLongitude());

            for(Member mem : alarmMembers){
                alarmService.saveMemberRequest(mem, newRequest);
            }

            // 결제 시작
            try {
                PayInfoDto payInfoDto = new PayInfoDto();
                payInfoDto.setRequest_id(newRequest.getRequestId());
                log.info("{} = request_id", newRequest.getRequestId());
                payInfoDto.setPrice(price);
                PayReadyResDto payReadyResDto = kakaoPayService.getRedirectUrl(payInfoDto, member);
                String kakaoUrl = payReadyResDto.getNext_redirect_pc_url();
                return ResponseEntity.ok(kakaoUrl);
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
            }
        }
        else return ResponseEntity.status(401).body("Unauthorized");
    }

    @GetMapping("/request-form")
    @ResponseBody
    public ArrayList<Object> requestForm(@RequestParam Long house_id){
        ArrayList<Object> address = houseService.getAddress(house_id);
        return address;
    }

    // 발품지도에 뜬 각각의 매물에 대한 요청 리스트
    @GetMapping("/requests")
    @ResponseBody
    public List<RequestDTO> GetRequests(@RequestParam Long house_id){
        List<RequestDTO> requestDTOList = houseService.getRequests(house_id);
        return requestDTOList;
    }

    // 요청/발품서 form get
    @GetMapping("/get-form")
    public BalpoomForm getForm(@RequestParam Long request_id){
        BalpoomForm balpoomForm = requestService.getRequestInfo(request_id);
        return balpoomForm;
    }

    // 구매 확정
    @PatchMapping ("/confirm")
    public ResponseEntity<?> confirmRequest(@RequestBody RequestIdForm requestIdForm, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member member = (Member) session.getAttribute("loginMember");
            // 구매 확정
            try {
                requestService.confirm(member,requestIdForm.getRequest_id());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("GOOD");
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
            }
        }
        else {
            // 로그인 안 한 사용자
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    // 구매 취소
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdrawRequest(@RequestBody RequestIdForm requestIdForm, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member member = (Member) session.getAttribute("loginMember");
            // 구매 취소
            try {
                requestService.withdraw(member,requestIdForm.getRequest_id());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("GOOD");
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
            }
        }
        else {
            // 로그인 안 한 사용자
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    // 요청서 환불 신청
    @PostMapping ("/refund")
    public ResponseEntity<?> refundRequest(@RequestParam Long request_id,
                                           HttpServletRequest request,
                                           @RequestParam String reasonForRefund) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Member member = (Member) session.getAttribute("loginMember");
            // 환불 신청
            try {
                requestService.refund(member, request_id, reasonForRefund);
                return ResponseEntity.status(HttpStatus.OK)
                        .body("GOOD");
            }
            catch(Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new BaseResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
            }
        }
        else {
            // 로그인 안 한 사용자
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

}
