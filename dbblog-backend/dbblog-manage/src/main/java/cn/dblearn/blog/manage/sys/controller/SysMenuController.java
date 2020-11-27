package cn.dblearn.blog.manage.sys.controller;


import cn.dblearn.blog.auth.service.ShiroService;
import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.base.AbstractController;
import cn.dblearn.blog.common.enums.MenuTypeEnum;
import cn.dblearn.blog.common.exception.MyException;
import cn.dblearn.blog.common.exception.ServiceException;
import cn.dblearn.blog.common.util.BeanUtil;
import cn.dblearn.blog.entity.sys.SysMenu;
import cn.dblearn.blog.entity.sys.dto.MenuPermissionsDto;
import cn.dblearn.blog.manage.sys.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author bobbi
 * @since 2018-10-19
 */

@RestController
@RequestMapping("/admin/sys/menu")
@Api(value = "菜单管理" ,tags = "菜单管理")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ShiroService shiroService;

    @ApiOperation(value = "【菜单授权】")
    @GetMapping("/nav")
    public Result nav(){
        List<SysMenu> menuList=sysMenuService.listUserMenu(getUserId());
        Set<String> permissions=shiroService.getUserPermissions(getUserId());
        return Result.sysSuccess(new MenuPermissionsDto(menuList, permissions));
    }

    @ApiOperation(value = "【菜单列表】")
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenu> list(){
        List<SysMenu> menuList = sysMenuService.list(null);
        menuList.forEach(sysMenu -> {
            SysMenu parentMenu = sysMenuService.getById(sysMenu.getParentId());
            if(parentMenu != null){
                sysMenu.setParentName(parentMenu.getName());
            }
        });
        return menuList;
    }

    @ApiOperation(value = "【选择菜单(添加、修改菜单)】")
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0);
        root.setName("一级菜单");
        root.setParentId(-1);
        root.setOpen(true);
        menuList.add(root);

        return Result.sysSuccess(menuList);
    }

    @ApiOperation(value = "【菜单详情】")
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result update(@PathVariable Integer menuId){
        SysMenu menu = sysMenuService.getById(menuId);
        return Result.sysSuccess(menu);
    }

    @ApiOperation(value = "【保存菜单】")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return Result.sysSuccess();
    }

    @ApiOperation(value = "【更新菜单】")
    @PutMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return Result.sysSuccess();
    }

    @ApiOperation(value = "【删除菜单】")
    @DeleteMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public Result delete(@PathVariable Integer menuId){
        if(menuId <= 29){
            return Result.exception("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return Result.exception("请先删除子菜单或按钮");
        }
        sysMenuService.delete(menuId);
        return Result.sysSuccess();
    }
    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new ServiceException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new ServiceException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == MenuTypeEnum.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new ServiceException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = MenuTypeEnum.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == MenuTypeEnum.CATALOG.getValue() ||
                menu.getType() == MenuTypeEnum.MENU.getValue()) {
            if (parentType != MenuTypeEnum.CATALOG.getValue()) {
                throw new ServiceException("上级菜单只能为目录类型");
            }
        }

        //按钮
        if (menu.getType() == MenuTypeEnum.BUTTON.getValue()) {
            if (parentType != MenuTypeEnum.MENU.getValue()) {
                throw new ServiceException("上级菜单只能为菜单类型");
            }
        }
    }

}
