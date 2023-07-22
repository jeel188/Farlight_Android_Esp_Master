//
// Created by Patel on 15-07-2023.
//

#ifndef COSMIC_ESP_PUBM_2_0_0_32BIT_ENTITY_H
#define COSMIC_ESP_PUBM_2_0_0_32BIT_ENTITY_H

#define NetDriverToWorldObject 0x38
#define NetConnectionToServerConnection 0x88
#define PlayerControllerToPlayerObject 0x30
static struct Ulevel{
    uintptr_t World;
    uintptr_t Level;
    uintptr_t ULevelToAActors;
    int ULevelToAActorsCount;
    uintptr_t uMyObject = 0;
};
static struct CameraManager{
    uintptr_t PlayerCameraManager;
    MinimalViewInfo POV = MinimalViewInfo();//SDK MinimalViewInfo
    CameraCacheEntry CameraCache = CameraCacheEntry();//Player CameraView
};

#endif //COSMIC_ESP_PUBM_2_0_0_32BIT_ENTITY_H
