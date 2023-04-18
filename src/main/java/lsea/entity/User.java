package lsea.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import io.jsonwebtoken.Claims;
import lombok.*;
import lsea.dto.CreateUserDto;
import lsea.errors.GenericConflictError;
import lsea.errors.GenericForbiddenError;
import lsea.utils.GlobalPermissions;
import org.springframework.security.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/* Requirement 2.1 */
/* Requirement 2.4 */
/**
 * Represents a user in the system.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "users")
public class User extends PermissionedEntity implements Serializable {
    /* Requirement 2.6 */
    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    private UUID id;

    /**
     * The username of the user.
     */
    @SerializedName("username")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /**
     * The encrypted password of the user.
     */
    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * The date the user was created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    @SerializedName("created_at")
    private Date createdAt;

    /**
     * The date the user was last updated.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @SerializedName("updated_at")
    @Column(name = "updated_at")
    private Date updatedAt;

    /**
     * The email address of the user.
     */
    @SerializedName("email")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    /**
     * The confirmation code of the user.
     */
    @Column(name = "confirmation_code")
    private String confirmationCode;

    /**
     * The date the user was confirmed.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "confirmed_at")
    private Date confirmedAt;

    /**
     * The global permission level of the user.
     */
    @Column(name = "global_permission", nullable = false)
    private int globalPermission;

    /* Requirement 2.6 */
    /**
     * Creates a new User instance based on the provided CreateUserDto.
     * 
     * @param dto the CreateUserDto containing the user data
     * @return a new User instance with the provided data
     */
    public static User create(CreateUserDto dto) {
        String encryptedPassword = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
        return User.builder()
                .id(UUID.randomUUID())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(encryptedPassword)
                .createdAt(new Date())
                .globalPermission(GlobalPermissions.USER)
                .build();
    }

    /**
     * Changes the password of the user with the specified ID.
     *
     * @param oldPassword the user's current password
     * @param newPassword the new password to set
     * @throws GenericConflictError if the old password is incorrect
     */
    public void changePassword(String oldPassword, String newPassword) throws GenericConflictError {
        if (BCrypt.checkpw(oldPassword, this.getPassword())) {
            this.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        } else {
            throw new GenericConflictError("Incorrect password");
        }
    }

    /**
     * Generates a JWT token for the user.
     *
     * @return The JWT token
     */
    public String getJwtToken() {
        Date now = new Date();
        String secret = System.getenv("JWT_SECRET");
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(id.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + 86400000)) // 24 hours
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Converts a User object to a JSON string using the Gson library.
     *
     * @return A JSON string representing the User object
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    /**
     * Verifies a JWT token and returns the user ID encoded in the token.
     *
     * @param token The JWT token to verify
     * @return The user ID encoded in the token
     * @throws GenericForbiddenError if the token is invalid
     */
    public UUID verifyToken(String token) throws GenericForbiddenError {
        String secret = System.getenv("JWT_SECRET");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            throw new GenericForbiddenError("Invalid token");
        }
    }

}
