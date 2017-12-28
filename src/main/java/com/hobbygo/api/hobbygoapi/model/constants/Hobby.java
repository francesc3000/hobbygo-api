package com.hobbygo.api.hobbygoapi.model.constants;

public enum Hobby {
    CHICHO_TERREMOTO("Generico"),
    FUTBOL_SALA("Futbol Sala"),
    PADEL("Padel"),
    PADEL_COUPLE("Padel en pareja");

    private String hobby;

    Hobby(String hobby) {
        this.hobby = hobby;
    }

    public String hobby() {
        return hobby;
    }

    public int getGroupPlayerNumber() {
        int groupNumber;
        switch (this){
            case FUTBOL_SALA:
                groupNumber = 7;
                break;
            case PADEL:
                groupNumber = 1;
                break;
            case PADEL_COUPLE:
                groupNumber = 2;
                break;
            default:
                groupNumber = 2;
        }

        return groupNumber;
    }

    public Boolean isSubsEnabled(){
        Boolean isEnabled;
        switch (this){
            case FUTBOL_SALA:
                isEnabled = true;
                break;
            case PADEL:
                isEnabled = false;
                break;
            case PADEL_COUPLE:
                isEnabled = false;
                break;
            default:
                isEnabled = false;
        }

        return isEnabled;
    }

    public int getEventGroupNumber() {
        int groupNumber;
        switch (this){
            default:
                groupNumber = 2;
        }

        return groupNumber;
    }
}
