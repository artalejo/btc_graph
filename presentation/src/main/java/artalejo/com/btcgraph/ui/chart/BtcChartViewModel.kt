package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.MutableLiveData
import artalejo.com.btcgraph.ui.base.BaseViewModel
import artalejo.com.btcgraph.ui.entities.BtcChartViewEntity
import artalejo.com.btcgraph.ui.entities.toBtcChartViewEntity
import artalejo.com.domain.interactor.FetchBtcChartDataInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BtcChartViewModel @Inject constructor(private val fetchBtcChartDataInteractor: FetchBtcChartDataInteractor): BaseViewModel() {

    private lateinit var subscription: Disposable
    val btcLiveData =  MutableLiveData<BtcChartModel>()

    fun loadBtcChartData(timeStamp: String){
        subscription = fetchBtcChartDataInteractor.fetchBtcChartData(timeStamp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchBtcDataStarted() }
                .map { it.toBtcChartViewEntity() }
                .subscribe(this::onFetchBtcDataSuccess, this::onFetchBtcDataError)
    }


    private fun onFetchBtcDataStarted() { btcLiveData.value = LoadingState() }

    private fun onFetchBtcDataSuccess(btcData: BtcChartViewEntity) {
        btcLiveData.value = DataRetrievedState(btcData)
    }

    private fun onFetchBtcDataError(error: Throwable){
        // TODO sartalejo: get a proper error message
        btcLiveData.value = ErrorState(error.message ?: "error Message")
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}