package com.zsy.goods.service.impl;

import com.zsy.goods.mapper.BookMapper;
import com.zsy.goods.pojo.*;
import com.zsy.goods.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BookServiceImpl implements BookService {
	
	@Resource
	private BookMapper bookMapper;

	/**
	 * 按分类查
	 */
	@Override
	public Pagination findByCategory(String cid, Integer pageNum) {
		Pagination pagination = new Pagination();
		/*
		 * 得到ps
		 */
		Integer pageSize = PageConstants.BOOK_PAGE_SIZE;//每页记录数
		//获取中记录数
		Integer totalCount = bookMapper.Count(cid, null);

		if(totalCount % pageSize == 0){
			pagination.setTotalPage(totalCount / pageSize);
		}else{
			pagination.setTotalPage(totalCount / pageSize + 1);
		}

		if(pageNum < 1){
			pageNum = 1;
		}
		if(pageNum > pagination.getTotalPage() && pageNum != 1){
			pageNum = pagination.getTotalPage();
		}
		pagination.setPageNum(pageNum);
		//size*(page-1)
		Integer offset = pageSize*(pageNum-1);//当前页首行记录的下标

		List<Book> beanList = bookMapper.find(cid, null, offset, pageSize);

		if(beanList.size() == 0){
			pagination.setPageList(null);
		}else{
			pagination.setPageList(beanList);
		}
		pagination.setPagintion();
		return pagination;
	}
	/**
	 * 按书名查
	 */
//	@Override
//	public Page<Book> findByBname(String bname, int pc) {
//		/*
//		 * 得到ps
//		 */
//		int ps = PageConstants.BOOK_PAGE_SIZE;//每页记录数
//		//获取中记录数
//		int tr = bookMapper.Count(null, bname, null, null);
//		int pc1 = (pc-1) * ps;//当前页首行记录的下标
//
//		List<Book> beanList = bookMapper.find(null, bname, null, null, pc1, ps);
//		Page<Book> pb = new Page<Book>();
//		pb.setBeanList(beanList);
//		pb.setPc(pc);
//		pb.setPs(ps);
//		pb.setTr(tr);
//		return pb;
//	}
	/**
	 * 按作者查
	 */
//	@Override
//	public Page<Book> findByAuthor(String author, int pc) {
//		/*
//		 * 得到ps
//		 */
//		int ps = PageConstants.BOOK_PAGE_SIZE;//每页记录数
//		//获取中记录数
//		int tr = bookMapper.Count(null, null, author, null);
//		int pc1 = (pc-1) * ps;//当前页首行记录的下标
//
//		List<Book> beanList = bookMapper.find(null, null, author, null, pc1, ps);
//		Page<Book> pb = new Page<Book>();
//		pb.setBeanList(beanList);
//		pb.setPc(pc);
//		pb.setPs(ps);
//		pb.setTr(tr);
//		return pb;
//	}
	/**
	 * 按出版社查
	 */
//	@Override
//	public Page<Book> findByPress(String press, int pc) {
//		/*
//		 * 得到ps
//		 */
//		int ps = PageConstants.BOOK_PAGE_SIZE;//每页记录数
//		//获取中记录数
//		int tr = bookMapper.Count(null, null, null, press);
//		int pc1 = (pc-1) * ps;//当前页首行记录的下标
//
//		List<Book> beanList = bookMapper.find(null, null, null, press, pc1, ps);
//		Page<Book> pb = new Page<Book>();
//		pb.setBeanList(beanList);
//		pb.setPc(pc);
//		pb.setPs(ps);
//		pb.setTr(tr);
//		return pb;
//	}

	/**
	 * 多条件组合查询
	 */
//	@Override
//	public Page<Book> findByCombination(Book criteria, int pc) {
//		/*
//		 * 得到ps
//		 */
//		int ps = PageConstants.BOOK_PAGE_SIZE;//每页记录数
//		//获取中记录数
//		int tr = bookMapper.Count(null, criteria.getBname(), criteria.getAuthor(), criteria.getPress());
//		int pc1 = (pc-1) * ps;//当前页首行记录的下标
//
//		List<Book> beanList = bookMapper.find(null, criteria.getBname(), criteria.getAuthor(), criteria.getPress(), pc1, ps);
//		Page<Book> pb = new Page<Book>();
//		pb.setBeanList(beanList);
//		pb.setPc(pc);
//		pb.setPs(ps);
//		pb.setTr(tr);
//		return pb;
//	}
	
	/**
	 * 查询一本书的分类
	 */
	@Override
	public Book findByBid(String bid) {
		Book book = bookMapper.findByBid(bid);
		book.setCategory(bookMapper.findByCid(book.getCid()));
		return book;
	}
	@Override
	public int findBookCountByCategory(String cid) {
		
		return bookMapper.Count(cid,null);
	}
	
	@Override
	public Category findCategoryByCid(String cid) {
		
		return bookMapper.findByCid(cid);
	}
	/**
	 * 添加图书
	 */
	@Override
	public int add(Book book) {
		return bookMapper.add(book);
	}
	@Override
	public int delete(String bid) {
		// TODO Auto-generated method stub
		return bookMapper.delete(bid);
	}
	@Override
	public int edit(Book book) {
		// TODO Auto-generated method stub
		return bookMapper.edit(book);
	}

	@Override
	public Pagination findBySearchMsg(String searchMsg, Integer pageNum) {
		Pagination pagination = new Pagination();
		/*
		 * 得到ps
		 */
		Integer pageSize = PageConstants.BOOK_PAGE_SIZE;//每页记录数
		//获取中记录数
		Integer totalCount = bookMapper.Count(null,searchMsg);

		if(totalCount % pageSize == 0){
			pagination.setTotalPage(totalCount / pageSize);
		}else{
			pagination.setTotalPage(totalCount / pageSize + 1);
		}

		if(pageNum < 1){
			pageNum = 1;
		}
		if(pageNum > pagination.getTotalPage() && pageNum != 1){
			pageNum = pagination.getTotalPage();
		}
		pagination.setPageNum(pageNum);
		//size*(page-1)
		Integer offset = pageSize*(pageNum-1);//当前页首行记录的下标

		List<Book> beanList = bookMapper.find(null, searchMsg, offset, pageSize);
		if(beanList.size() == 0){
			pagination.setPageList(null);
		}else{
			pagination.setPageList(beanList);
		}
		pagination.setPagintion();
		return pagination;
	}

}
