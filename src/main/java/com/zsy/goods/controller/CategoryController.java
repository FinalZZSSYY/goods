package com.zsy.goods.controller;//
//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                           O\  =  /O
//                        ____/`---'\____
//                      .'  \\|     |//  `.
//                     /  \\|||  :  |||//  \
//                    /  _||||| -:- |||||-  \
//                    |   | \\\  -  /// |   |
//                    | \_|  ''\---/''  |   |
//                    \  .-\__  `-`  ___/-. /
//                  ___`. .'  /--.--\  `. . __
//               ."" '<  `.___\_<|>_/___.'  >'"".
//              | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//              \  \ `-.   \_ __\ /__ _/   .-` /  /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//        ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
//                      Buddha Bless, No Bug !

import com.zsy.goods.pojo.Category;
import com.zsy.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Zhou ShiYang
 * @Description:
 * @Date:Create：in 2019/9/23 15:11
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryServiceImpl;

    /**
     * 显示所有分类
     */
    @GetMapping("/")
    public String findCategoryAll(HttpServletRequest req){
//        System.out.println("=============================================");
        /*
         * 1. 通过service得到所有的分类
         * 2. 保存到request中，转发到main.html
         */
        List<Category> parents = categoryServiceImpl.findAll();
        req.getSession().setAttribute("parents", parents);
        return "main";
    }
}
