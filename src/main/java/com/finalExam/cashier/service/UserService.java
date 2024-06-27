package com.finalExam.cashier.service;


import com.finalExam.cashier.model.User;
import com.finalExam.cashier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Long id,User updatedUser) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User existMember = user.get();
            existMember.setUsername(updatedUser.getUsername());
            existMember.setPassword(updatedUser.getPassword());
            existMember.setEmail(updatedUser.getEmail());
            existMember.setPhone(updatedUser.getPhone());
            existMember.setAddress(updatedUser.getAddress());
            existMember.setRole(updatedUser.getRole());
            return userRepository.save(existMember);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
