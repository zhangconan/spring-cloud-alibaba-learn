package com.zkn.mybatis.dao.first;

import com.zkn.mybatis.entity.UserInfo;

/**
 * @author conanzhang@木森
 * @description
 * @date 2020-04-05 12:11
 * @classname FirstDAO
 */
public interface FirstDAO {

    /**
     * 根据ID查询值
     *
     * @param id
     * @return
     */
    UserInfo selectById(Long id);
}
