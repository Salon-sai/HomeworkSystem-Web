package dao.impl;

import java.util.List;

import model.Information;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import dao.AbstractInfomationDAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Information entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see dao.impl.Information
 * @author MyEclipse Persistence Tools
 */

public class InformationDAOImpl extends AbstractInfomationDAO {
	private static final Logger log = LoggerFactory
			.getLogger(InformationDAOImpl.class);
	// property constants
	
	protected void initDao() {
		// do nothing
	}

	public void save(Information transientInstance) {
		log.debug("saving Information instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Information persistentInstance) {
		log.debug("deleting Information instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Information findById(java.io.Serializable id) {
		log.debug("getting Information instance with id: " + id);
		try {
			Information instance = (Information) getHibernateTemplate().get(
					Information.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<?> findByExample(Information instance) {
		log.debug("finding Information instance by example");
		try {
			List<?> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List<?> findByProperty(String propertyName, Object value) {
		log.debug("finding Information instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Information as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<?> findByName(Object name) {
		return findByProperty(NAME, name);
	}
	

	public List<?> findAll() {
		log.debug("finding all Information instances");
		try {
			String queryString = "from Information";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Information merge(Information detachedInstance) {
		log.debug("merging Information instance");
		try {
			Information result = (Information) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Information instance) {
		log.debug("attaching dirty Information instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Information instance) {
		log.debug("attaching clean Information instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InformationDAOImpl getFromApplicationContext(
			ApplicationContext ctx) {
		return (InformationDAOImpl) ctx.getBean("InformationDAO");
	}
}