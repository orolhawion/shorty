package com.mtsoft.shorty.persistence.model

import com.mtsoft.shorty.persistence.model.abstr.AbstractEntity
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MappingEntity : AbstractEntity() {

    @Column(name = "originalUrl")
    lateinit var originalUrl: String

    @Column(name = "createdOn")
    val createdOn: Instant = Instant.now()
}