import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        StellarCommand("menu")
            .addArgument("open")
            .addExecution<Player> {
//                NMSManager.nms.sendOpenScreenPacket(sender, MenuType.CHEST_9X3)
                TestMenu().open(sender)
            }
            .register(this)
    }

}