package studio.fractures.mcmods.freelook.models;

public interface EntityWithFreecam {
    float getCameraPitch();
    float getCameraYaw();

    void setCameraPitch(float pitch);
    void setCameraYaw(float yaw);
}
