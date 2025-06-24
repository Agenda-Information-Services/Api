package AS_API.dto;

import AS_API.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long userId;
    private String name;
    private String uid;
    private String phoneNumber;
    private String email;
    private String role;
    private String nickName;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.name = user.getName();
        this.uid = user.getUid();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.role = user.getRole().name();
        this.nickName = user.getNickName();
    }

    public static UserResponseDto fromSimple(User user) {
        UserResponseDto dto = new UserResponseDto(user);
        dto.phoneNumber = null;
        dto.email = null;
        return dto;
    }
}
