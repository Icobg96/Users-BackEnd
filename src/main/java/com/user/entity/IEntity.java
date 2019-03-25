package com.user.entity;

import com.user.dto.IDto;

public interface IEntity extends Identifiable<Long>{
    public IDto convertOnDto();
}
