package vn.com.dpm.main.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.com.dpm.main.api.model.entity.User;
import vn.com.dpm.main.api.model.request.SignInRequest;
import vn.com.dpm.main.api.model.response.SignInResponse;
import vn.com.dpm.main.api.model.response.UserInfo;
import vn.com.dpm.main.api.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Sign in the system
     * @param request
     * @return
     */
    public SignInResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var accessToken = jwtService.generateToken(user);
        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken("refresh_token")
                .build();
    }

    public UserInfo register(SignInRequest signInRequest) {
        var user = userRepository.findByUsername(signInRequest.getUsername());
        if(user.isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }
        var entity = User.builder()
                .username(signInRequest.getUsername())
                .password(passwordEncoder.encode(signInRequest.getPassword()))
                .phone(signInRequest.getPhone())
                .email(signInRequest.getEmail())
                .role("ADMIN")
                .build();
        userRepository.save(entity);
        return null;
    }
}
