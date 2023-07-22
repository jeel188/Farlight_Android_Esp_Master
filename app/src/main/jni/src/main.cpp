//
// Created by Sagar Shah on 07/5/2021.
//

#include "main.h"
#include <jni.h>

#include "CanvasEngine.h"
#include "HacksDrawing.h"

CanvasEngine canvasEngine;


extern "C"
JNIEXPORT void JNICALL
Java_android_cosmic_view_OverlayView_setValueBoolean(JNIEnv *env, jclass clazz,
                                                            jint feature, jboolean is_true) {
    switch (feature) {
        case 1:
            showName = is_true;
            break;
        case 2:
            showDistance = is_true;
            break;
        case 3:
            showHealth = is_true;
            break;
        case 4:
            showTeamID = is_true;
            break;
        case 5:
            showBox = is_true;
            break;
        case 6:
            showRadarLine = is_true;
            break;
        case 7:
            showSkeleton = is_true;
            break;
        case 8:
            showWeapon = is_true;
            break;
        case 9:
            showEnemyCount = is_true;
            break;
        case 10:
            show360Warning = is_true;
            break;
        case 11:
            showHeadDots = is_true;
            break;
        case 12:
            showGrenadeAlert = is_true;
            break;
        case 13:
            showLootBox = is_true;
            break;
        case 14:
            showNation = is_true;
            break;
        case 15:
            showUID = is_true;
            break;
        case 16:
            LessRecoil=is_true;
            break;
        case 17:
            ZeroRecoil=is_true;
            break;
        case 18:
            InstantHit=is_true;
            break;
        case 19:
            FastShootInterval=is_true;
            break;
        case 20:
            HitX=is_true;
            break;
        case 21:
            SmallCrosshair=is_true;
            break;
        case 22:
            NoShake=is_true;
            break;
        case 23:
            BulletTrack=is_true;
            break;
        case 24:
            AimBot=is_true;
            break;
        case 25:
            AimPrediction=is_true;
            break;
        case 26:
            AimIgnoreKnocked=is_true;
            break;
    }
}

extern "C" JNIEXPORT void JNICALL
Java_android_cosmic_view_OverlayView_Range(JNIEnv *, jclass clazz,jint range) {
    aimingRange=1+range;
}

extern "C" JNIEXPORT void JNICALL
Java_android_cosmic_view_OverlayView_Location(JNIEnv *, jclass clazz,jint location) {
    aimlocation=location;
}

extern "C" JNIEXPORT void JNICALL
Java_android_cosmic_view_OverlayView_Target(JNIEnv *, jclass clazz,jint target) {
    aimtarget=target;
}

extern "C" JNIEXPORT void JNICALL
Java_android_cosmic_view_OverlayView_Trigger(JNIEnv *, jclass clazz,jint trigger) {
    aimtrigger=trigger;
}

extern "C"
JNIEXPORT void JNICALL
Java_android_cosmic_view_OverlayView_GetInitCanvasDrawing(JNIEnv *env, jclass clazz,
                                                                 jobject r_canvas_draw,
                                                                 jobject canvas) {
    canvasEngine = CanvasEngine(env, r_canvas_draw, canvas);
    if (canvasEngine.isValid()){
        MainDraw(canvasEngine);
    }
}

extern "C"
JNIEXPORT jint JNICALL
Java_android_cosmic_service_MainService_InitBase(JNIEnv *env, jobject thiz, jint version_code,
                                                        jint width, jint heigth) {
    if (!Create()){
        LOGE("[NDK] Socket can't create");
        return -1;
    }
    if (!Bind()){
        LOGE("[NDK] Socket can't bind");
        return -1;
    }
    if (!Listen()){
        LOGE("[NDK] Socket can't listen");
        return -1;
    }
    if (Accept()){
        LOGI("[NDK] Socket accepted");
        request.Mode = InitMode;
        request.ScreenWidth = width;
        request.ScreenHeight = heigth;
        request.VersionCode = version_code;
        send((void*) &request, sizeof(request));
    }
    return 1;
}
extern "C"
JNIEXPORT void JNICALL
Java_android_cosmic_service_MainService_CloseSocket(JNIEnv *env, jclass clazz) {
    Close();
}
