package net.dontouchat.cowboyhats.item.custom;

import net.dontouchat.cowboyhats.entity.armor.CattlemanArmorRenderer;
import net.dontouchat.cowboyhats.entity.armor.ComicalArmorRenderer;
import net.dontouchat.cowboyhats.item.ModItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Consumer;

public class ComicalArmorItem extends CowboyHatItem {
    public ComicalArmorItem(ArmorMaterial pMaterial, Type slot, Properties pProperties) {
        super(pMaterial,slot,pProperties,1);
    }

    @Override
    public ItemLike getArmoredVariant(boolean value) {
        if(value){
            return ModItems.ARMOREDCOMICAL.get();
        }else{
            return ModItems.COMICAL.get();
        }
    }

    // Create our armor model/renderer for forge and return it
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (this.renderer == null)
                    this.renderer = new ComicalArmorRenderer();

                // This prepares our GeoArmorRenderer for the current render frame.
                // These parameters may be null however, so we don't do anything further with them
                this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

                return this.renderer;
            }
        });
    }
}
