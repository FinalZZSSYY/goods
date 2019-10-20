package com.zsy.goods.mapper;

import com.zsy.goods.pojo.Book;
import com.zsy.goods.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

	/**
	 * 查询
	 */
	List<Book> find(@Param("cid") String cid, @Param("searchMsg") String searchMsg, @Param("pc") int pc, @Param("ps") int ps);
	
	/*
	 * 总记录数 
	 */
	Integer Count(@Param("cid") String cid, @Param("searchMsg") String searchMsg);

	/**
	 * 查询一本书详细信息
	 */
	Book findByBid(@Param("bid")String bid);
	
	/**
	 * 查询一本书的分类
	 */
	Category findByCid(@Param("cid")String cid);

	/**
	 * 查询指定分类下图书的个数
	 */
//	@Select("select count(*) from t_book where cid = #{0}")
//	int findBookCountByCategory(String cid);
	
	/**
	 * 添加图书
	 */
	Integer add(@Param("book")Book book);

	/**
	 * 删除
	 */
	Integer delete(@Param("bid")String bid);
	
	/**
	 * 修改
	 */
	Integer edit(@Param("book")Book book);
	
}
