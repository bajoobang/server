package bajoobang.bajoobang_spring.service;

import bajoobang.bajoobang_spring.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import bajoobang.bajoobang_spring.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 이면 로그인 실패
     */
    public Member login(String email, String pw) {
        return memberRepository.findByEmail(email)
                .filter(m -> m.getPw().equals(pw))
                .orElse(null);
    }
}
