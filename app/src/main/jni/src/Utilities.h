//
// Created by Sagar Shah on 07/5/2021.
//

#ifndef COSMIC_UTILITIES_H
#define COSMIC_UTILITIES_H

char *GetItemsType(const char *classname){
    if (strstr(classname, "Rifle_M416") != 0)
        return "M416";
    if (strstr(classname, "Rifle_AKM") != 0)
        return "AKM";
    if (strstr(classname, "Rifle_SCAR") != 0)
        return "SCAR-L";
    if (strstr(classname, "Rifle_AUG") != 0)
        return "AUG";
    if (strstr(classname, "Rifle_M762") != 0)
        return "M762";
    if (strstr(classname, "Rifle_M16A4") != 0)
        return "M16A4";
    if (strstr(classname, "Rifle_Groza") != 0)
        return "Groza";
    if (strstr(classname, "Rifle_QBZ") != 0)
        return "QBZ";
    if (strstr(classname, "Other_M249") != 0)
        return "M249";
    if (strstr(classname, "Sniper_AWM") != 0)
        return "AWM";
    if (strstr(classname, "Sniper_QBU") != 0)
        return "QBU";
    if (strstr(classname, "Sniper_SLR") != 0)
        return "SLR";
    if (strstr(classname, "Sniper_SKS") != 0)
        return "SKS";
    if (strstr(classname, "Sniper_Mini14") != 0)
        return "Mini14";
    if (strstr(classname, "Sniper_M24") != 0)
        return "M24";
    if (strstr(classname, "Sniper_Kar98k") != 0)
        return "Kar98k";
    if (strstr(classname, "Sniper_VSS") != 0)
        return "VSS";
    if (strstr(classname, "Sniper_Win94") != 0)
        return "Win94";
    if (strstr(classname, "ShotGun_S12K") != 0)
        return "S12K";
    if (strstr(classname, "ShotGun_DP12") != 0)
        return "DBS";
    if (strstr(classname, "ShotGun_S686") != 0)
        return "S686";
    if (strstr(classname, "ShotGun_S1897") != 0)
        return "S1897";
    if (strstr(classname, "ShotGun_SawedOff") != 0)
        return "SawedOff";
    if (strstr(classname, "MachineGun_PP19") != 0)
        return "PP19";
    if (strstr(classname, "MachineGun_TommyGun") != 0)
        return "TommyGun";
    if (strstr(classname, "MachineGun_MP5K") != 0)
        return "MP5K";
    if (strstr(classname, "MachineGun_UMP9") != 0)
        return "UMP9";
    if (strstr(classname, "MachineGun_Vector") != 0)
        return "Vector";
    if (strstr(classname, "MachineGun_Uzi") != 0)
        return "Uzi";
    if (strstr(classname, "MachineGun_UMP9") != 0)
        return "UMP9";
    if (strstr(classname, "Pistol_R1895") != 0)
        return "R1895";
    if (strstr(classname, "Pistol_Vz61") != 0)
        return "Vz61";
    if (strstr(classname, "Pistol_P92") != 0)
        return "P92";
    if (strstr(classname, "Pistol_P18C") != 0)
        return "P18C";
    if (strstr(classname, "Pistol_R45") != 0)
        return "R45";
    if (strstr(classname, "Pistol_P1911") != 0)
        return "P1911";
    if (strstr(classname, "Pistol_DesertEagle") != 0)
        return "DesertEagle";
    if (strstr(classname, "Other_CrossBow") != 0)
        return "CrossBow";
    if (strstr(classname, "Pistol_Flaregun"))
        return "Flaregun";
    if (strstr(classname, "Other_M249") != 0)
        return "DP28";
    if (strstr(classname, "BP_WEP_Pan_C") != 0)
        return "Pan";
    if (strstr(classname, "BP_WEP_Mk14_Pickup") != 0)
        return "Mk14";
    if (strstr(classname, "BP_Ammo_762mm_Pickup") != 0)
        return "762mm";
    if (strstr(classname, "BP_Ammo_556mm_Pickup") != 0)
        return "556mm";
    if (strstr(classname, "BP_Ammo_45ACP_Pickup") != 0)
        return "45ACP";
    if (strstr(classname, "BP_Ammo_9mm_Pickup") != 0)
        return "9mm";
    if (strstr(classname, "BP_Ammo_300Magnum_Pickup") != 0)
        return "300Magnum";
    if (strstr(classname, "BP_Ammo_12Guage_Pickup") != 0)
        return "12Guage";
    if (strstr(classname, "BP_Ammo_Bolt_Pickup") != 0)
        return "Arrows";
    if (strstr(classname, "Compensator_Pickup") != 0)
        return "Compensator";
    if (strstr(classname, "FlashHider_Pickup") != 0)
        return "FlashHider";
    if (strstr(classname, "Suppressor_Pickup") != 0)
        return "Suppressor";
    if (strstr(classname, "EQ_Pickup") != 0)
        return "Extended Quickdraw";
    if (strstr(classname, "E_Pickup") != 0)
        return "Extended";
    if (strstr(classname, "Q_Pickup") != 0)
        return "Quickdraw";
    if (strstr(classname, "BP_MZJ_8X_Pickup") != 0)
        return "8X";
    if (strstr(classname, "BP_MZJ_6X_Pickup") != 0)
        return "6X";
    if (strstr(classname, "BP_MZJ_4X_Pickup") != 0)
        return "4X";
    if (strstr(classname, "BP_MZJ_3X_Pickup") != 0)
        return "3X";
    if (strstr(classname, "BP_MZJ_2X_Pickup") != 0)
        return "2X";
    if (strstr(classname, "BP_MZJ_QX_Pickup") != 0)
        return "Holo";
    if (strstr(classname, "BP_MZJ_HD_Pickup") != 0)
        return "Lazer";
    if (strstr(classname, "PickUp_BP_Bag_Lv3") != 0)
        return "Bag Lv3";
    if (strstr(classname, "PickUp_BP_Bag_Lv2") != 0)
        return "Bag Lv2";
    if (strstr(classname, "PickUp_BP_Bag_Lv1") != 0)
        return "Bag Lv1";
    if (strstr(classname, "PickUp_BP_Armor_Lv3") != 0)
        return "Armor Lv3";
    if (strstr(classname, "PickUp_BP_Armor_Lv2") != 0)
        return "Armor Lv2";
    if (strstr(classname, "PickUp_BP_Armor_Lv1") != 0)
        return "Armor Lv1";
    if (strstr(classname, "PickUp_BP_Helmet_Lv3") != 0)
        return "Helmet Lv3";
    if (strstr(classname, "PickUp_BP_Helmet_Lv2") != 0)
        return "Helmet Lv2";
    if (strstr(classname, "PickUp_BP_Helmet_Lv1") != 0)
        return "Helmet Lv1";
    if (strstr(classname, "Cowbar") != 0)
        return "Cowbar";
    if (strstr(classname, "Machete") != 0)
        return "Machete";
    if (strstr(classname, "Sickle") != 0)
        return "Sickle";
    if (strstr(classname, "BP_Grenade_Shoulei_Weapon") != 0)
        return "Grenade";
    if (strstr(classname, "BP_Grenade_Stun_Weapon_Wrapper") != 0)
        return "Stun";
    if (strstr(classname, "BP_Grenade_Smoke_Weapon_Wrapper") != 0)
        return "Smoke";
    if (strstr(classname, "BP_Grenade_Burn_Weapon") != 0)
        return "Molotov";
    if (strstr(classname, "Pills_Pickup") != 0)
        return "Painkiller";
    if (strstr(classname, "Injection_Pickup_C") != 0)
        return "Adrenaline";
    if (strstr(classname, "Drink_Pickup_C") != 0)
        return "EnergyDrink";
    if (strstr(classname, "Firstaid_Pickup_C") != 0)
        return "Firstaid";
    if (strstr(classname, "Bandage_Pickup_C") != 0 )
        return "Bandage";
    if (strstr(classname, "FirstAidbox_Pickup_C") != 0)
        return "MedKit";
    if (strstr(classname, "GasCan_Pickup_C") != 0)
        return "GasCan";
    if (strstr(classname, "Angled_Pickup") != 0)
        return "Angled Foregrip";
    if (strstr(classname, "ThumbGrip_Pickup") != 0)
        return "Thumb Grip";
    if (strstr(classname, "Lasersight_Pickup") != 0)
        return "Laser Sight";
    if (strstr(classname, "LightGrip_Pickup") != 0)
        return "Light Grip";
    if (strstr(classname, "HalfGrip_Pickup") != 0)
        return "Half Grip";
    if (strstr(classname, "Vertical_Pickup") != 0)
        return "Vertical Foregrip";
    if (strstr(classname, "PickUp_BP_Ghillie") != 0)
        return "GhillieSuit";
    if (strstr(classname, "DuckBill") != 0)
        return "DuckBill";
    if (strstr(classname, "Choke") != 0)
        return "Choke";
    if (strstr(classname, "BP_QT_UZI_Pickup_C") != 0)
        return "Stock";
    if (strstr(classname, "BP_QT_Sniper_Pickup_C") != 0)
        return "Check Pad";
    if (strstr(classname, "BP_QT_A_Pickup_C") != 0)
        return "Tactical";
    if (strstr(classname, "BP_AirDropPlane") != 0)
        return "Air Drop Plane";
    if (strstr(classname, "AirDrop") != 0)
        return "Air Drop";
    return nullptr;
}

char *GetVehicleType(char *classname)
{
    if (strstr(classname, "PickUp_07") != 0)
        return "PickUp";
    if (strstr(classname, "Buggy") != 0)
        return "Buggy";
    if (strstr(classname, "UAZ") != 0)
        return "UAZ";
    if (strstr(classname, "MotorcycleCart") != 0)
        return "MotorcycleCart";
    if (strstr(classname, "Motorcycle") != 0)
        return "Motorcycle";
    if (strstr(classname, "Dacia") != 0)
        return "Dacia";
    if (strstr(classname, "AquaRail") != 0)
        return "AquaRail";
    if (strstr(classname, "PG117") != 0)
        return "PG117";
    if (strstr(classname, "MiniBus") != 0)
        return "MiniBus";
    if (strstr(classname, "close") != 0)
        return "Mirado";
    if (strstr(classname, "open") != 0)
        return "Mirado";
    if (strstr(classname, "Mirado") != 0)
        return "Mirado";
    if (strstr(classname, "Scooter") != 0)
        return "Scooter";
    if (strstr(classname, "Rony") != 0)
        return "Rony";
    if (strstr(classname, "Snowbike") != 0)
        return "Snowbike";
    if (strstr(classname, "Snowmobile") != 0)
        return "Snowmobile";
    if (strstr(classname, "Tuk") != 0)
        return "Tuk";
    if (strstr(classname, "BRDM") != 0)
        return "BRDM";
    if (strstr(classname, "LadaNiva") != 0)
        return "LadaNiva";
    if (strstr(classname, "BP_AirDropPlane_C") != 0)
        return "AirDropPlane";
    return "VehicleNotFound";
}

#endif //COSMIC_UTILITIES_H
