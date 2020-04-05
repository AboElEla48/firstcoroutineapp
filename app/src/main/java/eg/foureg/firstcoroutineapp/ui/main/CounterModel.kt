package eg.foureg.firstcoroutineapp.ui.main

import kotlinx.coroutines.delay


class CounterModel {

    /**
     * Load data from backend simulator
     */
     suspend fun loadCounter() : Int{
         delay(2 * 1000)
         return ++counter
    }

    var counter : Int = 0
}