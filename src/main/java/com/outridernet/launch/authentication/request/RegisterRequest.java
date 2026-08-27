package com.outridernet.launch.authentication.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String phone;
    private String password;
}
