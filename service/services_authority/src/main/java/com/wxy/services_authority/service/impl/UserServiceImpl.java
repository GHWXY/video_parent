package com.wxy.services_authority.service.impl;

import com.wxy.services_authority.entity.User;
import com.wxy.services_authority.mapper.UserMapper;
import com.wxy.services_authority.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
