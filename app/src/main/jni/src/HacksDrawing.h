//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_HACKSDRAWING_H
#define COSMIC_HACKSDRAWING_H

#include <chrono>
#include "Utilities.h"

Request request;
Response response;
Color clrNew;

void DrawingSkeleton(CanvasEngine esp, Vector3 vec1, Vector3 vec2, Color color){
    esp.DrawLine(color, 1, Vector2(vec1.X, vec1.Y), Vector2(vec2.X, vec2.Y));
}

bool isnull(Vector3 dat)
{
    if (dat.X != NULL)
        if (dat.Y != NULL)
            if (dat.Z != NULL)
                return true;
    return false;
}


uint64_t GetTickCount() {
    using namespace std::chrono;
    return duration_cast<milliseconds>(steady_clock::now().time_since_epoch()).count();
}

class Interval {
private:
    int initial_;

public:
    inline Interval() : initial_(GetTickCount()) {}

    virtual ~Interval() {}

    inline unsigned int value() const {
        return GetTickCount() - initial_;
    }
};

class FPS {
protected:
    int32_t m_fps;
    int32_t m_fpscount;
    Interval m_fpsinterval;

public:
    FPS() : m_fps(0), m_fpscount(0) {}

    void Update() {
        m_fpscount++;
        if (m_fpsinterval.value() > 1000) {
            m_fps = m_fpscount;
            m_fpscount = 0;
            m_fpsinterval = Interval();
        }
    }

    int32_t get() const {
        return m_fps;
    }
};

FPS m_fps;


Color teamIdColors[] =
        {
                Color::Red(),
                Color::Yellow(),
                Color::Orange(),
                Color::BlueDongker(),
                Color::Pink(),
                Color::Green(),
                Color::Grey(),
                Color::SilverDark(),
                Color::Blue()
        };

Color colorByDistance(int distance, float alpha){
    Color _colorByDistance;
    if (distance < 450)
        _colorByDistance = Color(0,255,0, alpha);
    if (distance < 200)
        _colorByDistance = Color(255,255,0, alpha);
    if (distance < 120)
        _colorByDistance = Color(255,133,51, alpha);
    if (distance < 50)
        _colorByDistance = Color(255,0,0, alpha);
    return _colorByDistance;
}

bool isContain(string str, string check) {
    size_t found = str.find(check);
    return (found != string::npos);
}

extern "C"
void MainDraw(CanvasEngine CosmicDrawEngine){
    send((void *) &request, sizeof(request));
    receive((void *) &response);
    request.aimbot.aimprediction = AimPrediction;
    request.aimbot.aimignoreknocked = AimIgnoreKnocked;
    request.aimbot.aimingRange = aimingRange;
    request.aimbot.aimlocation = aimlocation;
    request.aimbot.aimtarget = aimtarget;
    request.aimbot.aimtrigger = aimtrigger;
    request.memory.AimBot = AimBot;
    request.memory.BulletTrack = BulletTrack;
    request.memory.LessRecoil = LessRecoil;
    request.memory.ZeroRecoil = ZeroRecoil;
    request.memory.InstantHit = InstantHit;
    request.memory.FastShootInterval = FastShootInterval;
    request.memory.SmallCrosshair = SmallCrosshair;
    request.memory.HitX = HitX;
    request.memory.NoShake = NoShake;
    int screenHeight = CosmicDrawEngine.getHeight();
    int screenWidth = CosmicDrawEngine.getWidth();
    m_fps.Update();
    char *tsr = OBFUSCATE("Developer JeelPatel: FPS : ");
    std::string Str = std::string(tsr);
    Str += std::to_string((int) m_fps.get());
    CosmicDrawEngine.DrawText(Color::Yellow(), Str.c_str(), Vector2(150, 50), 22);
    if (response.Success) {
        if(BulletTrack || AimBot) {
            CosmicDrawEngine.DrawCircle(Color(255,0,0),Vector2(screenWidth/2,screenHeight/2),aimingRange,screenHeight/500);
        }
        int count = response.PlayerCount;
        if (count > 0){
            for (int i = 0; i < count; i++) {
                PlayerData player = response.Players[i];
                float x = player.HeadLocation.X;
                float y = player.HeadLocation.Y;
                float z = player.HeadLocation.Z;
                if (player.Distance < 0 || player.Distance > 450) continue;
                string sDistance;
                sDistance += to_string((int) player.Distance);
                sDistance += "M";
                int Dist = 1000/(5+response.Players[i].Distance);
                int W = screenWidth / 2 ;
                int H = screenHeight / 2;
                int Ppw = response.Players[i].Bone.pelvis.X;
                int Pph = response.Players[i].Bone.pelvis.Y;
                int Pcw = response.Players[i].Bone.chest.X;
                int Pch = response.Players[i].Bone.chest.Y;
                bool insidePelvisW = Ppw > W-Dist && Ppw < W+Dist;
                bool insidePelvisH = Pph > H-Dist && Pph < H+Dist;
                bool insideCheastW = Pcw > W-Dist && Pcw < W+Dist;
                bool insideCheastH = Pch > H-Dist && Pch < H+Dist;
                if (insidePelvisW || insideCheastW){
                    if (insidePelvisH || insideCheastH){
                        clrNew = Color(0, 255, 0);
                    }
                } else {
                    clrNew = Color(255, 0, 0);
                }
                Color _colorByDistance = colorByDistance((int) player.Distance, 255);
                if (player.HeadLocation.Z > 0 && player.RootLocation.Z > 0) {
                    if (showName || showHealth || showTeamID) {
                        Color color = Color(0, 3, 12, 180);
                        CosmicDrawEngine.DrawLine(color, 30, Vector2(x - player.HealthMax, y - 60),
                                                  Vector2(x + player.HealthMax, y - 60));
                    }
                    if (showBox) {
                        float boxHeight = abs(player.HeadLocation.Y - player.RootLocation.Y);
                        float boxWidth = boxHeight * 0.6f;
                        Vector2 vStart = Vector2(player.HeadLocation.X - (boxWidth / 2), player.HeadLocation.Y);
                        Vector2 vEnd = Vector2(vStart.X + boxWidth, vStart.Y + boxHeight);
                        CosmicDrawEngine.DrawRect(clrNew, 2, vStart, vEnd);
                    }
                    if (showDistance) {
                        float a = 26;
                        if (player.Health >= 100) {
                            a = 26;
                        }
                        CosmicDrawEngine.DrawText(Color::Orange2(), sDistance.c_str(),
                                                  Vector2(x + player.HealthMax - a, y - 53), 15);
                    }
                    if (showTeamID) {
                        Color textColor = Color::White();
                        Color tColors;
                        if (player.TeamID < 100) {
                            textColor = Color::White();
                            tColors = teamIdColors[0];
                        }
                        if (player.TeamID < 95) {
                            textColor = Color(0, 3, 12, 180);
                            tColors = teamIdColors[1];
                        }
                        if (player.TeamID < 90) {
                            textColor = Color(0, 3, 12, 180);
                            tColors = teamIdColors[2];
                        }
                        if (player.TeamID < 85) {
                            textColor = Color::White();
                            tColors = teamIdColors[3];
                        }
                        if (player.TeamID < 80) {
                            textColor = Color::White();
                            tColors = teamIdColors[4];
                        }
                        if (player.TeamID < 75) {
                            textColor = Color::White();
                            tColors = teamIdColors[5];
                        }
                        if (player.TeamID < 70) {
                            textColor = Color::White();
                            tColors = teamIdColors[6];
                        }
                        if (player.TeamID < 65) {
                            textColor = Color::White();
                            tColors = teamIdColors[7];
                        }
                        if (player.TeamID < 60) {
                            textColor = Color::White();
                            tColors = teamIdColors[8];
                        }
                        if (player.TeamID < 55) {
                            textColor = Color::White();
                            tColors = teamIdColors[0];
                        }
                        if (player.TeamID < 50) {
                            textColor = Color(0, 3, 12, 180);
                            tColors = teamIdColors[1];
                        }
                        if (player.TeamID < 45) {
                            textColor = Color(0, 3, 12, 180);
                            tColors = teamIdColors[2];
                        }
                        if (player.TeamID < 40) {
                            textColor = Color::White();
                            tColors = teamIdColors[3];
                        }
                        if (player.TeamID < 35) {
                            textColor = Color::White();
                            tColors = teamIdColors[4];
                        }
                        if (player.TeamID < 30) {
                            textColor = Color::White();
                            tColors = teamIdColors[5];
                        }
                        if (player.TeamID < 25) {
                            textColor = Color::White();
                            tColors = teamIdColors[6];
                        }
                        if (player.TeamID < 20) {
                            textColor = Color::White();
                            tColors = teamIdColors[7];
                        }
                        if (player.TeamID < 15) {
                            textColor = Color::White();
                            tColors = teamIdColors[8];
                        }
                        if (player.TeamID < 10) {
                            textColor = Color::White();
                            tColors = teamIdColors[0];
                        }
                        if (player.TeamID < 5) {
                            textColor = Color(0, 3, 12, 180);
                            tColors = teamIdColors[1];
                        }
                        std::string sTeamID;
                        float a = 20;
                        if (player.Health >= 100) {
                            a = 26;
                        }
                        sTeamID += to_string((int) player.TeamID);
                        CosmicDrawEngine.DrawLine(tColors, 30,
                                                  Vector2(x - player.HealthMax, y - 60),
                                                  Vector2(x - player.HealthMax + 50, y - 60));
                        CosmicDrawEngine.DrawText2(textColor, sTeamID.c_str(),
                                                   Vector2(x - player.HealthMax + a, y - 53), 15);
                    }
                    if (showName) {

                            CosmicDrawEngine.DrawText2(Color::White(), player.Name,
                                                       Vector2(x, y - 53), 15);

                    }
                    if (showNation){
                        CosmicDrawEngine.DrawFLAG(Vector2(x + 70, (y - 83)), player.PlayerNationByte);
                    }
                    if (showUID){
                        CosmicDrawEngine.DrawText2(Color().White(), player.PlayerUID, Vector2(x + 10, (y - 113)), 15);
                    }
                    if (showRadarLine) {
                        float t = 0;
                        float at = 0;
                        if (showName) {
                            t = 75;
                        }
                        if (showEnemyCount) {
                            at = 125;
                        }
                        CosmicDrawEngine.DrawLine(clrNew, 2,
                                                  Vector2(CosmicDrawEngine.getWidth() / 2, at),
                                                  Vector2(x, y - t));
                    }
                    if (showHealth) {
                        Color healthColor = Color::White();
                        Vector2 v = Vector2(x + (-player.HealthMax) +
                                            2 * player.HealthMax * player.Health / player.HealthMax,
                                            y - 40);
                        if ((int) player.Health == 0) {
                            healthColor = Color::Red();
                            v = Vector2(x + player.HealthMax, y - 40);
                            CosmicDrawEngine.DrawText(Color::Orange3(), "Knocked",
                                                      Vector2(player.Bone.rKnee.X,
                                                              player.Bone.rKnee.Y + 50), 15);
                        }
                        CosmicDrawEngine.DrawLine(healthColor, 10,
                                                  Vector2(x - player.HealthMax, y - 40), v);
                    }
                    if (showSkeleton) {
                        Color _skeletonColors = clrNew;
                        if (isnull(player.HeadLocation) && isnull(player.Bone.chest) &&
                            isnull(player.Bone.pelvis) && isnull(player.Bone.lShoulder)
                            && isnull(player.Bone.rShoulder) && isnull(player.Bone.lElbow) &&
                            isnull(player.Bone.rElbow) && isnull(player.Bone.lWrist)
                            && isnull(player.Bone.rWrist) && isnull(player.Bone.lThigh) &&
                            isnull(player.Bone.rThigh) && isnull(player.Bone.lKnee)
                            && isnull(player.Bone.rKnee) && isnull(player.Bone.lAnkle) &&
                            isnull(player.Bone.rAnkle)) {
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.neck, player.Bone.chest,
                                            _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.chest, player.Bone.pelvis,
                                            _skeletonColors);

                            DrawingSkeleton(CosmicDrawEngine, player.Bone.chest,
                                            player.Bone.lShoulder, _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.chest,
                                            player.Bone.rShoulder, _skeletonColors);

                            DrawingSkeleton(CosmicDrawEngine, player.Bone.lShoulder,
                                            player.Bone.lElbow, _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.rShoulder,
                                            player.Bone.rElbow, _skeletonColors);

                            DrawingSkeleton(CosmicDrawEngine, player.Bone.lElbow,
                                            player.Bone.lWrist, _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.rElbow,
                                            player.Bone.rWrist, _skeletonColors);

                            DrawingSkeleton(CosmicDrawEngine, player.Bone.pelvis,
                                            player.Bone.lThigh, _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.pelvis,
                                            player.Bone.rThigh, _skeletonColors);

                            DrawingSkeleton(CosmicDrawEngine, player.Bone.lThigh, player.Bone.lKnee,
                                            _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.rThigh, player.Bone.rKnee,
                                            _skeletonColors);

                            DrawingSkeleton(CosmicDrawEngine, player.Bone.lKnee, player.Bone.lAnkle,
                                            _skeletonColors);
                            DrawingSkeleton(CosmicDrawEngine, player.Bone.rKnee, player.Bone.rAnkle,
                                            _skeletonColors);
                        }
                    }
                    if (showHeadDots) {
                        Color _HeadDotsColors = clrNew;
                        float boxHeight = fabsf(player.RootLocation.Y - player.HeadLocation.Y);
                        float boxWidth = boxHeight * 0.68;
                        CosmicDrawEngine.DrawCircle(_HeadDotsColors, Vector2(x, y), boxWidth / 7.5);
                    }
                    if (showWeapon) {
                        float a = 20;
                        if (player.Health >= 100) {
                            a = 26;
                        }
                        if (player.EnemyWeapon.IsWeapon) {
                            CosmicDrawEngine.DrawEnemyWeapon(Color::White(),
                                                             player.EnemyWeapon.WeaponId,
                                                             player.EnemyWeapon.CurBulletNumInClip,
                                                             Vector2(x - player.HealthMax + a,
                                                                     y - 83), 15);
                        }
                    }
                    if (show360Warning) {
                        Color _360warningColor = colorByDistance((int) player.Distance, 128);
                        float locZ = z;
                        float posX = x;
                        float posY = y;
                        float radius = screenHeight / 15;
                        if (locZ == 1.0f) {
                            if (posY > screenHeight - screenHeight / 12)
                                posY = screenHeight - screenHeight / 12;
                            else if (posY < screenHeight / 12)
                                posY = screenHeight / 12;
                            if (posX < screenWidth / 2) {
                                CosmicDrawEngine.DrawCircle(_360warningColor,
                                                            Vector2(screenWidth, posY), radius);
                                CosmicDrawEngine.DrawText2(Color::White(), sDistance.c_str(),
                                                           Vector2(screenWidth - screenWidth / 65,
                                                                   posY), 15);
                            } else {
                                CosmicDrawEngine.DrawCircle(_360warningColor, Vector2(0, posY),
                                                            radius);
                                CosmicDrawEngine.DrawText2(Color::White(), sDistance.c_str(),
                                                           Vector2(screenWidth / 65, posY), 15);
                            }
                        } else if (posX < -screenWidth / 10 ||
                                   posX > screenWidth + screenWidth / 10) {
                            if (posY > screenHeight - screenHeight / 12)
                                posY = screenHeight - screenHeight / 12;
                            else if (posY < screenHeight / 12)
                                posY = screenHeight / 12;
                            if (posX > screenWidth / 2) {
                                CosmicDrawEngine.DrawCircle(_360warningColor,
                                                            Vector2(screenWidth, posY), radius);
                                CosmicDrawEngine.DrawText2(Color::White(), sDistance.c_str(),
                                                           Vector2(screenWidth - screenWidth / 65,
                                                                   posY), 15);
                            } else {
                                CosmicDrawEngine.DrawCircle(_360warningColor, Vector2(0, posY),
                                                            radius);
                                CosmicDrawEngine.DrawText2(Color::White(), sDistance.c_str(),
                                                           Vector2(screenWidth / 65, posY), 15);
                            }
                        }
                    }
                }
            }
        }
        if (showEnemyCount){
            int colors;
            std::string eCountStr;
            if (count != 0){
                colors = 2;
                if (count > 5)
                    colors = 2;
                if (count > 10)
                    colors = 3;
                if (count > 15)
                    colors = 4;
                eCountStr = std::to_string((int) count);
            } else {
                colors = 1;
                eCountStr = "CLEAR";
            }
            CosmicDrawEngine.DrawEnemyCount(colors, Vector2(CosmicDrawEngine.getWidth()/2, 20));
            CosmicDrawEngine.DrawText2(Color::Black(), eCountStr.c_str(), Vector2(CosmicDrawEngine.getWidth() / 2, 110), 25);
        }
        int grenadeCount = response.GrenadeCount;
        if (grenadeCount > 0){
            for (int i = 0; i < grenadeCount; i++) {
                if (showGrenadeAlert) {
                    if (response.Grenade[i].Distance < 0 || response.Grenade[i].Distance > 200) continue;
                    CosmicDrawEngine.DrawText(Color(255, 0, 0), "[ALERT] Grenade!", Vector2(screenWidth / 2, 150), 15);
                    if (response.Grenade[i].Location.Z != 1.0f) {
                        char grenadeAlert[100];
                        if (response.Grenade[i].Type == 1)
                            sprintf(grenadeAlert, "[ALERT] Smoke Grenade! (%dM)",
                                    (int) response.Grenade[i].Distance);
                        if (response.Grenade[i].Type == 2)
                            sprintf(grenadeAlert, "[ALERT] Burn Grenade! (%dM)",
                                    (int) response.Grenade[i].Distance);
                        if (response.Grenade[i].Type == 3)
                            sprintf(grenadeAlert, "[ALERT] Flash Grenade! (%dM)",
                                    (int) response.Grenade[i].Distance);
                        if (response.Grenade[i].Type == 4)
                            sprintf(grenadeAlert, "[ALERT] Frag Grenade! (%dM)",
                                    (int) response.Grenade[i].Distance);
                        CosmicDrawEngine.DrawText(Color(255, 0, 0), grenadeAlert,
                                                  Vector2(response.Grenade[i].Location.X,
                                                          response.Grenade[i].Location.Y), 15);
                    }
                }
            }
        }

        int vehicleCount = response.VehicleCount;
        if (vehicleCount > 0){
            for (int i = 0; i < vehicleCount; i++) {
                if (response.Vehicles[i].Location.Z != 1.0f) {
                    if ((int) response.Vehicles[i].Distance <= 10 ||(int) response.Vehicles[i].Distance > 450) continue;
                    if (!isContain(GetVehicleType(response.Vehicles[i].Name), "VehicleNotFound")){
                        CosmicDrawEngine.DrawVehicles(GetVehicleType(response.Vehicles[i].Name), response.Vehicles[i].Distance, Vector2(response.Vehicles[i].Location.X, response.Vehicles[i].Location.Y - 15), 15);
                    }
                }
            }
        }

        int itemsCount = response.ItemsCount;
        if (itemsCount > 0){
            for (int i = 0; i < itemsCount; i++) {
                ItemData itemData = response.Items[i];
                const char* itemName = GetItemsType(itemData.Name);
                float X = itemData.Location.X;
                float Y = itemData.Location.Y;
                if (itemData.Distance > 2 && itemData.Distance < 500){
                    if (itemName != nullptr){
                        CosmicDrawEngine.DrawItem(itemName, itemData.Distance, Vector2(X, Y), 15);
                    }
                }
            }
        }

        if (showLootBox){
            int lootboxCount = response.LootBoxCount;
            for (int i = 0; i < lootboxCount; ++i) {
                LootBox lootBox = response.LotBox[i];
                if (lootBox.Distance > 2 && lootBox.Distance < 200){
                    char str[20];
                    sprintf(str, "LootBox (%fM)", lootBox.Distance);
                    CosmicDrawEngine.DrawText(Color::White(), str, Vector2(lootBox.Location.X, lootBox.Location.Y), 15);
                }
            }
        }
    }
}

#endif //COSMIC_HACKSDRAWING_H
