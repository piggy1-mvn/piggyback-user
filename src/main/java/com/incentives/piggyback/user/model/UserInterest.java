package com.incentives.piggyback.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class UserInterest extends Users {

    @Column(name="id")
    private Long id;

    @Column(name="user_interests")
    private ArrayList<String> user_interests;
}
