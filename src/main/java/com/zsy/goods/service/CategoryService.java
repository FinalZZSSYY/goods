package com.zsy.goods.service;

import com.zsy.goods.pojo.Category;

import java.util.List;

/**
 * 分类模块业务层
 * @author ASUS
 *
 */
public interface CategoryService {
	/**
	 * 查询所有分类
	 */
	List<Category> findAll();

	Integer add(Category category);

	List<Category> findParents();
	
	Category finParent(String pid);
	
	/**
	 * 查询指定父分类下子分类的个数
	 */
	Integer findChildrenCountByParent(String pid);
	
	/**
	 * 删除分类
	 */
	Integer delete(String cid);
	
	/**
	 * 修改分类
	 */
	Integer edit(Category category);
	
	/**
	 * 加载分类
	 */
	Category load(String cid);
		
	Category findParentByCid(String cid);

	List<Category> findChildren(String pid);
	
}
