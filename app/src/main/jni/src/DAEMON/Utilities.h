//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_UTILITIES_H
#define COSMIC_UTILITIES_H

#include <string>

using namespace std;

struct Actors {
    uint64_t Enc_1, Enc_2;
    uint64_t Enc_3, Enc_4;
};

struct Chunk {
    uint32_t val_1, val_2, val_3, val_4;
    uint32_t val_5, val_6, val_7, val_8;
};

uint64_t getActorsArray(uint64_t uLevel, int Actors_Offset, int EncryptedActors_Offset) {
    if (uLevel < 0x10000000)
        return 0;
 
    if (Read<uint64_t>(uLevel + Actors_Offset) > 0)
        return uLevel + Actors_Offset;
 
    if (Read<uint64_t>(uLevel + EncryptedActors_Offset) > 0)
        return uLevel + EncryptedActors_Offset;
 
    auto AActors = Read<Actors>(uLevel + EncryptedActors_Offset + 0x10);
 
    if (AActors.Enc_1 > 0) {
        auto Enc = Read<Chunk>(AActors.Enc_1 + 0x80);
        return (((Read<uint8_t>(AActors.Enc_1 + Enc.val_1)
            | (Read<uint8_t>(AActors.Enc_1 + Enc.val_2) << 8))
            | (Read<uint8_t>(AActors.Enc_1 + Enc.val_3) << 0x10)) & 0xFFFFFF
            | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_4) << 0x18)
            | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_5) << 0x20)) & 0xFFFF00FFFFFFFFFF
            | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_6) << 0x28)
            | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_7) << 0x30)
            | ((uint64_t)Read<uint8_t>(AActors.Enc_1 + Enc.val_8) << 0x38);
    }
    else if (AActors.Enc_2 > 0) {
        auto Lost_Actors = Read<uint64_t>(AActors.Enc_2);
        if (Lost_Actors > 0) {
            return (uint16_t)(Lost_Actors - 0x400) & 0xFF00
                | (uint8_t)(Lost_Actors - 0x04)
                | (Lost_Actors + 0xFC0000) & 0xFF0000
                | (Lost_Actors - 0x4000000) & 0xFF000000
                | (Lost_Actors + 0xFC00000000) & 0xFF00000000
                | (Lost_Actors + 0xFC0000000000) & 0xFF0000000000
                | (Lost_Actors + 0xFC000000000000) & 0xFF000000000000
                | (Lost_Actors - 0x400000000000000) & 0xFF00000000000000;
        }
    }
    else if (AActors.Enc_3 > 0) {
        auto Lost_Actors = Read<uint64_t>(AActors.Enc_3);
        if (Lost_Actors > 0) {
            return (Lost_Actors >> 0x38) | (Lost_Actors << (64 - 0x38));
        }
    }
    else if (AActors.Enc_4 > 0) {
        auto Lost_Actors = Read<uint64_t>(AActors.Enc_4);
        if (Lost_Actors > 0) {
            return Lost_Actors ^ 0xCDCD00;
        }
    }
    return 0;
}

bool isContain(string str, string check) {
    size_t found = str.find(check);
    return (found != string::npos);
}

void getCharacterName(uintptr_t address, UTF8 * transcoding)
{
    int classname;
    int m = 0;
    UTF8 buf88[256] = "";
    UTF16 buf16[34] = {0};
    int hex[2] = {0};
    for (int i = 0; i < 4; i++)
    {
        classname = Read<int>(address + i * 4);
        hex[0] = (classname & 0xfffff000) >> 16;
        hex[1] = classname & 0xffff;
        buf16[m] = hex[1];
        buf16[m + 1] = hex[0];
        m += 2;
    }
    Utf16_To_Utf8(buf16, buf88, sizeof(buf88), strictConversion);
    sprintf(transcoding, "%s", buf88);
}

void getNationByte(uintptr_t address, UTF8 * transcoding)
{
    int classname;
    int m = 0;
    UTF8 buf88[256] = "";
    UTF16 buf16[34] = {0};
    int hex[2] = {0};
    for (int i = 0; i < 4; i++)
    {
        classname = Read<int>(address + i * 4);
        hex[0] = (classname & 0xfffff000) >> 16;
        hex[1] = classname & 0xffff;
        buf16[m] = hex[1];
        buf16[m + 1] = hex[0];
        m += 2;
    }
    Utf16_To_Utf8(buf16, buf88, sizeof(buf88), strictConversion);
    sprintf(transcoding, "%s", buf88);
}

void getUIDByte(uintptr_t address, UTF8 * transcoding)
{
    int classname;
    int m = 0;
    UTF8 buf88[256] = "";
    UTF16 buf16[34] = {0};
    int hex[2] = {0};
    for (int i = 0; i < 4; i++)
    {
        classname = Read<int>(address + i * 4);
        hex[0] = (classname & 0xfffff000) >> 16;
        hex[1] = classname & 0xffff;
        buf16[m] = hex[1];
        buf16[m + 1] = hex[0];
        m += 2;
    }
    Utf16_To_Utf8(buf16, buf88, sizeof(buf88), strictConversion);
    sprintf(transcoding, "%s", buf88);
}

struct WideStr {
    static int is_surrogate(UTF16 uc) {
        return (uc - 0xd800u) < 2048u;
    }

    static int is_high_surrogate(UTF16 uc) {
        return (uc & 0xfffffc00) == 0xd800;
    }

    static int is_low_surrogate(UTF16 uc) {
        return (uc & 0xfffffc00) == 0xdc00;
    }

    static wchar_t surrogate_to_utf32(UTF16 high, UTF16 low) {
        return (high << 10) + low - 0x35fdc00;
    }

    static wchar_t *w_str(uintptr_t str, size_t len) {
        wchar_t *output = new wchar_t[len + 1];

        UTF16 *source = ReadArray<UTF16>(str, len);

        for (int i = 0; i < len; i++) {
            const UTF16 uc = source[i];
            if (!is_surrogate(uc)) {
                output[i] = uc;
            } else {
                if (is_high_surrogate(uc) && is_low_surrogate(source[i]))
                    output[i] = surrogate_to_utf32(uc, source[i]);
                else
                    output[i] = L'?';
            }
        }

        free(source);

        output[len] = L'\0';
        return output;
    }

    static std::string getString(uintptr_t StrPtr, int StrLength) {
        std::wstring str = w_str(StrPtr, StrLength);

        std::string result(MAX_SIZE, '\0');

        wcstombs((char *) result.data(), str.c_str(), MAX_SIZE);

        return result;
    }
};
std::string GetFNameFromID(uint32_t index ,uintptr_t base) {
    if (true) {
        uint32_t Block = index >> 16;
        uint16_t Offset = index & 65535;

        uintptr_t FNamePool = (base + Offsets::FNamePool);

        uintptr_t NamePoolChunk = Read<uintptr_t>(FNamePool + Offsets::FNamePoolToBlocks + (Block * 0x8));
        uintptr_t FNameEntry = NamePoolChunk + (Offsets::FNameStride * Offset);

        uint16_t FNameEntryHeader = Read<uint16_t >(FNameEntry + Offsets::FNameEntryHeader);
        uintptr_t StrPtr = FNameEntry + Offsets::FNameEntryToString;
        int StrLength = FNameEntryHeader >> Offsets::FNameEntryToLenBit;

        if (StrLength > 0 && StrLength < 250) {
            bool wide = FNameEntryHeader & 1;
            if (wide) {
                if (isDecrypt){
                    return XorCypher(WideStr::getString(StrPtr, StrLength));
                } else {
                    return WideStr::getString(StrPtr, StrLength);
                }
            } else {
                if (isDecrypt){
                    return XorCypher(ReadString2(StrPtr, StrLength));
                } else {
                    return ReadString2(StrPtr, StrLength);
                }
            }
        } else {
            return "None";
        }
    }
}
static uint32_t getNameID(uintptr_t object) {
    return Read<uint32_t>(object + Offsets::UObjectToFNameIndex);
}
static std::string getName(uintptr_t object,uintptr_t libbase) {
    return GetFNameFromID(getNameID(object),libbase);
}
int GetGrenadeType(string classname)
{
    if (classname.find("BP_Grenade_Smoke_C") != std::string::npos)
        return 1;
    if (classname.find("BP_Grenade_Burn_C") != std::string::npos)
        return 2;
    if (classname.find("BP_Grenade_Stun_C") != std::string::npos)
        return 3;
    if (classname.find("BP_Grenade_Shoulei_C") != std::string::npos)
        return 4;
    return 0;
}

PlayerWeapon getPlayerWeapon(uintptr_t address) {
    PlayerWeapon playerWeapon;
    uintptr_t base[3];
    VMRead((void *)getMemoryAddr(address + Offsets::Actor::
    Children), base, sizeof(base));
    if (isValid64(base[0])) {
        playerWeapon.IsWeapon = true;
        playerWeapon.WeaponId = Read<int>(getMemoryAddr(base[0] + Offsets::STExtraWeapon::WeaponEntityComp) + Offsets::WeaponEntity::WeaponId);
        playerWeapon.CurBulletNumInClip = Read<int>(base[0] + Offsets::STExtraShootWeapon::CurBulletNumInClip);
    } else if (isValid64(base[1])) {
        playerWeapon.IsWeapon = true;
        playerWeapon.WeaponId = Read<int>(getMemoryAddr(base[1] + Offsets::STExtraWeapon::WeaponEntityComp) + Offsets::WeaponEntity::WeaponId);
        playerWeapon.CurBulletNumInClip = Read<int>(base[1] + Offsets::STExtraShootWeapon::CurBulletNumInClip);
    } else if (isValid64(base[2])) {
        playerWeapon.IsWeapon = true;
        playerWeapon.WeaponId = Read<int>(getMemoryAddr(base[2] + Offsets::STExtraWeapon::WeaponEntityComp) + Offsets::WeaponEntity::WeaponId);
        playerWeapon.CurBulletNumInClip = Read<int>(base[2] + Offsets::STExtraShootWeapon::CurBulletNumInClip);
    } else {
        playerWeapon.IsWeapon = false;
    }
    return playerWeapon;
}

bool IsItems(char *classname){
    if (strstr(classname, "Wrapper"))
        return true;
    if (strstr(classname, "PickUp_"))
        return true;
    if (strstr(classname, "_Pickup_C"))
        return true;
    return false;
}

#endif //COSMIC_UTILITIES_H
