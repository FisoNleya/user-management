package com.manica.usermanagement.user.dtos;

import com.manica.usermanagement.user.Role;

public record ValidUser(
         String firstname,
         String lastname,
         String email,
         Role role

) {
}
