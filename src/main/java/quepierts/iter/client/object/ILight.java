package quepierts.iter.client.object;

import org.joml.Vector3f;

public interface ILight {
    Vector3f getLightPosition();
    Vector3f getLightColor();
    Vector3f getLightSpecular();
    float getLightStrength();

    void setLightPosition(Vector3f vector3f);
    void setLightColor(Vector3f vector3f);
    void setLightSpecular(Vector3f vector3f);
    void setLightStrength(float strength);

    void set(ILight light);
}
