//
// Created by Patel on 22-07-2023.
//

#ifndef COSMIC_ESP_PUBM_2_0_0_32BIT_FTRANSFORM_H
#define COSMIC_ESP_PUBM_2_0_0_32BIT_FTRANSFORM_H
struct FTransform {
    Quaternion Rotation;
    Vector3 Translation;
    char pad[0x4];
    Vector3 Scale3D;
    D3DMatrix ToMatrixWithScale()
    {
        D3DMatrix m;
        m._41 = Translation.X;
        m._42 = Translation.Y;
        m._43 = Translation.Z;

        float x2 = Rotation.X + Rotation.X;
        float y2 = Rotation.Y + Rotation.Y;
        float z2 = Rotation.Z + Rotation.Z;

        float xx2 = Rotation.X * x2;
        float yy2 = Rotation.Y * y2;
        float zz2 = Rotation.Z * z2;
        m._11 = (1.0f - (yy2 + zz2)) * Scale3D.X;
        m._22 = (1.0f - (xx2 + zz2)) * Scale3D.Y;
        m._33 = (1.0f - (xx2 + yy2)) * Scale3D.Z;

        float yz2 = Rotation.Y * z2;
        float wx2 = Rotation.W * x2;
        m._32 = (yz2 - wx2) * Scale3D.Z;
        m._23 = (yz2 + wx2) * Scale3D.Y;

        float xy2 = Rotation.X * y2;
        float wz2 = Rotation.W * z2;
        m._21 = (xy2 - wz2) * Scale3D.Y;
        m._12 = (xy2 + wz2) * Scale3D.X;

        float xz2 = Rotation.X * z2;
        float wy2 = Rotation.W * y2;
        m._31 = (xz2 + wy2) * Scale3D.Z;
        m._13 = (xz2 - wy2) * Scale3D.X;

        m._14 = 0.0f;
        m._24 = 0.0f;
        m._34 = 0.0f;
        m._44 = 1.0f;

        return m;
    }
};

FTransform GetBoneTransform(uintptr_t entity, int idx) {
    uintptr_t Mesh = getMemoryAddr(entity + Offsets::Character::Mesh);
    if (Mesh) {
        uintptr_t Bones = getMemoryAddr(Mesh + Offsets::StaticMeshComponent::MinLOD);
        if (Bones) {
            return Read<FTransform>(Bones + (idx * 48));
        }
    }
    return {};
}

FTransform GetComponentToWorld(uintptr_t entity) {
    uintptr_t Mesh = getMemoryAddr(entity + Offsets::Character::Mesh);
    if (Mesh) {
        return Read<FTransform>(Mesh + 0x250);
    }
    return {};
}
#endif //COSMIC_ESP_PUBM_2_0_0_32BIT_FTRANSFORM_H
