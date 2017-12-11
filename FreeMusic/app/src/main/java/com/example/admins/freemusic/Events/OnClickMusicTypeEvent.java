package com.example.admins.freemusic.Events;

import com.example.admins.freemusic.Databases.MusicTypeModel;

/**
 * Created by Admins on 11/22/2017.
 */

public class OnClickMusicTypeEvent {
    public MusicTypeModel musicTypeModel;

    public OnClickMusicTypeEvent(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }

}
