package study.datajpa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import study.datajpa.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String userName;
    private String TeamName;

    private MemberDto(Long id, String userName, String teamName) {
        this.id = id;
        this.userName = userName;
        TeamName = teamName;
    }

    public static MemberDto from(Member member) {
        return new MemberDto(
                member.getId(),
                member.getUsername(),
                null
        );
    }
}
