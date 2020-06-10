package arca.domain.entities;

import arca.util.GsonUtil;

import java.io.Serializable;

public abstract class JsonModel implements Serializable {
    public String toJson(){
        return GsonUtil.GSON.toJson(this);
    }
}
