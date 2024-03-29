package com.example.pokeapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Move {

    @SerializedName("move")
    @Expose
    private Move__1 move;
    @SerializedName("version_group_details")
    @Expose
    private List<VersionGroupDetail> versionGroupDetails = null;

    public Move__1 getMove() {
        return move;
    }

    public void setMove(Move__1 move) {
        this.move = move;
    }

    public List<VersionGroupDetail> getVersionGroupDetails() {
        return versionGroupDetails;
    }

    public void setVersionGroupDetails(List<VersionGroupDetail> versionGroupDetails) {
        this.versionGroupDetails = versionGroupDetails;
    }

}

