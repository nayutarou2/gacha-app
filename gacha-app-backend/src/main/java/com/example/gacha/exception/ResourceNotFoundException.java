package com.example.gacha.exception;


public class ResourceNotFoundException extends RuntimeException {
  
  // 404
  public ResourceNotFoundException(String resourceName,Long id){
    super(String.format("%s (ID:%d)が見つかりませんでした。",resourceName, id));
  }

}
