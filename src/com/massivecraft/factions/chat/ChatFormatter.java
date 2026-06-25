/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package com.massivecraft.factions.chat;

import com.massivecraft.factions.chat.ChatTag;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.command.CommandSender;

public class ChatFormatter {
    public static final String START = "{";
    public static final String END = "}";
    public static final String SEPARATOR = "|";
    public static final String ESC_START = "\\{";
    public static final String ESC_END = "\\}";
    public static final String ESC_SEPARATOR = "\\|";
    public static final Pattern pattern = Pattern.compile("\\{([^\\{\\}]+)\\}");

    public static String format(String msg, CommandSender sender, CommandSender recipient) {
        StringBuffer ret = new StringBuffer();
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            String replacement;
            String fullmatch = matcher.group(0);
            String submatch = matcher.group(1);
            String[] parts = submatch.split(ESC_SEPARATOR);
            ArrayList<String> modifierIds = new ArrayList<String>(Arrays.asList(parts));
            String tagId = (String)modifierIds.remove(0);
            ChatTag tag = ChatTag.getTag(tagId);
            if (tag == null) {
                replacement = fullmatch;
            } else {
                replacement = ChatFormatter.compute(tag, modifierIds, sender, recipient);
                if (replacement == null) {
                    replacement = fullmatch;
                }
            }
            matcher.appendReplacement(ret, replacement);
        }
        matcher.appendTail(ret);
        return ret.toString();
    }

    public static String compute(ChatTag tag, List<String> modifierIds, CommandSender sender, CommandSender recipient) {
        String ret = tag.getReplacement(sender, recipient);
        if (ret == null) {
            return null;
        }
        return ret;
    }
}

