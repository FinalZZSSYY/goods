package com.zsy.goods.mapper;

import com.zsy.goods.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * 分类模块持久层
 * @author ASUS
 *
 */
public interface CategoryMapper {
	
	/**
	 * 通过父分类查询子分类
	 */
	List<Category> findByParent(@Param("pid") String pid);
	
	/**
	 * 返回所有一级分类 
	 */
	List<Category> findAllFirst();
	
	/**
	 * 添加分类 
	 */
	Integer add(@Param("category")Category category);
	
	
	/**
	 * 通过父分类查询子分类
	 */
	Category findParent(@Param("pid")String pid);
	
	
	/**
	 * 加载分类
	 * 即可加载一级分类，也可加载二级分类
	 */
	Category load(@Param("cid")String cid);
	
	/**
	 * 修改分类
	 * 即可修改一级分类，也可修改二级分类
	 */
	Integer edit(@Param("category")Category category);
	
	/**
	 * 查询指定父分类下子分类的个数
	 */
	Integer findChildrenCountByParent(@Param("pid")String pid);
	
	/**
	 * 删除分类
	 */
	int delete(@Param("cid")String cid);
	
	/**
	 * 加载分类
	 * 即可加载一级分类，也可加载二级分类
	 */
	Category findPartentByCid(@Param("cid")String cid);
	
	
}
