package android.cosmic.utils;

import android.graphics.Color;
import com.cosmic.ui.R;
import static android.cosmic.view.OverlayView.getResString;
import static android.cosmic.view.OverlayView.getValueBoolean;
import static android.cosmic.view.OverlayView.getShowBoolean;

public class ESPEngine {

    public static String getEnemyWeapon(int id){
        if(id==101006)
            return "AUG";
        if(id==101008)
            return "M762" ;
        if(id==101003)
            return "SCAR-L";
        if(id==101004)
            return "M416";
        if(id==101002)
            return "M16A4";
        if(id==101009)
            return "Mutant";
        if(id==101010)
            return "G36C";
        if(id==101007)
            return "QBZ";
        if(id==101001)
            return "AKM";
        if(id==101005)
            return "Groza";
        if(id==102005)
            return "Bizon";
        if(id==102004)
            return "TommyGun";
        if(id==102007)
            return "MP5K";
        if(id==102002)
            return "UMP";
        if(id==102003)
            return "Vector";
        if(id==102001)
            return "Uzi";
        if(id==105002)
            return "DP28";
        if(id==105001)
            return "M249";
        //snipers
        if(id==103003)
            return "AWM";
        if(id==103010)
            return "QBU";
        if(id==103009)
            return "SLR";
        if(id==103004)
            return "SKS";
        if(id==103006)
            return "Mini14";
        if(id==103002)
            return "M24";
        if(id==103001)
            return "Kar98k";
        if(id==103005)
            return "VSS";
        if(id==103008)
            return "Win94";
        if(id==103007)
            return "Mk14";
        if(id==104003)
            return "S12K";
        if(id==104004)
            return "DBS";
        if(id==104001)
            return "S686";
        if(id==104002)
            return "S1897";
        if(id==108003)
            return "Sickle";
        if(id==108001)
            return "Machete";
        if(id==108002)
            return "Crowbar";
        if(id==107001)
            return "CrossBow";
        if(id==108004)
            return "Pan";
        if(id==106006)
            return "SawedOff";
        if(id==106003)
            return "R1895";
        if(id==106008)
            return "Vz61";
        if(id==106001)
            return "P92";
        if(id==106004)
            return "P18C";
        if(id==106005)
            return "R45";
        if(id==106002)
            return "P1911";
        if(id==106010)
            return "DesertEagle";
        return null;
    }

    public static String getVehicleName(String s){
        if(s.contains("Buggy") && getValueBoolean("SHOW_BUGGY"))
            return "Buggy";
        if(s.contains("UAZ") && getValueBoolean("SHOW_UAZ"))
            return "UAZ";
        if(s.contains("MotorcycleCart") && getValueBoolean("SHOW_MOTORCYLE_CART") )
            return "MotorcycleCart";
        if(s.contains("Motorcycle") && getValueBoolean("SHOW_MOTORCYLE"))
            return "Motorcycle";
        if(s.contains("Dacia") && getValueBoolean("SHOW_DACIA"))
            return "Dacia";
        if(s.contains("AquaRail") && getValueBoolean("SHOW_AQUARAIL"))
            return "AquaRail";
        if(s.contains("PG117") && getValueBoolean("SHOW_PG117"))
            return "PG117";
        if(s.contains("MiniBus") && getValueBoolean("SHOW_MINIBUS"))
            return "MiniBus";
        if(s.contains("Mirado") && getValueBoolean("SHOW_MIRADO"))
            return "Mirado";
        if(s.contains("Scooter") && getValueBoolean("SHOW_SCOOTER"))
            return "Scooter";
        if(s.contains("Rony") && getValueBoolean("SHOW_RONY"))
            return "Rony";
        if(s.contains("Snowbike") && getValueBoolean("SHOW_SNOWBIKE"))
            return "Snowbike";
        if(s.contains("Snowmobile") && getValueBoolean("SHOW_SNOWMOBILE"))
            return "Snowmobile";
        if(s.contains("Tuk") && getValueBoolean("SHOW_TUK"))
            return "Tuk";
        if(s.contains("BRDM") && getValueBoolean("SHOW_BRDM"))
            return "BRDM";
        if(s.contains("LadaNiva") && getValueBoolean("SHOW_LADANIVA"))
            return "LadaNiva";
        if(s.contains("AirDropPlane") && getValueBoolean("SHOW_AIR_DROP_PLANE"))
            return "AirDropPlane";
        return null;
    }

    public static int getVehicleNum(String s){
        if(s.contains("Buggy") && getValueBoolean("SHOW_BUGGY"))
            return 0;
        if(s.contains("UAZ") && getValueBoolean("SHOW_UAZ"))
            return 1;
        if(s.contains("MotorcycleCart") && getValueBoolean("SHOW_MOTORCYLE_CART") )
            return 2;
        if(s.contains("Motorcycle") && getValueBoolean("SHOW_MOTORCYLE"))
            return 3;
        if(s.contains("Dacia") && getValueBoolean("SHOW_DACIA"))
            return 4;
        if(s.contains("AquaRail") && getValueBoolean("SHOW_AQUARAIL"))
            return 5;
        if(s.contains("PG117") && getValueBoolean("SHOW_PG117"))
            return 6;
        if(s.contains("MiniBus") && getValueBoolean("SHOW_MINIBUS"))
            return 7;
        if(s.contains("Mirado") && getValueBoolean("SHOW_MIRADO"))
            return 8;
        if(s.contains("Scooter") && getValueBoolean("SHOW_SCOOTER"))
            return 9;
        if(s.contains("Rony") && getValueBoolean("SHOW_RONY"))
            return 10;
        if(s.contains("Snowbike") && getValueBoolean("SHOW_SNOWBIKE"))
            return 11;
        if(s.contains("Snowmobile") && getValueBoolean("SHOW_SNOWMOBILE"))
            return 12;
        if(s.contains("Tuk") && getValueBoolean("SHOW_TUK"))
            return 13;
        if(s.contains("BRDM") && getValueBoolean("SHOW_BRDM"))
            return 14;
        if(s.contains("LadaNiva") && getValueBoolean("SHOW_LADANIVA"))
            return 15;
        if(s.contains("AirDropPlane") && getValueBoolean("SHOW_AIR_DROP_PLANE"))
            return 16;
        return -1;
    }

    public static int color = Color.WHITE;

    public static void setColor(int color) {
        ESPEngine.color = color;
    }

    public static int getColor() {
        return color;
    }

    public static String GetItemsType(String classname){
        if (classname.contains("M416") && getShowBoolean(getResString(R.string.m416)))
            return "M416";
        if (classname.contains("AKM") && getShowBoolean(getResString(R.string.akm)))
            return "AKM";
        if (classname.contains("SCAR-L") && getShowBoolean(getResString(R.string.scar_l)))
            return "SCAR-L";
        if (classname.contains("AUG") &&getShowBoolean(getResString(R.string.aug)))
            return "AUG";
        if (classname.contains("M762") && getShowBoolean(getResString(R.string.m762)))
            return "M762";
        if (classname.contains("M16A4") && getShowBoolean(getResString(R.string.m16a4)))
            return "M16A4";
        if (classname.contains("Groza") && getShowBoolean(getResString(R.string.groza)))
            return "Groza";
        if (classname.contains("QBZ") && getShowBoolean(getResString(R.string.qbz)))
            return "QBZ";
        if (classname.contains("M249") && getShowBoolean(getResString(R.string.m249)))
            return "M249";
        if (classname.contains("AWM") && getShowBoolean(getResString(R.string.awm)))
            return "AWM";
        if (classname.contains("QBU") && getShowBoolean(getResString(R.string.qbu)))
            return "QBU";
        if (classname.contains("SLR") && getShowBoolean(getResString(R.string.slr)))
            return "SLR";
        if (classname.contains("SKS") && getShowBoolean(getResString(R.string.sks)))
            return "SKS";
        if (classname.contains("Mini14") && getShowBoolean(getResString(R.string.mini14)))
            return "Mini14";
        if (classname.contains("M24") && getShowBoolean(getResString(R.string.m24)))
            return "M24";
        if (classname.contains("Kar98k") && getShowBoolean(getResString(R.string.kar98k)))
            return "Kar98k";
        if (classname.contains("VSS") && getShowBoolean(getResString(R.string.vss)))
            return "VSS";
        if (classname.contains("Win94") && getShowBoolean(getResString(R.string.win94)))
            return "Win94";
        if (classname.contains("S12K") && getShowBoolean(getResString(R.string.s12k)))
            return "S12K";
        if (classname.contains("DBS") && getShowBoolean(getResString(R.string.dbs)))
            return "DBS";
        if (classname.contains("S686") && getShowBoolean(getResString(R.string.s686)))
            return "S686";
        if (classname.contains("S1897") && getShowBoolean(getResString(R.string.s1897)))
            return "S1897";
        if (classname.contains("SawedOff") && getShowBoolean(getResString(R.string.sawed_off)))
            return "SawedOff";
        if (classname.contains("TommyGun") && getShowBoolean(getResString(R.string.thompson)))
            return "Thompson";
        if (classname.contains("MP5K") && getShowBoolean(getResString(R.string.mp5k)))
            return "MP5K";
        if (classname.contains("Vector") && getShowBoolean(getResString(R.string.vector)))
            return "Vector";
        if (classname.contains("Uzi") && getShowBoolean(getResString(R.string.uzi)))
            return "Uzi";
        if (classname.contains("UMP") && getShowBoolean(getResString(R.string.ump45)))
            return "UMP45";
        if (classname.contains("R1895") && getShowBoolean(getResString(R.string.r1895)))
            return "R1895";
        if (classname.contains("P92") && getShowBoolean(getResString(R.string.p92)))
            return "P92";
        if (classname.contains("P18C") && getShowBoolean(getResString(R.string.p18c)))
            return "P18C";
        if (classname.contains("R45") && getShowBoolean(getResString(R.string.r45)))
            return "R45";
        if (classname.contains("P1911") && getShowBoolean(getResString(R.string.p1911)))
            return "P1911";
        if (classname.contains("DesertEagle") && getShowBoolean(getResString(R.string.deserteagle)))
            return "DesertEagle";
        if (classname.contains("CrossBow") && getShowBoolean(getResString(R.string.crossbow)))
            return "CrossBow";
        if (classname.contains("Flaregun") && getShowBoolean(getResString(R.string.flaregun)))
            return "Flaregun";
        if (classname.contains("DP28") && getShowBoolean(getResString(R.string.dp_28)))
            return "DP28";
        if (classname.contains("Pan") && getShowBoolean(getResString(R.string.pan)))
            return "Pan";
        if (classname.contains("Mk14") && getShowBoolean(getResString(R.string.mk14)))
            return "Mk14";
        if (classname.contains("762mm") && getShowBoolean(getResString(R.string._7_62mm)))
            return "7.62mm";
        if (classname.contains("556mm") && getShowBoolean(getResString(R.string._5_56mm)))
            return "5.56mm";
        if (classname.contains("45ACP") && getShowBoolean(getResString(R.string._45_acp)))
            return ".45 ACP";
        if (classname.contains("9mm") && getShowBoolean(getResString(R.string._99mm)))
            return "9mm";
        if (classname.contains("300Magnum") && getShowBoolean(getResString(R.string._300_magnum)))
            return ".300Magnum";
        if (classname.contains("12Guage") && getShowBoolean(getResString(R.string._12_gauge)))
            return "12 Guage";
        if (classname.contains("Compensator") && getShowBoolean(getResString(R.string.compensator)))
            return "Compensator";
        if (classname.contains("FlashHider") && getShowBoolean(getResString(R.string.flashhider)))
            return "FlashHider";
        if (classname.contains("Suppressor") && getShowBoolean(getResString(R.string.suppressor)))
            return "Suppressor";
        if (classname.contains("Extended Quickdraw") && getShowBoolean(getResString(R.string.extended_quickdraw)))
            return "Extended Quickdraw";
        if (classname.contains("Extended") && getShowBoolean(getResString(R.string.extended)))
            return "Extended";
        if (classname.contains("Quickdraw") && getShowBoolean(getResString(R.string.quickdraw)))
            return "Quickdraw";
        if (classname.contains("8X") && getShowBoolean(getResString(R.string._8x_scope)))
            return "8X";
        if (classname.contains("6X") && getShowBoolean(getResString(R.string._6x_scope)))
            return "6X";
        if (classname.contains("4X") && getShowBoolean(getResString(R.string._4x_scope)))
            return "4X";
        if (classname.contains("3X") && getShowBoolean(getResString(R.string._3x_scope)))
            return "3X";
        if (classname.contains("2X") &&getShowBoolean(getResString(R.string._2x_scope)))
            return "2X";
        if (classname.contains("Holo") && getShowBoolean(getResString(R.string.holo)))
            return "Holo";
        if (classname.contains("Lazer") && getShowBoolean(getResString(R.string.lazer)))
            return "RedDot";
        if (classname.contains("Bag Lv3") && getShowBoolean(getResString(R.string.bagpack_lv_3)))
            return "Bag Lv3";
        if (classname.contains("Bag Lv2") && getShowBoolean(getResString(R.string.bagpack_lv_2)))
            return "Bag Lv2";
        if (classname.contains("Bag Lv1") && getShowBoolean(getResString(R.string.bagpack_lv_1)))
            return "Bag Lv1";
        if (classname.contains("Armor Lv3") && getShowBoolean(getResString(R.string.vest_lv_3)))
            return "Vest Lv3";
        if (classname.contains("Armor Lv2") && getShowBoolean(getResString(R.string.vest_lv_2)))
            return "Vest Lv2";
        if (classname.contains("Armor Lv1") && getShowBoolean(getResString(R.string.vest_lv_2)))
            return "Vest Lv1";
        if (classname.contains("Helmet Lv3") && getShowBoolean(getResString(R.string.helmet_lv_3)))
            return "Helmet Lv3";
        if (classname.contains("Helmet Lv2") && getShowBoolean(getResString(R.string.helmet_lv_2)))
            return "Helmet Lv2";
        if (classname.contains("Helmet Lv1") &&getShowBoolean(getResString(R.string.helmet_lv_1)))
            return "Helmet Lv1";
        if (classname.contains("Cowbar") && getShowBoolean(getResString(R.string.crowbar)))
            return "Crowbar";
        if (classname.contains("Machete") && getShowBoolean(getResString(R.string.machete)))
            return "Machete";
        if (classname.contains("Pan") && getShowBoolean(getResString(R.string.pan)))
            return "Pan";
        if (classname.contains("Sickle") && getShowBoolean(getResString(R.string.sockle)))
            return "Sickle";
        if (classname.contains("Smoke") && getShowBoolean(getResString(R.string.smoke)))
            return "Smoke";
        if (classname.contains("Molotov") && getShowBoolean(getResString(R.string.molotov)))
            return "Molotov";
        if (classname.contains("Flashbang") && getShowBoolean(getResString(R.string.flashbang)))
            return "Flashbang";
        if (classname.contains("Grenade") && getShowBoolean(getResString(R.string.grenade)))
            return "Grenade";
        if (classname.contains("Stun") && getShowBoolean(getResString(R.string.flashbang)))
            return "Stun";
        if (classname.contains("EnergyDrink") && getShowBoolean(getResString(R.string.energydrink)))
            return "EnergyDrink";
        if (classname.contains("Painkiller") && getShowBoolean(getResString(R.string.paintkiller)))
            return "Painkiller";
        if (classname.contains("Adrenaline") && getShowBoolean(getResString(R.string.adrenalline)))
            return "Adrenaline";
        if (classname.contains("Firstaid") && getShowBoolean(getResString(R.string.firstaidkit)))
            return "Firstaid";
        if (classname.contains("Bandage") && getShowBoolean(getResString(R.string.bandage)))
            return "Bandage";
        if (classname.contains("MedKit") && getShowBoolean(getResString(R.string.medkit)))
            return "MedKit";
        if (classname.contains("Angled Foregrip") && getShowBoolean(getResString(R.string.angled_foregrip)))
            return "Angled Foregrip";
        if (classname.contains("Thumb Grip") && getShowBoolean(getResString(R.string.thumb_grip)))
            return "Thumb Grip";
        if (classname.contains("Laser Sight") && getShowBoolean(getResString(R.string.laser_sight)))
            return "Laser Sight";
        if (classname.contains("Light Grip") && getShowBoolean(getResString(R.string.light_grip)))
            return "Light Grip";
        if (classname.contains("Half Grip") && getShowBoolean(getResString(R.string.half_grip)))
            return "Half Grip";
        if (classname.contains("Vertical Foregrip") && getShowBoolean(getResString(R.string.vertical_foregrip)))
            return "Vertical Foregrip";
        if (classname.contains("GhillieSuit") && getShowBoolean(getResString(R.string.ghilliesuit)))
            return "GhillieSuit";
        if (classname.contains("DuckBill") && getShowBoolean(getResString(R.string.duckbill)))
            return "DuckBill";
        if (classname.contains("Choke") && getShowBoolean(getResString(R.string.choke)))
            return "Choke";
        if (classname.contains("Stock") && getShowBoolean(getResString(R.string.stock)))
            return "Stock";
        if (classname.contains("Check Pad") && getShowBoolean("Check Pad"))
            return "Check Pad";
        if (classname.contains("Tactical") && getShowBoolean(getResString(R.string.tactical_stock)))
            return "Tactical Stock";
        if (classname.contains("Air Drop") && getShowBoolean(getResString(R.string.air_drop)))
            return "Air Drop";
        if (classname.contains("Air Drop Plane") && getShowBoolean("Air Drop Plane"))
            return "Air Drop Plane";
        return null;
    }

    public static int GetItemsNum(String classname){
        if (classname.contains("M416") && getShowBoolean(getResString(R.string.m416)))
            return 0;
        if (classname.contains("AKM") && getShowBoolean(getResString(R.string.akm)))
            return 1;
        if (classname.contains("SCAR-L") && getShowBoolean(getResString(R.string.scar_l)))
            return 2;
        if (classname.contains("AUG") &&getShowBoolean(getResString(R.string.aug)))
            return 3;
        if (classname.contains("M762") && getShowBoolean(getResString(R.string.m762)))
            return 4;
        if (classname.contains("M16A4") && getShowBoolean(getResString(R.string.m16a4)))
            return 5;
        if (classname.contains("Groza") && getShowBoolean(getResString(R.string.groza)))
            return 6;
        if (classname.contains("QBZ") && getShowBoolean(getResString(R.string.qbz)))
            return 7;
        if (classname.contains("M249") && getShowBoolean(getResString(R.string.m249)))
            return 8;
        if (classname.contains("AWM") && getShowBoolean(getResString(R.string.awm)))
            return 9;
        if (classname.contains("QBU") && getShowBoolean(getResString(R.string.qbu)))
            return 10;
        if (classname.contains("SLR") && getShowBoolean(getResString(R.string.slr)))
            return 11;
        if (classname.contains("SKS") && getShowBoolean(getResString(R.string.sks)))
            return 12;
        if (classname.contains("Mini14") && getShowBoolean(getResString(R.string.mini14)))
            return 13;
        if (classname.contains("M24") && getShowBoolean(getResString(R.string.m24)))
            return 14;
        if (classname.contains("Kar98k") && getShowBoolean(getResString(R.string.kar98k)))
            return 15;
        if (classname.contains("VSS") && getShowBoolean(getResString(R.string.vss)))
            return 16;
        if (classname.contains("Win94") && getShowBoolean(getResString(R.string.win94)))
            return 17;
        if (classname.contains("S12K") && getShowBoolean(getResString(R.string.s12k)))
            return 18;
        if (classname.contains("DBS") && getShowBoolean(getResString(R.string.dbs)))
            return 19;
        if (classname.contains("S686") && getShowBoolean(getResString(R.string.s686)))
            return 20;
        if (classname.contains("S1897") && getShowBoolean(getResString(R.string.s1897)))
            return 21;
        if (classname.contains("SawedOff") && getShowBoolean(getResString(R.string.sawed_off)))
            return 22;
        if (classname.contains("TommyGun") && getShowBoolean(getResString(R.string.thompson)))
            return 23;
        if (classname.contains("MP5K") && getShowBoolean(getResString(R.string.mp5k)))
            return 24;
        if (classname.contains("Vector") && getShowBoolean(getResString(R.string.vector)))
            return 25;
        if (classname.contains("Uzi") && getShowBoolean(getResString(R.string.uzi)))
            return 26;
        if (classname.contains("UMP") && getShowBoolean(getResString(R.string.ump45)))
            return 27;
        if (classname.contains("R1895") && getShowBoolean(getResString(R.string.r1895)))
            return 28;
        if (classname.contains("P92") && getShowBoolean(getResString(R.string.p92)))
            return 29;
        if (classname.contains("P18C") && getShowBoolean(getResString(R.string.p18c)))
            return 30;
        if (classname.contains("R45") && getShowBoolean(getResString(R.string.r45)))
            return 31;
        if (classname.contains("P1911") && getShowBoolean(getResString(R.string.p1911)))
            return 32;
        if (classname.contains("DesertEagle") && getShowBoolean(getResString(R.string.deserteagle)))
            return 33;
        if (classname.contains("CrossBow") && getShowBoolean(getResString(R.string.crossbow)))
            return 34;
        if (classname.contains("Flaregun") && getShowBoolean(getResString(R.string.flaregun)))
            return 35;
        if (classname.contains("DP28") && getShowBoolean(getResString(R.string.dp_28)))
            return 36;
        if (classname.contains("Pan") && getShowBoolean(getResString(R.string.pan)))
            return 37;
        if (classname.contains("Mk14") && getShowBoolean(getResString(R.string.mk14)))
            return 38;
        if (classname.contains("762mm") && getShowBoolean(getResString(R.string._7_62mm)))
            return 39;
        if (classname.contains("556mm") && getShowBoolean(getResString(R.string._5_56mm)))
            return 40;
        if (classname.contains("45ACP") && getShowBoolean(getResString(R.string._45_acp)))
            return 41;
        if (classname.contains("9mm") && getShowBoolean(getResString(R.string._99mm)))
            return 42;
        if (classname.contains("300Magnum") && getShowBoolean(getResString(R.string._300_magnum)))
            return 43;
        if (classname.contains("12Guage") && getShowBoolean(getResString(R.string._12_gauge)))
            return 44;
        if (classname.contains("Compensator") && getShowBoolean(getResString(R.string.compensator)))
            return 45;
        if (classname.contains("FlashHider") && getShowBoolean(getResString(R.string.flashhider)))
            return 46;
        if (classname.contains("Suppressor") && getShowBoolean(getResString(R.string.suppressor)))
            return 47;
        if (classname.contains("Extended Quickdraw") && getShowBoolean(getResString(R.string.extended_quickdraw)))
            return 48;
        if (classname.contains("Extended") && getShowBoolean(getResString(R.string.extended)))
            return 49;
        if (classname.contains("Quickdraw") && getShowBoolean(getResString(R.string.quickdraw)))
            return 50;
        if (classname.contains("8X") && getShowBoolean(getResString(R.string._8x_scope)))
            return 51;
        if (classname.contains("6X") && getShowBoolean(getResString(R.string._6x_scope)))
            return 52;
        if (classname.contains("4X") && getShowBoolean(getResString(R.string._4x_scope)))
            return 53;
        if (classname.contains("3X") && getShowBoolean(getResString(R.string._3x_scope)))
            return 54;
        if (classname.contains("2X") &&getShowBoolean(getResString(R.string._2x_scope)))
            return 55;
        if (classname.contains("Holo") && getShowBoolean(getResString(R.string.holo)))
            return 56;
        if (classname.contains("Lazer") && getShowBoolean(getResString(R.string.lazer)))
            return 57;
        return -1;
    }

    public static String getFlagName(String s){
        s = s.toLowerCase();
        if(s.contains("ad")) {
            return "Andorra";}
        if(s.contains("ae")) {
            return "Kuwait";}
        if(s.contains("af")) {
            return "Afghanistan";}
        if(s.contains("ag")) {
            return "Antigua and Barbuda";}
        if(s.contains("ai")) {
            return "Anguilla";}
        if(s.contains("al")) {
            return "Albania";}
        if(s.contains("am")) {
            return "Armenia";}
        if(s.contains("ao")) {
            return "Angola";}
        if(s.contains("ar")) {
            return "Argentina";}
        if(s.contains("as")) {
            return "American Samoa";}
        if(s.contains("at")) {
            return "Austria";}
        if(s.contains("au")) {
            return "Australia";}
        if(s.contains("aw")) {
            return "Aruba";}
        if(s.contains("ax")) {
            return "Aland";}
        if(s.contains("az")) {
            return "Azerbaijan";}
        if(s.contains("ba")) {
            return "Bosnia";}
        if(s.contains("bb")) {
            return "Barbados";}
        if(s.contains("bd")) {
            return "Bangladesh";}
        if(s.contains("be")) {
            return "Belgium";}
        if(s.contains("bf")) {
            return "Burkina Faso";}
        if(s.contains("bg")) {
            return "Bulgaria";}
        if(s.contains("bh")) {
            return "Bahrain";}
        if(s.contains("bi")) {
            return "Burundi";}
        if(s.contains("bj")) {
            return "Benin";}
        if(s.contains("bm")) {
            return "Bermuda";}
        if(s.contains("bn")) {
            return "Brunei";}
        if(s.contains("bo")) {
            return "Bolivia";}
        if(s.contains("bq")) {
            return "Bonaire";}
        if(s.contains("br")) {
            return "Brazil";}
        if(s.contains("bs")) {
            return "Bahamas";}
        if(s.contains("bt")) {
            return "Bhutan";}
        if(s.contains("bw")) {
            return "Botswana";}
        if(s.contains("by")) {
            return "Belarus";}
        if(s.contains("bz")) {
            return "Belize";}
        if(s.contains("bzbz")) {
            return "Congo";}
        if(s.contains("ca")) {
            return "Canada";}
        if(s.contains("cc")) {
            return "Cocos Islands";}
        if(s.contains("ce")) {
            return "Ceuta";}
        if(s.contains("cf")) {
            return "Central African Republic";}
        if(s.contains("cg")) {
            return "Congo";}
        if(s.contains("ch")) {
            return "Switzerland";}
        if(s.contains("ci")) {
            return "Cote D'Ivoire";}
        if(s.contains("ck")) {
            return "Cook Islands";}
        if(s.contains("cl")) {
            return "Chile";}
        if(s.contains("cm")) {
            return "Cameroon";}
        if(s.contains("cn")) {
            return "China";}
        if(s.contains("co")) {
            return "Colombia";}
        if(s.contains("corsica")) {
            return "Corsica";}
        if(s.contains("cr")) {
            return "Costa Rica";}
        if(s.contains("cu")) {
            return "Cuba";}
        if(s.contains("cv")) {
            return "Cape Verde";}
        if(s.contains("cw")) {
            return "Curacao";}
        if(s.contains("cx")) {
            return "Christmas Island";}
        if(s.contains("cy")) {
            return "Cyprus";}
        if(s.contains("cz")) {
            return "Czech Republic";}
        if(s.contains("de")) {
            return "Germany";}
        if(s.contains("dhsh")) {
            return "British Columbia";}
        if(s.contains("dj")) {
            return "Djibouti";}
        if(s.contains("dk")) {
            return "Denmark";}
        if(s.contains("dm")) {
            return "Dominica";}
        if(s.contains("dz")) {
            return "Algeria";}
        if(s.contains("ea")) {
            return "Melilla";}
        if(s.contains("ec")) {
            return "Ecuador";}
        if(s.contains("ee")) {
            return "Estonia";}
        if(s.contains("eg")) {
            return "Egypt";}
        if(s.contains("eh")) {
            return "Western Sahara";}
        if(s.contains("er")) {
            return "Eritrea";}
        if(s.contains("es")) {
            return "Spain";}
        if(s.contains("et")) {
            return "Ethiopia";}
        if(s.contains("fj")) {
            return "Fiji";}
        if(s.contains("fk")) {
            return "Falkland Islands";}
        if(s.contains("fl")) {
            return "Finland";}
        if(s.contains("fm")) {
            return "Micronesia";}
        if(s.contains("fo")) {
            return "Faroe Islands";}
        if(s.contains("fr")) {
            return "France";}
        if(s.contains("g1")) {
            return "Erangle";}
        if(s.contains("ga")) {
            return "Gabon";}
        if(s.contains("gb")) {
            return "United Kingdom";}
        if(s.contains("gd")) {
            return "Grenada";}
        if(s.contains("ge")) {
            return "Georgia";}
        if(s.contains("gg")) {
            return "Guernsey";}
        if(s.contains("gh")) {
            return "Ghana";}
        if(s.contains("gi")) {
            return "Gibraltar";}
        if(s.contains("gl")) {
            return "Greenland";}
        if(s.contains("gm")) {
            return "Gambia";}
        if(s.contains("gn")) {
            return "Guinea";}
        if(s.contains("gq")) {
            return "Equatorial Guin";}
        if(s.contains("gr")) {
            return "Greece";}
        if(s.contains("gt")) {
            return "Guatemala";}
        if(s.contains("gu")) {
            return "Guam";}
        if(s.contains("gw")) {
            return "Guinea-Bissau";}
        if(s.contains("gy")) {
            return "Guyana";}
        if(s.contains("hawaii")) {
            return "Hawaii";}
        if(s.contains("hk")) {
            return "Hong Kong";}
        if(s.contains("hn")) {
            return "Honduras";}
        if(s.contains("hr")) {
            return "Croatia";}
        if(s.contains("ht")) {
            return "Haiti";}
        if(s.contains("hu")) {
            return "Hungary";}
        if(s.contains("id")) {
            return "Indonesia";}
        if(s.contains("ie")) {
            return "Ireland";}
        if(s.contains("il")) {
            return "Israel";}
        if(s.contains("im")) {
            return "Isle of Man";}
        if(s.contains("in")) {
            return "India";}
        if(s.contains("io")) {
            return "British Indian Ocean";}
        if(s.contains("iq")) {
            return "Iraq";}
        if(s.contains("ir")) {
            return "Iran";}
        if(s.contains("is")) {
            return "Iceland";}
        if(s.contains("it")) {
            return "Italy";}
        if(s.contains("je")) {
            return "Jersey";}
        if(s.contains("jm")) {
            return "Jamaica";}
        if(s.contains("jo")) {
            return "Jordan";}
        if(s.contains("jp")) {
            return "Japan";}
        if(s.contains("ke")) {
            return "Kenya";}
        if(s.contains("kg")) {
            return "Kyrgyzstan";}
        if(s.contains("kh")) {
            return "Cambodia";}
        if(s.contains("ki")) {
            return "Kiribati";}
        if(s.contains("km")) {
            return "Comoros";}
        if(s.contains("kn")) {
            return "Saint Kitts and Nevi";}
        if(s.contains("kp")) {
            return "North Korea";}
        if(s.contains("kr")) {
            return "South Korea";}
        if(s.contains("kw")) {
            return "Kuwait";}
        if(s.contains("ky")) {
            return "Cayman Islands";}
        if(s.contains("kz")) {
            return "Kazakhstan";}
        if(s.contains("la")) {
            return "Lao";}
        if(s.contains("lb")) {
            return "Lebanon";}
        if(s.contains("li")) {
            return "Liechtenstein";}
        if(s.contains("lk")) {
            return "Sri Lanka";}
        if(s.contains("lr")) {
            return "Liberia";}
        if(s.contains("ls")) {
            return "Lesotho";}
        if(s.contains("lt")) {
            return "Lithuania";}
        if(s.contains("lu")) {
            return "Luxembourg";}
        if(s.contains("lv")) {
            return "Latvia";}
        if(s.contains("ly")) {
            return "Libyan";}
        if(s.contains("ma")) {
            return "Morocco";}
        if(s.contains("madeira")) {
            return "Madeira";}
        if(s.contains("mc")) {
            return "Monaco";}
        if(s.contains("md")) {
            return "Moldova";}
        if(s.contains("me")) {
            return "Montenegro";}
        if(s.contains("mg")) {
            return "Madagascar";}
        if(s.contains("mh")) {
            return "Marshall Islands";}
        if(s.contains("mk")) {
            return "Macedonia";}
        if(s.contains("ml")) {
            return "Mali";}
        if(s.contains("mm")) {
            return "Myanmar";}
        if(s.contains("mn")) {
            return "Mongolia";}
        if(s.contains("mo")) {
            return "Macao";}
        if(s.contains("mp")) {
            return "Northern Mariana Islands";}
        if(s.contains("mq")) {
            return "Martinique";}
        if(s.contains("mr")) {
            return "Mauritania";}
        if(s.contains("ms")) {
            return "Montserrat";}
        if(s.contains("mt")) {
            return "Malta";}
        if(s.contains("mu")) {
            return "Mauritius";}
        if(s.contains("mv")) {
            return "Maldives";}
        if(s.contains("mw")) {
            return "Malawi";}
        if(s.contains("mx")) {
            return "Mexico";}
        if(s.contains("my")) {
            return "Malaysia";}
        if(s.contains("mz")) {
            return "Mozambique";}
        if(s.contains("na")) {
            return "Namibia";}
        if(s.contains("nato")) {
            return "Nato";}
        if(s.contains("ne")) {
            return "Niger";}
        if(s.contains("nf")) {
            return "Norfolk Island";}
        if(s.contains("ng")) {
            return "Nigeria";}
        if(s.contains("ni")) {
            return "Nicaragua";}
        if(s.contains("nl")) {
            return "Netherlands";}
        if(s.contains("no")) {
            return "Norway";}
        if(s.contains("np")) {
            return "Nepal";}
        if(s.contains("nr")) {
            return "Nauru";}
        if(s.contains("nu")) {
            return "Niue";}
        if(s.contains("nz")) {
            return "New Zealand";}
        if(s.contains("om")) {
            return "Oman";}
        if(s.contains("ossetia")) {
            return "Ossetia";}
        if(s.contains("pa")) {
            return "Panama";}
        if(s.contains("pe")) {
            return "Peru";}
        if(s.contains("pf")) {
            return "French Polynesia";}
        if(s.contains("pg")) {
            return "Papua New Guinea";}
        if(s.contains("ph")) {
            return "Philippines";}
        if(s.contains("pk")) {
            return "Pakistan";}
        if(s.contains("pl")) {
            return "Poland";}
        if(s.contains("pn")) {
            return "Pitcairn";}
        if(s.contains("pr")) {
            return "Puerto Rico";}
        if(s.contains("ps")) {
            return "Palestine";}
        if(s.contains("pt")) {
            return "Portugal";}
        if(s.contains("pw")) {
            return "Palau";}
        if(s.contains("py")) {
            return "Paraguay";}
        if(s.contains("qa")) {
            return "Qatar";}
        if(s.contains("ro")) {
            return "Romania";}
        if(s.contains("rs")) {
            return "Serbia";}
        if(s.contains("ru")) {
            return "Russia";}
        if(s.contains("rw")) {
            return "Rwanda";}
        if(s.contains("sa")) {
            return "Saudi Arabia";}
        if(s.contains("sb")) {
            return "Solomon Islands";}
        if(s.contains("sc")) {
            return "Seychelles";}
        if(s.contains("sd")) {
            return "Sudan";}
        if(s.contains("se")) {
            return "Sweden";}
        if(s.contains("sg")) {
            return "Singapore";}
        if(s.contains("si")) {
            return "Slovenia";}
        if(s.contains("sk")) {
            return "Slovakia";}
        if(s.contains("sl")) {
            return "Sierra Leone";}
        if(s.contains("sn")) {
            return "Senegal";}
        if(s.contains("so")) {
            return "Somalia";}
        if(s.contains("sr")) {
            return "Suriname";}
        if(s.contains("ss")) {
            return "South Sudan";}
        if(s.contains("st")) {
            return "Sao Tome and Principe";}
        if(s.contains("sv")) {
            return "El Salvador";}
        if(s.contains("sy")) {
            return "Syrian Arab Republic";}
        if(s.contains("sz")) {
            return "Eswatini";}
        if(s.contains("tc")) {
            return "Turks and Caicos Islands";}
        if(s.contains("td")) {
            return "Chad";}
        if(s.contains("tg")) {
            return "Togo";}
        if(s.contains("th")) {
            return "Thailand";}
        if(s.contains("tj")) {
            return "Tajikistan";}
        if(s.contains("tk")) {
            return "Tokelau";}
        if(s.contains("tl")) {
            return "Timor-Leste";}
        if(s.contains("tm")) {
            return "Turkmenistan";}
        if(s.contains("tn")) {
            return "Tunisia";}
        if(s.contains("to")) {
            return "Tonga";}
        if(s.contains("tr")) {
            return "Turkey";}
        if(s.contains("tt")) {
            return "Trinidad and Tobago";}
        if(s.contains("tv")) {
            return "Tuvalu";}
        if(s.contains("tw")) {
            return "Taiwan";}
        if(s.contains("tz")) {
            return "Tanzania";}
        if(s.contains("ua")) {
            return "Ukraine";}
        if(s.contains("ug")) {
            return "Uganda";}
        if(s.contains("us")) {
            return "USA";}
        if(s.contains("uy")) {
            return "Uruguay";}
        if(s.contains("uz")) {
            return "Uzbekistan";}
        if(s.contains("va")) {
            return "Vatican City";}
        if(s.contains("ve")) {
            return "Venezuela";}
        if(s.contains("vg")) {
            return "Virgin Islands";}
        if(s.contains("vi")) {
            return "United States Virgin Islands";}
        if(s.contains("vn")) {
            return "Vietnam";}
        if(s.contains("vu")) {
            return "Vanuatu";}
        if(s.contains("wales")) {
            return "Wales";}
        if(s.contains("ws")) {
            return "Samoa";}
        if(s.contains("xk")) {
            return "Kosovo";}
        if(s.contains("ye")) {
            return "Yemen";}
        if(s.contains("za")) {
            return "South Africa";}
        if(s.contains("zm")) {
            return "Zambia";}
        if(s.contains("zw")) {
            return "Zimbabwe";}
        return "";
    }

    public static int getFlag(String s)
    {
        boolean count = false;
        int num = -1;
        s = s.toLowerCase();
        for(int i = 0; i < ExtraUtils.Flags.length; i++)
        {
            if(ExtraUtils.Flags[i].equals(s))
            {
                count = true;
                num = i;
                break;
            }
        }
        if(count)
        {
            //do some other thing
            return num;
        }
        else
        {
            //do some other thing
            return 75;
        }
    }
}
