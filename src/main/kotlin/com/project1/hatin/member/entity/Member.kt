package com.project1.hatin.member.entity

import com.project1.hatin.common.enums.Gender
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_userId", columnNames = ["userId"])]
)
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Long?,

    //UK
    @Column(nullable = false, length = 100, updatable = false)
    val userId : String,

    @Column(nullable = false, length = 100)
    val password : String,

    @Column(nullable = false, length = 10)
    var nickName : String,

    @Column(nullable = false, length = 30)
    @Temporal(TemporalType.DATE)
    var birthday : LocalDate,

    @Column(nullable = false, length = 30)
    var phoneNumber : String,
    @Column(nullable = false, length = 30)
    var address : String,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    var gender : Gender,
    )
