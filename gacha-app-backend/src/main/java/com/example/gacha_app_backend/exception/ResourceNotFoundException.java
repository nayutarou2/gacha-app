package com.example.gacha_app_backend.exception;


public class ResourceNotFoundException extends RuntimeException {
  
  // 後々messages.propertiesに移行 
  public ResourceNotFoundException(String resourceName,Long id){
    super(String.format("%s (ID:%d)が見つかりませんでした。",resourceName, id));
  }

}
