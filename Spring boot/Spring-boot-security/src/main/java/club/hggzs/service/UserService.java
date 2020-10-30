package club.hggzs.service;

import club.hggzs.entity.Users;
import club.hggzs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Users> findAll(){
        return userRepository.findAll();
    }
    public Users findByUsername(String username){
        return userRepository.findById(username).orElse(null);
    }
    public boolean insert(Users user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
