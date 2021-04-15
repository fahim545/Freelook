package studio.fractures.mcmods.freelook.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.fractures.mcmods.freelook.Freelook;
import studio.fractures.mcmods.freelook.models.EntityWithFreecam;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    protected abstract void moveBy(double x, double y, double z);

    @Shadow
    protected abstract double clipToSpace(double desiredCameraDistance);

    @Shadow
    protected void setPos(double x, double y, double z) {
        this.setPos(new Vec3d(x, y, z));
    }

    @Shadow
    protected abstract void setPos(Vec3d pos);

    @Shadow
    private float cameraY;

    @Shadow
    private float lastCameraY;

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", ordinal = 0, shift = At.Shift.AFTER))
    public void freeRotate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (Freelook.isFreecam && focusedEntity instanceof ClientPlayerEntity) {
            EntityWithFreecam entitywithFreecam = (EntityWithFreecam) focusedEntity;
            MinecraftClient client = MinecraftClient.getInstance();
            this.setRotation(entitywithFreecam.getCameraYaw(), entitywithFreecam.getCameraPitch());
        }
    }

    @Inject(method = "update", at = @At(value = "TAIL"))
    public void outOfBody(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (Freelook.isFreecam && focusedEntity instanceof ClientPlayerEntity) {
            MinecraftClient.getInstance().options.setPerspective(Perspective.THIRD_PERSON_BACK);
        }
    }
}
