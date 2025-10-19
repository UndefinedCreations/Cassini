import com.undefined.cassini.CassiniConfig
import com.undefined.cassini.data.ServerLink
import com.undefined.cassini.internal.MojangAdapter
import com.undefined.cassini.util.setServerLinks
import com.undefined.stellar.StellarCommand
import io.papermc.paper.dialog.Dialog
import io.papermc.paper.registry.data.dialog.ActionButton
import io.papermc.paper.registry.data.dialog.DialogBase
import io.papermc.paper.registry.data.dialog.action.DialogAction
import io.papermc.paper.registry.data.dialog.input.DialogInput
import io.papermc.paper.registry.data.dialog.input.NumberRangeDialogInput
import io.papermc.paper.registry.data.dialog.type.DialogType
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.net.URI
import java.util.List


class Main : JavaPlugin(), Listener {

    @Suppress("UnstableApiUsage")
    override fun onEnable() {
        server.pluginManager.registerEvents(this, this)
        CassiniConfig.initialize(this)

        StellarCommand("menu")
            .then("links") {
                addExecution<Player> {
                    sender.setServerLinks(listOf(
                        ServerLink.KnownLink(ServerLink.Type.ANNOUNCEMENTS, URI.create("https://github.com/undefinedcreations")),
                        ServerLink.KnownLink(ServerLink.Type.BUG_REPORT, URI.create("https://discord.undefinedcreations.com")),
                        ServerLink.Custom(!"<red>test!!", URI.create("https://discord.undefinedcreations.com")),
                    ))
                }
            }
            .then("open") {
                addExecution<Player> {
                    println(TestMenu().open(sender))
                }
            }
            .register(this)
    }

}

inline fun <reified T : Entity> World.spawn(location: Location): T = spawn(location, T::class.java)
operator fun String.not(): Component = MiniMessage.miniMessage().deserialize(this)