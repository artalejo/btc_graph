package artalejo.com.btc_graph

import android.content.pm.ActivityInfo
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import artalejo.com.btc_graph.conditionWatcher.ConditionWatcher
import artalejo.com.btc_graph.conditionWatcher.instructions.FetchBtcChartDataInstruction
import artalejo.com.btcgraph.ui.chart.BtcChartActivity
import org.hamcrest.Matchers
import org.hamcrest.Matchers.containsString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BtcChartInstrumentedTest {

    @Rule
    @JvmField  var btcChartActivityRule: ActivityTestRule<BtcChartActivity> = ActivityTestRule(BtcChartActivity::class.java)

    private val  yearTitle = "1 Year"
    private val  allTimeTitle = "All time"
    private val fetchYearBtcChartDataInstruction = FetchBtcChartDataInstruction(yearTitle)
    private val fetchAllTimeBtcChartDataInstruction = FetchBtcChartDataInstruction(allTimeTitle)
    private lateinit var activity: BtcChartActivity

    @Before
    fun setUp(){
        this.activity = btcChartActivityRule.activity
    }

    @Test
    fun btcChartActivityDisplaysTimestampWidget() {
        validateTimestampWidgetIsDisplayed(activity)
    }

    @Test
    fun btcChartActivityDisplaysYearBtcChart() {
        validateTimestampWidgetIsDisplayed(activity)
        validateDefaultYearChartIsDisplayed(activity)
    }

    @Test
    fun btcChartActivityChangesChartWhenTimestampWidgetClicked() {
        validateTimestampWidgetIsDisplayed(activity)
        validateDefaultYearChartIsDisplayed(activity)
        onView(withId(R.id.timestamp_all_time_btn)).perform(click())
        validateAllTimeChartIsDisplayed(activity)
    }

    @Test
    fun btcChartActivityMaintainsDisplayedValueWhenRotating() {
        validateTimestampWidgetIsDisplayed(activity)
        validateDefaultYearChartIsDisplayed(activity)
        btcChartActivityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        validateDefaultYearChartIsDisplayed(activity)
    }

    @Test
    fun btcChartActivityMaintainsNonDefaultValueDisplayedValueWhenRotating() {
        validateTimestampWidgetIsDisplayed(activity)
        validateDefaultYearChartIsDisplayed(activity)
        onView(withId(R.id.timestamp_all_time_btn)).perform(click())
        validateAllTimeChartIsDisplayed(activity)
        btcChartActivityRule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        validateAllTimeChartIsDisplayed(activity)
    }

    // Validations

    private fun validateDefaultYearChartIsDisplayed(activity: BtcChartActivity) {
        ConditionWatcher.waitForCondition(activity, fetchYearBtcChartDataInstruction)
        Espresso.onView(withId(R.id.btc_chart)).check(matches(isDisplayed()))
    }

    private fun validateTimestampWidgetIsDisplayed(activity: BtcChartActivity) {
        onView(Matchers.hasToString(containsString(activity.getString(R.string.app_name))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_all_time_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_two_years_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_year_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_six_months_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_two_months_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_month_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_two_weeks_title))))
        onView(Matchers.hasToString(containsString(activity.getString(R.string.timestamp_week_title))))
    }

    private fun validateAllTimeChartIsDisplayed(activity: BtcChartActivity) {
        ConditionWatcher.waitForCondition(activity, fetchAllTimeBtcChartDataInstruction)
        onView(withId(R.id.btc_chart)).check(matches(isDisplayed()))
    }

}