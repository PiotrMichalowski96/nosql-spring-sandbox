package com.piotr.neo4j.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.piotr.neo4j.data.domain.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataNeo4jTest
public class InstagramUserRepositoryIntegrationTest {

  @Autowired
  private InstagramUserRepository instagramUserRepository;

  @Container
  private static final Neo4jContainer databaseServer = new Neo4jContainer<>("neo4j:4.4.7-community")
      .withAdminPassword(null);

  @DynamicPropertySource
  static void neo4jProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.neo4j.uri", databaseServer::getBoltUrl);
    registry.add("spring.neo4j.authentication.username", () -> "neo4j");
    registry.add("spring.neo4j.authentication.password", () -> null);
  }

  //TODO: populate DB

  @Test
  void shouldRetrieveInstagramUserById() {
    //given
    User expectedUser = createUser(1L, "hola", "Ola", "Rodriguez");

    //when
    List<User> users = instagramUserRepository.findAll();
    User actualUser = instagramUserRepository.findByUserId(expectedUser.getUserId());

    //then
    assertThat(actualUser).isEqualTo(expectedUser);
  }

  private User createUser(Long id, String username, String firstname, String lastname) {
    User user = new User();
    user.setUserId(id);
    user.setUsername(username);
    user.setFirstname(firstname);
    user.setLastname(lastname);
    return user;
  }
}
