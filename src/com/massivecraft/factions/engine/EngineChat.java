/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.massivecraft.massivecore.Engine
 *  com.massivecraft.massivecore.event.EventMassiveCorePlayerToRecipientChat
 *  com.massivecraft.massivecore.util.MUtil
 *  org.bukkit.Bukkit
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Event
 *  org.bukkit.event.EventException
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.AsyncPlayerChatEvent
 *  org.bukkit.plugin.EventExecutor
 *  org.bukkit.plugin.Plugin
 */
package com.massivecraft.factions.engine;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.chat.ChatFormatter;
import com.massivecraft.factions.entity.MConf;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.event.EventMassiveCorePlayerToRecipientChat;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

public class EngineChat
extends Engine {
    private static EngineChat i = new EngineChat();

    public static EngineChat get() {
        return i;
    }

    public EngineChat() {
        this.setPlugin(Factions.get());
    }

    public void setActiveInner(boolean active) {
        if (!active) {
            return;
        }
        Bukkit.getPluginManager().registerEvent(AsyncPlayerChatEvent.class, (Listener)this, MConf.get().chatSetFormatAt, (EventExecutor)new SetFormatEventExecutor(), (Plugin)Factions.get(), true);
        Bukkit.getPluginManager().registerEvent(AsyncPlayerChatEvent.class, (Listener)this, MConf.get().chatParseTagsAt, (EventExecutor)new ParseTagsEventExecutor(), (Plugin)Factions.get(), true);
        Bukkit.getPluginManager().registerEvent(EventMassiveCorePlayerToRecipientChat.class, (Listener)this, EventPriority.NORMAL, (EventExecutor)new ParseRelcolorEventExecutor(), (Plugin)Factions.get(), true);
    }

    public static void setFormat(AsyncPlayerChatEvent event) {
        event.setFormat(MConf.get().chatSetFormatTo);
    }

    public static void parseTags(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (MUtil.isntPlayer((Object)player)) {
            return;
        }
        String format = event.getFormat();
        format = ChatFormatter.format(format, (CommandSender)player, null);
        event.setFormat(format);
    }

    public static void parseRelcolor(EventMassiveCorePlayerToRecipientChat event) {
        String format = event.getFormat();
        format = ChatFormatter.format(format, (CommandSender)event.getSender(), event.getRecipient());
        event.setFormat(format);
    }

    private class ParseRelcolorEventExecutor
    implements EventExecutor {
        private ParseRelcolorEventExecutor() {
        }

        public void execute(Listener listener, Event event) throws EventException {
            try {
                if (!(event instanceof EventMassiveCorePlayerToRecipientChat)) {
                    return;
                }
                EngineChat.parseRelcolor((EventMassiveCorePlayerToRecipientChat)event);
            }
            catch (Throwable t) {
                throw new EventException(t);
            }
        }
    }

    private class ParseTagsEventExecutor
    implements EventExecutor {
        private ParseTagsEventExecutor() {
        }

        public void execute(Listener listener, Event event) throws EventException {
            try {
                if (!(event instanceof AsyncPlayerChatEvent)) {
                    return;
                }
                EngineChat.parseTags((AsyncPlayerChatEvent)event);
            }
            catch (Throwable t) {
                throw new EventException(t);
            }
        }
    }

    private class SetFormatEventExecutor
    implements EventExecutor {
        private SetFormatEventExecutor() {
        }

        public void execute(Listener listener, Event event) throws EventException {
            try {
                if (!(event instanceof AsyncPlayerChatEvent)) {
                    return;
                }
                EngineChat.setFormat((AsyncPlayerChatEvent)event);
            }
            catch (Throwable t) {
                throw new EventException(t);
            }
        }
    }
}

