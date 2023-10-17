package com.example.unimeeting.dao;

import com.example.unimeeting.domain.CloudtypeUserVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CloudtypeTestMapper {
  @Select("SELECT * FROM user")
  public List<CloudtypeUserVO> list();
}

