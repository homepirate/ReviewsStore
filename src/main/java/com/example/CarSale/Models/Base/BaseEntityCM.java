package com.example.CarSale.Models.Base;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntityCM extends BaseID{



    @CreationTimestamp
    @Column(name="created", nullable = false)
    protected LocalDateTime created;

    @UpdateTimestamp
    @Column(name="modified", nullable = false)
    protected LocalDateTime modified;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }
}
