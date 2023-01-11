package com.example.epapp_demo.model.local.modul;

import java.io.Serializable;

class FavoriteStore implements Serializable {
    private int Like;

    public int getLike() {
        return Like;
    }

    public void setLike(int like) {
        Like = like;
    }

    public FavoriteStore() {
    }

    public FavoriteStore(int like) {

        Like = like;
    }
}
