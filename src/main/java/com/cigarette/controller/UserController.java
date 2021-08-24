package com.cigarette.controller;

import com.cigarette.common.annotation.NoRepeatSubmit;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.response.ApiRestResponse;
import com.cigarette.common.utils.ConvertUtils;
import com.cigarette.common.utils.JwtUtils;
import com.cigarette.controller.viewObject.UserRegisterVo;
import com.cigarette.controller.viewObject.UserReturnVo;
import com.cigarette.service.UserService;
import com.cigarette.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EdwardLee
 * @create 2021-08-08 23:47
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @NoRepeatSubmit
    @PostMapping("/user/add")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiRestResponse register(@RequestBody @Valid UserRegisterVo userRegisterVo) throws BusinessException {
        // 验证码校验，暂时由emp过滤器实现

        // 参数校验，其他参数由 @Valid + UserVo上的注解结合判断

        // vo转为model
        UserModel record = ConvertUtils.convertVoToModel(userRegisterVo);
        // todo 由于该方法暂时用不到，故先设置role为无效值
        record.setRole(0);

        // 调用service插入
        userService.register(record);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    public ApiRestResponse login(@RequestBody Map<String, String> map) throws BusinessException {
        String tel = map.get("tel");
        String password = map.get("password");
        // 入参校验
        if (StringUtils.isEmpty(tel) || StringUtils.isEmpty(password)) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 生成并返回token
        String token = userService.login(tel, password);
        HashMap<String, String> returnMap = new HashMap<>(1);
        returnMap.put("MC_TOKEN", token);
        return ApiRestResponse.success(returnMap);
    }

    @PostMapping("/user/logout")
    public ApiRestResponse logout(HttpServletRequest request) throws BusinessException {
        // todo 登出，暂时实现为：判断用户是否有效后打印即可

        if(request != null){
            Integer infoByJwtToken = (Integer) JwtUtils.getInfoByJwtToken(request);
            if(infoByJwtToken != null){
                return ApiRestResponse.success();
            }
        }
        return ApiRestResponse.error(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
    }

    @GetMapping("/user/info")
    public ApiRestResponse geInfo(HttpServletRequest request) throws BusinessException {// @RequestParam 默认是参数名
        // 需登录，暂时由global过滤器实现
        String token = request.getHeader("MC_TOKEN");

        // 有效性确认
        boolean isLegal = JwtUtils.checkToken(token);
        if (!isLegal) {
            throw new BusinessException(EnumBusinessError.TOKEN_ILLEGAL);
        }

        // 查询用户信息
        UserReturnVo userReturnVo = userService.getInfo(token);

        return ApiRestResponse.success(userReturnVo);
    }

    @NoRepeatSubmit
    @PostMapping({"/user/{id}"})
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiRestResponse updateInfo(@PathVariable Integer id,
                                      @RequestBody @Valid UserRegisterVo userRegisterVo) throws BusinessException {
        // 需登录，暂时由global过滤器实现

        // 入参校验
        if (id == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = ConvertUtils.convertVoToModel(userRegisterVo);
        // 修改用户信息
        userService.updateInfo(id, userModel);

        return ApiRestResponse.success();
    }

    @GetMapping("/user/sellers")
    public ApiRestResponse getAllEnabledSellers() {
        // 鉴权，仅emp可操作，实现批量添加seller的方法，暂时由 emp filter 实现
        List<UserModel> allEnabledSellers = userService.getAllEnabledSellers();
        if (allEnabledSellers != null) {
            List<UserReturnVo> userReturnVos = new ArrayList<>(allEnabledSellers.size());
            for (int i = 0; i < allEnabledSellers.size(); i++) {
                UserModel userModel = allEnabledSellers.get(i);
                UserReturnVo userReturnVo = ConvertUtils.convertModelToVo(userModel);
                userReturnVos.add(userReturnVo);
            }
            return ApiRestResponse.success(userReturnVos);
        }
        return ApiRestResponse.success();
    }
}
