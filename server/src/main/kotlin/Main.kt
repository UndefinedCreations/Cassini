import com.undefined.stellar.StellarCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        StellarCommand("menu")
            .addArgument("open")
            .addExecution<Player> {

            }
            .register(this)
    }

}