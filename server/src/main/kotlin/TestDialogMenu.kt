import com.undefined.cassini.data.dialog.AfterAction
import com.undefined.cassini.menu.dialog.NoticeDialogMenu
import org.bukkit.entity.Player

class TestDialogMenu : NoticeDialogMenu(!"test") {

    override fun initialize(player: Player) {
        settings.canCloseWithEscape = false
        settings.afterAction = AfterAction.NONE
        player.sendMessage("test")
    }

}