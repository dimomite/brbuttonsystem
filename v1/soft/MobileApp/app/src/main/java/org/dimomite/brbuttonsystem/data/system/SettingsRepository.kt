package org.dimomite.brbuttonsystem.data.system

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.dimomite.brbuttonsystem.domain.common.*
import org.dimomite.brbuttonsystem.domain.models.AppSettingsModel
import org.dimomite.brbuttonsystem.domain.models.ControlOrientation
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(@ApplicationContext ctx: Context) : DataRepository<AppSettingsModel> {
    companion object {
        private const val SETTING_PREFERENCES_NAME = "SettingPreferences"

        private const val FIELD_SETTINGS_CONTAINER_STATE = "SettingsDataContainerState"
        private const val FIELD_NOTIFICATION_CONTROL_ENABLED = "NotificationControlEnabled"
        private const val FIELD_FLOATING_CONTROL_ENABLED = "FloatingControlEnabled"
        private const val FIELD_WIDGET_CONTROL_ENABLED = "WidgetControlEnabled"
        private const val FIELD_CONTROL_ORIENTATION = "ControlOrientation"
        private const val FIELD_ERROR_TEXT = "ErrorText"

        val initialSettings = AppSettingsModel(
            isNotificationControlEnabled = true,
            isFloatingControlEnabled = false,
            isWidgetControlEnabled = true,
            controlOrientation = ControlOrientation.Default,
        )
    }

    private val shared = ctx.getSharedPreferences(SETTING_PREFERENCES_NAME, Context.MODE_PRIVATE)

    //    private val settings = BehaviorSubject.createDefault<DataContainer<AppSettingsModel>>(DataContainer.Ok(initialSettings))
    private val settings = BehaviorSubject.create<DataContainer<AppSettingsModel>>()
    private val settingsFlow = settings.toFlowable(BackpressureStrategy.LATEST).distinctUntilChanged().replay(1).refCount()
    private val settingsProvider = object : DataProvider<AppSettingsModel> {
        override fun outFlow(): Flowable<DataContainer<AppSettingsModel>> = settingsFlow
    }

    private val subs = CompositeDisposable()

    private val patcher = object : DataModifier<AppSettingsModel> {
        override fun modifyData(patch: Function<AppSettingsModel, AppSettingsModel>): Single<Unit> {
            val mapper = DataContainerConverter(patch)
            val current = settings.value
            val next: DataContainer<AppSettingsModel>
            try {
                next = current.exec(mapper)
            } catch (e: Exception) {
                return Single.error(e)
            }
            settings.onNext(next)
            return Single.just(Unit)
        }
    }

    override fun provider(): DataProvider<AppSettingsModel> = settingsProvider
    override fun modifier(): DataModifier<AppSettingsModel> = patcher

    init {
        Timber.d("Setting repo created")
        subs.add(settingsFlow.skip(1).subscribe { saveSettings(it) }) // skip first one which is a saved value

        val sett = readSetting(shared)
        Timber.d("Setting are read: $sett")
        settings.onNext(sett)
    }

    private fun saveSettings(sett: DataContainer<AppSettingsModel>) {
        shared.edit {
            putString(FIELD_SETTINGS_CONTAINER_STATE, sett.stateName())

            sett.exec(object : DataContainer.Visitor<AppSettingsModel, Unit> {
                override fun visitOk(v: DataContainer.Ok<AppSettingsModel>) {
                    putString(FIELD_ERROR_TEXT, "")

                    putBoolean(FIELD_NOTIFICATION_CONTROL_ENABLED, v.data.isNotificationControlEnabled)
                    putBoolean(FIELD_FLOATING_CONTROL_ENABLED, v.data.isFloatingControlEnabled)
                    putBoolean(FIELD_WIDGET_CONTROL_ENABLED, v.data.isWidgetControlEnabled)
                    putString(FIELD_CONTROL_ORIENTATION, v.data.controlOrientation.name)
                }

                override fun visitPending(v: DataContainer.Pending<AppSettingsModel>) {
                    wipeContent(this@edit)
                }

                override fun visitError(v: DataContainer.Error<AppSettingsModel>) {
                    wipeContent(this@edit)

                    putString(FIELD_ERROR_TEXT, v.er.desc) // TODO Need to safe all errors or ignore completely
                }
            })
        }
        Timber.d("Saved settings: $sett")
    }

    private fun wipeContent(editor: SharedPreferences.Editor) {
        editor.putString(FIELD_ERROR_TEXT, "")

        editor.putBoolean(FIELD_NOTIFICATION_CONTROL_ENABLED, false)
        editor.putBoolean(FIELD_FLOATING_CONTROL_ENABLED, false)
        editor.putBoolean(FIELD_WIDGET_CONTROL_ENABLED, false)
        editor.putString(FIELD_CONTROL_ORIENTATION, ControlOrientation.Default.name)
    }

    private fun readSetting(sp: SharedPreferences): DataContainer<AppSettingsModel> {
        val typeText = sp.getString(FIELD_SETTINGS_CONTAINER_STATE, "")
        return when (typeText) {
            DataContainer.NAME_PENDING -> DataContainer.Pending(PendingProgress.ins())
            DataContainer.NAME_ERROR -> DataContainer.Error(ErrorWrap.TextError(ErrorWrap.UNDEFINED_ID, sp.getString(FIELD_ERROR_TEXT, "") ?: ""))
            DataContainer.NAME_OK -> {
                val sett = AppSettingsModel(
                    isNotificationControlEnabled = sp.getBoolean(FIELD_NOTIFICATION_CONTROL_ENABLED, false),
                    isFloatingControlEnabled = sp.getBoolean(FIELD_FLOATING_CONTROL_ENABLED, false),
                    isWidgetControlEnabled = sp.getBoolean(FIELD_WIDGET_CONTROL_ENABLED, false),
                    controlOrientation = ControlOrientation.valueOf(sp.getString(FIELD_CONTROL_ORIENTATION, "") ?: "")
                )
                DataContainer.Ok(sett)
            }
            else -> DataContainer.Ok(initialSettings)
        }
    }

}
