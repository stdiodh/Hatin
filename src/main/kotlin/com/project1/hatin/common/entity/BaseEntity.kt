package com.project1.hatin.common.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass // 상속 관계 매핑을 위한 어노테이션
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성되는 값으로 설정
    var id: Long? = null
        private set

    @Column(updatable = false) // 엔티티가 생성될 때만 값을 설정하고, 업데이트 시에는 변경하지 않음
    var createdAt: LocalDateTime? = null
        private set

    var updatedAt: LocalDateTime? = null
        private set

    @PrePersist // 엔티티가 저장되기 전에 실행
    fun prePersist() {
        val now = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }

    @PreUpdate // 엔티티가 업데이트되기 전에 실행
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}