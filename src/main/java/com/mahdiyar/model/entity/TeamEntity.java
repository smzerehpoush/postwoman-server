package com.mahdiyar.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author mahdiyar
 */
@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
public class TeamEntity {
    @Id
    @GenericGenerator(
            name = "teamsSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_teams"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "teamsSequenceGenerator", strategy = GenerationType.SEQUENCE)
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
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private UserEntity owner;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "team_members",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private Set<UserEntity> members;

    public TeamEntity(String name, String description, UserEntity owner, Set<UserEntity> members) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamEntity)) return false;
        TeamEntity that = (TeamEntity) o;
        return getId() == that.getId() &&
                getUniqueId().equals(that.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUniqueId());
    }
}
