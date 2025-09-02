package cz.moneta.monetanba.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


/**
 * Creates a simple [ViewModelProvider.Factory] that builds a [ViewModel] via [initializer].
 *
 * @param initializer Function that returns the [VM] instance.
 */
fun <VM: ViewModel> viewModelFactory(initializer: () -> VM): ViewModelProvider.Factory{
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    }
}