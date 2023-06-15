package eeapp.models;

import lombok.*;

import java.util.Date;
import java.util.UUID;


/**
 * Store information about a website.
 */
@Getter
@Setter
public class Website {

    /**
     * Unique id (primary key).
     */
    private UUID id;

    /**
     * The name of website. e.g.:Google, Facebook.
     */
    private String displayName;

    /**
     * uuid reference to users table
     */
    private UUID createdById;

    /**
     * defaults to time.now
     */
    private Date createdAt;

    /**
     * url to redirect from our single sign on application
     */
    private String redirectUrl;

    /**
     * private key of the application that requests the single sign on on their
     * websites
     */
    private String privateKey;

    /**
     * default to false
     */
    private Boolean isActive;

    /**
     * the date of update
     */
    private Date updatedAt;

}
