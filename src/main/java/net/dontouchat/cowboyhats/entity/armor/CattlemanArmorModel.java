package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class CattlemanArmorModel extends GeoModel<CattlemanArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/cattleman.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/cattleman.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(CattlemanArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(CattlemanArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(CattlemanArmorItem animatable) {
        return this.animations;
    }
}
