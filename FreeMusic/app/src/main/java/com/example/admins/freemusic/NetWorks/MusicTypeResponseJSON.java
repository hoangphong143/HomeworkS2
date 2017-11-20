package com.example.admins.freemusic.NetWorks;

import java.util.List;

import javax.security.auth.Subject;

/**
 * Created by Admins on 11/15/2017.
 */

public class MusicTypeResponseJSON {
    public List<SubjectJSON> subgenres;

    public class SubjectJSON {
        public String id;
        public String translation_key;
    }
}
