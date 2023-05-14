package client.models;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.*;
import client.utils.LogType;

/**
 * This class represents a log entry in the database.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
public class Log implements Serializable {

    /**
     * The serial version UID. Must match version on server.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier for the log entry.
     */
    private UUID id;

    /**
     * The data of the log entry.
     */
    private String data;

    /**
     * The type of the log entry.
     * user provided.
     */
    private LogType logType;

    /**
     * The user who created the log entry.
     * 'sub' provided from jwt token.
     */
    private UUID userId;

    /**
     * The timestamp of when the log entry was created.
     * server generated.
     */
    private Date createdAt;

    /**
     * The current state of the user.
     */
    private String userCurrentState;
}
