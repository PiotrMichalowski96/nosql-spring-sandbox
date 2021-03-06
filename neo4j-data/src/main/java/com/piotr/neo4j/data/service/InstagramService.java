package com.piotr.neo4j.data.service;

import com.piotr.neo4j.data.domain.User;
import com.piotr.neo4j.data.repository.InstagramUserRepository;
import java.util.Collection;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstagramService {

  private final InstagramUserRepository instagramUserRepository;

  public User getUserById(Long id) {
    return instagramUserRepository.findByUserId(id);
  }

  /**
   * It retrieves all following user by 2nd distance of nodes in graph
   */
  public Collection<User> getFollowingOfFollowingsUsers(String firstName) {
    return instagramUserRepository.findFollowersOfFollowersByUsername(firstName);
  }

  /**
   * It retrieves number of users - number of nodes in the shortest path between two nodes in graph
   */
  public Long findShortestPathBetweenUsers(String startUsername, String endUsername) {
    return instagramUserRepository.findShortestFollowingPathBetweenUsers(startUsername, endUsername);
  }

  public User save(@NonNull User user) {
    return instagramUserRepository.save(user);
  }

  public void delete(Long id) {
    Optional.ofNullable(instagramUserRepository.findByUserId(id))
        .ifPresent(instagramUserRepository::delete);
  }
}
