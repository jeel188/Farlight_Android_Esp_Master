//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_STRUCTS_H
#define COSMIC_STRUCTS_H

using namespace std;

class Color {
public:
    float r;
    float g;
    float b;
    float a;

    Color() {
        this->r = 0;
        this->g = 0;
        this->b = 0;
        this->a = 0;
    }

    Color(float r, float g, float b, float a) {
        this->r = r;
        this->g = g;
        this->b = b;
        this->a = a;
    }

    Color(float r, float g, float b) {
        this->r = r;
        this->g = g;
        this->b = b;
        this->a = 255;
    }

    static Color Red(){
        return Color(255,0,0);
    }
    static Color Red2(){
        return Color(255,26,26);
    }
    static Color Red3(){
        return Color(255,77,77);
    }

    static Color Orange(){
        return Color(255,102,0);
    }

    static Color Orange2(){
        return Color(255,133,51);
    }

    static Color Orange3(){
        return Color(255,133,51);
    }

    static Color Yellow(){
        return Color(255,255,0);
    }

    static Color Yellow2(){
        return Color(255,204,0);
    }

    static Color Yellow3(){
        return Color(255,255,51);
    }

    static Color White(){
        return Color(255,255,255);
    }

    static Color Green(){
        return Color(0,255,0);
    }
    static Color Green2(){
        return Color(26,255,26);
    }
    static Color Green3(){
        return Color(21,255,21);
    }
    static Color Blue(){
        return Color(0,0,255);
    }

    static Color BlueSky1(){
        return Color(0,102,255);
    }

    static Color BlueSky2(){
        return Color(51,133,255);
    }
    static Color Pink(){
        return Color(255,0,102);
    }
    static Color Pink2(){
        return Color(255,51,153);
    }
    static Color BlueDongker(){
        return Color(0,0,102);
    }
    static Color Grey(){
        return Color(102,102,153);
    }
    static Color Silver(){
        return Color(230,230,230);
    }
    static Color Silver2(){
        return Color(204,204,204);
    }
    static Color SilverDark(){
        return Color(64,64,64);
    }
    static Color Black(){
        return Color(0,0,0);
    }
    static Color Dark2(){
        return Color(0,3,12);
    }
};


class Vector2
{
public:
    float X;
    float Y;

    Vector2() {
        this->X = 0;
        this->Y = 0;
    }

    Vector2(float X, float Y) {
        this->X = X;
        this->Y = Y;
    }

};

class Vector3
{
public:
    float X;
    float Y;
    float Z;

    Vector3() {
        this->X = 0;
        this->Y = 0;
        this->Z = 0;
    }

    Vector3(float X, float Y, float Z) {
        this->X = X;
        this->Y = Y;
        this->Z = Z;
    }
};

class Vector4
{
public:
    float X;
    float Y;
    float Z;
    float W;

    Vector4() {
        this->X = 0;
        this->Y = 0;
        this->Z = 0;
        this->W = 0;
    }

    Vector4(float X, float Y, float Z, float W) {
        this->X = X;
        this->Y = Y;
        this->Z = Z;
        this->W = Z;
    }
};

class D3DMatrix {
public:
    float _11, _12, _13, _14;
    float _21, _22, _23, _24;
    float _31, _32, _33, _34;
    float _41, _42, _43, _44;
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
