package com.example.ex03.board.entity;

import com.example.ex03.common.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Member extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;
}
