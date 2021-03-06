package eg.foureg.firstcoroutineapp.ui.main

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.foureg.firstcoroutineapp.R
import eg.foureg.firstcoroutineapp.common.Logger
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class MainViewModel : ViewModel() {

    fun initModel(c: Context) {
        context = c
    }

    private fun processStarted() {
        progressVisibility.value = View.VISIBLE
        counterBtnEnabled.value = false
        cancelBtnEnabled.value = true
        timeoutBtnEnabled.value = false
    }

    private fun processFinished() {
        progressVisibility.value = View.GONE
        counterBtnEnabled.value = true
        cancelBtnEnabled.value = false
        timeoutBtnEnabled.value = true
    }

    /**
     * Load value counter from backend and change text in UI according to result value
     */
    fun changeText() {
        Logger.debug(TAG, "changeText() | started ")

        counterLoaderJob = viewModelScope.launch {
            Logger.debug(TAG, "changeText() | CounterModel counterLoaderJob started ")

            Logger.debug(TAG, "changeText() | Access isActive from inside coroutine ? ${coroutineContext[Job]!!.isActive}")

            // Prepare UI for loading
            processStarted()

            // Load Counter from async data provider (Backend simulation)
            txtMsg.value = String.format(context.getString(R.string.txt_counter_msg), counterModel.loadCounter())
        }

        viewModelScope.launch {
            Logger.debug(TAG, "changeText() | CounterModel Receiver coroutine stared")

            // Wait till loading is finished from backend
            counterLoaderJob.join()

            Logger.debug(TAG, "changeText() | CounterModel Loader coroutine result received")
            Logger.debug(TAG, "changeText() | Message is: ${txtMsg.value}")

            // Hide progresss bar when finishes loading
            processFinished()
        }

    }

    fun startTimeOutRequest() {
        Logger.debug(TAG, "startTimeOutRequest() | started")

        viewModelScope.launch {
            Logger.debug(TAG, "changeText() | CounterModel counterLoaderJob started ")

            // Prepare UI for loading
            processStarted()

            // Load Counter from async data provider (Backend simulation)
            val timeoutJob = withTimeoutOrNull(2000) {
                txtMsg.value = String.format(context.getString(R.string.txt_counter_msg), counterModel.loadCounter())
            }

            if(timeoutJob == null) {
                txtMsg.value = context.getString(R.string.txt_timeout_msg)
            }

            processFinished()

        }
    }

    /**
     * Cancel loading coroutine
     */
    fun cancelLoading() {
        Logger.debug(TAG, "cancelLoading() | started")

        viewModelScope.launch {
            if(counterLoaderJob.isActive) {
                Logger.debug(TAG, "cancelLoading() | send cancel message")
                counterLoaderJob.cancelAndJoin()

                Logger.debug(TAG, "cancelLoading() | CounterModel Loader coroutine canceled")
            }
        }
    }

    lateinit var context : Context
    private val counterModel : CounterModel = CounterModel()

    private lateinit var counterLoaderJob : Job

    val txtMsg : MutableLiveData<String> = MutableLiveData()

    val counterBtnEnabled : MutableLiveData<Boolean> = MutableLiveData()
    val cancelBtnEnabled : MutableLiveData<Boolean> = MutableLiveData()
    val timeoutBtnEnabled : MutableLiveData<Boolean> = MutableLiveData()
    val progressVisibility : MutableLiveData<Int> = MutableLiveData()


    companion object {
        const val TAG : String = "MainViewModel"
    }

}
