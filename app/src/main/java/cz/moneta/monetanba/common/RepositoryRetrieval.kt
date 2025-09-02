package cz.moneta.monetanba.common

import android.content.Context
import cz.moneta.monetanba.R

/**
 * Runs [call], invokes [unsuccessfulCallHandler] on failure, and returns the successful DTO or `null`.
 */
suspend fun <DATA>performRepositoryRetrieval(
    call: suspend () -> RepositoryCallResponse<DATA>,
    unsuccessfulCallHandler: ((UnsuccessfulRepositoryCall) -> Unit)? = null
): DATA?{
    val response = call.invoke()
    response.unsuccessfulRepositoryCall?.let { unsuccessfulCallHandler?.invoke(it) }
    return response.dto
}

/**
 * Wrapper for repository/network outcomes.
 *
 * @param DTO The payload type.
 * @property dto The successful result (null if unsuccessful).
 * @property unsuccessfulRepositoryCall Details about a failed call (null if successful).
 */
class RepositoryCallResponse<DTO>(
    val dto: DTO? = null,
    val unsuccessfulRepositoryCall: UnsuccessfulRepositoryCall? = null
)

/**
 * Describes an unsuccessful network/repository call.
 *
 * @property errorCode HTTP status code when available.
 * @property callException The thrown exception when a failure occurs before/after the HTTP layer.
 */
class UnsuccessfulRepositoryCall(
    val errorCode: Int? = null,
    val callException: Exception? = null
) {
    /**
     * Produces a user-facing message based on [errorCode] or [callException].
     *
     * @return Localized message (falls back to `R.string.unknown_error`).
     */
    fun getMessage(context: Context): String {
        return errorCode?.let { context.getString(R.string.server_error) + it }
            ?: (callException?.message ?: context.getString(R.string.unknown_error))
    }
}