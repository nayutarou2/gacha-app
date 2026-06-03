package com.example.gacha_app_backend.custom;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.gacha_app_backend.entity.User;
import com.example.gacha_app_backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
   
    User user = userRepository.selectByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("ユーザが見つかりません" + email);
    }

    return new CustomUserDetail(user);

  }

}