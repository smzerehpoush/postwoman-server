package com.mahdiyar.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author mahdiyar
 */
@Entity
@Table(name = "token", indexes = {
        @Index(name = "idx_token_token", columnList = "hashed_token")
}
)
@Data
@NoArgsConstructor
public class TokenEntity {
    @Id
    @GenericGenerator(
            name = "tokenSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_token"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "tokenSequenceGenerator", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", length = 15)
    private Long id;

    @Column(name = "unique_id", nullable = false, unique = true)
    private String uniqueId = UUID.randomUUID().toString();

    @Column(name = "hashed_token", nullable = false)
    private String hashedToken;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date creationDate;

    @Column(name = "expiration")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(name = "ip")
    private String ip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "last_activity")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastActivity;

    public TokenEntity(String hashedToken, Date expirationDate, String ip, UserEntity user) {
        this.hashedToken = hashedToken;
        this.expirationDate = expirationDate;
        this.ip = ip;
        this.user = user;
    }
}