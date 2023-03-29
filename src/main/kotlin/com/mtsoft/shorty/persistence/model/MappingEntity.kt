package com.mtsoft.shorty.persistence.model

import com.mtsoft.shorty.persistence.model.abstr.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.Instant

@Entity
class MappingEntity : AbstractEntity() {

    @Column(name = "originalUrl")
    lateinit var originalUrl: String

    @Column(name = "createdOn")
    val createdOn: Instant = Instant.now()
}
