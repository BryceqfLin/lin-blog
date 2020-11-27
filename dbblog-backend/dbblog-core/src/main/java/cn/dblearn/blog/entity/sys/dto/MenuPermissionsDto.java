package cn.dblearn.blog.entity.sys.dto;

import cn.dblearn.blog.entity.sys.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@ApiModel(value = "MenuPermissionsDto", description = "菜单权限封装类")
public class MenuPermissionsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单列表", name = "menuList", dataType = "List")
    private List<SysMenu> menuList;

    @ApiModelProperty(value = "权限列表",name = "permissionList",dataType = "List")
    private Set<String> permissionList;

    public MenuPermissionsDto(){

    }

    public MenuPermissionsDto(List<SysMenu> menuList, Set<String> permissionList) {
        this.menuList = menuList;
        this.permissionList = permissionList;
    }
}
