package lsea.service;

import java.util.*;
import javax.transaction.Transactional;
import lsea.dto.CreateWebsiteDto;
import lsea.dto.DeleteWebsiteDto;
import lsea.entity.User;
import lsea.entity.Website;
import lsea.errors.GenericForbiddenError;
import lsea.errors.GenericNotFoundError;
import lsea.repository.UserRepository;
import lsea.repository.WebsiteRepository;
import lsea.utils.GlobalPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for all business actions regarding Website entity.
 */
@Service
public class WebsiteService {

  /**
   * The website repository.
   */
  private final WebsiteRepository websiteRepository;

  /**
   * The user repository.
   */
  private final UserRepository userRepository;

  /**
   * Instantiates a new Website service.
   *
   * @param websiteRepository the website repository
   * @param userRepository    the user repository
   */
  @Autowired
  public WebsiteService(
      WebsiteRepository websiteRepository,
      UserRepository userRepository) {
    this.websiteRepository = websiteRepository;
    this.userRepository = userRepository;
  }

  /**
   * Create one.
   *
   * @param dto   the dto
   * @param token the token
   * @throws GenericNotFoundError  the generic not found error
   * @throws GenericForbiddenError the generic forbidden error
   */
  /* Requirement 7.2 */
  @Transactional
  public void createOne(CreateWebsiteDto dto, String token)
      throws GenericNotFoundError, GenericForbiddenError {
    UUID userId = User.verifyToken(token);
    Optional<User> user = userRepository.findById(userId);

    if (!user.isPresent()) {
      throw new GenericNotFoundError("User not found");
    } else if (user.get().hasGlobalAccess(GlobalPermissions.MODERATOR)) {
      throw new GenericForbiddenError(
          "User has no permission to create a website");
    }

    final Website website = Website.create(dto, user.get());
    websiteRepository.save(website);
  }

  /**
   * Find by user id and website displayName.
   *
   * @param id          the id of the user
   * @param displayName the displayName of the website
   * @return the list of Websites by user id and website displayName
   */
  /* @Requirement-3.4 */
  public List<Website> findAllByUserIdAndWebsiteDisplayName(
      UUID id,
      String displayName) {
    User user = userRepository.findById(id).get();
    List<Website> websites = websiteRepository.findByUserAndDisplayName(
        user,
        displayName);

    websites.sort(
        Comparator
            .comparing(Website::getUpdatedAt)
            .thenComparing(Website::getCreatedAt));

    return websites;
  }

  /**
   * find by id.
   *
   * @param id the id of the website
   * @return the Optional instance of Website
   */
  public Optional<Website> findById(UUID id) {
    return websiteRepository.findById(id);
  }

  /**
   * Find all by createdById and redirectUrl in priority queue. If user has multi
   * account in one website, he can choose one by one as most recently used.
   *
   * The PriorityQueue is used in order to handle a situation where the user
   * wants to choose websites sorted by most recent.
   *
   * @param userId the user id
   * @param url    the redirect url
   * @return the priority queue
   */
  /* Requirement-3.4 */
  public PriorityQueue<Website> findAllByCreatedByIdAndRedirectUrl(
      UUID userId,
      String url) {
    User user = userRepository.findById(userId).get();
    List<Website> websites = websiteRepository.findByUserAndRedirectUrl(
        user,
        url);

    websites.sort(
        Comparator
            .comparing(Website::getUpdatedAt)
            .thenComparing(Website::getCreatedAt));

    return new PriorityQueue<>(websites);
  }

  /**
   * Find all by createdById in sorted list.
   *
   * The order of the collection doesn't matter in retrieving
   * the data about the websites, so a standard list is used.
   *
   * @param userId the user id
   * @return the list
   */
  /* Requirement-3.4 */
  public List<Website> findAllByCreatedByIdSorted(UUID userId) {
    User user = new User();
    user.setId(userId);
    List<Website> websites = websiteRepository.findByUser(user);

    websites.sort(
        Comparator
            .comparing(Website::getUpdatedAt)
            .thenComparing(Website::getCreatedAt));

    return websites;
  }

  /**
   * Find all in set ordered by comparable of Website.
   *
   * TreeSet is used because comparable doesn't include id.
   * Having a set allows to have the collection sorted and
   * it will compare without an id so the Treeet will remove the redundant record.
   *
   * @return the set
   */
  /* Requirement-3.3 */
  public Set<Website> findAll() {
    List<Website> websites = websiteRepository.findAll();

    return new TreeSet<>(websites);
  }

  /**
   * Find all userid and website dictionary
   *
   * The HashMap is used to have an easy access to the website display name by the
   * key which is the website id.
   *
   * @return the map
   */
  /* Requirement-3.3 */
  public Map<UUID, String> findAllMap() {
    List<Website> websites = websiteRepository.findAll();

    Map<UUID, String> idUrlMap = new HashMap<>();

    for (Website website : websites) {
      idUrlMap.put(website.getUser().getId(), website.getDisplayName());
    }

    return idUrlMap;
  }

  /**
   * Delete by id, but only the owner of the website can delete it
   *
   * @param dto   the dto
   * @param token the token
   * @throws GenericNotFoundError  the generic not found error
   * @throws GenericForbiddenError the generic forbidden error
   */
  /* Requirement 7.6 */
  public void deleteOne(DeleteWebsiteDto dto, String token)
      throws GenericNotFoundError, GenericForbiddenError {
    UUID userId = User.verifyToken(token);
    User user = userRepository.findById(userId).orElse(null);
    List<Website> websites = websiteRepository.findByUser(user);
    Website website = websiteRepository.findById(UUID.fromString(dto.getWebsiteId())).orElse(null);
    if (website == null) {
      throw new GenericNotFoundError("Website not found for this user");
    }
    if (!websites.contains(website)) {
      throw new GenericForbiddenError("User has no permission to delete this website");
    }
    websiteRepository.deleteById(website.getId());
  }
}
