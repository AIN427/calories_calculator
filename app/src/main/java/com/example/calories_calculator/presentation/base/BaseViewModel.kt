package com.example.calories_calculator.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    protected val tag: String = this::class.java.simpleName

    protected fun <T> launch(
        context: CoroutineContext = Dispatchers.Default,
        onError: ((Throwable) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        runningBlock: suspend CoroutineScope.() -> T,
    ): Job {
        return viewModelScope.launch(context) {
            runCatching {
                val result = runningBlock.invoke(this)

                withMainContext {
                    onSuccess?.invoke(result)
                }
            }.onFailure {
                withMainContext {
                    onError?.invoke(it)
                }
            }.also {
                withMainContext {
                    onComplete?.invoke()
                }
            }
        }
    }

    protected fun launchIO(
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(Dispatchers.IO, start, block)

    protected suspend fun <T> withMainContext(
        block: suspend CoroutineScope.() -> T
    ): T = withContext(Dispatchers.Main, block)
}