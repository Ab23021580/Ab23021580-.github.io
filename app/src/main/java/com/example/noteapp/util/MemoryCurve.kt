package com.example.noteapp.util

import java.util.Calendar
import java.util.Date

object MemoryCurve {
    
    // 艾宾浩斯记忆曲线的复习间隔（天数）
    private val REVIEW_INTERVALS = intArrayOf(1, 2, 4, 7, 15)
    
    /**
     * 根据当前复习阶段计算下次复习时间
     * @param createdAt 笔记创建时间
     * @param currentStage 当前复习阶段 (0-5)
     * @return 下次复习时间的时间戳
     */
    fun calculateNextReviewDate(createdAt: Long, currentStage: Int): Long {
        if (currentStage >= REVIEW_INTERVALS.size) {
            // 已完成所有复习阶段，返回一个很远的未来时间
            return Long.MAX_VALUE
        }
        
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = createdAt
        
        // 计算从创建时间到当前阶段需要的总天数
        var totalDays = 0
        for (i in 0 until currentStage) {
            if (i < REVIEW_INTERVALS.size) {
                totalDays += REVIEW_INTERVALS[i]
            }
        }
        
        calendar.add(Calendar.DAY_OF_YEAR, totalDays)
        
        // 设置为当天的开始时间（00:00:00）
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        
        return calendar.timeInMillis
    }
    
    /**
     * 标记笔记为已复习，更新到下一个复习阶段
     * @param currentStage 当前复习阶段
     * @return 下一个复习阶段
     */
    fun getNextReviewStage(currentStage: Int): Int {
        return (currentStage + 1).coerceAtMost(REVIEW_INTERVALS.size)
    }
    
    /**
     * 获取复习间隔天数描述
     * @param stage 复习阶段 (0-5)
     * @return 间隔描述字符串
     */
    fun getReviewIntervalText(stage: Int): String {
        return when {
            stage >= REVIEW_INTERVALS.size -> "已完成"
            stage == 0 -> "开始"
            else -> "${REVIEW_INTERVALS[stage - 1]}天"
        }
    }
    
    /**
     * 格式化日期显示
     * @param timestamp 时间戳
     * @return 格式化后的日期字符串
     */
    fun formatDate(timestamp: Long): String {
        if (timestamp == Long.MAX_VALUE) {
            return "已完成所有复习"
        }
        
        val date = Date(timestamp)
        val today = Calendar.getInstance()
        val targetDate = Calendar.getInstance()
        targetDate.time = date
        
        return when {
            isSameDay(today, targetDate) -> "今天"
            isTomorrow(today, targetDate) -> "明天"
            isYesterday(today, targetDate) -> "昨天"
            else -> {
                val dateFormat = java.text.SimpleDateFormat("MM月dd日", java.util.Locale.CHINA)
                dateFormat.format(date)
            }
        }
    }
    
    private fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
    
    private fun isTomorrow(today: Calendar, target: Calendar): Boolean {
        val tomorrow = today.clone() as Calendar
        tomorrow.add(Calendar.DAY_OF_YEAR, 1)
        return isSameDay(tomorrow, target)
    }
    
    private fun isYesterday(today: Calendar, target: Calendar): Boolean {
        val yesterday = today.clone() as Calendar
        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        return isSameDay(yesterday, target)
    }
}
