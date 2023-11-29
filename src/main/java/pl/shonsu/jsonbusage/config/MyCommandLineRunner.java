package pl.shonsu.jsonbusage.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.shonsu.jsonbusage.model.*;
import pl.shonsu.jsonbusage.repository.UserRepository;

import java.util.List;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    public MyCommandLineRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        userRepository.flush();
        User user = new User(null, "Shonsu",
                new Authorities(List.of(Role.ROLE_USER, Role.ROLE_ADMIN)),
                new Address("Szczebrzeszyn", "Klonowa", 3),
                List.of(UserType.TYPE3, UserType.TYPE2));
        userRepository.saveAndFlush(user);
        User result = userRepository.findById(user.getId()).orElseThrow();
        System.out.println(result.getUserTypes());
    }
}
