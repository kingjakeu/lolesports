package com.kingjakeu.lolesports.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceKey){
        log.error(resourceKey + " not found");
    }
}
