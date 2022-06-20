package com.piotr.neo4j.data.domain;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

@Data
@EqualsAndHashCode(exclude = {"followingUsers"})
@Node
public class User {
  @Id
  @GeneratedValue
  private Long userId;
  private String username;
  private String firstname;
  private String lastname;
  @Relationship(type = "IsFollowing", direction = Direction.OUTGOING)
  private Set<User> followingUsers;
}