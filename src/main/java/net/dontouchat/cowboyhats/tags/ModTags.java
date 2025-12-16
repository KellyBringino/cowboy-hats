package net.dontouchat.cowboyhats.tags;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

@SuppressWarnings("removal")
public class ModTags {
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(CowboyHatsMod.MODID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> COWBOY_HATS = tag("cowboyhat");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(CowboyHatsMod.MODID, name));
        }
    }
}