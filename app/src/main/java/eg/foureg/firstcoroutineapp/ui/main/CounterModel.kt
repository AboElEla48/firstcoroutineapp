package eg.foureg.firstcoroutineapp.ui.main

import eg.foureg.firstcoroutineapp.common.Logger
import kotlinx.coroutines.delay


class CounterModel {

    /**
     * Load data from backend simulator
     */
     suspend fun loadCounter() : Int{
        Logger.debug(TAG, "loadCounter() | started ")

         delay(2 * 1000)
        Logger.debug(TAG, "loadCounter() | delay finished, return result loaded ")

         return ++counter
    }

    var counter : Int = 0

    companion object {
        const val TAG : String = "CounterModel"
    }

}