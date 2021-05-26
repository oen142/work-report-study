package biz.dreamaker.workreport.report.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    private String href;

    private Picture(Long id, String href) {
        this.id = id;
        this.href = href;
    }

    public static Picture ofNew(String href) {
        return new Picture(null, href);
    }

}

