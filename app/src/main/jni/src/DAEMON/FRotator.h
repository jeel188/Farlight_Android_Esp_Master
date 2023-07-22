//
// Created by Patel on 22-07-2023.
//

#ifndef COSMIC_ESP_PUBM_2_0_0_32BIT_FROTATOR_H
#define COSMIC_ESP_PUBM_2_0_0_32BIT_FROTATOR_H
struct FRotator {
    float Pitch;
    float Yaw;
    float Roll;
};

FRotator CalcAngle(Vector3 LocalHeadPosition, Vector3 AimPosition)
{
    Vector3 rotation = LocalHeadPosition - AimPosition;
    float hyp = sqrt(rotation.X * rotation.X + rotation.Y * rotation.Y);

    FRotator newAngle = FRotator();

    newAngle.Pitch = (-atan(rotation.Z / hyp) * (180.0f /(float) 3.14159265358979323846));
    newAngle.Yaw = (atan(rotation.Y / rotation.X) * (180.0f /(float) 3.14159265358979323846));
    newAngle.Roll = 0.0f;

    if (rotation.X >= 0.0f)
        newAngle.Yaw += 180.0f;

    return newAngle;
}

#endif //COSMIC_ESP_PUBM_2_0_0_32BIT_FROTATOR_H
