package org.dimomite.brbuttonsystem

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

class MainApp : Application() {
    override fun start(primaryStage: Stage?) {
        val res = ResourceBundle.getBundle("org.dimomite.brbuttonsystem.strings")
        val loader = FXMLLoader(javaClass.getResource("main_scene.fxml"), res)
        val root: Parent = loader.load()

        val scene = Scene(root)
        if (primaryStage != null) {
            primaryStage.title = res.getString("main.appname")
            primaryStage.scene = scene
            primaryStage.show()
        }
    }

    fun doLaunch(args: Array<String>) {
        launch(*args)
    }

}