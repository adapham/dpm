package vn.com.dpm.main.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import vn.com.dpm.main.api.model.request.SignInRequest;
import vn.com.dpm.main.api.model.response.SignInResponse;
import vn.com.dpm.main.api.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    /**
     * Sign in the system
     * @param request
     * @return
     */
    public SignInResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var accessToken = jwtService.generateToken(user);
        return SignInResponse.builder()
                .accessToken(accessToken)
                .refreshToken("refresh_token")
                .build();
    }
}
