package artalejo.com.data.repository.entities

import artalejo.com.domain.entities.BtcDataInfo

data class BtcDataEntity(
        val status: String,
        val name: String,
        val unit: String,
        val period: String,
        val description: String,
        val values: List<BtcValueEntity> = listOf())

fun BtcDataEntity.toBtcDataInfo() = BtcDataInfo(this.description, this.values.map { it.toBtcValueInfo() })