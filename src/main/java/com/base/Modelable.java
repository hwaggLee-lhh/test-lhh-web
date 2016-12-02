package com.base;

import java.io.Serializable;

public interface Modelable extends JSONNotAware,Serializable, Cloneable {
    String getIdStr();
    void setIdStr(String idStr);
}
