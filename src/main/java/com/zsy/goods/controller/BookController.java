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

import com.zsy.goods.common.utils.StringUtil;
import com.zsy.goods.pojo.Book;
import com.zsy.goods.pojo.Pagination;
import com.zsy.goods.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Zhou ShiYang
 * @Description:
 * @Date:Create：in 2019/9/18 16:59
 */
@Controller()
@RequestMapping("/BookController")
public class BookController {

    @Autowired
    private BookService bookServiceImpl;

    /**
     * 获取当前页码
     * @param req
     * @return
     */
    private int getPc(HttpServletRequest req) {
        int pc = 1;
        String param = req.getParameter("pc");
        if(param != null && !param.trim().isEmpty()) {
            try {
                pc = Integer.parseInt(param);
            } catch(RuntimeException e) {}
        }
        return pc;
    }

    /**
     * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
     * @param req
     * @return
     */
    /*
     * http://localhost:8080/goods/BookController/findByCategory?cid=xxx&pc=3
     * /goods/BookController/findByCategory + cid=xxx&pc=3
     */
    private String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI() + "?" + req.getQueryString();
        /*
         * 如果url中存在pc参数，截取掉，如果不存在那就不用截取。
         */
        int index = url.lastIndexOf("&pageNum=");
        if(index != -1) {
            url = url.substring(0, index);
        }
        return url;
    }

    /**
     * 按分类查
     */
    @GetMapping(value = "/findByCategory")
    public String findByCategory(@RequestParam("cid")String cid,
                                 @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                 HttpServletRequest req){
        /*
         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
         */
        System.out.println(cid);
        /*
         * 2. 得到url：...
         */
        String url = getUrl(req);
        System.out.println(url);
//        /*
//         * 3. 获取查询条件，本方法就是cid，即分类的id
//         */
        /*
         * 4. 使用pc和cid调用service#findByCategory得到Page
         */
        Pagination pagination = bookServiceImpl.findByCategory(cid, pageNum);
        /*
         * 5. 给Page设置url，保存Page，转发到/jsps/book/list.jsp
         */
        pagination.setUrl("/BookController/findByCategory?cid="+cid);
        req.setAttribute("pagination", pagination);
        return "main";
    }

    /**
     * 模糊查询
     */
    @GetMapping(value = "/findBySearchMsg")
    public String findBySearchMsg(@RequestParam(value = "searchMsg",required = false)String searchMsg,
                                  @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                  HttpServletRequest req){
        /*
         * 1. 得到url：...
         */
        String url = "";
        if (StringUtil.isEmpty(searchMsg)){
            url = req.getRequestURI()+"?searchMsg=";
        }else{
            url = req.getRequestURI()+"?searchMsg="+searchMsg;
        }

        /*
         * 2. 使用pc和author调用findByAuthor得到Page
         */
        Pagination pagination = bookServiceImpl.findBySearchMsg(searchMsg, pageNum);
        /*
         * 5. 给Page设置url，保存Page，转发到/jsps/book/list.jsp
         */
        pagination.setUrl("/BookController/findBySearchMsg?searchMsg="+searchMsg);
        req.setAttribute("pagination", pagination);
        return "main";
    }

    /**
     * 按作者查
     */
//    @RequestMapping(value = "/findByAuthor")
//    public String findByAuthor(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
//                               HttpServletRequest req){
//        /*
//         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
//         */
//        /*
//         * 2. 得到url：...
//         */
//        String url = getUrl(req);
//        /*
//         * 3. 获取查询条件，本方法就是author
//         */
//        String author = req.getParameter("author");
//        /*
//         * 4. 使用pc和author调用findByAuthor得到Page
//         */
//        Page<Book> pb = bookServiceImpl.findByAuthor(author, pageNum);
//        /*
//         * 5. 给Page设置url，保存Page，转发到/jsps/book/list.jsp
//         */
//        pb.setUrl(url);
//        req.setAttribute("pb", pb);
//        return "main";
//    }

    /**
     * 按出版社查询
     */
//    @RequestMapping(value = "/findByPress")
//    public String findByPress(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
////		System.out.println("===========================");
//        /*
//         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
//         */
//        int pc = getPc(req);
////		System.out.println(pc+"++++++++++++++++++++++++++++");
//        /*
//         * 2. 得到url：...
//         */
//        String url = getUrl(req);
////		System.out.println(url+"++++++++++++++++++++++++++++");
//        /*
//         * 3. 获取查询条件，本方法就是author
//         */
//        String press = req.getParameter("press");
//        /*
//         * 4. 使用pc和press调用findByPress得到Page
//         */
//        Page<Book> pb = bookServiceImpl.findByPress(press, pc);
//        /*
//         * 5. 给Page设置url，保存Page，转发到/jsps/book/list.jsp
//         */
//        pb.setUrl(url);
//        req.setAttribute("pb", pb);
//        return "/jsps/book/list.jsp";
//    }

    /**
     * 按图名查
     */
//    @RequestMapping(value = "/findByBname")
//    public String findByBname(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
////		System.out.println("===========================");
//        /*
//         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
//         */
//        int pc = getPc(req);
////		System.out.println(pc+"++++++++++++++++++++++++++++");
//        /*
//         * 2. 得到url：...
//         */
//        String url = getUrl(req);
////		System.out.println(url+"++++++++++++++++++++++++++++");
//        /*
//         * 3. 获取查询条件，本方法就是author
//         */
//        String bname = req.getParameter("bname");
//        /*
//         * 4. 使用pc和bname调用findByBname得到Page
//         */
//        Page<Book> pb = bookServiceImpl.findByBname(bname, pc);
//        /*
//         * 5. 给Page设置url，保存Page，转发到/jsps/book/list.jsp
//         */
//        pb.setUrl(url);
//        req.setAttribute("pb", pb);
//        return "/jsps/book/list.jsp";
//    }
    /**
     * 多条件组合查询
     */
//    @RequestMapping(value = "/findByCombination")
//    public String findByCombination(Book book,HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
////		System.out.println("===========================");
//        /*
//         * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
//         */
//        int pc = getPc(req);
////		System.out.println(pc+"++++++++++++++++++++++++++++");
//        /*
//         * 2. 得到url：...
//         */
//        String url = getUrl(req);
////		System.out.println(url+"++++++++++++++++++++++++++++");
//
//        /*
//         * 3. 使用pc和book调用findByCombination得到Page
//         */
//        Page<Book> pb = bookServiceImpl.findByCombination(book, pc);
//        /*
//         * 4. 给Page设置url，保存Page，转发到/jsps/book/list.jsp
//         */
//        pb.setUrl(url);
//        req.setAttribute("pb", pb);
//        return "/jsps/book/list.jsp";
//    }


    /**
     * 查询一本书的详细信息,按bid查询
     */
    @RequestMapping(value = "/load")
    public String load(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        String bid = req.getParameter("bid");//获取链接的参数bid
        Book book = bookServiceImpl.findByBid(bid);//通过bid得到book对象
        req.setAttribute("book", book);//保存到req中
        return "/jsps/book/desc.jsp";//转发到desc.jsp
    }
}
