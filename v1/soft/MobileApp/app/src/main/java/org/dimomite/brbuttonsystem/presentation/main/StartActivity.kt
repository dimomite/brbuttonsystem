package org.dimomite.brbuttonsystem.presentation.main

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.dimomite.brbuttonsystem.R
import org.dimomite.brbuttonsystem.data.remotecontrol.RemoteControlRepository
import org.dimomite.brbuttonsystem.presentation.hooks.DataHook
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {
    private val hook = DataHook()

    @Inject
    lateinit var remoteRepo: RemoteControlRepository

    private val subs = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)
        val toolbar: Toolbar = findViewById(R.id.start_nav_toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)

        hook.registerInActivity(this)

        subs.add(remoteRepo.remoteControlEvents().outFlow().subscribe({ events ->
//            events.exec(hook.wrapVisitor(object : DataContainer.Visitor<RemoteControlModel, Unit> {
//                override fun visitOk(v: DataContainer.Ok<RemoteControlModel>) {
//                    Timber.i("DBG: StartActivity: remoteControlEvents(): Ok: data: ${v.data}")
//                }
//
//                override fun visitPending(v: DataContainer.Pending<RemoteControlModel>) {
//                    Timber.i("DBG: StartActivity: remoteControlEvents(): Pending: progress: ${v.progress}")
//                }
//
//                override fun visitError(v: DataContainer.Error<RemoteControlModel>) {
//                    Timber.w("DBG: StartActivity: remoteControlEvents(): Error: er: ${v.er}")
//                }
//            }))
        }, {
            Timber.w("DBG: StartActivity: remoteControlEvents(): error in data flow: $it")
        }))

    }

}
