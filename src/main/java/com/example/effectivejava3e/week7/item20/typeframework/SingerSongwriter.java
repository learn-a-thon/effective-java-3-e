package com.example.effectivejava3e.week7.item20.typeframework;

public interface SingerSongwriter extends Singer, Songwriter{

    AudioClip strum();
    void actSensitive();
}
