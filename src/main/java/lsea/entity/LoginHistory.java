package lsea.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/* Requirement 7.1 */
/**
 * This class represents a login history entity in the system.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "login_history")
public class LoginHistory implements Serializable {

    /**
     * The unique identifier for this login history.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @SerializedName("id")
    private UUID id;

    /**
     * The ID of the user.
     */
    @Column(name = "user_id")
    @SerializedName("userId")
    private UUID userId;

    /**
     * The date when the user logged in.
     */
    @Column(name = "login_at")
    @SerializedName("loginAt")
    private Date loginAt;
}
