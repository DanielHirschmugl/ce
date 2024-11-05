package jku.ce.auth;


import jku.ce.config.JwtService;
import jku.ce.exceptions.UserAlreadyExistsException;
import jku.ce.exceptions.UserNotFoundException;
import jku.ce.exceptions.WrongInputException;
import jku.ce.user.Role;
import jku.ce.user.User;
import jku.ce.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UserAlreadyExistsException, WrongInputException {
        Role role;
        if(request.getRole() == 'A') role = Role.ADMIN;
        else if(request.getRole() =='U') role = Role.USER;
        else throw new WrongInputException("Nur A (Admin) oder U (User) möglich");

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();//Builder und build: builder am Anfang, build für den Abschluss, kommt von @Build
        if(repository.findByUsername(user.getUsername()).isPresent()) throw new UserAlreadyExistsException("Sie haben bereits einen Account.");
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()

                )
        );
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
