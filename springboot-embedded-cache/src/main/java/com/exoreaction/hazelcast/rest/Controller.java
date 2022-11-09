package com.exoreaction.hazelcast.rest;

import com.exoreaction.hazelcast.CacheClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/beers")
public class Controller {

  private final CacheClient cacheClient;

  public Controller(CacheClient cacheClient) {
    this.cacheClient = cacheClient;
  }

  @PostMapping(path = "/{number}", produces= MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(code = HttpStatus.CREATED)
  public Beer put(@RequestBody Beer beer, @PathVariable String number) {
    return cacheClient.put(number, beer);
  }

  @GetMapping(value = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Beer get(@PathVariable String number) {
    return cacheClient.get(number);
  }

}
