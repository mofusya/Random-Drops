package net.mofusya.randomrandomdrops.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.randomrandomdrops.component.ModComponents;
import net.mofusya.randomrandomdrops.config.ModConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin({ItemEntity.class})
public class ItemEntityMixin {

    @Inject(method = "setItem", at = @At("HEAD"), cancellable = true)
    private void setItem(ItemStack itemStack, CallbackInfo ci) {
        if (ModComponents.HAS_BEEN_MODIFIED.get(itemStack)) {
            ModComponents.HAS_BEEN_MODIFIED.set(itemStack, false);
            return;
        }

        ModConfigs.COMMON.load();
        JsonObject json = ModConfigs.COMMON.get();
        int r = Mth.nextInt(RandomSource.create(), 0, 100);
        if (r <= GsonHelper.getAsInt(json, "item_change_percentage")) {
            Item item = null;
            ItemStack modItemStack = null;
            List<Rarity> excludeRarities = GsonHelper.getAsJsonArray(json, "exclude_rarities").asList().stream().map(JsonElement::getAsString).map(Rarity::valueOf).toList();
            List<Item> excludeItems = GsonHelper.getAsJsonArray(json, "exclude_items").asList().stream().map(JsonElement::getAsString).map(itemId -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId))).toList();
            for (int i = 0; i < 200; i++) {
                if (modItemStack != null) {
                    if (!excludeRarities.contains(modItemStack.getRarity()) &&
                            !excludeItems.contains(modItemStack.getItem()) &&
                            !(item instanceof SpawnEggItem)
                    ) break;
                }

                List<Item> allItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
                int itemIndexR = Mth.nextInt(RandomSource.create(), 0, allItems.size());
                item = allItems.get(itemIndexR);
                modItemStack = new ItemStack(item, itemStack.getCount());
            }

            ModComponents.HAS_BEEN_MODIFIED.set(modItemStack, true);
            ((ItemEntity) (Object) this).setItem(modItemStack);
            ci.cancel();
        }
    }
}
