import com.google.gson.JsonParser
import com.undefined.cassini.Cassini
import com.undefined.cassini.internal.NMSManager
import com.undefined.cassini.util.openMenu
import com.undefined.stellar.StellarCommand
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        Cassini.initialize(this)

        StellarCommand("menu")
            .addArgument("open")
            .addExecution<Player> {
                sender.openMenu(TestMenu())
//                NMSManager.nms.showDialog(sender, JsonParser.parseString("""
//                    {
//                      "type": "minecraft:dialog_list",
//                      "title": "Test",
//                      "external_title": "Test",
//                      "inputs": [],
//                      "can_close_with_escape": true,
//                      "dialogs": [
//                        {
//                          "type": "minecraft:notice",
//                          "title": "Test!"
//                        },
//                        {
//                          "type": "minecraft:notice",
//                          "title": "Test"
//                        }
//                      ]
//                    }
//                """.trimIndent()))
            }
            .register(this)
    }

}

operator fun String.not(): Component = MiniMessage.miniMessage().deserialize(this)