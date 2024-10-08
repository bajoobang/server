package bajoobang.bajoobang_spring.domain;

import jakarta.persistence.*;
import lombok.*;
import bajoobang.bajoobang_spring.dto.SignupForm;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String pw;

    private String address;
    private double latitude;
    private double longitude;
    private String bankAccount;

    @Column(columnDefinition = "float default 0")
    private float star; // 별점
    @Column(columnDefinition = "integer default 0")
    private int starCount; // 별점 받은 횟수

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Alarm> alarms = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<BaDream> baDreams = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Likey> likeys = new ArrayList<>();

    public void setRequest(Request request) {
        this.getRequests().add(request);
    }

    public void setBaDream(BaDream baDream) {
        this.getBaDreams().add(baDream);
    }

    // 별점 갱신 함수
    public void updateStar(float newStar) {
        this.star = (this.star * this.starCount + newStar) / (this.starCount + 1);
        this.starCount++;
    }

    // dto를 엔티티로 변환
    public static Member toEntity(SignupForm signupForm) {
        return Member.builder()
                .name(signupForm.getName())
                .email(signupForm.getEmail())
                .pw(signupForm.getPw())
                .address(signupForm.getAddress())
                .latitude(signupForm.getLatitude())
                .longitude(signupForm.getLongitude())
                .bankAccount(signupForm.getAccount())
                .build();
    }

}
