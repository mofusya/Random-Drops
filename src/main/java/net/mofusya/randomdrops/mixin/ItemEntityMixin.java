package net.mofusya.randomdrops.mixin;

import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.mofusya.randomdrops.component.ModComponents;
import net.mofusya.randomdrops.config.ModConfigs;
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
        int r = Mth.nextInt(RandomSource.create(), 0, 100);
        if (r <= GsonHelper.getAsInt(ModConfigs.COMMON.get(), "item_change_percentage")) {
            List<Item> allItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
            int itemIndexR = Mth.nextInt(RandomSource.create(), 0, allItems.size());

            ItemStack modItemStack = new ItemStack(allItems.get(itemIndexR), itemStack.getCount());
            ModComponents.HAS_BEEN_MODIFIED.set(modItemStack, true);
            ((ItemEntity) (Object) this).setItem(modItemStack);
            ci.cancel();
        }
    }
}
