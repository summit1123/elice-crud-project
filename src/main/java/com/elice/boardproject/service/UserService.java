package com.elice.boardproject.service;


import com.elice.boardproject.entity.User;
import com.elice.boardproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(int userId, User updatedUser) {
        User user = getUserById(userId);
        user.setUsername(updatedUser.getUsername());
        // Update other user fields as needed
        return userRepository.save(user);

    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}