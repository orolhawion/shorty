package com.mtsoft.shorty.persistence.model.abstr

import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = [])
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @Type(type = "uuid-char" )
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
