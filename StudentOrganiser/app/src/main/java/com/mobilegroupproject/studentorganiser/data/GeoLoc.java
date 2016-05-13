package com.mobilegroupproject.studentorganiser.data;

/**
 * Created by joshuahughes on 13/05/2016.
 */
public class GeoLoc {
    public enum buildingCode {
        B, CC, D, EB, G, GG, HE, HH, J, JB, JJ, KG, L, LDS, N, RT, SCH, SMB, S, T, U, W, WPT, YY, ZZ
    }

    public GeoLoc() {

    }

    public String getGeoCoords(buildingCode buildCode) {
        String buildingGeo = "";
        switch (buildCode) {
            case B:
                buildingGeo = "52.765623,-1.227304";
                break;
            case CC:
                buildingGeo = "52.765019,-1.227113";
                break;
            case D:
                buildingGeo = "52.764759,-1.226676";
                break;
            case EB:
                buildingGeo = "52.7668638,-1.2208848";
                break;
            case G:
                buildingGeo = "52.764429,-1.228992";
                break;
            case GG:
                buildingGeo = "52.765162,-1.227908";
                break;
            case HE:
                buildingGeo = "52.767852,-1.223643";
                break;
            case HH:
                buildingGeo = "52.763910,-1.239783";
                break;
            case J:
                buildingGeo = "52.764839,-1.229666";
                break;
            case JB:
                buildingGeo = "52.767571,-1.224360";
                break;
            case JJ:
                buildingGeo = "52.766037,-1.222780";
                break;
            case KG:
                buildingGeo = "52.761991,-1.239462";
                break;
            case L:
                buildingGeo = "52.765561,-1.228391";
                break;
            case LDS:
                buildingGeo = "52.765421,-1.222857";
                break;
            case N:
                buildingGeo = "52.766836,-1.229172";
                break;
            case RT:
                buildingGeo = "52.762828,-1.240715";
                break;
            case SCH:
                buildingGeo = "52.766238,-1.228352";
                break;
            case SMB:
                buildingGeo = "52.765368,-1.226657";
                break;
            case S:
                buildingGeo = "52.762155,-1.241261";
                break;
            case T:
                buildingGeo = "52.762692,-1.239881";
                break;
            case U:
                buildingGeo = "52.765742,-1.227948";
                break;
            case W:
                buildingGeo = "52.761406,-1.240846";
                break;
            case WPT:
                buildingGeo = "52.760189,-1.243429";
                break;
            case YY:
                buildingGeo = "52.766306,-1.224800";
                break;
            case ZZ:
                buildingGeo = "52.765847,-1.224360";
                break;
            default:
                buildingGeo = "Error, building code not found.";
                break;
        }
        return (buildingGeo);
    }
}
