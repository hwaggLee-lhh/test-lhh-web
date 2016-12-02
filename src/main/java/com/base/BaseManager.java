package com.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;

/**此类不仅仅是为了Manager类的方便使用，在特定场景下是为了在ServiceImpl中能够直接调用。
 */
@SuppressWarnings("all")
public abstract class BaseManager<T> {

	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
    /**统一的ID的属性名称：id*/
    public static final String ID_NAME = "idStr";
    
    /**日志对象*/
    protected final Logger log = Logger.getLogger(getClass());

    /**通过子类实现这个方法，得到Model的类型
     * @return Model的类型
     */
    public abstract Class<T> getModelClass();
    
    /**得到当前处理Model的class的simpleName
     * @return clazz.getSimpleName()
     * @see java.lang.Class#getSimpleName()
     */
    public String getSimpleClassName() {
        return ClassUtils.getShortClassName(getModelClass());
    }

	public Session getCurrentSession(){  
        return sessionFactory.getCurrentSession();  
    } 
    public void flush() {
    	getCurrentSession().flush();
    }
    
    /**得到一个初始化好的DetachedCriteria查询对象
     * @return DetachedCriteria查询对象
     * @see org.hibernate.criterion.DetachedCriteria#forClass(Class)
     */
    public DetachedCriteria getDetachedCriteria() {
        return DetachedCriteria.forClass(getModelClass());
    }
    
    /**根据hql得到一个查询对象
     * @param hql hql
     * @return Query
     * @see org.hibernate.Session#createQuery()
     */
    public Query getQuery(String hql) {
        return getCurrentSession().createQuery(hql);
    }
    
    /**得到一个Criteria查询对象
     * @return Criteria查询对象
     * @see org.hibernate.Session#getCriteria()
     */
    public Criteria getCriteria() {
        return getCurrentSession().createCriteria(getModelClass());
    }
    
    /**查询Model对应的表中的所有数据，测试专用
     * @return List<Model>
     */
    public List<T> findList() {
    	return getCriteria().list();
    }
    
    /**查询Model对应的表中的所有数据，测试专用
     * @param orderByName 排序字段
     * @return List<Model>
     */
    public List<T> findList(String orderByName) {
        DetachedCriteria dc = DetachedCriteria.forClass(getModelClass());
        dc.addOrder(Order.asc(orderByName));
        return findList(dc);
    }
    
    /**根据指定查询对象，查找数据库中的数据，如果符合条件的个数超过1个抛异常
     * @param dc 查询对象
     * @return Model
     * @see org.hibernate.Criteria#uniqueResult()
     */
    public Object findUniqueResult(DetachedCriteria dc) {
        return (Object)dc.getExecutableCriteria(getCurrentSession()).uniqueResult();
    }
    
    /**根据指定查询对象，查找数据库中的数据，取得第一个对象，collect为空的话返回null
     * @param dc 查询对象
     * @return Model
     */
    public T findUniqueResultWithOutException(DetachedCriteria dc) {
        return (T)dc.getExecutableCriteria(getCurrentSession()).setMaxResults(1).uniqueResult();
    }
    
    /**根据指定查询对象，查找数据库中的数据
     * @param dc 查询对象
     * @return List<Model>
     * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(DetachedCriteria)
     */
    public List<T> findList(DetachedCriteria dc) {
    	return dc.getExecutableCriteria(getCurrentSession()).list();
    }
    
    /**根据指定查询对象，查找数据库中的数据，带分页参数
     * @param dc 查询对象
     * @param firstResult 第一条记录的位置
     * @param maxResults 最大记录数
     * @return List<Model>
     * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(DetachedCriteria, int, int)
     */
    public List<T> findList(DetachedCriteria dc, int firstResult, int maxResults) {
    	return dc.getExecutableCriteria(getCurrentSession()).setMaxResults(maxResults).setFirstResult(firstResult).list();
    }
    
    /**根据指定查询对象，查找数据库中的数据，带分页参数
     * @param dc 查询对象
     * @param firstResult 第一条记录的位置
     * @param maxResults 最大记录数
     * @return List<Model>
     * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(DetachedCriteria, int, int)
     */
    public List<T> findList(Criteria criteria, int firstResult, int maxResults) {
        criteria.setFirstResult(firstResult);
        criteria.setMaxResults(maxResults);
        return criteria.list();
    }
    
    /**添加一个对象
     * @param entity 需要添加的对象，添加后该游离对象的引用变成实体对象的引用
     * @return model包含了已有的idStr
     * @see org.springframework.orm.hibernate3.HibernateTemplate#save(Object)
     */
    public T save(T entity) {
    	getCurrentSession().save(entity);
        return entity;
    }
    
    /**根据对象id删除对象
     * @param id 对象id
     */
    public void deleteByIdStr(String id) {
        List<String> ids = new LinkedList<String>();
        ids.add(id);
        deleteByIdList(ids);
    }
    
    /**
     * 根据对象id列表删除对象列表
     * @param idList 对象id列表
     * @return 删除的个数
     */
    public void deleteByIdList(Collection<String> idList) {
        if (idList == null || idList.size() == 0) {
            return;
        }
        for (String id : idList) {
        	delete(findById(id));
		}
    }
    /**删除一个实体对象，如果能够事先得到对象id的话，推荐deleteById方法
     * @param entity
     * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(Object)
     */
    public void delete(Object entity) {
    	getCurrentSession().delete(entity);
    }
    
    /**删除一个Collection集合
     * @param entity
     * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(Object)
     */
    private void deleteAll(Collection<T> entities) {  
        if (entities == null)  
            return;  
        for (Object entity : entities) {  
            getCurrentSession().delete(entity);  
        }  
    }  
    
    /**更新一个持久化对象，如果只需要更新部分属性的话，推荐updateByParam方法
     * 例：更新一个字段为null，当然数据中该字段允许为空
     * User user = (User) userDao.findObjectById("1");
     * user.setName(null);
     * userDao.update(user);
     * @param entity 需要更新的持久化对象
     * @see org.springframework.orm.hibernate3.HibernateTemplate#update(Object)
     */
    public void update(T entity) {
    	getCurrentSession().update(entity);
    }
    
    /**根据指定id查询对象，如果该id的数据在数据库中不存在，返回null，测试专用
     * 与loadById区别为返回结果为持久化对象
     * @param id 指定id
     * @return model
     * @see org.springframework.orm.hibernate3.HibernateTemplate#load(Class, java.io.Serializable)
     */
    public T findById(Serializable id) {
    	T t = (T)getCurrentSession().get(getModelClass(), id);
    	if(t!=null) getCurrentSession().evict(t);
    	return t;
    }
    /**根据指定id查询对象，如果该id的数据在数据库中不存在，返回null
     * 与findById区别为返回结果为持久化对象
     * @param id 指定id
     * @return model
     * @see org.springframework.orm.hibernate3.HibernateTemplate#load(Class, java.io.Serializable)
     */
    public T loadById(Serializable id) {
    	return (T)getCurrentSession().get(getModelClass(), id);
    }
    
    public Query createQuery(String hql, Collection values) {
        return createQuery(hql, values.toArray());
    }
    
    /**创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置.
     * @param values Object[].
     * @return Query
     */
    public Query createQuery(String hql, Object[] values) {
        Assert.hasText(hql);
        if (values == null) {
            values = new Object[] {};
        }
        Query query = getQuery(hql);
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }
    
    /**取得对象的主键名
     * @return idName
     */
    public String getIdName() {
        Class clazz = getModelClass();
        ClassMetadata meta = sessionFactory.getClassMetadata(clazz);
        Assert.notNull(meta, "Class " + clazz.getName() + " not define in hibernate session factory.");
        String idName = meta.getIdentifierPropertyName();
        Assert.hasText(idName, clazz.getName() + " has no identifier property define.");
        return idName;
    }
    
    
    public List findListByHql(String hql) {
    	return getCurrentSession().createQuery(hql).list();
    }
    
    
    

    public Page<T> findPage(Criteria criteria, PageParam pageParam)
    {
        Assert.notNull(criteria);
        Assert.notNull(pageParam);
        CriteriaImpl impl = (CriteriaImpl)criteria;
        Projection projection = impl.getProjection();
        List orderEntries = (List)ModelUtils.forceGetProperty(impl, "orderEntries");
        ModelUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
        criteria.setProjection(Projections.rowCount());
        int totalProperty = ((Long)criteria.uniqueResult()).intValue();
        criteria.setProjection(projection);
        if(projection == null)
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        if(StringUtils.isNotEmpty(pageParam.getSort()))
        {
            if(pageParam.getDir().equals("ASC"))
                criteria.addOrder(Order.asc(pageParam.getSort()));
            else
                criteria.addOrder(Order.desc(pageParam.getSort()));
        } else
        {
            ModelUtils.forceSetProperty(impl, "orderEntries", orderEntries);
        }
        criteria.setFirstResult(pageParam.getStart());
        criteria.setMaxResults(pageParam.getLimit());
        List<T> list = criteria.list();
        return new Page(totalProperty, list);
    }

    public Page<T> findPage(DetachedCriteria dc, PageParam pageParam)
    {
        return findPage(dc.getExecutableCriteria(getCurrentSession()), pageParam);
    }

    public Page<T> findPageByUniquery(Criteria criteria, PageParam pageParam)
    {
        Assert.notNull(criteria);
        Assert.notNull(pageParam);
        CriteriaImpl impl = (CriteriaImpl)criteria;
        List orderEntries = (List)ModelUtils.forceGetProperty(impl, "orderEntries");
        int totalProperty = criteria.list().size();
        if(StringUtils.isNotEmpty(pageParam.getSort()))
        {
            if(pageParam.getDir().equals("ASC"))
                criteria.addOrder(Order.asc(pageParam.getSort()));
            else
                criteria.addOrder(Order.desc(pageParam.getSort()));
        } else
        {
            ModelUtils.forceSetProperty(impl, "orderEntries", orderEntries);
        }
        criteria.setFirstResult(pageParam.getStart());
        criteria.setMaxResults(pageParam.getLimit());
        List list = criteria.list();
        return new Page(totalProperty, list);
    }

    public Page<T> findPageByUniquery(DetachedCriteria dc, PageParam pageParam)
    {
        return findPage(dc.getExecutableCriteria(getCurrentSession()), pageParam);
    }

}
