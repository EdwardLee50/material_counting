package com.cigarette.service;

import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.utils.ConvertUtils;
import com.cigarette.common.utils.JwtUtils;
import com.cigarette.common.utils.Md5Utiles;
import com.cigarette.common.utils.TimeUtils;
import com.cigarette.controller.viewObject.UserRegisterVo;
import com.cigarette.controller.viewObject.UserReturnVo;
import com.cigarette.domain.UserPassword;
import com.cigarette.mapper.UserPasswordMapper;
import com.cigarette.service.model.UserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cigarette.domain.UserInfo;
import com.cigarette.mapper.UserInfoMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author EdwardLee
 * @create 2021-08-09 11:04
 */
@Service
public class UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserPasswordMapper userPasswordMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void register(UserModel userModel) throws BusinessException {

        // 由于register加了@Transactional，故出现异常时会回滚
        // 手机号需唯一，验证手机号是否存在
        if (!checkTelIdNotExists(userModel.getTel())) {
            throw new BusinessException(EnumBusinessError.USER_TEL_EXISTS);
        }
        // 获取操作时间
        Date now = TimeUtils.getNowOfDateObj();
        userModel.setGmtCreate(now);
        userModel.setGmtModify(now);
        // 获取info并插入
        UserInfo userInfo = ConvertUtils.convertModelToInfoDo(userModel);
        userInfoMapper.insertSelective(userInfo);
        userModel.setId(userInfo.getId());
        // 获取password并插入
        UserPassword userPassword = ConvertUtils.convertModelToPasswordDo(userModel);
        userPasswordMapper.insert(userPassword);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void batchRegister(List<UserModel> list) throws BusinessException {
        if(list == null){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 判断手机号唯一性
        // 获取插入用户的手机集合
        List<String> tels = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            tels.add(list.get(i).getTel());
        }
        // 根据tels查userId
        List<Integer> ids = userInfoMapper.selectEnabledInfoIdByTels(tels);
        if(ids.size() != 0){
            throw new BusinessException(EnumBusinessError.USER_TEL_EXISTS);
        }

        // 获取时间
        Date now = TimeUtils.getNowOfDateObj();

        // 获取info并插入
        List<UserInfo> userInfos = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            UserModel userModel = list.get(i);
            UserInfo userInfo = ConvertUtils.convertModelToInfoDo(userModel);
            userInfo.setGmtCreate(now);
            userInfo.setGmtModify(now);
            userInfo.setIsDisabled(false);
            userInfos.add(userInfo);
        }
        userInfoMapper.batchInsert(userInfos);

        for (int i = 0; i < userInfos.size(); i++) {
            list.get(i).setId(userInfos.get(i).getId());
        }

        // 获取password并插入
        List<UserPassword> userPasswords = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            UserModel userModel = list.get(i);
            UserPassword userPassword = ConvertUtils.convertModelToPasswordDo(userModel);
            userPassword.setGmtCreate(now);
            userPassword.setGmtModify(now);
            userPasswords.add(userPassword);
        }
        userPasswordMapper.batchInsert(userPasswords);
    }

    /**
     * tel不存在返回true，tel存在返回false
     *
     * @param tel
     * @return
     */
    private boolean checkTelIdNotExists(String tel) {
        Integer id = userInfoMapper.selectEnabledInfoIdByTel(tel);
        return id == null;
    }

    public String login(String tel, String password) throws BusinessException {
        // 获取userId，且应只有一个
        Integer id = userInfoMapper.selectEnabledInfoIdByTel(tel);
        if(id == null){
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }
        // 获取userPassword，判断密码是否匹配
        UserPassword userPassword = userPasswordMapper.selectByUserId(id);
        String md5Str = Md5Utiles.getMd5Str(password);
        if (!md5Str.equals(userPassword.getEncryptedPassword())) {
            throw new BusinessException(EnumBusinessError.USER_LOGIN_FAIL);
        }

        // 生成token并返回
        String jwtToken = JwtUtils.getJwtToken(id);

        // todo 用redis+token重写

        return jwtToken;
    }

    public UserReturnVo getInfo(String token) throws BusinessException {
        // 用户确认，token中存在id，查询用户信息
        Object infoByJwtToken = JwtUtils.getInfoByJwtToken(token);
        if(infoByJwtToken == null){
            throw new BusinessException(EnumBusinessError.USER_PERMISSION_DENIED);
        }
        // 信息查询
        Integer id = Integer.parseInt(String.valueOf(infoByJwtToken));
        UserInfo userInfo = userInfoMapper.selectEnabledInfoByPrimaryKey(id);
        if(userInfo == null){
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }
        UserModel userModel = ConvertUtils.convertInfoDoToModel(userInfo);
        // model转为Vo
        return ConvertUtils.convertModelToVo(userModel);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateInfo(Integer id, UserModel userModel) throws BusinessException {

        // 获取修改时间
        Date now = TimeUtils.getNowOfDateObj();
        if(id == null){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 判断用户是否存在
        UserInfo userInfo = userInfoMapper.selectEnabledInfoByPrimaryKey(id);
        if(userInfo == null){
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }
        // 判断同手机启用用户是否存在
        Integer oldId = userInfoMapper.selectEnabledInfoIdByTel(userModel.getTel());
        if (oldId != null) {
            throw new BusinessException(EnumBusinessError.USER_TEL_EXISTS);
        }

        // 将model转为domain
        if(userModel == null){
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        userModel.setId(id);
        UserInfo insertInfo = ConvertUtils.convertModelToInfoDo(userModel, now);
        // 修改信息
        userInfoMapper.updateByPrimaryKeySelective(insertInfo);
    }

    public List<UserModel> getAllEnabledSellers() {
        List<UserInfo> enabledSellers = userInfoMapper.selectEnabledSellers();
        if (enabledSellers != null) {
            List<UserModel> userModels = new ArrayList<>(enabledSellers.size());
            for (int i = 0; i < enabledSellers.size(); i++) {
                UserInfo userInfo = enabledSellers.get(i);
                UserModel userModel = ConvertUtils.convertInfoDoToModel(userInfo);
                userModels.add(userModel);
            }
            return userModels;
        }
        return null;
    }
}
