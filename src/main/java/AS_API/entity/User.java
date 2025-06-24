package AS_API.entity;

import AS_API.dto.UserRegisterDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String status;


    @Column(nullable = false)
    private String nickName;

    public static User createUser(UserRegisterDto userRegisterDto, PasswordEncoder passwordEncoder){
        String password = passwordEncoder.encode(userRegisterDto.getPassword());

        User user = User.builder()
                .uid(userRegisterDto.getUid())
                .name(userRegisterDto.getName())
                .password(password)
                .phoneNumber(userRegisterDto.getPhoneNumber())
                .email(userRegisterDto.getEmail())
                .nickName(userRegisterDto.getNickName())
                .role(Role.USER)
                .status("ACTIVE")
                .build();

        return user;
    }
    public void suspend() {
    this.status = "SUSPENDED";
    }

    public void resume() {
    this.status = "ACTIVE";
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNickName(String newNickName) {
        this.nickName = newNickName;
    }
}
