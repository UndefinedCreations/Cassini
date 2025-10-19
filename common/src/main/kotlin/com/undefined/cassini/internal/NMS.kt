package com.undefined.cassini.internal

import com.google.gson.JsonElement
import com.undefined.cassini.data.MenuType
import com.undefined.cassini.data.ServerLink
import com.undefined.cassini.internal.listener.PacketHandler
import net.kyori.adventure.text.Component
import org.bukkit.Server
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

interface NMS {
//    fun createChestMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper
//    fun createAnvilMenu(player: Player, size: Int, title: Component, config: MenuConfig): AnvilMenuWrapper
//    fun createSmithingMenu(player: Player, size: Int, title: Component, config: MenuConfig): MenuWrapper
//    fun sendContainerClosePacket(player: Player, wrapper: MenuWrapper)

    /**
     * Send the [player], an open screen packet with [type] and [title].
     *
     * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Open_Screen">Protocol Reference</a>
     */
    fun sendOpenScreenPacket(player: Player, type: MenuType, title: Component)

    /**
     * Send the [player], a set container content packet with [contents].
     *
     * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Set_Container_Content">Protocol Reference</a>
     */
    fun sendContentsPacket(player: Player, contents: List<ItemStack>)
//    fun sendOpenBookPacket(player: Player, book: ItemStack)
//    fun setContainerMenu(player: Player, wrapper: MenuWrapper)
//    fun resetContainerMenu(player: Player)
//    fun initMenu(player: Player, wrapper: MenuWrapper)

    /**
     * Get the next container id for [player].
     */
    fun getContainerId(player: Player): Int

    /**
     * Initialize the NMS packet listener.
     */
    fun initializePacketListener(plugin: JavaPlugin, listener: PacketHandler)

    /**
     * Encode the [item] into a [JsonElement].
     */
    fun encodeItemStack(item: ItemStack): JsonElement

    /**
     * Shows a dialog to [player] from NBT.
     *
     * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Show_Dialog_(play)">Protocol Reference</a>
     */
    fun showDialog(player: Player, json: JsonElement)

    /**
     * Modifies the server links in NMS.
     */
    fun setServerLinks(server: Server, serverLinks: Collection<ServerLink>)

    /**
     * Sets a list of server links on the server and sends that list to the player.
     *
     * @see <a href="https://minecraft.wiki/w/Java_Edition_protocol/Packets#Server_Links">Protocol Reference</a>
     */
    fun sendServerLinks(player: Player, serverLinks: Collection<ServerLink>)

}