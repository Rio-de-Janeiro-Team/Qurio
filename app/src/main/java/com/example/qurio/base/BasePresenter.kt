package com.example.qurio.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BasePresenter<V : BaseView> {

    private var _view: V? = null
    protected val view: V?
        get() = _view

    private val job = SupervisorJob()
    protected val presenterScope = CoroutineScope(job + Dispatchers.Main)

    open fun attachView(view: V) {
        this._view = view
        onViewAttached()
    }

    open fun onViewAttached() {}

    open fun detachView() {
        job.cancel()
        _view = null
        onViewDetached()
    }

    open fun onViewDetached() {}

    protected fun executeIfViewAttached(action: V.() -> Unit) {
        _view?.let { action(it) }
    }

    protected fun <T> tryToCall(
        block: suspend () -> T,
        onStart: suspend () -> Unit = {},
        onSuccess: (T) -> Unit = {},
        onEnd: () -> Unit = {},
        onError: (Throwable) -> Unit = {},
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) = presenterScope.launch(dispatcher) {
        withContext(Dispatchers.Main) { onStart() }
        runCatching { block() }
            .onSuccess { result ->
                withContext(Dispatchers.Main) { onSuccess(result) }
            }
            .onFailure { error ->
                withContext(Dispatchers.Main) { onError(error) }
            }
        withContext(Dispatchers.Main) { onEnd() }
    }

}