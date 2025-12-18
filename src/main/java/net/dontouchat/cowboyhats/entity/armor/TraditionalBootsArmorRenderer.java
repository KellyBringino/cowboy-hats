package net.dontouchat.cowboyhats.entity.armor;

import net.dontouchat.cowboyhats.item.custom.CattlemanArmorItem;
import net.dontouchat.cowboyhats.item.custom.TraditionalBootsArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class TraditionalBootsArmorRenderer extends GeoArmorRenderer<TraditionalBootsArmorItem> {
    public TraditionalBootsArmorRenderer() {
        super(new TraditionalBootsArmorModel());
    }
}