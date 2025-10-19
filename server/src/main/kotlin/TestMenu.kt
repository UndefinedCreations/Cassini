import com.undefined.cassini.data.dialog.MultiLineOptions
import com.undefined.cassini.element.dialog.DialogButton
import com.undefined.cassini.element.dialog.input.TextDialogInput
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import org.bukkit.entity.Player

class TestMenu : NoticeDialogMenu(!"Change Lore", DialogButton(!"Confirm")) {

    override fun initialize(player: Player) {
        button.addAction { player, payload ->
            player.sendMessage(payload.getText("input"))
        }

        this.inputContainer.addElement(TextDialogInput(
            key = "input",
            label = !"Input",
            multiline = MultiLineOptions(
                height = 16,
            )
        ))
    }

}