package com.example.tddmovieapp.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
fun<T> CoroutineScope.observeFlow(
    stateFlow: StateFlow<T>,
    dropInitialValue: Boolean = false,
    dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    block: () -> Unit
): List<T> {
    val result = mutableListOf<T>()
    val collectJob = launch(dispatcher) {
        stateFlow.toCollection(result)
    }
    block()
    collectJob.cancel()
    return if (dropInitialValue) result.drop(1) else result
}