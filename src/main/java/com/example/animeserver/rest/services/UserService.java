package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.Users;
import com.example.animeserver.domain.repositories.UserRepository;
import com.example.animeserver.rest.dto.UserRequest;
import com.example.animeserver.rest.services.exceptions.CustomException;
import com.example.animeserver.rest.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    public void create(UserRequest request){
        verifyEmail(request.getEmail());
        var user = Users.of(request);
        user.setPassword(encoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public Users findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("The user is not found"));
    }

    public Users findByEmail(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("The user is not found"));
    }

    public Users findByNickName(String nickName){
        return userRepository.findByNickName(nickName)
                .orElseThrow(() -> new ObjectNotFoundException("The user is not found"));
    }

    @Transactional
    public void deleteById(Long id){
        try{
            userRepository.delete(findById(id));
        }
        catch(DataIntegrityViolationException e){
            throw new CustomException("The user cannot be deleted");
        }
    }

    public void update(HttpServletRequest request, UserRequest userDto){
        var user = findById(jwtService.getId(request));
        updateData(user,userDto);
        userRepository.save(user);
    }

    private void updateData(Users user, UserRequest userDto) {
        verifyEmail(userDto.getEmail());
        user.setNickName(userDto.getNickName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
    }

    public void verifyEmail(String email){
        userRepository.findByEmail(email).ifPresent(ex ->{
            throw new CustomException("This email already exist");
        });
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ObjectNotFoundException("The user is not found"));
        return user;
    }
}
