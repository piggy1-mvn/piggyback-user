package com.incentives.piggyback.user.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FbRequest implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private  String email;
    private  String fb_user_id;
}
