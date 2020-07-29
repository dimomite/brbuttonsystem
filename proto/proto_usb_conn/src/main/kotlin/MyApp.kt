import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class MyApp : Application() {
    override fun start(stage: Stage?) {
        val loader = FXMLLoader(javaClass.getResource("scene.fxml"))
        val root: Parent = loader.load()

        val scene = Scene(root)
        scene.stylesheets.add(javaClass.getResource("styles.css").toExternalForm())

        if (stage != null) {
            stage.title = "JavaFX and Gradle"
            stage.scene = scene

            stage.setOnHidden { (loader.getController() as? FXMLController)?.release() }

            stage.show()
        }
    }

    fun doLaunch(args: Array<String>) {
        launch(*args)
    }

}

