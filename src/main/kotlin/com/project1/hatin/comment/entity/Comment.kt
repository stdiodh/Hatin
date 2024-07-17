package com.project1.hatin.comment.entity

import com.project1.hatin.common.entity.BaseEntity
import com.project1.hatin.feed.entity.FeedEntity
import com.project1.hatin.member.entity.Member
import jakarta.persistence.*

@Entity
class CommentEntity(
    @Column(nullable = false)
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_feed_comment_feed_id"))
    val feed: FeedEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_comment_author_id"))
    val author: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_comment_comment_parent_id"))
    val parentComment: CommentEntity? = null,

    @OneToMany(mappedBy = "parentComment", cascade = [CascadeType.ALL], orphanRemoval = true)
    val childComments: MutableList<CommentEntity> = mutableListOf()
) : BaseEntity()