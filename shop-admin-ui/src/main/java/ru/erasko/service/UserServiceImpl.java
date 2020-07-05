package ru.erasko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erasko.controller.repr.UserRepr;
import ru.erasko.model.User;
import ru.erasko.repo.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = encoder;
    }

    @Override
    public List<UserRepr> findAll() {
        return userRepository.findAll().stream()
                .map(UserRepr::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserRepr> findById(Long id) {
        return userRepository.findById(id).map(UserRepr::new);
    }

    @Override
    public void save(UserRepr userRepr) {
        User user = new User();
        user.setId(userRepr.getId());
        user.setName(userRepr.getName());
        user.setAge(userRepr.getAge());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        user.setFirstName(userRepr.getFirstName());
        user.setLastName(userRepr.getLastName());
        user.setEmail(userRepr.getEmail());
        user.setRoles(userRepr.getRoles());
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
