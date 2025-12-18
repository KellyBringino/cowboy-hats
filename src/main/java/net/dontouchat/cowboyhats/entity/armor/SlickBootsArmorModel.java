package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.SlickBootsArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

@SuppressWarnings(value = "removal")
public class SlickBootsArmorModel extends GeoModel<SlickBootsArmorItem> {
    private final ResourceLocation model = new ResourceLocation(CowboyHatsMod.MODID, "geo/slickboots.geo.json");
    private final ResourceLocation texture = new ResourceLocation(CowboyHatsMod.MODID, "textures/models/armor/slickboots.png");
    private final ResourceLocation animations = new ResourceLocation(CowboyHatsMod.MODID, "animations/armor_animation.json");

    @Override
    public ResourceLocation getModelResource(SlickBootsArmorItem animatable) {
        return this.model;
    }

    @Override
    public ResourceLocation getTextureResource(SlickBootsArmorItem animatable) {
        return this.texture;
    }

    @Override
    public ResourceLocation getAnimationResource(SlickBootsArmorItem animatable) {
        return this.animations;
    }
}
