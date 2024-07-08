package com.project1.hatin.member.entity


import com.project1.hatin.ban.entity.BanKeyword
import com.project1.hatin.common.entity.BaseEntity
import com.project1.hatin.common.enums.Gender
import com.project1.hatin.member.dto.MemberResponseDto
import com.project1.hatin.routine.entity.Routine
import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_userId", columnNames = ["userId"])]
)
class Member (
    //UK
    @Column(nullable = false, length = 100, updatable = false)
    val userId : String,

    @Column(nullable = false, length = 100)
    var password : String,

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

    @OneToMany(fetch = FetchType.LAZY,cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "member")
    val role : List<MemberRole>? = null,

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "member_id")
    var memberList: MutableList<Member> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "member_id")
    var routineList: MutableList<Routine> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "member_id")
    var banKeywordList: MutableList<BanKeyword> = mutableListOf(),

    ) : BaseEntity() {
        fun toResponse() : MemberResponseDto = MemberResponseDto(
            userId = userId,
            password = password,
            nickName = nickName,
            birthday = birthday,
            phoneNumber = phoneNumber,
            address = address,
            gender = gender
        )
    }
