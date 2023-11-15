package com.sky.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class Entity<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "主键")
    protected T id;

    @ApiModelProperty(value = "最后修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateTime;

    @ApiModelProperty(value = "最后修改人ID")
    @TableField( fill = FieldFill.INSERT_UPDATE)
    protected T updateUser;



    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "创建人ID")
    @TableField(fill = FieldFill.INSERT)
    protected T createUser;



}
