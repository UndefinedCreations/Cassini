import com.undefined.cassini.data.dialog.AfterAction
import com.undefined.cassini.data.dialog.MultiLineOptions
import com.undefined.cassini.element.dialog.input.TextDialogInput
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import org.bukkit.entity.Player

class TestDialogMenu : NoticeDialogMenu(!"test") {

    override fun initialize(player: Player) {
        settings.canCloseWithEscape = false
        settings.afterAction = AfterAction.CLOSE
        player.sendMessage("test")

        inputContainer.addElement(
            TextDialogInput(
                key = "input",
                label = !"Input",
                initial = "testing",
                multiline = MultiLineOptions(
                    height = 64,
                    maxLines = 6,
                ),
            )
        )
    }

}