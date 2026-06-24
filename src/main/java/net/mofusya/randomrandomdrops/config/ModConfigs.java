package net.mofusya.randomrandomdrops.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.ornatelib.config.JsonConfig;
import net.mofusya.randomrandomdrops.C;

import java.util.List;

public class ModConfigs {

    public static final JsonConfig COMMON = new JsonConfig(C.MOD_ID, () -> {
        JsonObject defaultJson = new JsonObject();
        defaultJson.addProperty("item_change_percentage", 20);

        List<ItemLike> excludeItems = List.of(
                Blocks.END_PORTAL_FRAME,
                Blocks.BEDROCK,
                Items.TOTEM_OF_UNDYING,
                Items.ENDER_EYE,
                Items.BLAZE_ROD,
                Items.BLAZE_POWDER,
                Items.ENDER_PEARL
        );
        JsonArray excludeItemsArray = new JsonArray();
        excludeItems.stream().map(ItemLike::asItem).map(ForgeRegistries.ITEMS::getKey).map(ResourceLocation::toString).forEach(excludeItemsArray::add);
        defaultJson.add("exclude_items", excludeItemsArray);

        JsonArray excludeRaritiesArray = new JsonArray();
        excludeRaritiesArray.add(Rarity.RARE.toString());
        excludeRaritiesArray.add(Rarity.EPIC.toString());
        defaultJson.add("exclude_rarities", excludeRaritiesArray);
        return defaultJson;
    });
}
