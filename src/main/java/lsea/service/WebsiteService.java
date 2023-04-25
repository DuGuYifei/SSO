package lsea.service;

import lsea.entity.Website;
import lsea.repository.WebsiteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * Instantiates a new Website service.
     *
     * @param websiteRepository the website repository
     */
    @Autowired
    public WebsiteService(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    /**
     * Find by user id and website displayName.
     *
     * @param id          the id of the user
     * @param displayName the displayName of the website
     * @return the list of Websites by user id and website displayName
     */
    /* @Requirement-3.4 */
    public List<Website> findAllByUserIdAndWebsiteDisplayName(UUID id, String displayName) {
        List<Website> websites = websiteRepository.findAllByCreatedByIdAndDisplayName(id, displayName);

        websites.sort(Comparator.comparing(Website::getUpdatedAt).thenComparing(Website::getCreatedAt));

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
    public PriorityQueue<Website> findAllByCreatedByIdAndRedirectUrl(UUID userId, String url) {
        List<Website> websites = websiteRepository.findAllByCreatedByIdAndRedirectUrl(userId, url);

        websites.sort(Comparator.comparing(Website::getUpdatedAt).thenComparing(Website::getCreatedAt));

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
        List<Website> websites = websiteRepository.findAllByCreatedById(userId);

        websites.sort(Comparator.comparing(Website::getUpdatedAt).thenComparing(Website::getCreatedAt));

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
     * The HashMap is used to have an easy access to the website display name by the key which is the website id.
     *
     * @return the map
     */
    /* Requirement-3.3 */
    public Map<UUID, String> findAllMap() {
        List<Website> websites = websiteRepository.findAll();

        Map<UUID, String> idUrlMap = new HashMap<>();

        for (Website website : websites) {
            idUrlMap.put(website.getCreatedById(), website.getDisplayName());
        }

        return idUrlMap;
    }
}
