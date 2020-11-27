package cn.dblearn.blog.manage.book.controller;

import cn.dblearn.blog.common.Result;
import cn.dblearn.blog.common.base.AbstractController;
import cn.dblearn.blog.common.constants.RedisCacheNames;
import cn.dblearn.blog.common.util.PageUtils;
import cn.dblearn.blog.common.validator.ValidatorUtils;
import cn.dblearn.blog.entity.book.Book;
import cn.dblearn.blog.entity.book.dto.BookDTO;
import cn.dblearn.blog.manage.book.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 图书表 前端控制器
 * </p>
 *
 * @author bobbi
 * @since 2019-01-27
 */
@RestController
@RequestMapping("/admin/book")
@CacheConfig(cacheNames ={RedisCacheNames.RECOMMEND,RedisCacheNames.TAG,RedisCacheNames.BOOK,RedisCacheNames.TIMELINE})
@Api(value = "书籍管理", tags = {"书籍管理"})
public class BookController extends AbstractController {
    @Resource
    private BookService bookService;

    @ApiOperation(value = "【分页查询书籍列表】")
    @GetMapping("/list")
    @RequiresPermissions("book:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = bookService.queryPage(params);

        return Result.sysSuccess(page);
    }

    @ApiOperation(value = "【获取所有书籍列表】")
    @GetMapping("/select")
    @RequiresPermissions("book:list")
    public Result select() {
        List<Book> bookList = bookService.list(null);
        return Result.sysSuccess(bookList);
    }

    @ApiOperation(value = "【书籍详情】")
    @GetMapping("/info/{id}")
    @RequiresPermissions("book:info")
    public Result info(@PathVariable("id") String id) {
        BookDTO book = bookService.getBook(id);
        return Result.sysSuccess(book);
    }

    @ApiOperation(value = "【新增书籍】")
    @PostMapping("/save")
    @CacheEvict(allEntries = true)
    @RequiresPermissions("book:save")
    public Result save(@RequestBody BookDTO book) {
        ValidatorUtils.validateEntity(book);
        bookService.saveBook(book);

        return Result.sysSuccess();
    }

    @ApiOperation(value = "【更新书籍】")
    @PutMapping("/update")
    @CacheEvict(allEntries = true)
    @RequiresPermissions("book:update")
    public Result update(@RequestBody BookDTO book) {
        ValidatorUtils.validateEntity(book);
        bookService.updateBook(book);
        return Result.sysSuccess();
    }

    @ApiOperation(value = "【更新状态】")
    @PutMapping("/update/status")
    @CacheEvict(allEntries = true)
    @RequiresPermissions("book:update")
    public Result updateStatus(@RequestBody Book readBook) {
        bookService.updateById(readBook);
        return Result.sysSuccess();
    }

    @ApiOperation(value = "【删除书籍】")
    @DeleteMapping("/delete")
    @CacheEvict(allEntries = true)
    @RequiresPermissions("book:delete")
    public Result delete(@RequestBody Integer[] ids) {
        bookService.deleteBatch(ids);

        return Result.sysSuccess();
    }


}
