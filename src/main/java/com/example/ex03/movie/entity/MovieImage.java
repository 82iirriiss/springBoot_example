package com.example.ex03.movie.entity;

import com.example.ex03.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
}
