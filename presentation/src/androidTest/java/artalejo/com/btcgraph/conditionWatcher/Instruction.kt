package artalejo.com.btcgraph.conditionWatcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


abstract class Instruction {

    private var dataContainer = Bundle()

    abstract val description: String

    fun setData(dataContainer: Bundle) {
        this.dataContainer = dataContainer
    }

    abstract fun checkCondition(activity: AppCompatActivity): Boolean
}