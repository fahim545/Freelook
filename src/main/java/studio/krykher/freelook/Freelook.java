package studio.krykher.freelook;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import studio.krykher.freelook.models.EntityWithFreecam;

public class Freelook implements ClientModInitializer {
    public static boolean isFreecam = false;

    public static final KeyBinding FreecamKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.freelook.freecam", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, "key.categories.misc") {
        @Override
        public void setPressed(boolean pressed) {
            super.setPressed(pressed);

            MinecraftClient client = MinecraftClient.getInstance();
            EntityWithFreecam entityWithFreecam = (EntityWithFreecam) client.player;

            if (entityWithFreecam != null) {
                if (pressed) {
                    if (isFreecam) return;
                    isFreecam = true;
                    entityWithFreecam.setCameraPitch(client.player.getPitch());
                    entityWithFreecam.setCameraYaw(client.player.getYaw());
                } else {
                    isFreecam = false;
                    client.options.setPerspective(Perspective.FIRST_PERSON);
                }
            }
        }
    });

    @Override
    public void onInitializeClient() {

    }
}
