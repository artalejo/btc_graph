package artalejo.com.btcgraph.ui.chart

import android.arch.lifecycle.MutableLiveData
import android.util.Log
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

    init {
        btcLiveData.value = LoadingState()
    }

    fun loadBtcChartData(timeStamp: String, rollingAverage: String){
        subscription = fetchBtcChartDataInteractor.fetchBtcChartData(timeStamp, rollingAverage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onFetchBtcDataStarted() }
                .doOnSuccess { onFetchBtcDataSuccess(it.toBtcChartViewEntity()) }
                .doOnError { onFetchBtcDataError() }
                .subscribe()
    }


    private fun onFetchBtcDataStarted(){

    }

    private fun onFetchBtcDataSuccess(btcData: BtcChartViewEntity){
        Log.wtf("HOLA", btcData.status)
    }

    private fun onFetchBtcDataError(){
        // TODO sartalejo: get a proper error message
        btcLiveData.value = ErrorState("errorMessage")
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}