package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BodyScale;
import org.springframework.stereotype.Repository;


@Repository
public interface BodyScaleMapper {

    int insert(BodyScale bodyScale);

    int updateByPrimaryKey(BodyScale bodyScale);

}