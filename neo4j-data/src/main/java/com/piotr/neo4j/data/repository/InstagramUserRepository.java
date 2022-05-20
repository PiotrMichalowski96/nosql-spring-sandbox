package com.piotr.neo4j.data.repository;

import com.piotr.neo4j.data.domain.User;
import java.util.Collection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface InstagramUserRepository extends Neo4jRepository<User, Long> {

  User findByUserId(Long userId);

  @Query("MATCH (u:User)<-[:IsFollowing *..2]->(theUser:User {username: $username}) RETURN u")
  Collection<User> findFollowersOfFollowersByUsername(@Param("username") String username);

  @Query("MATCH (startNode:User {username: $startUsername}), (endNode:User {username: $endUsername}) "
      + "MATCH p = shortestPath((startNode)-[:IsFollowing *]->(endNode)) "
      + "RETURN length(p)")
  Long findShortestFollowingPathBetweenUsers(@Param("startUsername") String startUsername,
      @Param("endUsername") String endUsername);
}
