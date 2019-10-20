package com.zsy.goods.service.impl;

import com.zsy.goods.mapper.CategoryMapper;
import com.zsy.goods.pojo.Category;
import com.zsy.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类模块业务层实现类
 * @author ASUS
 *
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * 返回所有分类 
	 */
	@Override
	public List<Category> findAll() {
		List<Category> parents = categoryMapper.findAllFirst();
		for(Category parent : parents) {
			// 查询出当前父分类的所有子分类
			parent.setParent(null);
			List<Category> children = categoryMapper.findByParent(parent.getCid());
			for(Category c : children) {
				c.setParent(parent);
			}
			// 设置给父分类
			parent.setChildren(children);
		}
		return parents;
	}
	/**
	 * 添加分类
	 */
	@Override
	public Integer add(Category category) {
		return categoryMapper.add(category);
	}
	/**
	 * 添加第二分类：第一步
	 */
	@Override
	public List<Category> findParents() {
		
		return categoryMapper.findAllFirst();
	}
	
	@Override
	public Category finParent(String pid) {
		// TODO Auto-generated method stub
		return categoryMapper.findParent(pid);
	}
	
	
	/**
	 * 查询指定父分类下子分类的个数
	 */
	public Integer findChildrenCountByParent(String pid) {
		return categoryMapper.findChildrenCountByParent(pid);
		
	}
	
	/**
	 * 删除分类
	 */
	public Integer delete(String cid) {
		
		return categoryMapper.delete(cid);
	}
	
	/**
	 * 修改分类
	 */
	public Integer edit(Category category) {
		
		return categoryMapper.edit(category);
	}
	
	/**
	 * 加载分类
	 */
	public Category load(String cid) {
		Category category = categoryMapper.load(cid);
		if(category.getPid() == null) {
			// 查询出当前父分类的所有子分类
			category.setParent(null);
			List<Category> children = categoryMapper.findByParent(category.getCid());
			for(Category c : children) {
				c.setParent(category);
			}
			// 设置给父分类
			category.setChildren(children);
		}else {
			category.setParent(categoryMapper.load(category.getPid()));
		}
		
		return category;
	}
	@Override
	public Category findParentByCid(String cid) {
		// TODO Auto-generated method stub
		return categoryMapper.findPartentByCid(cid);
	}
	
	@Override
	public List<Category> findChildren(String pid) {
		// TODO Auto-generated method stub
		return categoryMapper.findByParent(pid);
	}

	
}
