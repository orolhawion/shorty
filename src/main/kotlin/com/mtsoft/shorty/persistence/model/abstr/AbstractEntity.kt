package com.mtsoft.shorty.persistence.model.abstr

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*

@MappedSuperclass
abstract class AbstractEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = [])
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    open var id: UUID? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractEntity) return false

        if (id == null || id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: super.hashCode()
    }

    override fun toString(): String = "Entity of type ${this.javaClass.name} with id: ${id?.toString() ?: "<not set>"}"
}
