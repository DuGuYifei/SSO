package lsea.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import lsea.dto.CreateWebsiteDto;
import lsea.utils.RandomBase64Generator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/* Requirement 2.1 */
/**
 * Represents a website in the system.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
@Table(name = "websites")
public class Website implements Serializable, Comparable<Website> {

    /**
     * Unique id (primary key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    private UUID id;

    /**
     * The name of website. e.g.:Google, Facebook.
     */
    @SerializedName("display_name")
    private String displayName;

    /**
     * uuid reference to users table
     */
    @SerializedName("created_by_id")
    private UUID createdById;

    /**
     * defaults to time.now
     */
    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("created_at")
    private Date createdAt;

    /**
     * url to redirect from our single sign on application
     */
    @SerializedName("redirect_url")
    private String redirectUrl;

    /**
     * private key of the application that requests the single sign on on their
     * websites
     */
    @SerializedName("private_key")
    private String privateKey;

    /**
     * default to false
     */
    @SerializedName("is_active")
    private Boolean isActive;

    /**
     * the date of update
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    private Date updatedAt;

    /**
     * Creates a new website based on the provided CreateWebsiteDto.
     * 
     * @param dto     The dto containing the information to create the website.
     * @param creator The user creating the website.
     * @return The newly created website row.
     */
    public static Website create(CreateWebsiteDto dto, User creator) {
        Website website = Website.builder()
                .id(UUID.randomUUID())
                .displayName(dto.getDisplayName())
                .createdById(creator.getId())
                .createdAt(new Date())
                .redirectUrl(dto.getRedirectUrl())
                .privateKey(RandomBase64Generator.generateLong())
                .isActive(false)
                .build();
        website.setUpdatedAt(website.getCreatedAt());
        return website;
    }

    /**
     * CompareTo method for Website class. It is compare without id, because in case
     * there is two same website row.
     */
    @Override
    public int compareTo(Website o) {
        int result = this.isActive.compareTo(o.getIsActive());
        /* Requirement 2.3 */
        if (result != 0) {
            return result;
        }
        result = this.createdById.compareTo(o.getCreatedById());
        if (result != 0) {
            return result;
        }
        result = this.updatedAt.compareTo(o.getUpdatedAt());
        if (result != 0) {
            return result;
        }
        result = this.createdAt.compareTo(o.getCreatedAt());
        if (result != 0) {
            return result;
        }
        result = this.displayName.compareTo(o.getDisplayName());
        if (result != 0) {
            return result;
        }
        result = this.redirectUrl.compareTo(o.getRedirectUrl());
        if (result != 0) {
            return result;
        }
        return this.privateKey.compareTo(o.getPrivateKey());
    }

}
