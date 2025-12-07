package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import net.dontouchat.cowboyhats.item.custom.ComicalArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ComicalArmorRenderer extends GeoArmorRenderer<ComicalArmorItem> {
    public ComicalArmorRenderer() {
        super(new ComicalArmorModel());
    }
}