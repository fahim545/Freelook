package studio.fractures.mcmods.freelook.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import studio.fractures.mcmods.freelook.Freelook;
import studio.fractures.mcmods.freelook.models.EntityWithFreecam;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityWithFreecam {
    @Unique
    private float cameraPitch;

    @Unique
    private float cameraYaw;

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeCameraLookDirection(double xDelta, double yDelta, CallbackInfo ci) {
        if (!Freelook.isFreecam) return;

        double pitchDelta = (yDelta * 0.15);
        double yawDelta = (xDelta * 0.15);

        this.cameraPitch = MathHelper.clamp(this.cameraPitch + (float) pitchDelta, -90.0f, 90.0f);
        this.cameraYaw += (float) yawDelta;

        ci.cancel();
    }

    @Override
    @Unique
    public float getCameraPitch() {
        return this.cameraPitch;
    }

    @Override
    @Unique
    public float getCameraYaw() {
        return this.cameraYaw;
    }

    @Override
    @Unique
    public void setCameraPitch(float pitch) {
        this.cameraPitch = pitch;
    }

    @Override
    @Unique
    public void setCameraYaw(float yaw) {
        this.cameraYaw = yaw;
    }
}
