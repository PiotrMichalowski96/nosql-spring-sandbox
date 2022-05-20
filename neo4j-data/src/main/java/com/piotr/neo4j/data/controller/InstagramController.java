package com.piotr.neo4j.data.controller;

import com.piotr.neo4j.data.domain.User;
import com.piotr.neo4j.data.service.InstagramService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/instagram/users")
@RequiredArgsConstructor
public class InstagramController {

  private final InstagramService instagramService;

  @GetMapping("/{id}")
  public User findUserById(@PathVariable Long id) {
    return instagramService.getUserById(id);
  }

  @GetMapping
  public Collection<User> findUsersFollowing(@RequestParam String username) {
    return instagramService.getFollowingOfFollowingsUsers(username);
  }

  @GetMapping("/path")
  public Long findShortestPathBetweenUsers(@RequestParam String startUsername, @RequestParam String endUsername) {
    return instagramService.findShortestPathBetweenUsers(startUsername, endUsername);
  }
}
