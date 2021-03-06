package artalejo.com.btcgraph.conditionWatcher

import android.support.v7.app.AppCompatActivity

class ConditionWatcher private constructor() {

    private var timeoutLimit = DEFAULT_TIMEOUT_LIMIT
    private var watchInterval = DEFAULT_INTERVAL

    companion object {

        private const val CONDITION_NOT_MET = 0
        private const val CONDITION_MET = 1
        private const val TIMEOUT = 2

        const val DEFAULT_TIMEOUT_LIMIT = 1000 * 60
        const val DEFAULT_INTERVAL = 250

        private var conditionWatcher: ConditionWatcher? = null

        private val instance: ConditionWatcher
            get() {
                if (conditionWatcher == null) {
                    conditionWatcher = ConditionWatcher()
                }
                return conditionWatcher!!
            }

        @Throws(Exception::class)
        fun waitForCondition(activity: AppCompatActivity, instruction: Instruction) {
            var status = CONDITION_NOT_MET
            var elapsedTime = 0

            do {
                if (instruction.checkCondition(activity)) {
                    status = CONDITION_MET
                } else {
                    elapsedTime += instance.watchInterval
                    Thread.sleep(instance.watchInterval.toLong())
                }

                if (elapsedTime >= instance.timeoutLimit) {
                    status = TIMEOUT
                    break
                }
            } while (status != CONDITION_MET)

            if (status == TIMEOUT)
                throw Exception(instruction.description + " - took more than " + instance.timeoutLimit / 1000 + " seconds. Test stopped.")
        }
    }
}
