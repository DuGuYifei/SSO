package lsea.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.*;
import lsea.dto.CreateLogDto;
import lsea.errors.GenericForbiddenError;
import lsea.utils.LogType;

/* Requirement 7.1 */
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
@Table(name = "logs")
public class Log implements Serializable {

  /**
   * The serial version UID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The unique identifier for the log entry.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @SerializedName("id")
  private UUID id;

  /**
   * The data of the log entry.
   */
  @Column(length = 512)
  @Size(min = 3, max = 512)
  @SerializedName("data")
  private String data;

  /**
   * The type of the log entry.
   * user provided.
   */
  @Enumerated(EnumType.ORDINAL)
  @SerializedName("logType")
  private LogType logType;

  /**
   * The user who created the log entry.
   * 'sub' provided from jwt token.
   */
  @SerializedName("userId")
  private UUID userId;

  /**
   * The timestamp of when the log entry was created.
   * server generated.
   */
  @Temporal(TemporalType.TIMESTAMP)
  @SerializedName("createAt")
  private Date createdAt;

  /**
   * The current state of the user.
   * Extracted from jwt token - user.toJson().
   */
  @Lob // Large object
  @SerializedName("userCurrentState")
  private String userCurrentState;

  /**
   * Creates a new log entry.
   *
   * @param dto  - The data transfer object for creating a new log entry.
   * @param user - The user who created the log entry.
   * @return - The new log entry.
   * @throws GenericForbiddenError - If the user is not found by the jwt.
   */
  public static Log create(CreateLogDto dto, User user)
      throws GenericForbiddenError {
    return Log
        .builder()
        .data(dto.getData())
        .logType(LogType.values()[dto.getLogType()])
        .userId(user.getId())
        .createdAt(new Date())
        .userCurrentState(user.toJson())
        .build();
  }
}
