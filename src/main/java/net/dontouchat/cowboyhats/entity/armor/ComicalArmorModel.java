package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import net.dontouchat.cowboyhats.item.custom.ComicalArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class ComicalArmorModel extends GeoModel<ComicalArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/comical.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/comical.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(ComicalArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(ComicalArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(ComicalArmorItem animatable) {
        return this.animations;
    }
}
