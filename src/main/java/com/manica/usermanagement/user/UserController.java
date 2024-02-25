package com.manica.usermanagement.user;

import com.manica.usermanagement.user.dtos.AuthenticationUser;
import com.manica.usermanagement.user.dtos.ValidUser;
import com.manica.usermanagement.user.dtos.ValidateTokenRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/valid")
    public ResponseEntity<ValidUser> authenticate(
            @RequestBody ValidateTokenRequest request
    ) {
        return ResponseEntity.ok(userService.validateToken(request));
    }


    @GetMapping("/me")
    public ResponseEntity<ValidUser> find(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(userService.findMe(request));
    }

}
