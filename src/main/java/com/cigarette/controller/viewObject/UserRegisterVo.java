package com.cigarette.controller.viewObject;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author EdwardLee
 * @create 2021-08-09 11:04
 *
 * 注册时，对于前端传递user信息的包装
 */

@ApiModel(value = "com-cigarette-domain-UserInfo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVo implements Serializable {

    private static final long serialVersionUID = 3996153800849235287L;

    @NotNull(message = "姓名不可为空")
    @Length(max = 4, message = "姓名不可超过4位")
    @ApiModelProperty(value = "")
    private String name;

    @NotNull(message = "密码不可为空")
    @ApiModelProperty(value = "")
    private String password;

    @NotNull(message = "电话不可为空")
    @Length(max = 11, message = "联系电话需小于11位")
    @ApiModelProperty(value = "")
    private String tel;

}