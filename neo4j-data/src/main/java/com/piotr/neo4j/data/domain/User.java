package com.piotr.neo4j.data.domain;

import java.util.Set;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@Node
public class User {
  @Id
  @GeneratedValue
  private Long userId;
  private String username;
  private String firstname;
  private String lastname;
  @Relationship(type = "IS_FOLLOWING")
  private Set<User> followingUsers;
}