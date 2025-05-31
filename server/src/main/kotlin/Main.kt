import com.undefined.cassini.menu.item.ChestMenu
import com.undefined.cassini.nms.NMSManager
import com.undefined.stellar.StellarCommand
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        StellarCommand("menu")
            .addArgument("open")
            .addExecution<Player> {
                NMSManager.nms.sendOpenContainerScreenPacket(sender)
            }
            .register(this)
    }

}