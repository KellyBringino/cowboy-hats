package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import net.dontouchat.cowboyhats.item.custom.TraditionalBootsArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class TraditionalBootsArmorModel extends GeoModel<TraditionalBootsArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/traditionalboots.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/traditionalboots.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(TraditionalBootsArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(TraditionalBootsArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(TraditionalBootsArmorItem animatable) {
        return this.animations;
    }
}
