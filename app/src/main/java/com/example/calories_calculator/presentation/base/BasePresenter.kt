package com.example.calories_calculator.presentation.base

import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null
    protected val tag: String = this::class.java.simpleName

    // Scope для корутин - будет предоставлен через Compose
    private var coroutineScope: CoroutineScope? = null

    @CallSuper
    override fun onAttach(view: V) {
        Log.d(tag, "onAttach()")
        this.view = view
        onCreate()
    }

    @CallSuper
    override fun onCreate() {
        Log.d(tag, "onCreate()")
    }

    @CallSuper
    override fun onStart() {
        Log.d(tag, "onStart()")
    }

    @CallSuper
    override fun onResume() {
        Log.d(tag, "onResume()")
    }

    @CallSuper
    override fun onPause() {
        Log.d(tag, "onPause()")
    }

    @CallSuper
    override fun onStop() {
        Log.d(tag, "onStop()")
    }

    @CallSuper
    override fun onDestroy() {
        Log.d(tag, "onDestroy()")
        view = null
        coroutineScope = null
    }

    // Устанавливаем scope извне (из Composable)
    fun setScope(scope: CoroutineScope) {
        this.coroutineScope = scope
    }

    // Запуск корутины в презентере
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ): Job? {
        return coroutineScope?.launch(context, start, block)
    }

    // Получение ViewModel (будет вызываться из Composable)
    protected inline fun <reified VM : ViewModel> getViewModel(
        viewModelStoreOwner: ViewModelStoreOwner
    ): VM {
        return ViewModelProvider(viewModelStoreOwner)[VM::class.java]
    }
}