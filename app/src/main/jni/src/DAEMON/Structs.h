//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_STRUCTS_H
#define COSMIC_STRUCTS_H


struct MinimalViewInfo {
    Vector3 Location;
    FRotator Rotation;
    float FOV;
};

struct CameraCacheEntry {
    float TimeStamp;
    char chunks[0xC];
    MinimalViewInfo POV;
};


Vector3 TransformToLocation(FTransform c2w, FTransform transform) {
    return MatrixToVector(MatrixMultiplication(TransformToMatrix(transform), TransformToMatrix(c2w)));
}

void ClampAngles(FRotator &angles) {
    if (angles.Pitch > 180)
        angles.Pitch -= 360;
    if (angles.Pitch < -180)
        angles.Pitch += 360;

    if (angles.Pitch < -75.f)
        angles.Pitch = -75.f;
    else if (angles.Pitch > 75.f)
        angles.Pitch = 75.f;

    while (angles.Yaw < -180.0f)
        angles.Yaw += 360.0f;
    while (angles.Yaw > 180.0f)
        angles.Yaw -= 360.0f;
}

void ClampAngles(float *angles) {
    if (angles[0] > 180)
        angles[0] -= 360;
    if (angles[0] < -180)
        angles[0] += 360;

    if (angles[0] < -75.f)
        angles[0] = -75.f;
    else if (angles[0] > 75.f)
        angles[0] = 75.f;

    while (angles[1] < -180.0f)
        angles[1] += 360.0f;
    while (angles[1] > 180.0f)
        angles[1] -= 360.0f;
}

enum BoneID : int {
    Root = 0,
    pelvis = 1,
    spine_01 = 2,
    spine_02 = 3,
    spine_03 = 4,
    neck_01 = 45,
    Head = 46,
    face_root = 7,
    eyebrows_pos_root = 8,
    eyebrows_root = 9,
    eyebrows_r = 10,
    eyebrows_l = 11,
    eyebrow_l = 12,
    eyebrow_r = 13,
    forehead_root = 14,
    forehead = 15,
    jaw_pos_root = 16,
    jaw_root = 17,
    jaw = 18,
    mouth_down_pos_root = 19,
    mouth_down_root = 20,
    lip_bm_01 = 21,
    lip_bm_02 = 22,
    lip_br = 23,
    lip_bl = 24,
    jaw_01 = 25,
    jaw_02 = 26,
    cheek_pos_root = 27,
    cheek_root = 28,
    cheek_r = 29,
    cheek_l = 30,
    nose_side_root = 31,
    nose_side_r_01 = 32,
    nose_side_r_02 = 33,
    nose_side_l_01 = 34,
    nose_side_l_02 = 35,
    eye_pos_r_root = 36,
    eye_r_root = 37,
    eye_rot_r_root = 38,
    eye_lid_u_r = 39,
    eye_r = 40,
    eye_lid_b_r = 41,
    eye_pos_l_root = 42,
    eye_l_root = 43,
    eye_rot_l_root = 44,
    eye_lid_u_l = 45,
    eye_l = 46,
    eye_lid_b_l = 47,
    nose_pos_root = 48,
    nose = 49,
    mouth_up_pos_root = 50,
    mouth_up_root = 51,
    lip_ul = 52,
    lip_um_01 = 53,
    lip_um_02 = 54,
    lip_ur = 55,
    lip_l = 56,
    lip_r = 57,
    hair_root = 58,
    hair_b_01 = 59,
    hair_b_02 = 60,
    hair_l_01 = 61,
    hair_l_02 = 62,
    hair_r_01 = 63,
    hair_r_02 = 64,
    hair_f_02 = 65,
    hair_f_01 = 66,
    hair_b_pt_01 = 67,
    hair_b_pt_02 = 68,
    hair_b_pt_03 = 69,
    hair_b_pt_04 = 70,
    hair_b_pt_05 = 71,
    camera_fpp = 72,
    GunReferencePoint = 73,
    GunRef = 74,
    breast_l = 75,
    breast_r = 76,
    clavicle_l = 77,
    upperarm_l = 78,
    lowerarm_l = 79,
    hand_l = 80,
    thumb_01_l = 81,
    thumb_02_l = 82,
    thumb_03_l = 83,
    thumb_04_l_MBONLY = 84,
    index_01_l = 85,
    index_02_l = 86,
    index_03_l = 87,
    index_04_l_MBONLY = 88,
    middle_01_l = 89,
    middle_02_l = 90,
    middle_03_l = 91,
    middle_04_l_MBONLY = 92,
    ring_01_l = 93,
    ring_02_l = 94,
    ring_03_l = 95,
    ring_04_l_MBONLY = 96,
    pinky_01_l = 97,
    pinky_02_l = 98,
    pinky_03_l = 99,
    pinky_04_l_MBONLY = 100,
    item_l = 101,
    lowerarm_twist_01_l = 102,
    upperarm_twist_01_l = 103,
    clavicle_r = 104,
    upperarm_r = 105,
    lowerarm_r = 106,
    hand_r = 107,
    thumb_01_r = 108,
    thumb_02_r = 109,
    thumb_03_r = 110,
    thumb_04_r_MBONLY = 111,
    index_01_r = 112,
    index_02_r = 113,
    index_03_r = 114,
    index_04_r_MBONLY = 115,
    middle_01_r = 116,
    middle_02_r = 117,
    middle_03_r = 118,
    middle_04_r_MBONLY = 119,
    ring_01_r = 120,
    ring_02_r = 121,
    ring_03_r = 122,
    ring_04_r_MBONLY = 123,
    pinky_01_r = 124,
    pinky_02_r = 125,
    pinky_03_r = 126,
    pinky_04_r_MBONLY = 127,
    item_r = 128,
    lowerarm_twist_01_r = 129,
    upperarm_twist_01_r = 130,
    BackPack = 131,
    backpack_01 = 132,
    backpack_02 = 133,
    Slot_Primary = 134,
    Slot_Secondary = 135,
    Slot_Melee = 136,
    slot_throwable = 137,
    coat_l_01 = 138,
    coat_l_02 = 139,
    coat_l_03 = 140,
    coat_l_04 = 141,
    coat_fl_01 = 142,
    coat_fl_02 = 143,
    coat_fl_03 = 144,
    coat_fl_04 = 145,
    coat_b_01 = 146,
    coat_b_02 = 147,
    coat_b_03 = 148,
    coat_b_04 = 149,
    coat_r_01 = 150,
    coat_r_02 = 151,
    coat_r_03 = 152,
    coat_r_04 = 153,
    coat_fr_01 = 154,
    coat_fr_02 = 155,
    coat_fr_03 = 156,
    coat_fr_04 = 157,
    thigh_l = 158,
    calf_l = 159,
    foot_l = 160,
    ball_l = 161,
    calf_twist_01_l = 162,
    thigh_twist_01_l = 163,
    thigh_r = 164,
    calf_r = 165,
    foot_r = 166,
    ball_r = 167,
    calf_twist_01_r = 168,
    thigh_twist_01_r = 169,
    Slot_SideArm = 170,
    skirt_l_01 = 171,
    skirt_l_02 = 172,
    skirt_l_03 = 173,
    skirt_f_01 = 174,
    skirt_f_02 = 175,
    skirt_f_03 = 176,
    skirt_b_01 = 177,
    skirt_b_02 = 178,
    skirt_b_03 = 179,
    skirt_r_01 = 180,
    skirt_r_02 = 181,
    skirt_r_03 = 182,
    ik_hand_root = 183,
    ik_hand_gun = 184,
    ik_hand_r = 185,
    ik_hand_l = 186,
    ik_aim_root = 187,
    ik_aim_l = 188,
    ik_aim_r = 189,
    ik_foot_root = 190,
    ik_foot_l = 191,
    ik_foot_r = 192,
    camera_tpp = 193,
    ik_target_root = 194,
    ik_target_l = 195,
    ik_target_r = 196,
    VB_spine_03_spine_03 = 197,
    VB_upperarm_r_lowerarm_r = 198
};

Vector3 WorldToScreen(Vector3 worldLocation, MinimalViewInfo camViewInfo, int screenWidth, int screenHeight) {
    FMatrix tempMatrix = RotatorToMatrix(camViewInfo.Rotation);

    Vector3 vAxisX(tempMatrix.M[0][0], tempMatrix.M[0][1], tempMatrix.M[0][2]);
    Vector3 vAxisY(tempMatrix.M[1][0], tempMatrix.M[1][1], tempMatrix.M[1][2]);
    Vector3 vAxisZ(tempMatrix.M[2][0], tempMatrix.M[2][1], tempMatrix.M[2][2]);

    Vector3 vDelta = worldLocation - camViewInfo.Location;

    Vector3 vTransformed(Vector3::Dot(vDelta, vAxisY), Vector3::Dot(vDelta, vAxisZ), Vector3::Dot(vDelta, vAxisX));

    float fov = camViewInfo.FOV;
    float screenCenterX = (screenWidth / 2.0f);
    float screenCenterY = (screenHeight / 2.0f);

    float X = (screenCenterX + vTransformed.X * (screenCenterX / tanf(fov * ((float)3.14159265358979323846 / 360.0f))) / vTransformed.Z);
    float Y = (screenCenterY - vTransformed.Y * (screenCenterX / tanf(fov * ((float)3.14159265358979323846 / 360.0f))) / vTransformed.Z);
    float Z = vTransformed.Z;

    return {X, Y, Z};
}


Vector3 GetBoneLocation(uintptr_t Player, int boneIdx) {
    if (Player) {
        return TransformToLocation(GetComponentToWorld(Player), GetBoneTransform(Player, boneIdx));
    }
    return {};
}

Vector3 GetHeadLocation(uintptr_t entity) {
    return GetBoneLocation(entity, BoneID::Head);
}

struct ShootWeaponBase {
    uintptr_t WeaponManagerComponent;
    uintptr_t CurrentWeaponReplicated;
    uintptr_t ShootWeaponEntityComponent;
    int bIsWeaponFiring;
    int bIsGunADS;
    explicit ShootWeaponBase (uintptr_t LocalPlayer) {
        WeaponManagerComponent = getMemoryAddr(LocalPlayer + Offsets::STExtraBaseCharacter::WeaponManagerComponent);
        CurrentWeaponReplicated = getMemoryAddr(WeaponManagerComponent + Offsets::WeaponManagerComponent::CurrentWeaponReplicated);
        ShootWeaponEntityComponent = getMemoryAddr(CurrentWeaponReplicated + Offsets::STExtraShootWeapon::ShootWeaponEntityComp);
        bIsWeaponFiring = Read<int>(LocalPlayer + Offsets::STExtraBaseCharacter::bIsWeaponFiring);
        bIsGunADS = Read<int>(LocalPlayer + Offsets::STExtraCharacter::bIsGunADS);
    }

    void setInstantHit() const {
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::BulletFireSpeed, "900000", TYPE_FLOAT);
    }

    void setLessRecoil() const {
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::AccessoriesVRecoilFactor,"0", TYPE_FLOAT);
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::AccessoriesHRecoilFactor, "0", TYPE_FLOAT);
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::AccessoriesRecoveryFactor, "0", TYPE_FLOAT);
    }

    void setZeroRecoil() const{
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::RecoilKickADS, "0", TYPE_FLOAT);
    }

    void setFastShootInterval() const{
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::ShootInterval, "0.048000", TYPE_FLOAT);
    }

    void setSmallCrosshair() const{
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::GameDeviationFactor,"0", TYPE_FLOAT);
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::ShotGunVerticalSpread,"0", TYPE_FLOAT);
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::ShotGunHorizontalSpread,"0", TYPE_FLOAT);
    }

    void setHitX() const{
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::ExtraHitPerformScale, "50", TYPE_FLOAT);
    }

    void setNoShake() const{
        Write(ShootWeaponEntityComponent + Offsets::ShootWeaponEntity::AnimationKick, "0", TYPE_FLOAT);
    }

    bool isFiring(){
        return (bIsWeaponFiring != 0);
    }

    bool isADS(){
        return (bIsGunADS != 0);
    }

    bool isValid() const{
        return (CurrentWeaponReplicated != 0);
    }
};

#define maxplayerCount 50
#define maxvehicleCount 20
#define maxitemsCount 100
#define maxgrenadeCount 10
#define maxlootBoxCount 25

enum Mode {
    InitMode = 1,
};

struct PlayerWeapon {
	bool IsWeapon = false;
    int WeaponId;
    int CurBulletNumInClip;
};

struct PlayerBone {
    Vector3 neck;
    Vector3 chest;
    Vector3 pelvis;
    Vector3 lShoulder;
    Vector3 rShoulder;
    Vector3 lElbow;
    Vector3 rElbow;
    Vector3 lWrist;
    Vector3 rWrist;
    Vector3 lThigh;
    Vector3 rThigh;
    Vector3 lKnee;
    Vector3 rKnee;
    Vector3 lAnkle;
    Vector3 rAnkle;
};

struct Memory {
    bool AimBot;
    bool BulletTrack;
    bool LessRecoil;
    bool ZeroRecoil;
    bool InstantHit;
    bool FastShootInterval;
    bool HitX;
    bool SmallCrosshair;
    bool NoShake;
};

struct Aimbot {
    int aimingRange;
    int aimlocation;
    int aimtarget;
    int aimtrigger;
    bool aimprediction;
    bool aimignoreknocked;
};

struct PlayerData {
    char Name[34];
    char PlayerNationByte[32];
    char PlayerUID[32];
    int TeamID;
    float Health;
    float HealthMax;
    float Distance;
    bool IsRobot;
    Vector3 HeadLocation;
    Vector3 RootLocation;
    PlayerWeapon EnemyWeapon;
    PlayerBone Bone;
};

struct GrenadeData {
    int Type;
    float Distance;
    Vector3 Location;
};

struct VehicleData {
    char Name[50];
    float Distance;
    Vector3 Location;
};

struct ItemData {
    char Name[50];
    float Distance;
    Vector3 Location;
};

struct LootBox {
    float Distance;
    Vector3 Location;
};

struct Request {
    int Mode;
    int VersionCode;
    int ScreenWidth;
    int ScreenHeight;
    Memory memory;
    Aimbot aimbot;
};

struct Response {
    bool Success;
    int PlayerCount;
    int VehicleCount;
    int ItemsCount;
    int GrenadeCount;
    int LootBoxCount;
    PlayerData Players[maxplayerCount];
    GrenadeData Grenade[maxgrenadeCount];
    VehicleData Vehicles[maxvehicleCount];
    ItemData Items[maxitemsCount];
    LootBox LotBox[maxlootBoxCount];
};

#endif //COSMIC_STRUCTS_H
