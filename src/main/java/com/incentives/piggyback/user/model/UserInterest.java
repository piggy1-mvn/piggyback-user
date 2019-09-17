package com.incentives.piggyback.user.model;

import java.util.ArrayList;

import javax.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInterest extends Users {

    @Column(name="id")
    private Long id;

    @Column(name="user_interests")
    private ArrayList<String> user_interests;
}
