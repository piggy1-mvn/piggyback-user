package com.incentives.piggyback.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class UserInterest extends Users {

    @Column(name="id")
    private Long id;

    @Column(name="user_interests")
    private String user_interests;
}
