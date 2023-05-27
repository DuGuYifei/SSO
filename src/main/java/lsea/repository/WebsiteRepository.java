package lsea.repository;

import java.util.List;
import java.util.UUID;

import lsea.entity.User;
import lsea.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;

/* Requirement-2.2 */

/**
 * The interface Website repository provides methods for interacting with
 * website data in the system.
 */
public interface WebsiteRepository extends JpaRepository<Website, UUID> {
  /**
   * Find all by created by id list.
   *
   * @param user the user
   * @return the list of websites
   */
  /* Requirement 7.4 */
  /*
   * Based on
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query
   * -methods.query-creation,
   * the following method declaration, under the hood works as a parametrized
   * query like: "where x.createdById = ?1"
   */
  List<Website> findByUser(User user);

  /**
   * Find all by created by id and website name.
   *
   * @param user        the user id
   * @param displayName the name of the website
   * @return the list of websites
   */
  /* Requirement 7.4 */
  /*
   * Based on
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query
   * -methods.query-creation,
   * the following method declaration, under the hood works as a parametrized
   * query like: "where x.createdById = ?1 and x.displayName = ?2"
   */
  List<Website> findByUserAndDisplayName(User user, String displayName);

  /**
   * Find all by created by id and website redirect url.
   *
   * @param user the user id
   * @param url  the redirect url of the website
   * @return the list of websites
   */
  /* Requirement 7.4 */
  /*
   * Based on
   * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query
   * -methods.query-creation,
   * the following method declaration, under the hood works as a parametrized
   * query like: "where x.createdById = ?1 and x.redirectUrl = ?2"
   */
  List<Website> findByUserAndRedirectUrl(User user, String url);
}
