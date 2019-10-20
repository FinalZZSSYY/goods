package com.zsy.goods.service;

import com.zsy.goods.pojo.Book;
import com.zsy.goods.pojo.Category;
import com.zsy.goods.pojo.Pagination;

public interface BookService {
	/**
	 * 按分类查
	 */
	Pagination findByCategory(String cid, Integer pageNum);
	
	/**
	 * 按书名查
	 */
//	Page<Book> findByBname(String bname, int pc);
	
	/**
	 * 按作者查
	 */
//	Page<Book> findByAuthor(String author, int pc);
	
	/**
	 * 按出版社查
	 */
//	Page<Book> findByPress(String press, int pc);
	
	/**
	 * 多条件组合查询
	 */
//	Page<Book> findByCombination(Book criteria, int pc);
		
	/**
	 * 查询一本书的信息
	 */
	Book findByBid(String bid);

	int findBookCountByCategory(String cid);

	Category findCategoryByCid(String cid);
	
	int add(Book book);

	int delete(String bid);

	int edit(Book book);

	Pagination findBySearchMsg(String searchMsg, Integer pageNum);
}
