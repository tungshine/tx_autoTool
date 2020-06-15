package com.tanglover.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:处理基本的JDBC方法
 * Description:描述
 * Copyright: Copyright (c) 2014
 * Company: rumtel Technology Chengdu Co. Ltd.
 *
 * @author 敬小虎
 * @version 1.0 2014-7-18
 */
@Repository("baseDao")
public class BaseDao {
    Logger log = LoggerFactory.getLogger(BaseDao.class);

    /*BasicDataSource ds;
    public BasicDataSource getDs() {
        return ds;
    }*/
/*	@Resource(name="dataSource")
	DynamicDataSource ds = null;
	
	public DynamicDataSource getDs() {
		return ds;
	}
	*//*DruidDataSource ds;
	public DruidDataSource getDs() {
		return ds;
	}*/
    //定义JDBC
    @Resource(name = "_np")
    public NamedParameterJdbcTemplate _np;

    SimpleDateFormat sdfMm = new SimpleDateFormat("yyyyMM");

    SimpleDateFormat sdfDd = new SimpleDateFormat("yyyyMMdd");

    public NamedParameterJdbcTemplate getJdbc() {
        return _np;
    }

    /**
     * String sql="select s_name as sname from stu where s_name=:sname";
     * 泛型查询
     */
    public <T> T getdatas(Class<T> beanclass, String sql) {
        try {
            //String sql="select s_name as sname from stu where s_name='jingxiaohu' limit 1";
            List<T> list = this._np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<T>(beanclass));
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            log.error("BaseDao getdatas", e);
        }
        return null;
    }


    /**
     * Map<String, Object> paramMap = new HashMap<String, Object>();
     * paramMap.put("deptIds", l);
     * String sql = "select z.dept_id,z.sid from z_docs_catalog z "
     * + "where z.state = 1 and z.flag = '000'and z.dept_id in (:deptIds)";
     * List<Map<String,Object>> result = namedParameterJdbcTemplate.queryForList(sql, paramMap);
     *
     * @param @param  sql
     * @param @param  paramMap
     * @param @return 设定文件
     * @return List<Map                               <                               String                               ,                               Object>>    返回类型
     * @throws
     * @Title: queryForList
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
        try {
            return this._np.queryForList(sql, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseDao queryForList", e);
            return null;
        }
    }


    public Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        try {
            return this._np.queryForMap(sql, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseDao queryForList", e);
            return null;
        }
    }

    /**
     * 不靠主键的查询
     *
     * @param KeyValue          键值
     * @param KeyName           键名
     * @param selectByOther_SQL
     * @return
     */
    public List<Map<String, Object>> selectByOther(Object KeyValue, String KeyName, String selectByOther_SQL) {
        try {
            selectByOther_SQL += String.format("%s = :%s", KeyName, KeyName);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put(KeyName, KeyValue);
            return this._np.queryForList(selectByOther_SQL, params);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseDao selectByOther", e);
            return null;
        }
    }

    /**
     * 依靠 参数进行更新
     *
     * @param sql
     * @param paramMap
     * @return
     */
    public int updateBySQL(String sql, Map<String, Object> paramMap) throws Exception {
        //String sql ="UPDATE user SET password = :password WHERE account = :account";
        return this._np.update(sql, paramMap);
    }

    /**
     * 直接写SQL查询的语句
     *
     * @param <T>
     * @param sql
     * @param cls
     * @return
     */
    public <T> List<T> executeQuery(String sql, Class<T> cls, Object... args) {
        try {
            return this._np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<T>(cls), args);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseDao executeQuery", e);
            return null;
        }
    }

    /**
     * 直接写SQL查询的语句
     *
     * @param <T>
     * @param sql
     * @param cls
     * @return
     */
    public <T> T executeQueryUniqueT(String sql, Class<T> cls, Object... args) {
        try {
            List<T> data = this._np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<T>(cls), args);
            return (data == null || data.size() == 0) ? null : data.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("BaseDao executeQuery", e);
            return null;
        }
    }


    /**
     * 直接SQL执行
     *
     * @param sql
     */
    public void execute(String sql) throws Exception {
        this._np.getJdbcOperations().execute(sql);
    }

    /**
     * 直接SQL执行
     *
     * @param sql
     */
    public int update(String sql, Object... args) throws Exception {
        return this._np.getJdbcOperations().update(sql, args);
    }

    /************************扩展DAO*****************************/
    public <T> List<T> queryListT(String sql, Class<T> cla, Object... args)
            throws Exception {
        BeanPropertyRowMapper<T> ormHandler = new BeanPropertyRowMapper<T>(cla);
        return this._np.getJdbcOperations().query(sql, ormHandler, args);
    }

    public <T> T queryUniqueT(String sql, Class<T> cla, Object... args)
            throws Exception {
        List<T> data = queryListT(sql, cla, args);
        return (data == null || data.size() == 0) ? null : data.get(0);
    }
}
