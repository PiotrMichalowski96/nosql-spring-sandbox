package com.piotr.neo4j.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.piotr.neo4j.data.domain.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

@Testcontainers
@DataNeo4jTest
public class InstagramUserRepositoryIntegrationTest {

  @Container
  private static final Neo4jContainer<?> databaseServer = new Neo4jContainer<>("neo4j:4.4.7-community")
      .withAdminPassword(null);

  private final InstagramUserRepository instagramUserRepository;
  private final List<User> savedUsers;

  @Autowired
  public InstagramUserRepositoryIntegrationTest(InstagramUserRepository instagramUserRepository) {
    this.instagramUserRepository = instagramUserRepository;

    this.savedUsers = LongStream.range(0, 10)
        .mapToObj(id -> createUser(id,
            RandomStringUtils.randomAlphanumeric(10),
            RandomStringUtils.randomAlphabetic(10),
            RandomStringUtils.randomAlphabetic(10)
        ))
        .collect(Collectors.toList());

    this.savedUsers.stream()
        .reduce((user1, user2) -> {
          user2.setFollowingUsers(Set.of(user1));
          return user2;
        });
  }

  @DynamicPropertySource
  static void neo4jProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.neo4j.uri", databaseServer::getBoltUrl);
    registry.add("spring.neo4j.authentication.username", () -> "neo4j");
    registry.add("spring.neo4j.authentication.password", () -> null);
  }

  @BeforeEach
  void setup() {
    instagramUserRepository.deleteAll();
    instagramUserRepository.saveAll(savedUsers);
  }

  @Test
  void shouldRetrieveInstagramUserById() {
    //given
    User expectedUser = savedUsers.get(0);

    //when
    User actualUser = instagramUserRepository.findByUserId(expectedUser.getUserId());

    //then
    assertThat(actualUser).usingRecursiveComparison()
        .ignoringFields("followingUsers")
        .isEqualTo(expectedUser);
  }

  @Test
  void shouldFindShortestPathBetweenUsers() {
    //given
    String startUsername = savedUsers.get(savedUsers.size() - 1).getUsername();
    String endUsername = savedUsers.get(0).getUsername();
    Long expectedPath = savedUsers.size() - 1L;

    //when
    Long path = instagramUserRepository.findShortestFollowingPathBetweenUsers(startUsername, endUsername);

    //then
    assertThat(path).isEqualTo(expectedPath);
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
