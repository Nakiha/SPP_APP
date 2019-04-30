package com.nakiha.release.DatabaseModule;

import org.litepal.crud.LitePalSupport;

public class AccountData extends LitePalSupport {
    private String name;
    private String passWord;
    private String noteMap;
    private String carControlMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getNoteMap() {
        return noteMap;
    }

    public void setNoteMap(String noteMap) {
        this.noteMap = noteMap;
    }

    public String getCarControlMap() {
        return carControlMap;
    }

    public void setCarControlMap(String carControlMap) {
        this.carControlMap = carControlMap;
    }
}
