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
import org.dimomite.brbuttonsystem.domain.channels.*
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

    private inner class LocalDataChannelVisitor<D>(private val delegate: DataChannel.Visitor<D>) : DataChannel.Visitor<D> {
        override fun visitDataWrap(v: DataWrap<D>) {
            delegate.visitDataWrap(v)
        }

        override fun visitProgressWrap(v: ProgressWrap) {
            delegate.visitProgressWrap(v)
        }

        override fun visitUnhandledState(v: UnhandledState<*>) {
            if (!hook.processUnhandledState(v)) {
                delegate.visitUnhandledState(v)
            }
        }
    }

    private inner class LocalChannelDataHandler<D, R>(private val delegate: ChannelDataHandler<D, R>) : ChannelDataHandler<D, R> {
        override fun onData(data: D): R = delegate.onData(data)
        override fun onNothing(): R = delegate.onNothing()
    }

    private inner class LocalProgressWrapVisitor(private val delegate: ProgressWrap.Visitor) : ProgressWrap.Visitor {
        override fun visitReady(v: ProgressWrap.Ready) {
            delegate.visitReady(v)
        }

        override fun visitInProgress(v: ProgressWrap.InProgress) {
            delegate.visitInProgress(v)
        }

        override fun visitFlagProgress(v: ProgressWrap.FlagProgress) {
            delegate.visitFlagProgress(v)
        }

        override fun visitToValueProgress(v: ProgressWrap.ToValueProgress) {
            delegate.visitToValueProgress(v)
        }

        override fun visitComposite(v: ProgressWrap.Composite) {
            delegate.visitComposite(v)
        }
    }

    private inner class LocalProgressWrapVisitorR<R>(private val delegate: ProgressWrap.VisitorR<R>) : ProgressWrap.VisitorR<R> {
        override fun visitReady(v: ProgressWrap.Ready): R = delegate.visitReady(v)
        override fun visitInProgress(v: ProgressWrap.InProgress): R = delegate.visitInProgress(v)
        override fun visitFlagProgress(v: ProgressWrap.FlagProgress): R = delegate.visitFlagProgress(v)
        override fun visitToValueProgress(v: ProgressWrap.ToValueProgress): R = delegate.visitToValueProgress(v)
        override fun visitComposite(v: ProgressWrap.Composite): R = delegate.visitComposite(v)
    }

    private inner class LocalUnhandledStateVisitor<E>(private val delegate: UnhandledState.Visitor<E>) : UnhandledState.Visitor<E> {
        override fun visitNotExist(v: UnhandledState.NotExist<E>) {
            delegate.visitNotExist(v)
        }

        override fun visitNotAvailable(v: UnhandledState.NotAvailable<E>) {
            delegate.visitNotAvailable(v)
        }

        override fun visitNotAllowed(v: UnhandledState.NotAllowed<E>) {
            delegate.visitNotAllowed(v)
        }

        override fun visitDataError(v: UnhandledState.DataError<E>) {
            delegate.visitDataError(v)
        }
    }

    fun <D> wrapVisitor(v: DataChannel.Visitor<D>): DataChannel.Visitor<D> {
        return LocalDataChannelVisitor<D>(v)
    }

    // name is not "wrapChannelDataHandler" for consistency and easier use
    fun <D, R> wrapVisitor(dh: ChannelDataHandler<D, R>): ChannelDataHandler<D, R> {
        return LocalChannelDataHandler(dh)
    }

    fun wrapVisitor(v: ProgressWrap.Visitor): ProgressWrap.Visitor {
        return LocalProgressWrapVisitor(v)
    }

    fun <R> wrapVisitor(v: ProgressWrap.VisitorR<R>): ProgressWrap.VisitorR<R> {
        return LocalProgressWrapVisitorR<R>(v)
    }

    fun <D> wrapVisitor(v: UnhandledState.Visitor<D>): UnhandledState.Visitor<D> {
        return LocalUnhandledStateVisitor<D>(v)
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

        /**
         * @return true when unhandled state was fully processed and it can be removed from processing by next layer(s).
         */
        fun processUnhandledState(us: UnhandledState<*>): Boolean {
            return false
        }

//        fun processPermissionError(e: UnhandledState.NotAllowed<*>) {
//            Timber.i("DBG: DataHook: processPermissionError(): $e")
//            val perm = extractPermissionName(e) ?: return // TODO log or throw
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                val shouldShowRationale = act.shouldShowRequestPermissionRationale(perm)
//                Timber.i("DBG: DataHook: processPermissionError(): perm: \"$perm\", need rationale: $shouldShowRationale")
//                // TODO show rationale and wait until user closes it
//            } else {
//                Timber.i("DBG: DataHook: processPermissionError(): perm: \"$perm\"")
//            }
//
////            if (!Manifest.permission.SYSTEM_ALERT_WINDOW.equals(perm)) {
//            permissionCalls.put(perm, e)
//            permissionRequester.launch(arrayOf(perm))
////            } else {
////                handleSystemAlertWindowPermission(e)
////            }
//        }

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