package com.cigarette.controller.viewObject;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Date;

/**
 * @author EdwardLee
 * @create 2021-08-13 20:16
 */
@ApiModel(value = "com-cigarette-domain-Order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateVo implements Serializable   {

    private static final long serialVersionUID = 3634021894569533122L;

    @ExcelProperty("物料名称")
    @NotNull(message = "物料名不可为空")
    @Length(min = 1,max = 16,message = "物料名不可超过16个字符")
    @ApiModelProperty(value = "物料名不可为空，不可超过16个字符")
    private String material;

    @ExcelProperty("计量单位")
    @NotNull(message = "计量单位不可为空")
    @Length(min = 1,max = 4,message = "物料单位字符描述需在1-4字符之间")
    @ApiModelProperty(value = "物料单位字符描述需在1-4字符之间")
    private String unit;

    @ExcelProperty("数量")
    @NotNull(message = "物料数量不可为空")
    @Positive(message = "物料数量需为正整数")
    @ApiModelProperty(value = "物料数量需为正整数")
    private Integer number;

    @ExcelProperty("描述")
    @ApiModelProperty(value = "物料描述，可以为空")
    private String description;

    @ExcelProperty("商家电话")
    @NotNull(message = "商家电话不可为空")
    @ApiModelProperty(value = "商家电话，不可为空")
    private String shopSellerTel;
}