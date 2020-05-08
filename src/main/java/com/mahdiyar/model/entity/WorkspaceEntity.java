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
@Table(name = "workspaces")
@Data
@NoArgsConstructor
public class WorkspaceEntity {
    @Id
    @GenericGenerator(
            name = "workspacesSequenceGenerator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "seq_workspaces"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @GeneratedValue(generator = "workspacesSequenceGenerator", strategy = GenerationType.SEQUENCE)
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
    @ManyToMany
    @JoinTable(name = "workspaces_teams",
            joinColumns = {@JoinColumn(name = "workspace_id")},
            inverseJoinColumns = {@JoinColumn(name = "team_id")})
    private Set<TeamEntity> teams;
    @ManyToMany
    @JoinTable(name = "workspaces_collections",
            joinColumns = {@JoinColumn(name = "workspace_id")},
            inverseJoinColumns = {@JoinColumn(name = "collection_id")})
    private Set<CollectionEntity> collections;
    @ManyToMany
    @JoinTable(name = "workspaces_environments",
            joinColumns = {@JoinColumn(name = "workspace_id")},
            inverseJoinColumns = {@JoinColumn(name = "environment_id")})
    private Set<EnvironmentEntity> environments;
    @ManyToMany
    @JoinTable(name = "workspaces_users",
            joinColumns = {@JoinColumn(name = "workspace_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<TeamEntity> users;

    public WorkspaceEntity(String name, String description, UserEntity owner) {
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkspaceEntity)) return false;
        WorkspaceEntity that = (WorkspaceEntity) o;
        return getId() == that.getId() &&
                getUniqueId().equals(that.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUniqueId());
    }
}
