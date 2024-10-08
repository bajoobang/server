package bajoobang.bajoobang_spring.service;

import bajoobang.bajoobang_spring.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import bajoobang.bajoobang_spring.domain.Request;
import bajoobang.bajoobang_spring.repository.MemberRepository;
import bajoobang.bajoobang_spring.repository.RequestRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisteredService {
    private final RequestRepository requestRepository;
    private final MemberRepository memberRepository;

    public List<Map<String, Object>> getRegistered(Member member) {
        List<Map<String, Object>> registeredList = new ArrayList<>();
        List<Request> requests = requestRepository.findByMember(member);
        log.info("requests={}", requests);
        for (Request request : requests) {
            Map<String, Object> registered = new HashMap<>();
            registered.put("address", request.getHouse().getContent());
            registered.put("price", request.getPriceRequest());
            // 매칭 상태값 가져오기
            registered.put("state", request.getStatus());
            log.info("STATE: " + request.getStatus());
            registered.put("date", request.getRequestDate());
            registered.put("request_id", request.getRequestId());
            registeredList.add(registered);
        }
        log.info("registeredList={}", registeredList);
        return registeredList;
    }

    // 등록매물 개수 전달하기
    public int getNumOfRegistered(Member member) {
//        List<Request> requests = member.getRequests();
        List<Request> byMember = requestRepository.findByMember(member);
        return byMember.size();
    }

    // 등록매물에서 매칭 정보 확인하기
    public Map<String, Object> getMatchingInfo(Member member, Long requestId) {
        Map<String, Object> matchingInfo = new HashMap<>();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requestId: " + requestId));
        matchingInfo.put("requester", member.getName());
        matchingInfo.put("worker", request.getBalpoomin().getName());
        matchingInfo.put("worker_id", request.getBalpoomin().getId());
        matchingInfo.put("price", request.getPriceRequest());
        matchingInfo.put("request_id", request.getRequestId());
        matchingInfo.put("date", request.getRequestDate());
        return matchingInfo;
    }

//    public MatchingDTO getMatchingDTO(Member member, Long requestId) {
//        Request request = requestRepository.findById(requestId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid requestId: " + requestId));
//        return new MatchingDTO(
//                member.getName(),
//                request.getBalpoomin().getName(), // 가정: Balpoomin 엔티티에 getName() 메소드가 있다고 가정
//                request.getBalpoomin().getId(),
//                request.getPriceRequest(),
//                request.getRequestId(),
//                request.getRequestDate()
//        );
}
