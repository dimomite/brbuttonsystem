package org.dimomite.brbuttonsystem.presentation.hooks

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import org.dimomite.brbuttonsystem.domain.common.DataContainer
import org.dimomite.brbuttonsystem.domain.common.ErrorWrap
import timber.log.Timber

class DataHook {
    private lateinit var hook: HookImpl

    fun registerInActivity(act: AppCompatActivity) {
        val name = act.javaClass.simpleName
        if (!this::hook.isInitialized) {
            hook = HookImpl(name, act)
            act.lifecycle.addObserver(hook)
        } else {
            throw IllegalStateException("Hook is already initialized for activity: \"$name\"")
        }
    }

    private inner class LocalVisitor<D, R>(private val delegate: DataContainer.Visitor<D, R>) : DataContainer.Visitor<D, R> {
        override fun visitOk(v: DataContainer.Ok<D>): R {
            return delegate.visitOk(v)
        }

        override fun visitPending(v: DataContainer.Pending<D>): R {
            return delegate.visitPending(v)
        }

        override fun visitError(v: DataContainer.Error<D>): R {
            v.er.exec(pendingErrorsVisitor)
            return delegate.visitError(v)
        }
    }

    fun <D, R> wrapVisitor(v: DataContainer.Visitor<D, R>): DataContainer.Visitor<D, R> {
        return LocalVisitor<D, R>(v)
    }

    private val pendingErrorsVisitor = object : ErrorWrap.Visitor {
        override fun visitTextError(v: ErrorWrap.TextError) {}
        override fun visitDataError(v: ErrorWrap.DataError) {}
        override fun visitNoInternetConnection(v: ErrorWrap.NoInternetConnection) {}
        override fun visitNotAvailable(v: ErrorWrap.NotAvailable) {}

        override fun visitNoPermission(v: ErrorWrap.NoPermission) {
            Timber.d("DBG: DataHook: NoPermission: $v")
            hook.processPermissionError(v)
        }

        override fun visitSyntheticContainer(v: ErrorWrap.SyntheticContainer) {
            Timber.d("DBG: DataHook: SyntheticContainer: ${v.children.contentToString()}")
            for (er in v.children) {
                val permError = er.execR(noPermissionExtractingVisitor)
                if (permError != null) {
                    hook.processPermissionError(permError)
                }
            }
        }
    }

    private val noPermissionExtractingVisitor = object : ErrorWrap.VisitorR<ErrorWrap.NoPermission?> {
        override fun visitRTextError(v: ErrorWrap.TextError): ErrorWrap.NoPermission? = null
        override fun visitRDataError(v: ErrorWrap.DataError): ErrorWrap.NoPermission? = null
        override fun visitRNoInternetConnection(v: ErrorWrap.NoInternetConnection): ErrorWrap.NoPermission? = null
        override fun visitRNotAvailable(v: ErrorWrap.NotAvailable): ErrorWrap.NoPermission? = null
        override fun visitRNoPermission(v: ErrorWrap.NoPermission): ErrorWrap.NoPermission? = v
        override fun visitRSyntheticContainer(v: ErrorWrap.SyntheticContainer): ErrorWrap.NoPermission? = null
    }

    private class HookImpl(val name: String, private val act: AppCompatActivity) : LifecycleObserver {
        private val permissionRequester = act.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { handlePermissionRequestResult(it) }
        private val resultRequester = act.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { handleActivityResult(it) }
        private val permissionCalls: Multimap<String, ErrorWrap.NoPermission> = ArrayListMultimap.create()

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun actionOnCreate() {
            Timber.d("DBG: actionOnCreate(): name: \"$name\"")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        fun actionOnStart() {
            Timber.d("actionOnStart(): name: \"$name\"")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun actionOnResume() {
            Timber.d("actionOnResume(): name: \"$name\"")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun actionOnPause() {
            Timber.d("actionOnPause(): name: \"$name\"")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun actionOnStop() {
            Timber.d("actionOnStop(): name: \"$name\"")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun actionOnDestroy() {
            Timber.d("actionOnDestroy(): name: \"$name\"")
        }

        fun processPermissionError(e: ErrorWrap.NoPermission) {
            Timber.i("DBG: DataHook: processPermissionError(): $e")
            val perm = extractPermissionName(e) ?: return // TODO log or throw
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val shouldShowRationale = act.shouldShowRequestPermissionRationale(perm)
                Timber.i("DBG: DataHook: processPermissionError(): perm: \"$perm\", need rationale: $shouldShowRationale")
                // TODO show rationale and wait until user closes it
            } else {
                Timber.i("DBG: DataHook: processPermissionError(): perm: \"$perm\"")
            }

//            if (!Manifest.permission.SYSTEM_ALERT_WINDOW.equals(perm)) {
                permissionCalls.put(perm, e)
                permissionRequester.launch(arrayOf(perm))
//            } else {
//                handleSystemAlertWindowPermission(e)
//            }
        }

        private fun handleSystemAlertWindowPermission(e: ErrorWrap.NoPermission) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val uri = Uri.parse("package: ${act.packageName}")
                val permRequest = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri)
                resultRequester.launch(permRequest)
            } else {
                val perm = Manifest.permission.SYSTEM_ALERT_WINDOW
                permissionCalls.put(perm, e)
                permissionRequester.launch(arrayOf(perm))
            }
        }

        private fun handlePermissionRequestResult(res: Map<String, Boolean>) {
            Timber.i("DBG: DataHook: handlePermissionRequestResult(): result: \"$res\"")
            for ((perm, isGranted) in res) {
                val errors = permissionCalls.removeAll(perm) // remove from storage even if permission is not granted
                if (isGranted) {
                    for (e in errors) {
                        e.retry(Bundle())
                    }
                }
            }
        }

        private fun handleActivityResult(r: ActivityResult) {
            Timber.i("DBG: handleActivityResult(): result: $r")
        }
    }

}