package lsea.repository;

import java.util.List;
import java.util.UUID;
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
   * @param userId the user id
   * @return the list of websites
   */
  List<Website> findAllByCreatedById(UUID userId);

  /**
   * Find all by created by id and website name.
   *
   * @param userId      the user id
   * @param displayName the name of the website
   * @return the list of websites
   */
  List<Website> findAllByCreatedByIdAndDisplayName(
      UUID userId,
      String displayName);

  /**
   * Find all by created by id and website redirect url.
   *
   * @param userId the user id
   * @param url    the redirect url of the website
   * @return the list of websites
   */
  List<Website> findAllByCreatedByIdAndRedirectUrl(UUID userId, String url);
}
