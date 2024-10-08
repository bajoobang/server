package bajoobang.bajoobang_spring.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BaDream {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BaDreamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 발품인 id임
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private Request request;

    // ex) 발품 신청합니다!
    private String message;
}
