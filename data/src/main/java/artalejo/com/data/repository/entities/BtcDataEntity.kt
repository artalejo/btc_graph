package artalejo.com.data.repository.entities

import artalejo.com.domain.entities.BtcDataInfo

data class BtcDataEntity(
        var status: String = "",
        var name: String = "",
        var unit: String = "",
        var period: String = "",
        var description: String = "",
        var values: List<BtcValueEntity> = listOf())

fun BtcDataEntity.toBtcDataInfo() = BtcDataInfo(this.description, this.values.map { it.toBtcValueInfo() })