package bajoobang.bajoobang_spring.service;

import bajoobang.bajoobang_spring.domain.BaDream;
import bajoobang.bajoobang_spring.domain.Member;
import bajoobang.bajoobang_spring.domain.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import bajoobang.bajoobang_spring.domain.House;
import bajoobang.bajoobang_spring.repository.BaDreamRepository;
import bajoobang.bajoobang_spring.repository.RequestRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FootworkService {
    private final BaDreamRepository baDreamRepository;
    private final RequestRepository requestRepository;
    // 신청 발품
    public List<Map<String, Object>> getFootworks(Member member) {
        List<BaDream> baDreams = baDreamRepository.findByMember(member);
        List<Map<String, Object>> footworks = new ArrayList<>();
        for (BaDream baDream : baDreams) {
            Map<String, Object> footwork = new HashMap<>();
            Request request = baDream.getRequest();
            House house = request.getHouse();
            footwork.put("request_id", request.getRequestId());
            footwork.put("address", house.getContent());
            footwork.put("price", request.getPriceRequest());
            // 매칭 상태값 전달하기
//            if (request.getBalpoomin() == null) footwork.put("state", "요청 중");
//            else footwork.put("state", "매칭 완료");
            // 매칭 전
            if (request.getStatus().equals("매칭 전")) {
                footwork.put("state", "요청 중");
                footwork.put("worker_id", "");
            }
            // 매칭 후
            else {
                // 매칭 성공 (발품인 == member)
                if (request.getBalpoomin().getId().equals(member.getId())) {
                    // 작성 완료 혹은 매칭 완료 혹은 구매 확정 혹은 환불 중 혹은 환불 완료
                    footwork.put("state", request.getStatus());
                    footwork.put("worker_id", request.getBalpoomin().getId());
                }
                else {
                    // 매칭 실패 (발품인 != member)
                    footwork.put("state", "매칭 실패");
                    footwork.put("worker_id", "");
                }
            }
            footwork.put("date", request.getRequestDate());
            footworks.add(footwork);
        }
        return footworks;
    }

    public int getNumOfFootworks(Member member) {
//        List<BaDream> baDreams = member.getBaDreams();
        List<BaDream> byMember = baDreamRepository.findByMember(member);

        return byMember.size();
    }

    public Map<String, Object> getMatchingInfo(Member member, Long requestId) {
        Map<String, Object> matchingInfo = new HashMap<>();
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid requestId: " + requestId));
        matchingInfo.put("requester", request.getBalpoomin().getName());
        matchingInfo.put("worker", member.getName());
        matchingInfo.put("worker_id", member.getId());
        matchingInfo.put("price", request.getPriceRequest());
        matchingInfo.put("request_id", request.getRequestId());
        matchingInfo.put("date", request.getRequestDate());
        return matchingInfo;
    }
}
