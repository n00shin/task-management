package ir.chica.task.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ir.chica.task.deserialize.CustomDateDeserializer;
import ir.chica.task.dto.TaskDto;
import ir.chica.task.enums.TaskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String name;
    @NotBlank
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private LocalDateTime createdAt;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @CreationTimestamp
    private LocalDateTime deletedAt;
    @Column( updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private boolean done;
    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskTypeEnum taskType;
    private boolean deleted;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public Task(String name) {
        this.name=name;
    }

    public static Task fromDto(TaskDto taskDto) {
        Task task = new Task();
        task.setName( taskDto.name() );
        task.setCreatedAt( taskDto.createdAt());
        task.setUpdatedAt(taskDto.updatedAt());
        task.setDeletedAt( taskDto.deletedAt());
        task.setDone( taskDto.done() );
        return task;
    }


//    @PrePersist
//    private void setCreationDate() {
//        this.createdAt = new LocalDateTime();
//    }

//
//      @PreUpdate
//    private void setChangeDate() {
//                this.updatedAt = new LocalDateTime();
//    }



    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        if (this.getId() != null) {
            Task task = (Task) object;
            return this.getId().equals( task.getId() );
        } else {
            return false;
        }

    }


}


