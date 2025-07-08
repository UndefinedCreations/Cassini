import com.undefined.cassini.Cassini
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("menu")
            .addArgument("open")
            .addExecution<Player> {
                sender.openMenu(TestMenu())
            }
            .register(this)
    }

}