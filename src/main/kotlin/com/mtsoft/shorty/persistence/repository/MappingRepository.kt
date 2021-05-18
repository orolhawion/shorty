package com.mtsoft.shorty.persistence.repository

import com.mtsoft.shorty.persistence.model.MappingEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MappingRepository : JpaRepository<MappingEntity, UUID>
