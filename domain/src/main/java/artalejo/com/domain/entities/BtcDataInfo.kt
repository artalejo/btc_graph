package artalejo.com.domain.entities

data class BtcDataInfo(
        val status: String = "",
        val name: String = "",
        val unit: String = "",
        val period: String = "",
        val description: String = "",
        val values: List<BtcValueInfo> = listOf())