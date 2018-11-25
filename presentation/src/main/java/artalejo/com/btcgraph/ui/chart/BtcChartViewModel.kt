package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.MutableLiveData
import artalejo.com.btcgraph.di.application.SCHEDULERS_IO
import artalejo.com.btcgraph.di.application.SCHEDULERS_MAIN_THREAD
import artalejo.com.btcgraph.ui.base.BaseViewModel
import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity
import artalejo.com.btcgraph.ui.entities.toBtcChartViewEntity
import artalejo.com.domain.interactor.FetchBtcChartDataInteractor
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

class BtcChartViewModel @Inject constructor(private val fetchBtcChartDataInteractor: FetchBtcChartDataInteractor,
                                            @Named(SCHEDULERS_IO) val subscribeOnScheduler: Scheduler,
                                            @Named(SCHEDULERS_MAIN_THREAD) val observeOnScheduler: Scheduler): BaseViewModel() {

    private lateinit var subscription: Disposable
    val btcLiveData =  MutableLiveData<BtcChartModel>()

    fun loadBtcChartData(timeStamp: String){
        subscription = fetchBtcChartDataInteractor.fetchBtcChartData(timeStamp)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .doOnSubscribe { onFetchBtcDataStarted() }
                .map { it.toBtcChartViewEntity() }
                .subscribe(this::onFetchBtcDataSuccess, this::onFetchBtcDataError)
    }

    private fun onFetchBtcDataStarted() { btcLiveData.value = LoadingState() }

    private fun onFetchBtcDataSuccess(btcData: BtcChartViewEntity) {
        btcLiveData.value = DataRetrievedState(btcData)
    }

    private fun onFetchBtcDataError(error: Throwable){
        btcLiveData.value = ErrorState(error.message)
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}