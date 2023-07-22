#include "main.h"

#include "Utilities.h"
#include "socket.h"

using namespace std;

int fd = 0;
bool isLessRecoil = false;
bool isZeroRecoil = false;
bool isInstantHit = false;
bool isFastShootInterval = false;
bool isSmallCrosshair = false;
bool isHitX = false;
bool isNoShake = false;

bool isBulletTrack = false;
bool isAimBot = false;
float aimRadius = 200;
int aimlocation = 1;
int aimtarget = 0;
int aimtrigger = 3;
bool aimprediction = false;
bool aimignoreknocked = false;

void UpdateCoordinator(Response &response);
void AimbotData();
static float GetHealth(uintptr_t ActorStatePointer)
{
    return Read<float>(ActorStatePointer + Offsets::STExtraCharacter::Health);
}
static float GetMaxHealth(uintptr_t ActorStatePointer)
{
    return Read<float>(ActorStatePointer + Offsets::STExtraCharacter::HealthMax);
}
static int GetTeamId(uintptr_t ActorStatePointer)
{
    return Read<int>(ActorStatePointer + Offsets::UAECharacter::TeamID);
}


int main(int argc, char *argv[])
{
    if (!Create())
    {
        LOGE("[Server] Socket can't create");
        return -1;
    }
    if (!Connect())
    {
        LOGE("[Server] Socket can't connect");
        return -1;
    }
    LOGI("[Server] Socket connected");
    Request request{};
    receive((void *)&request);
    if (request.Mode == InitMode)
    {
        Width = request.ScreenWidth;
        Height = request.ScreenHeight;
        target_pid = find_pid("com.miraclegames.farlight84");
        if (target_pid == 0)
        {
            LOGI("Game Not Running..");
        }
        if (Height > Width)
        {
            swap(Height, Width);
        }
        if (!getModule())
        {
            LOGE("[Server] Can't get game base");
            exit(1);
        }
        //        LOGI("Device screen:");
        //        LOGI("Width : %d", (int) Width);
        //        LOGI("Height : %d", (int) Height);
        //        LOGI("Base Addr: %lu",libbase);
        //        LOGI("Pid: %d",target_pid);
    }
    while ((receive((void *)&request) > 0))
    {
        Response response{};
        response.Success = true;
        response.PlayerCount = 0;
        response.ItemsCount = 0;
        response.VehicleCount = 0;
        response.GrenadeCount = 0;
        aimRadius = (float)request.aimbot.aimingRange;
        aimlocation = request.aimbot.aimlocation;
        aimtarget = request.aimbot.aimtarget;
        aimtrigger = request.aimbot.aimtrigger;
        aimprediction = request.aimbot.aimprediction;
        aimignoreknocked = request.aimbot.aimignoreknocked;
        isAimBot = request.memory.AimBot;
        isBulletTrack = request.memory.BulletTrack;
        isLessRecoil = request.memory.LessRecoil;
        isZeroRecoil = request.memory.ZeroRecoil;
        isInstantHit = request.memory.InstantHit;
        isFastShootInterval = request.memory.FastShootInterval;
        isHitX = request.memory.HitX;
        isNoShake = request.memory.NoShake;
        isSmallCrosshair = request.memory.SmallCrosshair;
        UpdateCoordinator(response);
        if (isAimBot || isBulletTrack)
        {
            //            AimbotData();
        }
        send((void *)&response, sizeof(response));
    }
    Close();
    return 0;
}

void UpdateCoordinator(Response &response)
{
    //    LOGI("Function call");
    Ulevel ulevel{};
    CameraManager cameraManager;
    uintptr_t  possible_change = 0;
    uintptr_t LocalPlayer;
    ulevel.World = getMemoryAddr(getRealOffset(Offsets::GWorld));
    if (ulevel.World == possible_change){
        return;
    }
    possible_change = ulevel.World;
    uintptr_t  OwningGameInstance = getMemoryAddr(possible_change+0x220);
    if (OwningGameInstance == 0){
        return;
    }
    uintptr_t localPayers = getMemoryAddr(OwningGameInstance+NetDriverToWorldObject);
    uintptr_t  LocalPlayerPointer = getMemoryAddr(localPayers);
    uintptr_t LocalPlayerControllerPtr = getMemoryAddr(LocalPlayerPointer+PlayerControllerToPlayerObject);
    uintptr_t LocalPlayerOwingPtr = getMemoryAddr(LocalPlayerControllerPtr+0x2c0);
    ulevel.Level = getMemoryAddr(ulevel.World + Offsets::PersistentLevel);                                                                                                           // PersistentLevel Level
    cameraManager.PlayerCameraManager = getMemoryAddr(LocalPlayerControllerPtr + Offsets::PlayerController::PlayerCameraManager);                                                     // localPlayerCameraView
    if (cameraManager.PlayerCameraManager)
    {
        cameraManager.CameraCache = Read<CameraCacheEntry>(cameraManager.PlayerCameraManager + Offsets::PlayerCameraManager::CameraCache);
        cameraManager.POV = cameraManager.CameraCache.POV;
    }
    ulevel.ULevelToAActors = getMemoryAddr(ulevel.Level + Offsets::AddActor);
    ulevel.ULevelToAActorsCount = Read<int>(ulevel.Level + Offsets::AddActorCount); // AddActor +POinterSize
    if (ulevel.ULevelToAActorsCount <= 0)
    {
        LOGI("Actor Not Found");
        ulevel.ULevelToAActorsCount = 0;
    }
    else if (ulevel.ULevelToAActorsCount > 1024)
    {
        ulevel.ULevelToAActorsCount = 1024;
    }
    for (int i = 0; i < ulevel.ULevelToAActorsCount; ++i)
    {
        uintptr_t actor = getMemoryAddr(ulevel.ULevelToAActors + i * sizeof(uintptr_t)); // Pointer Size
        if (!isValid64(actor))
        {
            continue;
        }
        char resName[100];
        strcpy(resName, getName(actor, libbase).c_str());
        if (strlen(resName) <= 0)
        {
            continue;
        }
        if(actor == LocalPlayerOwingPtr){
            continue;
        }
        if (strstr(resName, "BP_Character") || strstr(resName, "Character"))
        {
            auto ActorStatePointer = getMemoryAddr(actor +0x248);
            uintptr_t TeamId =GetTeamId(ActorStatePointer);
            Vector3 RootPos, HeadPos;
            uintptr_t RootComponent = getMemoryAddr(actor + Offsets::Actor::RootComponent);
            if (RootComponent==0){
                continue;
            }
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::SceneComponent::RelativeLocation);
//            if (RelativePosition == Vector3::Zero()){
//                continue;
//            }
            auto Distance = (Vector3::Distance(RelativePosition, cameraManager.POV.Location) / 100.0f);
            if (Distance>300){
                continue;
            }
            HeadPos = WorldToScreen(RelativePosition, cameraManager.POV, Width, Height);
            RootPos = WorldToScreen(RelativePosition, cameraManager.POV, Width, Height);
            HeadPos.Z += 90.0f;
            RootPos.Z -= 90.0f;
//            LOGI("Head X: %f Y: %f Z: %f", HeadPos.X, HeadPos.Y, HeadPos.Z);
//            LOGI("Root X: %f Y: %f Z: %f", RootPos.X, RootPos.Y, RootPos.Z);
            Vector3 neckPos = WorldToScreen(GetBoneLocation(actor, BoneID::neck_01), cameraManager.POV, Width,
                                            Height);
            Vector3 chestPos = WorldToScreen(GetBoneLocation(actor, BoneID::spine_03), cameraManager.POV, Width,
                                             Height);
            Vector3 pelvisPos = WorldToScreen(GetBoneLocation(actor, BoneID::pelvis), cameraManager.POV, Width,
                                              Height);
            Vector3 lShoulderPos = WorldToScreen(GetBoneLocation(actor, BoneID::eyebrow_l), cameraManager.POV,
                                                 Width, Height);
            Vector3 rShoulderPos = WorldToScreen(GetBoneLocation(actor, BoneID::nose_side_r_02),
                                                 cameraManager.POV, Width, Height);
            Vector3 lElbowPos = WorldToScreen(GetBoneLocation(actor, BoneID::eyebrow_r), cameraManager.POV, Width,
                                              Height);
            Vector3 rElbowPos = WorldToScreen(GetBoneLocation(actor, BoneID::nose_side_l_01), cameraManager.POV,
                                              Width, Height);
            Vector3 lWristPos = WorldToScreen(GetBoneLocation(actor, BoneID::hair_r_02), cameraManager.POV, Width,
                                              Height);
            Vector3 rWristPos = WorldToScreen(GetBoneLocation(actor, BoneID::hair_r_01), cameraManager.POV, Width,
                                              Height);
            Vector3 lThighPos = WorldToScreen(GetBoneLocation(actor, BoneID::lip_um_01), cameraManager.POV, Width,
                                              Height);
            Vector3 rThighPos = WorldToScreen(GetBoneLocation(actor, BoneID::lip_r), cameraManager.POV, Width,
                                              Height);
            Vector3 lKneePos = WorldToScreen(GetBoneLocation(actor, BoneID::lip_um_02), cameraManager.POV, Width,
                                             Height);
            Vector3 rKneePos = WorldToScreen(GetBoneLocation(actor, BoneID::hair_root), cameraManager.POV, Width,
                                             Height);
            Vector3 lAnklePos = WorldToScreen(GetBoneLocation(actor, BoneID::lip_ur), cameraManager.POV, Width,
                                              Height);
            Vector3 rAnklePos = WorldToScreen(GetBoneLocation(actor, BoneID::hair_b_01), cameraManager.POV, Width,
                                              Height);
            PlayerBone playerBone{neckPos, chestPos, pelvisPos, lShoulderPos, rShoulderPos, lElbowPos, rElbowPos, lWristPos, rWristPos, lThighPos, rThighPos, lKneePos, rKneePos, lAnklePos, rAnklePos};
            PlayerData *data = &response.Players[response.PlayerCount];
            getCharacterName(getMemoryAddr(ActorStatePointer+Offsets::UAECharacter::PlayerName),data->Name);
            data->TeamID =TeamId;
            data->Health = GetHealth(ActorStatePointer);
            data->HealthMax = GetMaxHealth(ActorStatePointer);
            data->Distance = Distance;
            data->IsRobot = false;
            data->EnemyWeapon = getPlayerWeapon(actor);
            data->HeadLocation = HeadPos;
            data->RootLocation = RootPos;
            data->Bone = playerBone;
            ++response.PlayerCount;
            if (response.PlayerCount == maxplayerCount)
                continue;
        }
        int grenadeAlert = GetGrenadeType(resName);
        if (grenadeAlert != 0)
        {
            uintptr_t RootComponent = getMemoryAddr(actor + Offsets::Actor::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::SceneComponent::RelativeLocation);

            Vector3 Pos;
            //            if (LocalPlayer) {
            //                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            //            } else {
            Pos = cameraManager.POV.Location;
            //            }
            float Distance = (Vector3::Distance(RelativePosition, Pos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, cameraManager.POV, Width, Height);
            if (screenPos.Z > 0 && Distance <= 500.0f)
            {
                GrenadeData *grenadeData = &response.Grenade[response.GrenadeCount];
                grenadeData->Type = grenadeAlert;
                grenadeData->Location = screenPos;
                grenadeData->Distance = Distance;
                response.GrenadeCount++;
                if (response.GrenadeCount == maxgrenadeCount)
                    continue;
            }
        }
        if (isContain(resName, "VH_") || isContain(resName, "Tuk_") || isContain(resName, "Rony_") || isContain(resName, "Mirado_") || isContain(resName, "PickUp_07"))
        {
            uintptr_t RootComponent = getMemoryAddr(actor + Offsets::Actor::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::SceneComponent::RelativeLocation);

            Vector3 myPos;
            if (LocalPlayer)
            {
                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            }
            else
            {
                myPos = cameraManager.POV.Location;
            }

            float Distance = (Vector3::Distance(RelativePosition, myPos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, cameraManager.POV, Width, Height);

            if (Distance < 0.0f || Distance > 450.0f)
                continue;
            if (screenPos.Z > 0)
            {
                VehicleData *vehicleData = &response.Vehicles[response.VehicleCount];
                strcpy(vehicleData->Name, resName);
                vehicleData->Location = screenPos;
                vehicleData->Distance = Distance;
                if (response.VehicleCount >= maxvehicleCount)
                {
                    continue;
                }
                ++response.VehicleCount;
            }
        }
        if (IsItems(resName))
        {
            uintptr_t RootComponent = getMemoryAddr(actor + Offsets::Actor::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::SceneComponent::RelativeLocation);

            Vector3 myPos;
            if (LocalPlayer)
            {
                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            }
            else
            {
                myPos = cameraManager.POV.Location;
            }

            float Distance = (Vector3::Distance(RelativePosition, myPos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, cameraManager.POV, Width, Height);

            if (Distance < 0.0f || Distance > 200.0f)
                continue;
            if (screenPos.Z > 0)
            {
                ItemData *itemData = &response.Items[response.ItemsCount];
                strcpy(itemData->Name, resName);
                itemData->Location = screenPos;
                itemData->Distance = Distance;
                if (response.ItemsCount >= maxitemsCount)
                {
                    continue;
                }
                ++response.ItemsCount;
            }
        }
        if (isContain(resName, "PlayerDeadInventoryBox"))
        {
            uintptr_t RootComponent = getMemoryAddr(actor + Offsets::Actor::RootComponent);
            Vector3 RelativePosition = Read<Vector3>(RootComponent + Offsets::SceneComponent::RelativeLocation);

            Vector3 myPos;
            if (LocalPlayer)
            {
                myPos = TransformToLocation(GetComponentToWorld(LocalPlayer), GetBoneTransform(LocalPlayer, 0));
            }
            else
            {
                myPos = cameraManager.POV.Location;
            }

            float Distance = (Vector3::Distance(RelativePosition, myPos) / 100.0f);
            Vector3 screenPos = WorldToScreen(RelativePosition, cameraManager.POV, Width, Height);

            if (Distance < 0.0f || Distance > 450.0f)
                continue;
            LootBox *lootBox = &response.LotBox[response.LootBoxCount];
            lootBox->Location = screenPos;
            lootBox->Distance = Distance;
            if (response.LootBoxCount >= maxlootBoxCount)
            {
                continue;
            }
            ++response.LootBoxCount;
        }
    }

    if (LocalPlayer != 0)
    {
        ShootWeaponBase shootWeaponBase(LocalPlayer);
        if (shootWeaponBase.isValid())
        {

            if (shootWeaponBase.isFiring())
            {
                if (isLessRecoil || isZeroRecoil)
                {
                    shootWeaponBase.setLessRecoil();
                    if (isZeroRecoil)
                    {
                        shootWeaponBase.setZeroRecoil();
                    }
                }
                if (isInstantHit)
                {
                    shootWeaponBase.setInstantHit();
                }
                if (isFastShootInterval)
                {
                    shootWeaponBase.setFastShootInterval();
                }
                if (isHitX)
                {
                    shootWeaponBase.setHitX();
                }
                if (isSmallCrosshair)
                {
                    shootWeaponBase.setSmallCrosshair();
                }
                if (isNoShake)
                {
                    shootWeaponBase.setNoShake();
                }
            }
        }
    }
}

/*void AimbotData() {
    uintptr_t World = getMemoryAddr(getMemoryAddr(getRealOffset(Offsets::GWorld)) - 0x20);
    uintptr_t uLevel = getMemoryAddr(World + Offsets::PersistentLevel);
    uintptr_t PlayerController = getMemoryAddr(getMemoryAddr(getMemoryAddr(World + 0x38) + 0x78) + 0x30);
    uintptr_t uMyObject = getMemoryAddr(PlayerController + Offsets::PlayerController::AcknowledgedPawn);
    uintptr_t PlayerCameraManager = getMemoryAddr(PlayerController + Offsets::PlayerController::PlayerCameraManager);

    uintptr_t LocalPlayer;
    uintptr_t LocalController;
    uintptr_t Target = 0;
    MinimalViewInfo POV = MinimalViewInfo();
    CameraCacheEntry CameraCache = CameraCacheEntry();

    if (PlayerCameraManager) {
        CameraCache = Read<CameraCacheEntry>(PlayerCameraManager + Offsets::PlayerCameraManager::CameraCache);
        POV = CameraCache.POV;
    }

    uintptr_t entityPtr = getActorsArray(uLevel, Offsets::ULevelToAActors, 0x448);
    uintptr_t ULevelToAActors = getMemoryAddr(entityPtr);
    int ULevelToAActorsCount = Read<int>(entityPtr + 0x8);
    if (ULevelToAActorsCount < 0) {
        ULevelToAActorsCount = 0;
    } else if (ULevelToAActorsCount > 1024) {
        ULevelToAActorsCount = 1024;
    }

    for (int i = 0; i < ULevelToAActorsCount; ++i) {
        uintptr_t actor = getMemoryAddr(ULevelToAActors + i * 0x8);
        if (actor <= 0) continue;
        char resName[32];
        getUE4ResName(actor, resName);

        if (isContain(resName, "BP_PlayerPawn") || isContain(resName, "BP_PlayerCharacter") || isContain(resName, "PlanET_FakePlayer") || isContain(resName, "PlayerPawn_Infec")) {
            auto Health = Read<float>(actor + Offsets::STExtraCharacter::Health);
            int TeamID = Read<int>(actor + Offsets::UAECharacter::TeamID);
            int PlayerDeath = Read<int>(actor + Offsets::STExtraCharacter::bDead);
            if (PlayerDeath == 1) continue;

            if (PlayerController) {
                int LocalPlayerKey = Read<int>(PlayerController + Offsets::UAEPlayerController::PlayerKey);
                if (actor) {
                    int PlayerKey = Read<int>(actor + Offsets::UAECharacter::PlayerKey);
                    if (PlayerKey == LocalPlayerKey) {
                        LocalPlayer = actor;
                        LocalController = getMemoryAddr(LocalPlayer + Offsets::STExtraPlayerCharacter::STPlayerController);
                    }
                }
            } else {
                LocalPlayer = 0;
                LocalController = 0;
            }

            if (LocalPlayer) {
                int LocalKey = Read<int>(LocalPlayer + Offsets::UAEPlayerController::PlayerKey);
                int PlayerKey = Read<int>(actor + Offsets::UAECharacter::PlayerKey);
                if (PlayerKey == LocalKey) {
                    continue;
                }

                int myTeamID = Read<int>(LocalPlayer + Offsets::UAECharacter::TeamID);
                if (TeamID == myTeamID) {
                    continue;
                }
            }

            //Aim-Target FOV
            if (aimtarget == 0) {
                float max = std::numeric_limits<float>::infinity();

                if (aimignoreknocked && Health < 1)
                    continue;

                auto root = WorldToScreen(GetBoneLocation(actor, 0), POV, Width, Height);
                auto head = WorldToScreen(GetHeadLocation(actor), POV, Width, Height);

                float height = abs(head.Y - root.Y);
                float width = height * 0.65f;

                Vector3 middlePoint = {head.X + (width / 2), head.Y + (height / 2), 0};

                Vector2 v2Middle = Vector2((float) (Width / 2), (float) (Height / 2));
                Vector2 v2Loc = Vector2(middlePoint.X, middlePoint.Y);

                float distance = Vector2::Distance(v2Middle, v2Loc);

                if (isInsideFOV((int) middlePoint.X, (int) middlePoint.Y)) {
                    if (distance < max) {
                        Target = actor;
                    }
                }
            }

            //Aim-Target Crosshair
            if (aimtarget == 1) {
                float max = std::numeric_limits<float>::infinity();

                if (aimignoreknocked && Health < 1)
                    continue;

                Vector3 myPos = GetBoneLocation(LocalPlayer, 0);
                Vector3 rootPos = GetBoneLocation(actor, 0);

                float distance = (Vector3::Distance(rootPos, myPos) / 100.0f);

                if (distance < max) {
                    Target = actor;
                }
            }
        }
    }

    if(LocalPlayer != 0){
        ShootWeaponBase shootWeaponBase(LocalPlayer);
        if (shootWeaponBase.isValid()) {
            bool bReady = false;
            if (aimtrigger == 1) {
                bReady = Read<bool>(LocalPlayer + Offsets::STExtraCharacter::bIsGunADS);
            }
            if (aimtrigger == 2) {
                bReady = Read<bool>(LocalPlayer + Offsets::STExtraBaseCharacter::bIsWeaponFiring);
            }
            if (aimtrigger == 3) {
                bReady = Read<bool>(LocalPlayer + Offsets::STExtraCharacter::bIsGunADS) || Read<bool>(LocalPlayer + Offsets::STExtraBaseCharacter::bIsWeaponFiring);
            }
            if (bReady) {
                if (Target) {
                    Vector3 targetAimPos = GetBoneLocation(Target, BoneID::Head);
                    if (aimlocation == 1) {
                        targetAimPos = GetBoneLocation(Target, BoneID::Head);
                    }
                    if (aimlocation == 2) {
                        targetAimPos = GetBoneLocation(Target, BoneID::neck_01);
                    }
                    if (aimlocation == 3) {
                        targetAimPos = GetBoneLocation(Target, BoneID::spine_02);
                    }
                    Vector3 LocalPos = GetHeadLocation(LocalPlayer);
                    if (aimprediction) {
                        auto mBulletSpeed = Read<float>(shootWeaponBase.ShootWeaponEntityComponent +
                                                        Offsets::ShootWeaponEntity::BulletFireSpeed);
                        if (mBulletSpeed > 0) {
                            uintptr_t CurrentVehicle = getMemoryAddr(
                                    Target + Offsets::STExtraCharacter::CurrentVehicle);
                            if (CurrentVehicle) {
                                Vector3 LinearVelocity = Read<Vector3>(
                                        CurrentVehicle + Offsets::Actor::ReplicatedMovement);

                                float dist = Vector3::Distance(targetAimPos, LocalPos);
                                float timeToTravel = dist / mBulletSpeed;

                                targetAimPos += LinearVelocity * timeToTravel;
                            } else {
                                uintptr_t STCharacterMovement = getMemoryAddr(
                                        Target + Offsets::STCharacterMovementComponent::STCharacterMovement);
                                if (STCharacterMovement) {
                                    Vector3 Velocity = Read<Vector3>(
                                            STCharacterMovement + Offsets::MovementComponent::Velocity);

                                    float dist = Vector3::Distance(targetAimPos, LocalPos);
                                    float timeToTravel = dist / mBulletSpeed;

                                    targetAimPos += Velocity * timeToTravel;
                                }
                            }
                        }
                    }

                    if(isBulletTrack) {
                        if(Read<bool>(LocalPlayer + Offsets::STExtraCharacter::bIsGunADS)) {
                            FRotator aimRotation = CalcAngle(GetHeadLocation(LocalPlayer), targetAimPos);
                            uintptr_t Control = getMemoryAddr(LocalPlayer + Offsets::Pawn::Controller);
                            Write<FRotator>(Control + Offsets::Controller::ControlRotation, aimRotation);
                        }
                        else {
                            uintptr_t LocalPlayerCameraManager = getMemoryAddr(LocalController + Offsets::PlayerController::PlayerCameraManager);
                            CameraCacheEntry LocalCameraCache = Read<CameraCacheEntry>(LocalPlayerCameraManager + Offsets::PlayerCameraManager::CameraCache);
                            Vector3 currViewAngle = LocalCameraCache.POV.Location;
                            FRotator aimRotation = CalcAngle(currViewAngle, targetAimPos);
                            //ClampAngles(aimRotation);
                            LocalCameraCache.POV.Rotation = aimRotation;
                            Write<CameraCacheEntry>(LocalPlayerCameraManager + Offsets::PlayerCameraManager::CameraCache, LocalCameraCache);
                        }
                    }

                    if(isAimBot) {
                        FRotator aimRotation = CalcAngle(GetHeadLocation(LocalPlayer), targetAimPos);
                        uintptr_t Control = getMemoryAddr(LocalPlayer + Offsets::Pawn::Controller);
                        Write<FRotator>(Control + Offsets::Controller::ControlRotation, aimRotation);
                    }
                }
            }
        }
    }
}
*/