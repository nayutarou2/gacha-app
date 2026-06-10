package com.example.gacha.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.gacha.entity.User;

public class CustomUserDetail implements UserDetails {

  private final User user;

  public CustomUserDetail(User user) {
    this.user = user;
  }

  public Long getId(){
    return user.getId();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList();
  }

  @Override
  public String getPassword() {
    return user.getPasswordHash();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }


}
