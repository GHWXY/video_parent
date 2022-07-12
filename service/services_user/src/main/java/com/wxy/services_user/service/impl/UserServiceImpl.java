package com.wxy.services_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_base.exception.FmException;
import com.wxy.services_user.entity.LoginVo;
import com.wxy.services_user.entity.RegisterVo;
import com.wxy.services_user.entity.User;
import com.wxy.services_user.mapper.UserMapper;
import com.wxy.services_user.service.UserService;
import com.wxy.utils.JwtUtils;
import com.wxy.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Override
    public String login(LoginVo loginVo) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();
        //校验参数
        if(StringUtils.isEmpty(phone) ||
           StringUtils.isEmpty(password)
        ) {
            throw new FmException(20001,"缺少参数");
        }
        //获取用户
        User user = baseMapper.selectOne(new QueryWrapper<User>()
                .eq("mobile", phone).or().eq("nickname",phone));
        if(null == user) {
            throw new FmException(20001,"用户不存在");
        }
        //校验密码
        if(!MD5.encrypt(password).equals(user.getPassword())) {
            throw new FmException(20001,"密码错误");
        }
        //校验是否被禁用
        if(user.getIsDisabled()) {
            throw new FmException(20001,"用户被禁用");
        }
        //使用JWT生成token字符串
        String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        return token;
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();
        //校验参数
        if(StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(phone) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code))
            throw new FmException(20001,"缺少参数");
        //校验校验验证码
        //从redis获取发送的验证码
        String phoneCode = redisTemplate.opsForValue().get(phone);
        if(!code.equals(phoneCode))
            throw new FmException(20001,"验证码错误");
        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<User>().eq("mobile", phone));
        if(count.intValue() > 0)
            throw new FmException(20001,"手机号已被使用");
        //添加注册信息到数据库
        User member = new User();
        member.setNickname(nickname);
        member.setMobile(registerVo.getPhone());
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://edu-image.nosdn.127.net/4DC4257CE559B29ED80DDA6BE0E4E0DC.jpg?imageView&thumbnail=120y120&quality=100");
        this.save(member);
    }

    @Override
    public User getByOpenid(String openid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        User user = baseMapper.selectOne(queryWrapper);
        return user;
    }


}
