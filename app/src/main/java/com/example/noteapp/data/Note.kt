package com.example.noteapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    
    val title: String,
    
    val content: String,
    
    val createdAt: Long = System.currentTimeMillis(),
    
    // 复习阶段：0-5（0表示未开始，1-5表示五个复习阶段）
    val reviewStage: Int = 0,
    
    // 下次复习日期时间戳
    val nextReviewDate: Long = System.currentTimeMillis()
) {
    // 检查是否需要今天复习
    fun needsReviewToday(): Boolean {
        val today = Date().time
        return nextReviewDate <= today
    }
    
    // 获取复习状态描述
    fun getReviewStatusText(): String {
        return when {
            reviewStage == 0 -> "未开始复习"
            reviewStage >= 5 -> "已完成所有复习"
            needsReviewToday() -> "需要复习"
            else -> "复习阶段 $reviewStage/5"
        }
    }
}
