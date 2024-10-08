package bajoobang.bajoobang_spring.controller;

import bajoobang.bajoobang_spring.domain.Member;
import bajoobang.bajoobang_spring.dto.HouseDTO;
import bajoobang.bajoobang_spring.service.LikeyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import bajoobang.bajoobang_spring.service.HouseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HouseController {
    private final HouseService houseService;
    private final LikeyService likeyService;

    @GetMapping("/helpinfo")
    @ResponseBody
    public List<HouseDTO> getHouse(@RequestParam Long local_id){
        List<HouseDTO> houseList = houseService.getHouse(local_id);
        return houseList;
    }

    @GetMapping("/helpinfo/detail")
    @ResponseBody
    public HouseDTO getHouseDetail(@RequestParam Long house_id){
        // 아마도 매물 정보 text 처리하려면 HouseDetailDTO랑 House 테이블 수정 필요할 거로 보임.
        HouseDTO houseDTO = houseService.getHouseDetail(house_id);
        return houseDTO;
    }

    @PostMapping("/like")
    public String setLike(HttpServletRequest request,
                          @RequestParam Long house_id) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션에서 멤버를 꺼내오기
            Member member = (Member) session.getAttribute("loginMember");
            likeyService.setLike(member, house_id);
            return "GOOD";
        }
        else {
            return "FAIL";
        }
    }

    @DeleteMapping("/like")
    public String deleteLike(HttpServletRequest request,
                          @RequestParam Long house_id) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 세션에서 멤버를 꺼내오기
            Member member = (Member) session.getAttribute("loginMember");
            likeyService.deleteLike(member, house_id);
            return "GOOD";
        }
        else {
            return "FAIL";
        }
    }
    
}


