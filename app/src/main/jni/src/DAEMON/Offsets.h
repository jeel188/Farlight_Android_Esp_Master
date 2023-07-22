//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_OFFSETS_H
#define COSMIC_OFFSETS_H

#include<stdint.h>

namespace Offsets {
    uintptr_t FNamePool = 0x9440CF0;
    uintptr_t FNamePoolToBlocks = 0x10;//May be 0 sometimes
    uintptr_t  FNameStride = 0x2;
    uintptr_t FNameEntryToString = 0x2;
    uintptr_t FNameEntryToLenBit = 0x6;
    uintptr_t FNameEntryHeader = 0x0;



    uintptr_t GWorld = 0x95e2c00;
    uintptr_t PersistentLevel = 0x30;
    uintptr_t  AddActor = 0x98;
    uintptr_t  AddActorCount = AddActor+ sizeof(uintptr_t);

    uintptr_t UObjectToInternalIndex = 0xC;
    uintptr_t UObjectToClassPrivate = 0x10;
    uintptr_t UObjectToFNameIndex = 0x18;
    namespace Actor {
        uintptr_t RootComponent = 0x138;
        uintptr_t Children = 0x130;
    }

    namespace PlayerController {
//        uintptr_t AcknowledgedPawn = 0x438;
        uintptr_t PlayerCameraManager = 0x2d8;
    }

    namespace PlayerCameraManager {
        uintptr_t CameraCache = 0x1bf0;
    }

    namespace Character {
        uintptr_t Mesh =  0x578 +0x8;
    }

    namespace StaticMeshComponent {
        int  MinLOD = 0x830 ;
    }

    namespace SceneComponent {
        uintptr_t RelativeLocation = 0x11c;
    }

    namespace UAECharacter {
        uintptr_t PlayerName = 0x308;
        uintptr_t TeamID = 0x3F4;
    }

    namespace STExtraCharacter {
        uintptr_t Health =   0x570;
        uintptr_t HealthMax = 0x574;
        uintptr_t bIsGunADS = 0xf71;
    }

    namespace STExtraWeapon {
        uintptr_t WeaponEntityComp =  0x778;
    }

    namespace STExtraShootWeapon {
        uintptr_t CurBulletNumInClip = 0xe28;
    }

    namespace WeaponEntity {
        uintptr_t WeaponId =  0x170;
    }

    namespace STExtraShootWeapon {
        uintptr_t ShootWeaponEntityComp = 0xf68;
    }

    namespace STExtraBaseCharacter {
        uintptr_t bIsWeaponFiring = 0x1508;
        uintptr_t WeaponManagerComponent = 0x2008;
    }

    namespace WeaponManagerComponent{
        uintptr_t CurrentWeaponReplicated = 0x4f0;
    }

    namespace ShootWeaponEntity {
        uintptr_t BulletFireSpeed = 0x4e0;
        uintptr_t ShootInterval = 0x510;
        uintptr_t AccessoriesVRecoilFactor = 0xaa8;
        uintptr_t AccessoriesHRecoilFactor = 0xaac;
        uintptr_t AccessoriesRecoveryFactor = 0xab0;
        uintptr_t ShotGunVerticalSpread = 0xafc;
        uintptr_t ShotGunHorizontalSpread = 0xb00;
        uintptr_t GameDeviationFactor = 0xb04;
        uintptr_t RecoilKickADS = 0xbb8;
        uintptr_t ExtraHitPerformScale = 0xbbc;
        uintptr_t AnimationKick = 0xbd4;
    }

    namespace Pawn {
        uintptr_t Controller = 0x3f8;
    }

    namespace Controller {
        uintptr_t ControlRotation = 0x3f0;
    }

    namespace MovementComponent {
        uintptr_t Velocity = 0x124;
    }

    namespace STCharacterMovementComponent {
        uintptr_t STCharacterMovement = 0x1a40;
    }

    namespace STExtraPlayerCharacter {
        constexpr auto STPlayerController = 0x38c0;
    }

    namespace Actor {
        uintptr_t ReplicatedMovement = 0xb0;
    }
}

#endif //COSMIC_OFFSETS_H
