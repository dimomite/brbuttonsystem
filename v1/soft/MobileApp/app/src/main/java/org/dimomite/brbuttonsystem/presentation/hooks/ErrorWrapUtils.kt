package org.dimomite.brbuttonsystem.presentation.hooks

import android.os.Bundle
import org.dimomite.brbuttonsystem.domain.common.ErrorRetryCallback
import org.dimomite.brbuttonsystem.domain.common.ErrorWrap

private const val FIELD_PERMISSION_NAME = "Permission"

fun createErrorWrapForNoPermission(permission: String, cb: ErrorRetryCallback? = null): ErrorWrap {
    return ErrorWrap.NoPermission("Not granted permission: \"$permission\"").apply {
        retryCallback = cb
        args = Bundle().apply {
            putString(FIELD_PERMISSION_NAME, permission)
        }
    }
}

fun extractPermissionName(err: ErrorWrap.NoPermission): String? = err.args?.getString(FIELD_PERMISSION_NAME)
