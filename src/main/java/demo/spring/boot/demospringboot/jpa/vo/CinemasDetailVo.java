package demo.spring.boot.demospringboot.jpa.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "t_cinemas_details")
@Data
@ToString
public class CinemasDetailVo {
    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    private Integer id;//
    private Integer cinemasId;//电影院id
    private Integer movieId;//电影id
    @Lob
    private String content;//电影内容
}
