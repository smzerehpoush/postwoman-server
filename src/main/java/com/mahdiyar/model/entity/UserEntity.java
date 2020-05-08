package com.mahdiyar.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author mahdiyar
 */
@Entity
@Table(name = "users",
        indexes = {@Index(unique = true, name = "idx_users_username", columnList = "username")})
@Data
@NoArgsConstructor
public class UserEntity {
    @Id
    @GenericGenerator(
            name = "usersSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_users"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "usersSequenceGenerator", strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "unique_id", unique = true, nullable = false)
    private String uniqueId = UUID.randomUUID().toString();
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date creationDate;
    @Column(name = "last_modification_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastModificationDate;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;
    @Column(name = "email", unique = true)
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity that = (UserEntity) o;
        return getId() == that.getId() &&
                getUniqueId().equals(that.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUniqueId());
    }
}
