package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.DerbyArmorItem;
import net.dontouchat.cowboyhats.item.custom.GamblerArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class DerbyArmorModel extends GeoModel<DerbyArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/derby.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/derby.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(DerbyArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(DerbyArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(DerbyArmorItem animatable) {
        return this.animations;
    }
}