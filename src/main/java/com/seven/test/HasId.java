package com.seven.test;

public interface HasId {
    Integer getId();

    void setId(Integer id);

    default boolean isNew() {
        return (getId() == null);
    }

    default boolean isNotNew() {
        return (getId() != null);
    }
}
